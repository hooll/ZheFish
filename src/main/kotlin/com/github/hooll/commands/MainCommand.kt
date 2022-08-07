package com.github.hooll.commands

import com.github.hooll.api.ZheFishApi
import com.github.hooll.commands.subCommands.*
import com.github.hooll.util.warn
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.platform.util.onlinePlayers

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

    @CommandBody(permission = "ZheFish.sell")
    val sell = CommandSell.command


    @CommandBody(permission = "ZheFish.Info")
    val info = subCommand {
        execute<Player> { sender, _, _ ->
            val level = ZheFishApi.getPlayerData(sender)?.level
            val exp = ZheFishApi.getPlayerData(sender)?.exp
            val nextLevelExp = ZheFishApi.getPlayerData(sender)?.nextLevelExp
            sender.sendMessage("§a等级: $level §a经验: $exp §a下一等级经验: $nextLevelExp")

        }
        //玩家
        dynamic("player") {
            suggestion<ProxyCommandSender> { _, _ ->  onlinePlayers.map { it.displayName } }
            //查看特定玩家的等级
            execute<ProxyCommandSender> { sender, _, argument ->
                val player = ZheFishApi.getPlayerData(argument)
                if (player != null) {
                    sender.sendMessage("${player.player.displayName} 的等级是 ${player.level} 经验是 ${player.exp} 下一等级经验是 ${player.nextLevelExp}")
                } else {
                    sender.sendMessage("没有找到该玩家")
                }
            }
        }
    }
}