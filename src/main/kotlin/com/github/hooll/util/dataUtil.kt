package com.github.hooll.util

import com.github.hooll.api.data.PlayerData
import com.github.hooll.core.NextLevelExp
import org.bukkit.entity.Player
import taboolib.common5.Coerce
import taboolib.expansion.getDataContainer
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

fun Player.toDataByDataBase(): PlayerData {
    releaseDataContainer()
    setupDataContainer()
    val level = Coerce.toInteger(getDataContainer()["level"]?: 0)
    val exp = Coerce.toDouble(getDataContainer()["exp"]?: 0.0)
    val nextLevelExp = NextLevelExp.getExpToNextLevel(level)
    return PlayerData(player!!, level, exp, nextLevelExp)
}
