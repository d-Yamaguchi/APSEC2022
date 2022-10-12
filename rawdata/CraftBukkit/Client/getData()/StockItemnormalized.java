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
    int _CVAR0 = 0;
    boolean _CVAR1 = item.getData().getData() != _CVAR0;
     _CVAR2 = (!net.dandielo.citizens.traders_v3.utils.ItemUtils.itemHasDurability(item)) && _CVAR1;
    if () {
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