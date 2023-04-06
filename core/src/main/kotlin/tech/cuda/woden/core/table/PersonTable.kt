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
package tech.cuda.woden.core.table

import org.ktorm.schema.*

/**
 * 用户基础信息表
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object PersonTable : Table<Nothing>("person") {
    /**
     * 用户ID，主键
     */
    val personId = long("person_id").primaryKey()

    /**
     * 用户登录名, 唯一键, 只读字段, 即一旦创建则不允许修改
     */
    val loginName = varchar("login_name")

    /**
     * 登录密码
      */
    val password = varchar("password")

    /**
     * 用户邮箱
     */
    val email = varchar("person_email")

    /**
     * 是否删除
     */
    val isRemove = boolean("is_remove")

    /**
     * 创建时间
     */
    val createTime = datetime("create_time")

    /**
     * 更新时间
     */
    val updateTime = datetime("update_time")
}