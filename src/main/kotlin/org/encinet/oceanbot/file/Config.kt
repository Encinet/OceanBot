package org.encinet.oceanbot.file

import org.bukkit.configuration.file.FileConfiguration
import org.encinet.oceanbot.OceanBot

object Config {
    fun config(): FileConfiguration {
        return OceanBot.plugin!!.config
    }

    var ver = 0
    var commandPrefix: List<String>? = null
    var BotID: Long? = null
    var BotNick: String? = null
    var GroupID: List<Long>? = null
    var MainGroup: Long? = null
    var numMessage: MutableMap<Int, List<String>?>? = null
    var noWhiteKick: String? = null
    var chatPrefix: List<String>? = null
    var serverToQQ: String? = null
    var join: String? = null
    var admin: List<Long>? = null
    var recallEnable = false
    var recallMuteValue = 0
    var recallMuteTime = 0
    var recallText: List<String>? = null
    fun load() {
        OceanBot.plugin!!.reloadConfig()
        ver = config().getInt("ver", 3)
        commandPrefix = config().getStringList("prefix")
        BotID = config().getLong("BotID")
        BotNick = config().getString("BotNick", "Bot")
        GroupID = config().getLongList("GroupID")
        MainGroup = config().getLong("MainGroup")
        numMessage = HashMap()
        val nums = config().getMapList("NumMessage")
        for (map in nums) {
            val num = map["num"] as Int
            val messages = map["text"] as List<String>
            (numMessage as HashMap<Int, List<String>?>)[num] = messages
        }
        noWhiteKick = config().getString("noWhiteKick")
        join = config().getString("join")
        chatPrefix = config().getStringList("chat.prefix")
        serverToQQ = config().getString("chat.format.server-to-qq")
        admin = config().getLongList("admin")
        recallEnable = config().getBoolean("recall.enable", true)
        recallMuteValue = config().getInt("recall.mute.value", 3)
        recallMuteTime = config().getInt("recall.mute.time", 120)
        recallText = config().getStringList("recall.text")
    }
}