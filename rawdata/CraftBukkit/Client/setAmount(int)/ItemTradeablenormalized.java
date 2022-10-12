@java.lang.Override
public <T extends me.josvth.trade.goods.Tradeable> T add(T tradeable) {
    if (!(tradeable instanceof me.josvth.trade.goods.ItemTradeable)) {
        return tradeable;
    }
    me.josvth.trade.goods.ItemTradeable remaining = ((me.josvth.trade.goods.ItemTradeable) (tradeable)).clone();
    if (getItem().isSimilar(remaining.getItem())) {
        org.bukkit.inventory.ItemStack _CVAR1 = remaining.getItem();
        int _CVAR2 = _CVAR1.getAmount();
        int remainingAmount = (getItem().getMaxStackSize() - getItem().getAmount()) - _CVAR2;
        if (remainingAmount <= 0) {
            return null;
        } else {
            org.bukkit.inventory.ItemStack _CVAR0 = remaining.getItem();
            int _CVAR3 = remainingAmount;
            _CVAR0.setAmount(_CVAR3);
            return ((T) (remaining));
        }
    }
    return tradeable;
}