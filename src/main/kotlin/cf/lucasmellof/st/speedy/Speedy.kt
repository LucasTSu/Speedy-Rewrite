package cf.lucasmellof.st.speedy

import cf.lucasmellof.st.speedy.core.config.instances.Config
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
class Speedy(var config: Config) {
    lateinit var jda: JDA
    fun load() {
        println(" > Loading Speedy")
        println(" - Initializing JDA")
        jda = JDABuilder.createDefault(config.token)
            .setAutoReconnect(true)
            .build()
            .awaitReady()
    }
}