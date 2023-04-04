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

import com.zaxxer.hikari.HikariConfig
import java.util.*

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
class DataSourceConfig(props: Properties) {
    private val username = props.getProperty("MYSQL_USER_NAME", "root")
    private val password = props.getProperty("MYSQL_PASSWORD", "root")
    private val host = props.getProperty("MYSQL_HOST", "localhost")
    private val port = props.getProperty("MYSQL_PORT", "3306")
    private val dbName = props.getProperty("MYSQL_DB_NAME", "woden")
    private val minIdle = props.getProperty("MYSQL_MIN_IDLE", "10").toInt()
    private val maxPoolSize = props.getProperty("MYSQL_MAX_POOL_SIZE", "10").toInt()

    val hikariConfig = HikariConfig().also {
        it.jdbcUrl = "jdbc:mysql://$host:$port/$dbName?characterEncoding=UTF-8&useAffectedRows=true"
        it.poolName = "WodenService"
        it.username = username
        it.password = password
        it.minimumIdle = minIdle
        it.maximumPoolSize = maxPoolSize
    }
}
