package com.github.hooll

import com.github.hooll.util.info
import taboolib.common.io.newFile
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.*
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin

object ZheFish : Plugin() {

    @Config("setting.yml", migrate = true)
    lateinit var set:Configuration

    val plugin by lazy{ BukkitPlugin.getInstance() }

    override fun onEnable() {
        info("Successfully running ZheFish!")
        dataBaseInit()
    }

    fun dataBaseInit() {
        try {
            val type = set.getString("DataBase.Type")!!
            val host = set.getString("DataBase.Host")!!
            val database = set.getString("DataBase.Database")!!
            val user = set.getString("DataBase.User")!!
            val password = set.getString("DataBase.Password")!!
            val port = set.getInt("DataBase.Port")
            val table = "${pluginId.lowercase()}_data"
            if (type.contains("MySQL", true)) {
                setupPlayerDatabase(host, port, user, password, database, table)
                console().info("Info-Database-Enabled", "MySQL")
            } else {
                setupPlayerDatabase(newFile(getDataFolder(), "data//levelData.db"))
                console().info("Info-Database-Enabled", "SQLite")
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            disablePlugin()
            return
        }
    }
}