package com.github.hooll.util

import com.github.hooll.ZheFish
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.chat.colored
import taboolib.module.configuration.util.getMap

object Config {
    val set = ZheFish.set
    var fishGuiTitle:String = ""
    var rodGuiTitle:String= ""
    var notUseTheRod: MutableMap<String,Int> = mutableMapOf()

    @Awake(LifeCycle.ENABLE)
    fun configReload(){
        set.reload()
        fishGuiTitle = set.getString("Gui.FishGuiTitle")?:"&a物品列表"
        rodGuiTitle = set.getString("Gui.RodGuiTitle")?:"&a鱼竿列表"
        notUseTheRod = set.getMap<String,Int>("NotUseTheRod").toMutableMap()
    }
}