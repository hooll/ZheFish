package com.github.hooll.commands

import com.github.hooll.commands.subCommands.*
import com.github.hooll.warn
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand

@CommandHeader("ZheFish", aliases = ["zFish","zf"] , permissionDefault = PermissionDefault.TRUE)
object MainCommand {

    @CommandBody
    val main = mainCommand {
        incorrectCommand { sender, _, _, _ ->
            sender.warn("Warn-CommandError")
        }
        incorrectSender { sender, _ ->
            sender.warn("Warn-CommandSenderError")
        }
        execute<ProxyCommandSender> { sender, _, _ ->
            CommandHelp.helpMessage(sender)
        }
    }

    @CommandBody
    val fish = CommandFish.command

    @CommandBody
    val rod = CommandRod.command

    @CommandBody(permission = "ZheFish.reload")
    val reload = CommandReload.command

    @CommandBody(permission = "ZheFish.sell")
    val sell = CommandSell.command

    @CommandBody
    val help = CommandHelp.command
}