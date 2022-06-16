package com.github.hooll.data

import org.bukkit.inventory.ItemStack

class RodData(
    val name: String,
    val itemStack: ItemStack,
    var fishingData: MutableMap<String,Int> = mutableMapOf()
)