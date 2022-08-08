package com.github.hooll.gui

import com.github.hooll.api.ZheFishApi
import com.github.hooll.api.data.FishData
import com.github.hooll.config.Config
import com.github.hooll.config.DataYML
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.*

class FishGui(
    private val player: Player
    )
{
    init {
        openFishUi()
    }
    private fun openFishUi() {
        player.openMenu<Linked<FishData>>(title = Config.fishGuiTitle.colored()) {
            rows(6)
            slots(Slots.CENTER)
            elements { ZheFishApi.fishes }
            onGenerate { player, element, _, _ ->
                var lore = ""
                val itemStacks = element.getItem()
                if (player.hasPermission("ZheFish.give")) {
                    lore += "${player.asLangText("Info-GuiItemGetLore")} "
                }
                if (player.hasPermission("ZheFish.remove")) {
                    lore += "${player.asLangText("Info-GuiItemRemoveLore")} "
                }
                return@onGenerate itemStacks.modifyLore {
                    add(" ")
                    add(lore.colored())
                    add(player.asLangText("Info-ItemNameLore", element.name))
                    add(player.asLangText("Info-ItemPriceLore", element.price))
                }
            }
            onClick { event, element ->
                if (!event.clickEvent().isShiftClick) {
                    if (event.clickEvent().isLeftClick) {
                        if (player.hasPermission("ZheFish.give")) {
                            player.giveItem(element.getItem())
                        }
                    } else if (event.clickEvent().isRightClick) {
                        if (player.hasPermission("ZheFish.remove")) {
                            ZheFishApi.fishes.remove(element)
                            DataYML.save()
                            submit(delay = 1) {
                                openFishUi()
                            }
                        }
                    }
                }
            }
            setNextPage(51) { _, hasNextPage ->
                if (hasNextPage) {
                    buildItem(XMaterial.SPECTRAL_ARROW) {
                        name = "&f下一页".colored()
                    }
                } else {
                    buildItem(XMaterial.ARROW) {
                        name = "&f下一页".colored()
                    }
                }
            }
            setPreviousPage(47) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    buildItem(XMaterial.SPECTRAL_ARROW) {
                        name = "&f上一页".colored()
                    }
                } else {
                    buildItem(XMaterial.ARROW) {
                        name = "&f上一页".colored()
                    }
                }
            }
        }
    }
}