package com.github.hooll.commands.subCommands

import com.github.hooll.ZheFish
import com.github.hooll.api.ZheFishApi
import com.github.hooll.data.DataYml
import com.github.hooll.data.FishData
import com.github.hooll.gui.FishGui
import com.github.hooll.info
import com.github.hooll.warn
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandContext
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common5.Coerce
import taboolib.platform.util.giveItem
import taboolib.platform.util.inputBook
import taboolib.platform.util.isAir

object CommandFish {
    val command = subCommand {
        //添加物品
        literal("add", permission = "ZheFish.add"){
            dynamic(commit = "物品名称") {
                suggestion<Player>(uncheck = true) { _, _ -> listOf("物品名称")  }
                execute<Player> { sender, _, argument ->
                    if (sender.inventory.itemInMainHand.isAir()){
                        sender.warn("Warn-MainHandAir")
                        return@execute
                    }
                    ZheFishApi.fishes.add(FishData(argument, sender.inventory.itemInMainHand))
                    DataYml.save()
                    sender.info("Info-AddSuccess",argument)
                }
            }
        }
        //删除物品
        literal("remove", permission = "ZheFish.remove"){
            dynamic(commit = "物品名称") {
                suggestion<Player>(uncheck = true) { _, _ -> ZheFishApi.fishes.map { it.name } }
                execute<Player> { sender, _, argument ->
                    ZheFishApi.fishes.remove(ZheFishApi.getFish(argument))
                    DataYml.save()
                    sender.info("Info-RemoveSuccess",argument)
                }
            }
        }
        //删除所有物品
        literal("removeAll", permission = "ZheFish.removeAll"){
            execute<Player> { sender, _, _ ->
                ZheFishApi.fishes.clear()
                DataYml.save()
                sender.info("Info-RemoveAllSuccess")
            }
        }
        //列举物品
        literal("list", permission = "ZheFish.list"){
            execute<Player> { sender, _, _ ->
                var listName = ""
                ZheFishApi.fishes.forEach {
                    listName += "${it.name}, "
                }
                sender.info("Info-ListInfo",listName)
            }
        }
        //打开gui
        literal("gui", permission = "ZheFish.gui"){
            execute<Player> { sender, _, _ ->
                FishGui(sender)
            }
        }
        //给物品
        literal("give", permission = "ZheFish.give"){
            dynamic(commit = "玩家名字") {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> Bukkit.getOnlinePlayers().map { it.name } }
                dynamic(commit = "物品名字") {
                    suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> ZheFishApi.fishes.map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val player = getProxyPlayer(context.argument(-1))?.cast<Player>()!!
                        if (player.isOnline) {
                            player.giveItem(ZheFishApi.getFishItemStack(argument)!!)
                            sender.info("Info-GiveSuccess",context.argument(-1),argument)
                        }else{
                            sender.info("Warn-PlayerNotOnline",context.argument(-1))
                        }
                    }
                }
            }
        }
        //修改物品售价
        literal("price", permission = "ZheFish.price"){
            dynamic(commit = "物品名字") {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> ZheFishApi.fishes.map { it.name } }
                dynamic(commit = "售价") {
                    suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> listOf("售价") }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        ZheFishApi.setPrice(context.argument(-1),Coerce.toDouble(argument))
                        sender.info("Info-ChangePriceSuccess",argument)
                    }
                }
            }
        }
    }
}