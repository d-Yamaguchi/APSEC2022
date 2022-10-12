/**
 * Ensure that the given stack can store arbitrary NBT information.
 *
 * @param stack
 * 		- the stack to check.
 */
private static void checkItemStack(org.bukkit.inventory.ItemStack stack) {
    if (stack == null) {
        throw new java.lang.IllegalArgumentException("Stack cannot be NULL.");
    }
    if (!com.scottwoodward.rpitems.items.NbtFactory.get().CRAFT_STACK.isAssignableFrom(stack.getClass())) {
        throw new java.lang.IllegalArgumentException("Stack must be a CraftItemStack.");
    }
    int _CVAR0 = 0;
    boolean _CVAR1 = stack.getTypeId() == _CVAR0;
    if () {
        throw new java.lang.IllegalArgumentException("ItemStacks representing air cannot store NMS information.");
    }
}