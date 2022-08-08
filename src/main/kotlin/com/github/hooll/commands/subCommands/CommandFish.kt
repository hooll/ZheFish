package com.github.hooll.commands.subCommands

import com.github.hooll.api.ZheFishApi
import com.github.hooll.api.data.FishData
import com.github.hooll.config.DataYML
import com.github.hooll.gui.FishGui
import com.github.hooll.util.info
import com.github.hooll.util.warn
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.getProxyPlayer
import taboolib.platform.util.giveItem
import taboolib.platform.util.isAir

object CommandFish {
    val command = subCommand {
        //添加物品
        literal("add"){
            dynamic(commit = "value") {
                suggestion<Player>(uncheck = true) { _, _ -> listOf("物品名称")  }
                execute<Player> { sender, _, argument ->
                    if (sender.inventory.itemInMainHand.isAir()){
                        sender.warn("Warn-MainHandAir")
                        return@execute
                    }
                    ZheFishApi.fishes.add(FishData(argument, sender.inventory.itemInMainHand))
                    DataYML.save()
                    sender.info("Info-AddSuccess",argument)
                }
            }
        }
        //删除物品
        literal("remove"){
            dynamic(commit = "value") {
                suggestion<Player>(uncheck = true) { _, _ -> ZheFishApi.fishes.map { it.name } }
                execute<Player> { sender, _, argument ->
                    ZheFishApi.fishes.remove(ZheFishApi.getFish(argument))
                    DataYML.save()
                    sender.info("Info-RemoveSuccess",argument)
                }
            }
        }
        //删除所有物品
        literal("removeAll"){
            execute<Player> { sender, _, _ ->
                ZheFishApi.fishes.clear()
                DataYML.save()
                sender.info("Info-RemoveAllSuccess")
            }
        }
        //列举物品
        literal("list"){
            execute<Player> { sender, _, _ ->
                var listName = ""
                ZheFishApi.fishes.forEach {
                    listName += "${it.name}, "
                }
                sender.info("Info-ListInfo",listName)
            }
        }
        //打开gui
        literal("gui"){
            execute<Player> { sender, _, _ ->
                FishGui(sender)
            }
        }
        //给物品
        literal("give", permission = "ZheFish.give"){
            dynamic(commit = "player") {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> Bukkit.getOnlinePlayers().map { it.name } }
                dynamic(commit = "value") {
                    suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> ZheFishApi.fishes.map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val player = getProxyPlayer(context.argument(-1))?.cast<Player>()!!
                        if (player.isOnline) {
                            ZheFishApi.getFish(argument)?.getItem()?.let { player.giveItem(it) }
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