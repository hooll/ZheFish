package com.github.hooll.commands

import com.github.hooll.commands.subCommands.CommandFish
import com.github.hooll.commands.subCommands.CommandReload
import com.github.hooll.commands.subCommands.CommandRod
import com.github.hooll.commands.subCommands.CommandSell
import com.github.hooll.warn
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.module.lang.sendWarnMessage

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
    }

    @CommandBody
    val fish = CommandFish.command

    @CommandBody
    val rod = CommandRod.command

    @CommandBody
    val reload = CommandReload.command

    @CommandBody
    val sell = CommandSell.command

}