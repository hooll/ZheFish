package com.github.hooll.commands

import com.github.hooll.commands.subCommands.*
import com.github.hooll.util.warn
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader("ZheFish", aliases = ["zFish","zf"] , permissionDefault = PermissionDefault.TRUE)
object MainCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
        incorrectCommand { sender, _, _, _ ->
            sender.warn("Warn-CommandError")
        }
        incorrectSender { sender, _ ->
            sender.warn("Warn-CommandSenderError")
        }
    }

    @CommandBody(permission = "ZheFish.fish")
    val fish = CommandFish.command

    @CommandBody(permission = "ZheFish.level")
    val level = CommandLevel.command

    @CommandBody(permission = "ZheFish.exp")
    val exp = CommandExp.command

    @CommandBody(permission = "ZheFish.reload")
    val reload = CommandReload.command

    @CommandBody(permission = "ZheFish.sell", permissionDefault = PermissionDefault.TRUE)
    val sell = CommandSell.command


    @CommandBody(permission = "ZheFish.Info", permissionDefault = PermissionDefault.TRUE)
    val info = CommandInfo.command
}