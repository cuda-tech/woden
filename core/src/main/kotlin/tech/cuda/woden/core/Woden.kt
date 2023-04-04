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
package tech.cuda.woden.core

import com.google.common.io.Resources
import com.zaxxer.hikari.HikariDataSource
import org.ktorm.database.Database
import tech.cuda.woden.core.config.DataSourceConfig
import tech.cuda.woden.core.config.EmailConfig
import tech.cuda.woden.core.config.SchedulerConfig
import java.util.*
import javax.sql.DataSource

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
@Suppress("UnstableApiUsage")
object Woden {

    private val props = Properties().also {
        it.load(Resources.getResource("woden.properties").openStream())
    }

    private val dsConf = DataSourceConfig(props)

    val ds: DataSource by lazy { HikariDataSource(dsConf.hikariConfig) }

    /**
     * 数据库连接池
     */
    val database by lazy { Database.connect(ds) }

    val email = EmailConfig(props)

    val scheduler = SchedulerConfig(props)
}
