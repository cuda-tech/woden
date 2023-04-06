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
import tech.cuda.woden.core.table.ctype.PersonRole

/**
 * 用户与项目组映射表
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object Person2ProjectTable : Table<Nothing>("person_project_relation") {
    /**
     * 项目组 ID
     */
    val projectId = long("project_id")

    /**
     * 用户 ID
     */
    val personId = long("person_id")

    /**
     * 用户在项目组中的角色
     */
    val personRole = enum<PersonRole>("person_role")

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