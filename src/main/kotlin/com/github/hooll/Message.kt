package com.github.hooll

import org.bukkit.command.CommandSender
import taboolib.common.platform.ProxyCommandSender
import taboolib.module.lang.sendInfo
import taboolib.module.lang.sendWarn
import taboolib.platform.util.sendInfo
import taboolib.platform.util.sendWarn


fun CommandSender.info(lang_key: String, vararg args: Any) {
    sendInfo(lang_key, *args)
}
fun CommandSender.warn(lang_key: String, vararg args: Any){
    sendWarn(lang_key, *args)
}
fun ProxyCommandSender.info(lang_key: String, vararg args: Any) {
    sendInfo(lang_key, *args)
}
fun ProxyCommandSender.warn(lang_key: String, vararg args: Any){
    sendWarn(lang_key, *args)
}