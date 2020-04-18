package cf.lucasmellof.st.speedy.core.commands

import cf.lucasmellof.st.speedy.Speedy
import org.reflections.Reflections

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
class Registry {
    companion object {
        val commands = mutableListOf<Command>()
        var aliases = HashMap<String, Command>()
        fun getCommandByName(name: String): Command? = commands.find { name in it.aliases || name == it.name }
    }

    fun registerCommandsByReflections() {
        var reflections = Reflections("cf.lucasmellof.st.speedy")
        reflections.getSubTypesOf(Command::class.java).forEach {
            registerCommand(it.newInstance())
        }
    }

    @Throws(Exception::class)
    fun registerCommand(cmd: Command): Boolean {
        val duplicate = commands.find { it.name == cmd.name }
        if (duplicate != null) throw Exception("A commands with the name ${cmd.name} already exists")
        Speedy.logger.info("O comando ${cmd.name} foi carregado na classe ${cmd::class.simpleName}")
        cmd.aliases.forEach { aliases[it] = cmd }
        return commands.add(cmd)
    }

}