package cf.lucasmellof.st.speedy

import cf.lucasmellof.st.speedy.core.config.instances.Config
import cf.lucasmellof.st.speedy.core.config.loadConfig
import cf.lucasmellof.st.speedy.enums.ExitStatus
import cf.lucasmellof.st.speedy.utils.printCoolLogo
import kotlin.system.exitProcess

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
object SpeedyLauncher {
    lateinit var config: Config
    lateinit var speedy: Speedy
    var underDevelopment = true
    @JvmStatic
    fun main(args: Array<String>) {
        config = loadConfig(Config::class)!!
        printCoolLogo()
        if (config.token == "<change-me>") {
            println("> Set up config before starting the bot!")
            exitProcess(ExitStatus.CONFIG_MISSING.code)
        }
        speedy = Speedy(config)
        speedy.load()
    }

}