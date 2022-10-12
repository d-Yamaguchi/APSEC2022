public static void setHealth(org.bukkit.entity.Player player, int health) {
    if (mc.alk.arena.controllers.HeroesController.hasHeroes) {
        try {
            mc.alk.arena.util.HeroesUtil.setHealth(player, health);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    } else {
        org.bukkit.entity.Player _CVAR0 = player;
        int _CVAR1 = health;
        _CVAR0.setHealth(_CVAR1);
    }
}