package com.github.hooll.commands.subCommands

import com.github.hooll.data.DataYml
import com.github.hooll.info
import com.github.hooll.util.Config
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.sendInfo

object CommandReload {
    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            DataYml.reload()
            Config.configReload()
            sender.info("Info-ReloadSuccess")
        }
    }
}