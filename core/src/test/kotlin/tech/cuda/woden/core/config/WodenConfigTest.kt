package tech.cuda.woden.core.config

import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldStartWith
import tech.cuda.woden.core.DataSourceMocker
import tech.cuda.woden.core.Woden


class WodenConfigTest : StringSpec({
    "datasource config" {
        DataSourceMocker.mock()
        val datasource = Woden.ds as HikariDataSource
        datasource.jdbcUrl shouldStartWith "jdbc:mysql://localhost:"
        datasource.jdbcUrl shouldEndWith "/test?characterEncoding=UTF-8"
        datasource.username shouldBe "root"
        datasource.password shouldBe null
        datasource.isAutoCommit shouldBe true
        datasource.connectionTimeout shouldBe 30000
        datasource.idleTimeout shouldBe 600000
        datasource.maxLifetime shouldBe 1800000
        datasource.connectionTestQuery shouldBe null
        datasource.minimumIdle shouldBe 10
        datasource.maximumPoolSize shouldBe 20
        datasource.metricRegistry shouldBe null
        datasource.healthCheckRegistry shouldBe null
        datasource.initializationFailTimeout shouldBe 1
        datasource.isIsolateInternalQueries shouldBe false
        datasource.isAllowPoolSuspension shouldBe false
        datasource.isReadOnly shouldBe false
        datasource.isRegisterMbeans shouldBe false
        datasource.catalog shouldBe null
        datasource.connectionInitSql shouldBe null
        datasource.driverClassName shouldBe null
        datasource.validationTimeout shouldBe 5000
        datasource.leakDetectionThreshold shouldBe 0
        datasource.threadFactory shouldBe null
        datasource.scheduledExecutor shouldBe null
        DataSourceMocker.unMock()
    }

    "email config" {
        Woden.email.host shouldBe "localhost"
        Woden.email.sender shouldBe "root@host.com"
        Woden.email.password shouldBe "root"
        Woden.email.port shouldBe 3465
    }

    "scheduler config" {
        Woden.scheduler.role shouldBe "master"
        Woden.scheduler.pythonPath shouldBe "/usr/bin/python3"
        Woden.scheduler.bashPath shouldBe "/bin/bash"
        Woden.scheduler.sparkHome shouldBe ""
    }

})
