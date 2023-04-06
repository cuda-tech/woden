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

import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import org.ktorm.dsl.*
import tech.cuda.woden.core.Woden
import tech.cuda.woden.core.table.ProjectTable
import tech.cuda.woden.core.table.ctype.PersonRole
import tech.cuda.woden.core.table.Person2ProjectTable
import tech.cuda.woden.core.table.PersonTable
import tech.cuda.woden.core.entity.PersonEntity

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object ProjectDAO {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * 创建名为 [name] 的项目组，并返回项目组 ID
     */
    fun createProject(name: String) : Long {
        val now = LocalDateTime.now()
        // todo: 工作区根目录
        return Woden.database.insertAndGenerateKey(ProjectTable) {
            set(it.projectName, name)
            set(it.isRemove, false)
            set(it.createTime, now)
            set(it.updateTime, now)
        } as Long
    }

    /**
     * 移除项目组 [projectId]
     */
    fun removeProject(projectId: Long) {

    }

    /**
     * 更新项目组 [projectId] 的名称为 [name]
     */
    fun rename(projectId: Long, name: String) {

    }

    /**
     * 通过 [projectId] 获取项目组详情
     */
    fun getByProjectId(projectId: Long) {

    }

    /**
     * 获取 [projectId] 下的所有 User
     */
    fun listPerson(projectId: Long): List<PersonEntity> {
        val personIdList = Woden.database.from(Person2ProjectTable)
            .select(Person2ProjectTable.personId)
            .where {
                (Person2ProjectTable.projectId eq projectId) and
                    (Person2ProjectTable.isRemove eq false)
            }
            .map { it[Person2ProjectTable.personId] as Long }
        if (personIdList.isEmpty()) {
            return emptyList()
        }

        return Woden.database.from(PersonTable)
            .select(
                PersonTable.personId,
                PersonTable.loginName,
                PersonTable.email,
                PersonTable.createTime,
                PersonTable.updateTime
            )
            .where {
                PersonTable.personId.inList(personIdList)
            }
            .map {
                PersonEntity(
                    id = it[PersonTable.personId]!!,
                    name = it[PersonTable.loginName]!!,
                    email = it[PersonTable.email]!!,
                    createTime = it[PersonTable.createTime]!!,
                    updateTime = it[PersonTable.updateTime]!!,
                )
            }
    }


    /**
     * 将用户 [personId] 以 [role] 权限加入项目组 [projectId]
     */
    fun includePerson(projectId: Long, personId: Long, role: PersonRole) {

    }

    /**
     * 将用户 [personId] 移除出项目组 [projectId]
     */
    fun excludePerson(projectId: Long, personId: Long) {

    }


}