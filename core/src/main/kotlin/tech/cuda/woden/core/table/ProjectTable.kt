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
 * 项目组详情表
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object ProjectTable : Table<Nothing>("project") {
    /**
     * 项目组ID，唯一主键
     */
    val projectId = long("project_id").primaryKey()

    /**
     * 项目组名称
     */
    val projectName = varchar("project_name")

    /**
     * 项目组工作区根节点
     */
    val workingTreeRoot = long("working_tree_root")

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