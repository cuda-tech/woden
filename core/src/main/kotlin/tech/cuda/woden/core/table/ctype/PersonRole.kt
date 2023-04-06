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
package tech.cuda.woden.core.table.ctype

/**
 * 用户在项目组中的角色
 *
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
enum class PersonRole {
    ADMIN, // 管理员, 可以执行增减成员, 文件读写, 更新项目组信息等造作
    DEV, // 开发人员, 可以文件读写
    GUEST // 访客, 只能读取文件
}