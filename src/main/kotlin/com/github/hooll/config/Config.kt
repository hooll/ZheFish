package com.github.hooll.config

import com.github.hooll.ZheFish
import com.github.hooll.core.FishItem
import com.github.hooll.core.LevelReward
import com.github.hooll.core.NextLevelExp
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.util.getMap

object Config {
    val set = ZheFish.set
    var fishGuiTitle:String = ""
    var default_NextLevelExp = 0.0
    var default_FishExp = 0.0


    @Awake(LifeCycle.ENABLE)
    fun configReload(){
        set.reload()
        fishGuiTitle = set.getString("Options.FishGuiTitle")?:"&a物品列表"
        default_NextLevelExp = set.getDouble("Options.Default-NextLevelExp")
        default_FishExp = set.getDouble("Options.Default-FishExp")

        NextLevelExp.nextLevelExp.clear()
        NextLevelExp.nextLevelExp = set.getMap<String,Double>("Custom.ExpToNextLevel").toMutableMap()

        LevelReward.levelReward.clear()
        set.getConfigurationSection("Custom")?.getConfigurationSection("LevelReward")?.getKeys(false)?.forEach {
            LevelReward.levelReward[it] = set.getStringList("Custom.LevelReward.$it")
        }

        FishItem.fishItems.clear()
        set.getConfigurationSection("Custom")?.getConfigurationSection("LevelFishItems")?.getKeys(false)?.forEach {
            FishItem.fishItems[it] = set.getStringList("Custom.LevelFishItems.$it")
        }
    }
}