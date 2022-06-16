package com.github.hooll.commands.subCommands

import com.github.hooll.ZheFish
import com.github.hooll.api.ZheFishApi
import com.github.hooll.data.DataYml
import com.github.hooll.data.FishData
import com.github.hooll.data.RodData
import com.github.hooll.gui.FishGui
import com.github.hooll.gui.RodGui
import com.github.hooll.info
import com.github.hooll.warn
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.getProxyPlayer
import taboolib.platform.util.giveItem
import taboolib.platform.util.isAir
import taboolib.platform.util.sendWarn

object CommandRod {
    val command = subCommand {
        //添加物品
        literal("add", permission = "ZheFish.add"){
            dynamic(commit = "鱼竿名称") {
                suggestion<Player>(uncheck = true) { _, _ -> listOf("鱼竿名称")  }
                execute<Player> { sender, _, argument ->
                    if (sender.inventory.itemInMainHand.isAir()){
                        sender.warn("Warn-MainHandAir")
                        return@execute
                    }
                    ZheFishApi.rods.add(RodData(argument, sender.inventory.itemInMainHand))
                    DataYml.save()
                    sender.info("Info-AddSuccess",argument)
                }
            }
        }
        //删除物品
        literal("remove",permission = "ZheFish.remove"){
            dynamic(commit = "鱼竿名称") {
                suggestion<Player>(uncheck = true) { _, _ -> ZheFishApi.rods.map { it.name }  }
                execute<Player> { sender, _, argument ->
                    ZheFishApi.rods.remove(ZheFishApi.getRod(argument))
                    DataYml.save()
                    sender.info("Info-RemoveSuccess",argument)
                }
            }
        }
        //删除所有物品
        literal("removeAll",permission = "ZheFish.removeAll"){
            execute<Player> { sender, _, argument ->
                ZheFishApi.rods.clear()
                DataYml.save()
                sender.info("Info-RemoveAllSuccess")
            }
        }
        //列举物品
        literal("list",permission = "ZheFish.list"){
            execute<Player> { sender, _, _ ->
                var listName = ""
                ZheFishApi.rods.forEach {
                    listName += "${it.name}, "
                }
                sender.info("Info-ListInfo",listName)
            }
        }
        //打开gui
        literal("gui",permission = "ZheFish.gui"){
            execute<Player> { sender, _, _ ->
                RodGui(sender)
            }
        }
        //给物品
        literal("give", permission = "ZheFish.give"){
            dynamic(commit = "玩家名字") {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> Bukkit.getOnlinePlayers().map { it.name } }
                dynamic(commit = "物品名字") {
                    suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> ZheFishApi.rods.map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val player = getProxyPlayer(context.argument(-1))?.cast<Player>()!!
                        if (player.isOnline) {
                            player.giveItem(ZheFishApi.getRodItemStack(argument)!!)
                            sender.info("Info-GiveSuccess",context.argument(-1),argument)
                        }else{
                            sender.info("Warn-PlayerNotOnline",context.argument(-1))
                        }
                    }
                }
            }
        }
    }
}