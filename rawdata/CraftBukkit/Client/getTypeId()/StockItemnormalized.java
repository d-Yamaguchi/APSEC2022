/**
 * Creates a string representation of the item, used for saving.
 * Only lores are saved in another way.
 *
 * @return prepared save string
 */
public java.lang.String save() {
    // result string
    java.lang.String result = "";
    // add id and data information
    result += item.getTypeId();
    if ((!net.dandielo.citizens.traders_v3.utils.ItemUtils.itemHasDurability(item)) && (item.getData().getData() != 0)) {
        result += ":" + item.getData().getData();
    }
    // save each attribute
    for (net.dandielo.citizens.traders_v3.utils.items.ItemAttr entry : attr.values()) {
        result += " " + entry.toString();
    }
    // remove abstract modifier
    net.dandielo.citizens.traders_v3.utils.items.ItemFlag abs = flags.remove(net.dandielo.citizens.traders_v3.utils.items.flags.Abstract.class);
    // save each flag
    for (net.dandielo.citizens.traders_v3.utils.items.ItemFlag flag : flags.values()) {
        result += " " + flag.getKey();
    }
    // put it back
    if (abs != null) {
        flags.put(net.dandielo.citizens.traders_v3.utils.items.flags.Abstract.class, abs);
    }
    // return the result
    return result;
}