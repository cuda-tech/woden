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
package tech.cuda.woden.scheduler.runner

import tech.cuda.woden.common.configuration.Woden
import java.io.File

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 * @since 0.1.0
 */
class SparkShellRunner(
    code: String,
    override val sparkConf: Map<String, String> = mapOf()
) : AbstractSparkRunner() {
    override val mainClass = "org.apache.spark.repl.Main"

    private val tempFile = File.createTempFile("__adhoc__", ".scala").also {
        // Spark-Shell 抛的异常貌似没法被 Throwable 捕获，所以在 try 里面正常退出，try 外面异常退出
        it.writeText("""
            |println(s""${'"'}Welcome to
            |      ____              __
            |     / __/__  ___ _____/ /__
            |    _\\ \\/ _ \\/ _ `/ __/  '_/
            |   /___/ .__/\\_,_/_/ /_/\\_\\   version ${"$"}{sc.version}
            |      /_/

            |Using Scala ${"$"}{util.Properties.versionString} (${"$"}{System.getProperty("java.vm.name")}, Java ${"$"}{System.getProperty("java.version")})
            |""${'"'})
            |try{
            |    $code
            |    System.exit(0)
            |}finally{}
            |System.exit(-1)
        """.trimMargin(), Charsets.UTF_8)
        it.deleteOnExit()
    }

    private val interactive = "-I".takeIf {
        File(Woden.scheduler.sparkHome, "jars").listFiles()
            ?.firstOrNull { it.name.startsWith("spark-core") }
            ?.name?.split("-")?.last()?.split(".")?.first() == "3"
    } ?: "-i"

    override val appArgs = listOf(interactive, tempFile.path, "-usejavacp")
}