package com.github.hooll.api.data

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import taboolib.module.chat.colored
import taboolib.platform.util.modifyMeta

class FishData(
    val name: String,
    val itemStack: ItemStack,
    var price: Double = 0.0,
    var canSell: Boolean = false
){
    fun getItem(): ItemStack {
        val item = itemStack.clone()
        item.modifyMeta<ItemMeta> {
            this.setDisplayName(itemStack.itemMeta?.displayName?.colored())
            lore = itemStack.itemMeta?.lore?.colored()
        }
        return item
    }

//    fun openMenu(player: Player){
//        player.openMenu<Basic>("&a物品编辑器".colored()) {
//            rows(3)
//            map(
//                "    I    ",
//                " A B C D ",
//               // " E F G H ",
//                "         ",
//            )
//
//            set('I', getItem())
//            set('A', XMaterial.NAME_TAG) {
//                name = " &f物品Name ".colored()
//                lore += " &f当前Name: &c${getItem().itemMeta?.displayName}".colored()
//                lore += " &f点击打开输入框 ".colored()
//            }
//            set('B', XMaterial.OAK_SIGN) {
//                name = " &f物品Lore ".colored()
//                lore += " &f当前Lore: ".colored()
//                getItem().itemMeta?.lore?.forEach {
//                    lore += " $it"
//                }
//                lore += " &f点击打开输入框 ".colored()
//            }
//            set('C',XMaterial.GOLD_INGOT){
//                name = " &f物品价格 ".colored()
//                lore += " &f当前价格: &c${price}".colored()
//                lore += " &f点击打开输入框 ".colored()
//            }
//            set('D',XMaterial.REDSTONE) {
//                name = " &f物品是否可出售 ".colored()
//                lore += " &f当前是否可出售: &c${canSell}".colored()
//                lore += " &f点击打开输入框 ".colored()
//            }
//            onClick('A'){
//                val name = ArrayList<String>()
//                name[0] = getItem().itemMeta?.displayName ?: ""
//                name[1] = " &f在上面输入物品名称 ".colored()
//                name[2] = " &f仅最上面间有效 ".colored()
//                player.closeInventory()
//                player.inputSign(name.toTypedArray()) {
//                    itemStack.itemMeta?.setDisplayName(it[0].colored())
//                    submit(delay = 1){
//                        openMenu(player)
//                    }
//                    return@inputSign
//                }
//            }
//            onClick('B'){
//                player.closeInventory()
//                player.inputBook("&f在下面输入物品Lore".colored(),true) {
//                    itemStack.itemMeta?.lore?.plusAssign(it.toMutableList().colored())
//                    submit(delay = 1){
//                        openMenu(player)
//                    }
//                    return@inputBook
//                }
//            }
//            onClick('C'){
//                val name = ArrayList<String>()
//                name[0] = "$price"
//                name[1] = " &f在下面输入物品价格 ".colored()
//                name[2] = " &f仅中间有效 ".colored()
//                player.inputSign(name.toTypedArray()) { list->
//                    if (list[0].toDouble() > 0 || list[0].toDouble() != price) {
//                        price = list[0].toDouble()
//                        price = Coerce.toDouble(list[0])
//                    }
//                    submit(delay = 1){
//                        openMenu(player)
//                    }
//                    return@inputSign
//                }
//            }
//            onClick('D'){
//                canSell = !canSell
//                submit(delay = 1){
//                    openMenu(player)
//                }
//            }
//        }
//    }
}
