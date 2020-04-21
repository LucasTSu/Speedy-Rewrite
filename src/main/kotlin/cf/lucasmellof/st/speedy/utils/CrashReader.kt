package cf.lucasmellof.st.speedy.utils

import cf.lucasmellof.st.speedy.core.config.configfolder
import cf.lucasmellof.st.speedy.utils.Utils.runAsync
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 19/04/2020
 */

//This system is based on Aladdin Bot system https://github.com/AladdinBOT/Aladdin
class CrashReader(var event: GuildMessageReceivedEvent) {
    private var auto_solve = "Eu não tenho uma resolução automatica para esse erro!"
    private var finallines = ""
    private var memory = ""
    private var minecraft_version = ""
    private var java_version = ""
    private var hastebin = "Não consegui enviar seu crash-report para o Hastebin!"
    fun checkIfIsCrash(): Boolean {
        if (!event.message.attachments.isNullOrEmpty()) {
            val attachment = event.message.attachments[0]
            if (attachment.fileName.contains("crash-") && attachment.fileName.contains("-client.txt")) {
                runAsync({
                    val file = File(configfolder, "crash-report")
                    if (file.exists().not()) file.mkdir()
                    val crash = File(file, "crash-${random.nextInt(Integer.MAX_VALUE)}-${event.author.name}.txt")
                    attachment.downloadToFile(crash)
                    event.channel.sendMessage("Analizando seu crash-report...(Estou em beta, então me desculpe pela demora)")
                        .queue {
                            it.editMessage(crashParser(crash, event)).queue()
                        }
                })
                return true
            }
            return false
        }
        return false
    }

    private fun crashParser(file: File, event: GuildMessageReceivedEvent): MessageEmbed {

        Thread.sleep(2000)
        try {
            lateinit var line: String
            Scanner(FileReader(file)).use {
                while (it.hasNextLine()) {
                    line = it.nextLine()
                    if (line.toLowerCase().contains("minecraft version:")) {
                        minecraft_version = line.replace("\tMinecraft Version: ", "")
                    }
                    if (line.toLowerCase().contains("java version:")) {
                        java_version = line.replace("\tJava Version: ", "")
                    }

                    if (line.toLowerCase().contains("memory:")) {
                        var fkMemory = line.replace("\tMemory: ", "")
                        val matcher: Matcher = Pattern.compile("(\\(.*?\\))").matcher(fkMemory)
                        while (matcher.find()) {
                            if (memory === "") {
                                memory = matcher.group().replace("(", "").replace(")", "")
                                fkMemory = fkMemory.replace(matcher.group(), "")
                                continue
                            }
                            memory = memory + " | " + matcher.group().replace("(", "").replace(")", "")
                            fkMemory = fkMemory.replace(matcher.group(), "")
                        }
                    }
                    if (finallines.toLowerCase().contains("pixel format not accelerated")) {
                        auto_solve =
                            "Os drivers da sua placa de vídeo podem estar desatualizados. Atualize-os e tente novamente — caso não funcione, sua placa de vídeo não possui suporte ao OpenGL requisitado."
                    }
                    if (finallines.toLowerCase().contains("already tesselating!")) {
                        auto_solve =
                            "Este erro ocorre quando algo sobrepõe uma renderização. Normalmente não é necessário fazer nada, apenas reabrir seu jogo. Caso o problema persista, contate os moderadores."
                    }
                    if (finallines.toLowerCase().contains("could not get provider type for dimension")) {
                        auto_solve =
                            "Você encontra-se em uma dimensão inválida.  Para resolver, peça para um administrador te mover ao spawn."
                    }
                    if (finallines.toLowerCase().contains("outofmemoryerror")) {
                        auto_solve =
                            "Seu jogo possui pouca memória alocada ($memory). Aumente essa quantidade e seu problema será resolvido."
                    }
                    if (finallines.toLowerCase().contains("IndexOutOfBoundsException".toLowerCase())) {
                        auto_solve =
                            "Este crash está relacionado a cliques em slots inválidos. Existem diversas causas, mas é geralmente resolvido só de abrir o jogo novamente."
                    }
                    finallines = """
                        $finallines
                        $line
                        """.trimIndent()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            file.delete()
        }
        if (finallines != null) {
            try {
                hastebin = sendToHastebin(finallines)!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        var embed = emptyEmbedBuilder(event.jda, event.author)
            .setThumbnail(getGithubAsset("crash-report"))
            .setTitle("Crash Reader")
            .setDescription("Crash-report de ${event.author.name}")
            .addField("Versão do Minecraft: ", minecraft_version, true)
            .addField("Solução automatica:", auto_solve, true)
            .addField("Memória(U/D/M): ", memory, true)
            .addField("Versão do Java: ", java_version, true)
            .addField("Hastebin: ", hastebin, true)
        return embed.build()
    }
}