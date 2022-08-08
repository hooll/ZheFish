package com.github.hooll.commands.subCommands

import com.github.hooll.api.ZheFishApi
import com.github.hooll.util.info
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.onlinePlayers

object CommandInfo {
    val command = subCommand {
        execute<Player> { sender, _, _ ->
            val level = ZheFishApi.getPlayerData(sender)?.level!!
            val exp = ZheFishApi.getPlayerData(sender)?.exp!!
            val nextLevelExp = ZheFishApi.getPlayerData(sender)?.nextLevelExp!!
            sender.info("Info-LevelExp-Info",level,exp,nextLevelExp)

        }
        //玩家
        dynamic("player") {
            suggestion<ProxyCommandSender> { _, _ -> onlinePlayers.map { it.displayName } }
            //查看特定玩家的等级
            execute<ProxyCommandSender> { sender, _, argument ->
                val player = ZheFishApi.getPlayerData(argument)
                if (player != null) {
                    sender.info("Info-Player-Info", player.level, player.exp, player.nextLevelExp)
                } else {
                    sender.info("Warn-PlayerNotOnline",argument)
                }
            }
        }
    }
}