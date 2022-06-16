package com.github.hooll

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin

object ZheFish : Plugin() {

    @Config("setting.yml", migrate = true)
    lateinit var set:Configuration

    val plugin by lazy{ BukkitPlugin.getInstance() }

    override fun onEnable() {
        info("Successfully running ZheFish!")
    }
}