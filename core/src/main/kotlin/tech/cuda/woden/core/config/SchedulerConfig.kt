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
package tech.cuda.woden.core.config

import java.util.Properties

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */

class SchedulerConfig(props: Properties) {
    val role: String = props.getProperty("SCHEDULER_ROLE", "master")
    val pythonPath: String = props.getProperty("PYTHONPATH", "/usr/bin/python3")
    val bashPath: String = props.getProperty("BASH_PATH", "/bin/bash")
    val sparkHome: String? = props.getProperty("SPARK_HOME", "")
}
