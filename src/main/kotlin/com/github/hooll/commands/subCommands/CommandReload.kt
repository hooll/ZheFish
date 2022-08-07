package com.github.hooll.commands.subCommands

import com.github.hooll.ZheFish
import com.github.hooll.config.Config
import com.github.hooll.config.DataYML
import com.github.hooll.util.info
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand

object CommandReload {
    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            DataYML.reload()
            Config.configReload()
            ZheFish.dataBaseInit()
            sender.info("Info-ReloadSuccess")
        }
    }
}