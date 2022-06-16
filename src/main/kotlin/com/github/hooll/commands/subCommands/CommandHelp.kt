package com.github.hooll.commands.subCommands

import com.github.hooll.info
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand

object CommandHelp {
    fun helpMessage(sender: ProxyCommandSender) {
        sender.info("Info-HelpMessage")
        if (sender.isOp) {
            sender.info("Info-AdminMessage")
        }
    }

    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            helpMessage(sender)
        }
    }
}