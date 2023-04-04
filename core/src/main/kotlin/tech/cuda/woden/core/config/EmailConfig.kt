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

import com.sun.mail.util.MailSSLSocketFactory
import java.util.*
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication

/**
 * @author Jensen Qi <jinxiu.qi@alu.hit.edu.cn>
 */
class EmailConfig(props: Properties) {

    val host: String = props.getProperty("EMAIL_HOST", "smtp.163.com")
    val sender: String = props.getProperty("EMAIL_SENDER", "someone@163.com")
    val password: String = props.getProperty("EMAIL_PASSWORD", "root")
    val port = props.getProperty("EMAIL_PORT", "465").toInt()

    val auth: Authenticator = object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(sender, password)
        }
    }

    val properties: Properties = Properties().also {
        it["mail.transport.protocol"] = "SMTP"
        it["mail.smtp.host"] = host
        it["mail.from"] = sender
        it["mail.smtp.socketFactory.port"] = port
        it["mail.smtp.port"] = port
        it["mail.smtp.auth"] = "true"
        it["mail.smtp.ssl.enable"] = true
        it["mail.smtp.ssl.socketFactory"] = MailSSLSocketFactory().also { sf -> sf.isTrustAllHosts = true }
    }


}
