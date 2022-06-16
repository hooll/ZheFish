package com.github.hooll.gui

import com.github.hooll.api.ZheFishApi
import com.github.hooll.data.DataYml
import com.github.hooll.data.FishData
import com.github.hooll.data.RodData
import com.github.hooll.util.Config
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.submit
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.*

class RodGui(
    private val player: Player
    )
{
    init {
        openRodUi()
    }

    private fun getRodItemStack(name: String): ItemStack?{
        return ZheFishApi.getRod(name)?.itemStack?.clone()
    }

    private fun openRodUi() {
        player.openMenu<Linked<RodData>>(title = Config.rodGuiTitle.colored()) {
            rows(6)
            slots(inventoryCenterSlots)
            elements { ZheFishApi.rods }
            onGenerate { player, element, _, _ ->
                var lore = ""
                val itemStacks = getRodItemStack(element.name)
                if (player.hasPermission("ZheFish.give")) {
                    lore += "${player.asLangText("Info-GuiItemGetLore")} "
                }
                if (player.hasPermission("ZheFish.remove")) {
                    lore += "${player.asLangText("Info-GuiItemRemoveLore")} "
                }
                return@onGenerate itemStacks?.modifyLore {
                    add(" ")
                    add(lore)
                    add(player.asLangText("Info-ItemNameLore",element.name))
                }!!
            }
            onClick { event, element ->
                if (event.clickEvent().isLeftClick) {
                    if (player.hasPermission("ZheFish.give")) {
                        player.giveItem(element.itemStack)
                    }
                } else if (event.clickEvent().isRightClick) {
                    if (player.hasPermission("ZheFish.remove")) {
                        ZheFishApi.rods.remove(element)
                        DataYml.save()
                        submit(delay = 1) {
                            openRodUi()
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