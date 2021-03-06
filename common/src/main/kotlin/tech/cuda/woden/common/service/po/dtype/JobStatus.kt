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
package tech.cuda.woden.common.service.po.dtype

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 * @since 0.1.0
 */
enum class JobStatus {
    INIT, // 新建状态
    READY, // 可执行状态
    RUNNING, // 执行中
    SUCCESS,
    FAILED, // 执行失败后，如果允许后续会自动重试
    KILLED; // Kill 后，后续不会自动重试

    fun canChangeTo(newStatus: JobStatus) = when (this) {
        INIT -> newStatus == READY
        READY -> newStatus == RUNNING
        RUNNING -> newStatus == SUCCESS || newStatus == FAILED || newStatus == KILLED
        SUCCESS -> newStatus == INIT // 重跑
        FAILED -> newStatus == INIT || newStatus == SUCCESS // 重跑或直接置成功
        KILLED -> newStatus == INIT || newStatus == SUCCESS // 重跑或直接置成功
    }
}