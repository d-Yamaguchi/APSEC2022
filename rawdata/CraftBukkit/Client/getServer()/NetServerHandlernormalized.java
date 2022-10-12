public void a(Packet9Respawn packet9respawn) {
    if (this.player.viewingCredits) {
        org.bukkit.Location toLocation;
        if (this.player.getBukkitEntity().getBedSpawnLocation() == null) {
            org.bukkit.craftbukkit.CraftWorld cworld = ((org.bukkit.craftbukkit.CraftWorld) (this.server.getWorlds().get(0)));
            ChunkCoordinates chunkcoordinates = cworld.getHandle().getSpawn();
            toLocation = new org.bukkit.Location(cworld, chunkcoordinates.x + 0.5, chunkcoordinates.y, chunkcoordinates.z + 0.5);
            this.player.netServerHandler.sendPacket(new Packet70Bed(0, 0));
        } else {
            toLocation = this.player.getBukkitEntity().getBedSpawnLocation();
            toLocation = new org.bukkit.Location(toLocation.getWorld(), toLocation.getX() + 0.5, toLocation.getY(), toLocation.getZ() + 0.5);
        }
        event.useTravelAgent(false);
        org.bukkit.Server _CVAR0 = org.bukkit.Bukkit.getServer();
        net.minecraft.server.EntityPlayer _CVAR2 = this.player;
        net.minecraft.server.EntityPlayer _CVAR4 = this.player;
         _CVAR5 = _CVAR4.getBukkitEntity();
        // CraftBukkit start
        PortalTravelAgent pta = new PortalTravelAgent();
         _CVAR3 = _CVAR2.getBukkitEntity();
         _CVAR6 = _CVAR5.getLocation();
        org.bukkit.Location _CVAR7 = toLocation;
        org.bukkit.craftbukkit.PortalTravelAgent _CVAR8 = pta;
        org.bukkit.event.player.PlayerTeleportEvent.TeleportCause _CVAR9 = org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.END_PORTAL;
        org.bukkit.event.player.PlayerPortalEvent event = new org.bukkit.event.player.PlayerPortalEvent(_CVAR3, _CVAR6, _CVAR7, _CVAR8, _CVAR9);
        org.bukkit.plugin.PluginManager _CVAR1 = _CVAR0.getPluginManager();
        org.bukkit.event.player.PlayerPortalEvent _CVAR10 = event;
        _CVAR1.callEvent(_CVAR10);
        this.player = this.minecraftServer.serverConfigurationManager.moveToWorld(this.player, 0, true, event.getTo());
        // CraftBukkit end
    } else {
        if (this.player.getHealth() > 0) {
            return;
        }
        this.player = this.minecraftServer.serverConfigurationManager.moveToWorld(this.player, 0, false);
    }
    // CraftBukkit start
    this.getPlayer().setHandle(this.player);
    this.player.viewingCredits = false;// allow the player to receive movement packets again.

    // CraftBukkit end
}