public void load(org.bukkit.plugin.Plugin plugin) {
    if (((plugin != null) && (this.wrapper == null)) && (this.pluginName != null)) {
        org.bukkit.plugin.PluginDescriptionFile _CVAR1 = pdf;
        java.lang.String _CVAR2 = _CVAR1.getName();
        java.lang.String _CVAR3 = this.pluginName;
        boolean _CVAR4 = _CVAR2.equalsIgnoreCase(_CVAR3);
        boolean _CVAR5 = this.pluginName.isEmpty() || _CVAR4;
        org.bukkit.plugin.Plugin _CVAR0 = plugin;
        org.bukkit.plugin.Plugin _CVAR7 = _CVAR0;
        org.bukkit.plugin.PluginDescriptionFile pdf = _CVAR7.getDescription();
        if () {
            boolean loaded = this.customLoad(plugin);
            if (!loaded) {
                org.bukkit.plugin.PluginDescriptionFile _CVAR8 = pdf;
                java.util.Map<java.lang.String, ? extends de.xzise.wrappers.Factory<W>> _CVAR6 = factories;
                java.lang.String _CVAR9 = _CVAR8.getName();
                de.xzise.wrappers.Factory<W> factory = _CVAR6.get(_CVAR9);
                if (factory != null) {
                    if (plugin.isEnabled()) {
                        try {
                            this.wrapper = factory.create(plugin, this.logger);
                            loaded = true;
                        } catch (de.xzise.wrappers.InvalidWrapperException e) {
                            this.logger.warning(((("Error while loading the plugin " + pdf.getFullName()) + " into ") + this.type) + " system.");
                            this.logger.warning("Error message: " + e.getMessage());
                            this.wrapper = null;
                        } catch (java.lang.Throwable e) {
                            this.logger.warning(((("Unspecified error while loading the plugin " + pdf.getFullName()) + " into ") + this.type) + " system.");
                            this.logger.warning(((("Error message: '" + e.getMessage()) + "' of '") + e.getClass().getSimpleName()) + "'");
                            this.wrapper = null;
                        }
                    } else {
                        this.logger.warning((("Skiped disabled " + this.type) + " system: ") + pdf.getFullName());
                    }
                }
            }
            if (loaded) {
                if (this.wrapper == null) {
                    this.logger.warning((("Invalid " + this.type) + " system found: ") + pdf.getFullName());
                } else {
                    this.loaded();
                    this.logger.info((("Linked with " + this.type) + " system: ") + pdf.getFullName());
                }
            }
        }
    }
}