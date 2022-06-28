package com.github.hooll.commands.subCommands

import com.github.hooll.api.ZheFishApi
import com.github.hooll.info
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.command.subCommand
import taboolib.platform.compat.VaultService
import taboolib.platform.util.isAir

object CommandSell {

    private fun ItemStack.isE(itemStack: ItemStack):Boolean{
        if (this.isAir || itemStack.isAir){
            return false
        }
        val a = this.clone()
        a.amount = 1
        val b = itemStack.clone()

        b.amount = 1
        return a == b
    }

    val command = subCommand {
        execute<Player> { sender, _, _ ->
            var money = 0.0
            ZheFishApi.fishes.toList().forEach { data->
                var num = 0
                if (data.canSell){
                    sender.inventory.contents.forEach {
                        if (it!=null&& it.isE(data.itemStack)){
                            num += it.amount
                            it.amount = 0
                        }
                    }
                    money += num * data.price
                }
            }
            VaultService.economy?.depositPlayer(sender,money)
            sender.info("Info-SellSuccess",money)
        }
    }
}