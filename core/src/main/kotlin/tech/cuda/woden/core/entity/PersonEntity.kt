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
package tech.cuda.woden.core.entity

import java.time.LocalDateTime

/**
 * 用户基础信息实体
 * 
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
data class PersonEntity(
    /**
     * 用户 ID
     */
    val id: Long,

    /**
     * 用户登录 & 展示名称
     */
    val name: String,

    /**
     * 用户邮箱
     */
    val email: String,

    /**
     * 创建时间
     */
    val createTime: LocalDateTime,

    /**
     * 更新时间
     */
    val updateTime: LocalDateTime
)