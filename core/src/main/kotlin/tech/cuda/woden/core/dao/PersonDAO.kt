/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.cuda.woden.core.dao

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import org.ktorm.dsl.*
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory
import tech.cuda.woden.core.Woden
import tech.cuda.woden.core.entity.PersonEntity
import tech.cuda.woden.core.table.Person2ProjectTable
import tech.cuda.woden.core.table.PersonTable
import java.time.LocalDateTime
import java.util.*

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object PersonDAO {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 创建用户, 并返回用户 ID
     */
    fun createPerson(loginName: String, password: String, email: String): Long {
        val now = LocalDateTime.now()
        return Woden.database.insertAndGenerateKey(PersonTable) {
            set(it.loginName, loginName)
            set(it.password, BCrypt.hashpw(password, BCrypt.gensalt()))
            set(it.email, email)
            set(it.isRemove, false)
            set(it.createTime, now)
            set(it.updateTime, now)
        } as Long
    }

    /**
     * 更新用户 [personId] 的基础信息
     * 如果提供了 [password], [email], [isRemove] 字段, 则更新对应的字段, 否则跳过
     * 
     * 如果更新成功, 则返回 true, 否则返回 false
     */
    fun updatePerson(
        personId: Long,
        password: String? = null,
        email: String? = null,
        isRemove: Boolean? = null
    ): Boolean {
        val effectedRowCount = Woden.database.update(PersonTable) { table ->
            password?.let { set(table.password, BCrypt.hashpw(password, BCrypt.gensalt())) }
            email?.let { set(table.email, email) }
            isRemove?.let { set(table.isRemove, isRemove) }
            set(table.updateTime, LocalDateTime.now())
            where {
                (table.personId eq personId) and (table.isRemove eq false)
            }
        }
        return effectedRowCount == 1
    }


    /**
     * 删除用户 [personId], 并清理用户与项目组的映射关系
     */
    fun removePerson(personId: Long) {
        Woden.database.useTransaction {
            updatePerson(personId, isRemove = true)
            Woden.database.update(Person2ProjectTable) {
                set(it.isRemove, true)
                set(it.updateTime, LocalDateTime.now())
                where { it.personId eq personId }
            }
        }
    }

    /**
     * 通过 [personId] 查询用户的基础信息
     * 
     * 如果 [personId] 存在且未删除, 则返回对应的 [PersonEntity] 实体
     * 否则返回 null
     */
    fun getByPersonId(personId: Long): PersonEntity? {
        return Woden.database.from(PersonTable)
            .select(PersonTable.loginName, PersonTable.email, PersonTable.createTime, PersonTable.updateTime)
            .where { (PersonTable.personId eq personId) and (PersonTable.isRemove eq false) }
            .map {
                PersonEntity(
                    id = personId,
                    name = it[PersonTable.loginName]!!,
                    email = it[PersonTable.email]!!,
                    createTime = it[PersonTable.createTime]!!,
                    updateTime = it[PersonTable.updateTime]!!,
                )
            }.firstOrNull()
    }

    /**
     * 获取 [personId] 归属的项目组
     */
    fun listProject(personId: Long) {

    }

    /**
     * 通过用户的登录名 [loginName] 获取对应的登录密码, 用于登录校验
     * 
     * 如果用户 [loginName] 存在且未删除, 则返回对应的密码
     * 否则返回 null
     */
    private fun getPassword(loginName: String): String? {
        return Woden.database.from(PersonTable)
            .select(PersonTable.password)
            .where { (PersonTable.loginName eq loginName) and (PersonTable.isRemove eq false) }
            .map { it[PersonTable.password] }
            .firstOrNull()
    }

    private const val EXPIRE_TIME = 86400000L // 默认 token 失效时间为 1 天
    private const val CLAIM = "person_name" // token 埋点字段

    /**
     * 生成用户登录 token
     * 
     * 如果用户 [loginName] 不存在, 则返回 null
     * 如果数据库中用户 [loginName] 的登录密码与提供的密码 [providePassword] 匹配,
     * 则返回生成的 token 签名, 在 token 中写入登录名和加密的密码信息, 否则返回 null
     */
    fun sign(loginName: String, providePassword: String): String? {
        val expectPassword = getPassword(loginName) ?: return null
        if (!BCrypt.checkpw(providePassword, expectPassword)) {
            return null
        }
        return try {
            JWT.create().withClaim(CLAIM, loginName)
                .withExpiresAt(Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(providePassword))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 验证 token 有效性
     * 
     * 根据 [token] 中记录的登录名与密码信息，与数据库中的密码信息做校验
     * 如果账号匹配则返回 true, 否则返回 false
     */
    fun verify(token: String): Boolean {
        // todo: 添加缓存
        val name = try {
            JWT.decode(token).getClaim(CLAIM).asString()
        } catch (e: JWTDecodeException) {
            return false
        }
        val password = getPassword(name) ?: return false
        return try {
            JWT.require(Algorithm.HMAC256(password))
                .withClaim(CLAIM, name)
                .build()
                .verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

}