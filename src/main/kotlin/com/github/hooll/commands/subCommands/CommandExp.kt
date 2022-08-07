package com.github.hooll.commands.subCommands

import com.github.hooll.api.ZheFishApi
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common5.Coerce
import taboolib.platform.util.onlinePlayers

object CommandExp {
    val command = subCommand {
        //添加等级
        literal("add"){
            //数值
            dynamic("value"){
                //给自己添加
                execute<Player> { sender, _, argument ->
                    val value = Coerce.toDouble(argument)
                    ZheFishApi.getPlayerData(sender)?.addExp(value)
                }
                //玩家
                dynamic("player") {
                    suggestion<ProxyCommandSender> { _, _ ->  onlinePlayers.map { it.displayName } }
                    //给特定玩家添加
                    execute<ProxyCommandSender> { _, context, argument ->
                        val value = Coerce.toDouble(context.argument(-1))
                        ZheFishApi.getPlayerData(argument)?.addExp(value)
                    }
                }
            }
        }
        //减少等级
        literal("take"){
            //数值
            dynamic("value"){
                //给自己添加
                execute<Player> { sender, _, argument ->
                    val value = Coerce.toDouble(argument)
                    ZheFishApi.getPlayerData(sender)?.takeExp(value)
                }
                //玩家
                dynamic("player") {
                    suggestion<ProxyCommandSender> { _, _ ->  onlinePlayers.map { it.displayName } }
                    //给特定玩家添加
                    execute<ProxyCommandSender> { _, context, argument ->
                        val value = Coerce.toDouble(context.argument(-1))
                        ZheFishApi.getPlayerData(argument)?.takeExp(value)
                    }
                }
            }
        }
        //设置等级
        literal("set"){
            //数值
            dynamic("value"){
                //给自己添加
                execute<Player> { sender, _, argument ->
                    val data = ZheFishApi.getPlayerData(sender)
                    val value = Coerce.toDouble(argument)
                    data?.setExp(value)
                }
                //玩家
                dynamic("player") {
                    suggestion<ProxyCommandSender> { _, _ ->  onlinePlayers.map { it.displayName } }
                    //给特定玩家添加
                    execute<ProxyCommandSender> { _, context, argument ->
                        val data = ZheFishApi.getPlayerData(argument)
                        val value = Coerce.toDouble(context.argument(-1))
                        data?.setExp(value)
                    }
                }
            }
        }
    }
}