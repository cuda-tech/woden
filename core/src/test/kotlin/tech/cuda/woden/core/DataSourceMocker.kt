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

import ch.vorburger.mariadb4j.DB
import ch.vorburger.mariadb4j.DBConfigurationBuilder
import com.zaxxer.hikari.HikariDataSource
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
object DataSourceMocker {

    fun mock() {
        val config = DBConfigurationBuilder.newBuilder().also {
            it.port = 0
            it.baseDir = System.getProperty("java.io.tmpdir") + "/" + this.javaClass.simpleName
            it.isDeletingTemporaryBaseAndDataDirsOnShutdown = true
        }.build()
        val db = DB.newEmbeddedDB(config).also { it.start() }
        mockkObject(Woden)
        every { Woden.ds } returns HikariDataSource().also { ds ->
            ds.jdbcUrl = "jdbc:mysql://localhost:${db.configuration.port}/test?characterEncoding=UTF-8"
            ds.username = "root"
            ds.minimumIdle = 10
            ds.maximumPoolSize = 20
        }
    }

    fun unMock() {
        unmockkObject(Woden)
    }
}