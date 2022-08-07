package com.github.hooll.api

import com.github.hooll.api.data.FishData
import com.github.hooll.api.data.PlayerData
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ZheFishApi {

    val fishes = ArrayList<FishData>()
    val players = ArrayList<PlayerData>()

    fun getFish(name: String): FishData? {
        return fishes.firstOrNull { it.name == name }
    }

    fun getFish(itemStack: ItemStack): FishData? {
        return fishes.firstOrNull { it.itemStack == itemStack }
    }

    fun getPlayerData(player: Player): PlayerData? {
        return players.firstOrNull { it.player == player }
    }

    fun getPlayerData(name: String): PlayerData? {
        return players.firstOrNull { it.player.displayName == name }
    }


}
