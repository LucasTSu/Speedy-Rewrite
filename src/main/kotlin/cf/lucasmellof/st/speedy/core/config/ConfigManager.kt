package cf.lucasmellof.st.speedy.core.config

import com.google.gson.GsonBuilder
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 17/04/2020
 */
val configfolder = File(System.getProperty("user.dir"), "data")
val gson = GsonBuilder().setPrettyPrinting().create()

inline fun <reified T : Any> loadConfig(clazz: KClass<*>): T? {
    val ann = clazz.findAnnotation<Config>() ?: return null
    configfolder.mkdirs()
    val configfile = File(configfolder, ann.name + ".json")
    if (!configfile.exists()) {
        val instance = clazz.createInstance() as T
        val json = gson.toJson(instance)
        configfile.writeText(json, StandardCharsets.UTF_8)
        return instance
    }
    return gson.fromJson(configfile.readText(StandardCharsets.UTF_8), T::class.java) as T
}
