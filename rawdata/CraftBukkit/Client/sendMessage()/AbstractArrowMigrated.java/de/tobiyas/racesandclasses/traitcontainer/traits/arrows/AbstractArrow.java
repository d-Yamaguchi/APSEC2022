package de.tobiyas.racesandclasses.traitcontainer.traits.arrows;
import de.tobiyas.racesandclasses.RacesAndClasses;
import de.tobiyas.racesandclasses.datacontainer.arrow.ArrowManager;
import de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.Trait;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.TraitEventsUsed;
public abstract class AbstractArrow extends de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait {
    protected de.tobiyas.racesandclasses.RacesAndClasses plugin = de.tobiyas.racesandclasses.RacesAndClasses.getPlugin();

    protected int duration;

    protected double totalDamage;

    protected int uplinkTime = 0;

    @java.lang.Override
    public boolean canBeTriggered(org.bukkit.event.Event event) {
        if (!((((event instanceof org.bukkit.event.player.PlayerInteractEvent) || (event instanceof org.bukkit.event.entity.EntityShootBowEvent)) || (event instanceof org.bukkit.event.entity.ProjectileHitEvent)) || (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent)))
            return false;

        // Change ArrowType
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            org.bukkit.event.player.PlayerInteractEvent Eevent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
            if (!((Eevent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (Eevent.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)))
                return false;

            org.bukkit.entity.Player player = Eevent.getPlayer();
            if (!isThisArrow(player))
                return false;

            if (!de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder.checkContainer(player.getName(), this))
                return false;

            if (player.getItemInHand().getType() != org.bukkit.Material.BOW)
                return false;

            return true;
        }
        // Projectile launch
        if (event instanceof org.bukkit.event.entity.EntityShootBowEvent) {
            org.bukkit.event.entity.EntityShootBowEvent Eevent = ((org.bukkit.event.entity.EntityShootBowEvent) (event));
            if (Eevent.getEntity().getType() != org.bukkit.entity.EntityType.PLAYER)
                return false;

            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (Eevent.getEntity()));
            if (!de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder.checkContainer(player.getName(), this))
                return false;

            if (!isThisArrow(player))
                return false;

            return true;
        }
        // Arrow Hit Location
        if (event instanceof org.bukkit.event.entity.ProjectileHitEvent) {
            org.bukkit.event.entity.ProjectileHitEvent Eevent = ((org.bukkit.event.entity.ProjectileHitEvent) (event));
            if (Eevent.getEntityType() != org.bukkit.entity.EntityType.ARROW)
                return false;

            if (Eevent.getEntity().getShooter() == null)
                return false;

            org.bukkit.entity.Arrow arrow = ((org.bukkit.entity.Arrow) (Eevent.getEntity()));
            if (arrow.getShooter().getType() != org.bukkit.entity.EntityType.PLAYER)
                return false;

            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (arrow.getShooter()));
            if (!de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder.checkContainer(player.getName(), this))
                return false;

            if (!isThisArrow(player))
                return false;

            return true;
        }
        // Arrow Hits target
        if (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
            org.bukkit.event.entity.EntityDamageByEntityEvent Eevent = ((org.bukkit.event.entity.EntityDamageByEntityEvent) (event));
            if (Eevent.getDamager().getType() != org.bukkit.entity.EntityType.ARROW)
                return false;

            org.bukkit.entity.Arrow realArrow = ((org.bukkit.entity.Arrow) (Eevent.getDamager()));
            org.bukkit.entity.Entity shooter = realArrow.getShooter();
            if (((shooter == null) || (realArrow == null)) || realArrow.isDead())
                return false;

            if (shooter.getType() != org.bukkit.entity.EntityType.PLAYER)
                return false;

            if ((Eevent.getEntity() == shooter) && (realArrow.getTicksLived() < 10))
                return false;

            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (shooter));
            if (!de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder.checkContainer(player.getName(), this))
                return false;

            if (!isThisArrow(player))
                return false;

            return true;
        }
        return false;
    }

    /**
     * Checks if the Arrow is active at the moment from the passed player
     *
     * @param player
     * 		to check
     * @return true if active, false if not.
     */
    private boolean isThisArrow(org.bukkit.entity.Player player) {
        de.tobiyas.racesandclasses.datacontainer.arrow.ArrowManager arrowManager = plugin.getPlayerManager().getArrowManagerOfPlayer(player.getName());
        de.tobiyas.racesandclasses.traitcontainer.traits.arrows.AbstractArrow arrow = arrowManager.getCurrentArrow();
        if ((arrow == null) || (arrow != this))
            return false;

        return true;
    }

    @java.lang.Override
    public boolean trigger(org.bukkit.event.Event event) {
        // Change ArrowType
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            org.bukkit.event.player.PlayerInteractEvent Eevent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
            changeArrowType(Eevent.getPlayer());
            return false;
        }
        // Projectile launch
        if (event instanceof org.bukkit.event.entity.EntityShootBowEvent) {
            org.bukkit.event.entity.EntityShootBowEvent Eevent = ((org.bukkit.event.entity.EntityShootBowEvent) (event));
            return onShoot(Eevent);
        }
        // Arrow Hit Location
        if (event instanceof org.bukkit.event.entity.ProjectileHitEvent) {
            org.bukkit.event.entity.ProjectileHitEvent Eevent = ((org.bukkit.event.entity.ProjectileHitEvent) (event));
            boolean change = onHitLocation(Eevent);
            return change;
        }
        // Arrow Hits target
        if (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
            org.bukkit.event.entity.EntityDamageByEntityEvent Eevent = ((org.bukkit.event.entity.EntityDamageByEntityEvent) (event));
            boolean change = onHitEntity(Eevent);
            Eevent.getDamager().remove();
            return change;
        }
        return false;
    }

    /**
     * Changes to the next arrow.
     */
    protected void changeArrowType(org.bukkit.entity.Player player) {
        de.tobiyas.racesandclasses.datacontainer.arrow.ArrowManager arrowManager = plugin.getPlayerManager().getArrowManagerOfPlayer(player.getName());
        de.tobiyas.racesandclasses.traitcontainer.traits.arrows.AbstractArrow arrow = arrowManager.getCurrentArrow();
        if ((arrow == null) || (arrow != this))
            return;

        de.tobiyas.racesandclasses.traitcontainer.traits.arrows.AbstractArrow newArrow = arrowManager.nextArrow();
        if ((newArrow != null) && (newArrow != arrow)) {
            send(com.titankingdoms.nodinchan.titanchat.TitanChat.MessageLevel.INFO, sender, "\"/titanchat commands [page]\" for command list");
        }
    }

    /**
     * This is called when a Player shoots an Arrow with this ArrowTrait present
     *
     * @param event
     * 		that was triggered
     * @return true if a cooldown should be triggered
     */
    protected abstract boolean onShoot(org.bukkit.event.entity.EntityShootBowEvent event);

    /**
     * This is triggered when the Player Hits an Entity with it's arrow
     *
     * @param event
     * 		that triggered the event
     * @return true if an Cooldown should be triggered
     */
    protected abstract boolean onHitEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event);

    /**
     * This is triggered when the Player hits an Location
     *
     * @param event
     * 		that triggered the event
     * @return true if an Cooldown should be triggered
     */
    protected abstract boolean onHitLocation(org.bukkit.event.entity.ProjectileHitEvent event);

    /**
     * Returns the name of the Arrow type
     *
     * @return 
     */
    protected abstract java.lang.String getArrowName();

    @java.lang.Override
    public boolean isBetterThan(de.tobiyas.racesandclasses.traitcontainer.interfaces.Trait trait) {
        if (trait.getClass() != this.getClass())
            return false;

        // TODO Not sure about this...
        return false;
    }

    @java.lang.Override
    public int getMaxUplinkTime() {
        return uplinkTime;
    }

    @java.lang.Override
    public boolean triggerButHasUplink(org.bukkit.event.Event event) {
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            changeArrowType(((org.bukkit.event.player.PlayerInteractEvent) (event)).getPlayer());
            return true;
        }
        if (event instanceof org.bukkit.event.entity.ProjectileHitEvent) {
            return true;
        }
        if (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public boolean isStackable() {
        return false;
    }

    @de.tobiyas.racesandclasses.traitcontainer.interfaces.TraitEventsUsed(registerdClasses = { org.bukkit.event.entity.EntityDamageByEntityEvent.class, org.bukkit.event.player.PlayerInteractEvent.class, org.bukkit.event.entity.EntityShootBowEvent.class, org.bukkit.event.entity.ProjectileHitEvent.class })
    @java.lang.Override
    public void generalInit() {
    }
}