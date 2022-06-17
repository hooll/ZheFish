package com.github.hooll

import org.bukkit.command.CommandSender
import taboolib.common.Isolated
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.function.pluginId
import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored
import taboolib.module.lang.asLangTextList
import taboolib.module.lang.asLangTextOrNull
import taboolib.platform.util.asLangTextList
import taboolib.platform.util.asLangTextOrNull


fun CommandSender.info(lang_key: String, vararg args: Any) {
    sendLang(Type.Info, lang_key, *args)
}
fun CommandSender.warn(lang_key: String, vararg args: Any){
    sendLang(Type.Warn, lang_key, *args)
}

fun ProxyCommandSender.info(lang_key: String, vararg args: Any) {
    sendLang(Type.Info, lang_key, *args)
}
fun ProxyCommandSender.warn(lang_key: String, vararg args: Any){
    sendLang(Type.Warn, lang_key, *args)
}

fun CommandSender.sendLang(type: Type, lang_key: String, vararg args: Any) {
    val prefix = asLangTextOrNull("${type.name}-Prefix") ?: asLangTextOrNull("Prefix") ?: "§c[$pluginId]"
    asLangTextList(lang_key, *args).forEach {
        val str = "$prefix &r${it}".replaceWithOrder(*args).colored()
        sendMessage(str)
    }
}

fun ProxyCommandSender.sendLang(type: Type, lang_key: String, vararg args: Any) {
    val prefix = asLangTextOrNull("${type.name}-Prefix") ?: asLangTextOrNull("Prefix") ?: "§c[$pluginId]"
    asLangTextList(lang_key, *args).forEach {
        val str = "$prefix &r${it}".replaceWithOrder(*args).colored()
        sendMessage(str)
    }
}

@Isolated
enum class Type {
    Info,Warn
}