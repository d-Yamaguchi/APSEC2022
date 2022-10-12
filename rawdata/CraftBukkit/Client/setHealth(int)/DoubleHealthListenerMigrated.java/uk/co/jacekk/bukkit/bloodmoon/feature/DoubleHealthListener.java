package uk.co.jacekk.bukkit.bloodmoon.feature;
import uk.co.jacekk.bukkit.baseplugin.v6.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.v6.event.BaseListener;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
public class DoubleHealthListener extends uk.co.jacekk.bukkit.baseplugin.v6.event.BaseListener<uk.co.jacekk.bukkit.bloodmoon.BloodMoon> {
    private java.util.ArrayList<org.bukkit.event.entity.EntityDamageEvent.DamageCause> playerCauses;

    public DoubleHealthListener(uk.co.jacekk.bukkit.bloodmoon.BloodMoon plugin) {
        super(plugin);
        this.playerCauses = new java.util.ArrayList<org.bukkit.event.entity.EntityDamageEvent.DamageCause>();
        this.playerCauses.add(org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        this.playerCauses.add(org.bukkit.event.entity.EntityDamageEvent.DamageCause.MAGIC);
        this.playerCauses.add(org.bukkit.event.entity.EntityDamageEvent.DamageCause.POISON);
        this.playerCauses.add(org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK);
        this.playerCauses.add(org.bukkit.event.entity.EntityDamageEvent.DamageCause.PROJECTILE);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamage(org.bukkit.event.entity.EntityDamageEvent event) {
        java.lang.String worldName = entity.getWorld().getName();
        org.bukkit.entity.Entity entity = event.getEntity();
        if (((entity instanceof org.bukkit.entity.Creature) && this.playerCauses.contains(event.getCause())) && plugin.isActive(worldName)) {
            uk.co.jacekk.bukkit.baseplugin.v6.config.PluginConfig worldConfig = plugin.getConfig(worldName);
            org.bukkit.entity.Creature creature = ((org.bukkit.entity.Creature) (entity));
            if (((creature.getTarget() instanceof org.bukkit.entity.Player) && worldConfig.getBoolean(Config.FEATURE_DOUBLE_HEALTH_ENABLED)) && worldConfig.getStringList(Config.FEATURE_DOUBLE_HEALTH_MOBS).contains(entity.getType().name().toUpperCase())) {
                event.setDamage(((int) (java.lang.Math.floor(event.getDamage() / 2.0F))));
                if (creature.getHealth() < 0) {
                    HeroesUtil.setHealthP(player, health);
                }
            }
        }
    }
}