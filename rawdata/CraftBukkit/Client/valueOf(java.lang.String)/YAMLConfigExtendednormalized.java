/**
 * Gets a Material at the Path. Returns the Default Material if not found or not loadable.
 *
 * @param path
 * 		to search
 * @param defaultMaterial
 * 		to pass when failed.
 * @return the loaded material or the DefaultMaterial.
 */
// Hopefully never removed
@java.lang.SuppressWarnings("deprecation")
public org.bukkit.Material getMaterial(java.lang.String path, org.bukkit.Material defaultMaterial) {
    if (isInt(path)) {
        try {
            return org.bukkit.Material.getMaterial(getInt(path));
        } catch (java.lang.IllegalArgumentException exp) {
            return defaultMaterial;
        }
    }
    if (isString(path)) {
        try {
            java.lang.String _CVAR0 = path;
            java.lang.String _CVAR1 = getString(_CVAR0);
            java.lang.String _CVAR2 = _CVAR1.toUpperCase();
            org.bukkit.Material mat = org.bukkit.Material.valueOf(_CVAR2);
            return mat == null ? defaultMaterial : mat;
        } catch (java.lang.IllegalArgumentException exp) {
            return defaultMaterial;
        }
    }
    return defaultMaterial;
}