package de.tobiyas.racesandclasses.traitcontainer.traits.magic;
import org.bukkit.event.player.PlayerInteractEvent;
import de.tobiyas.racesandclasses.APIs.LanguageAPI;
import de.tobiyas.racesandclasses.RacesAndClasses;
import de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.AbstractTraitHolder;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationField;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationNeeded;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitEventsUsed;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.markerinterfaces.MagicSpellTrait;
import de.tobiyas.racesandclasses.traitcontainer.interfaces.markerinterfaces.Trait;
import de.tobiyas.racesandclasses.util.traitutil.TraitConfigurationFailedException;
import de.tobiyas.util.naming.MCPrettyName;
import static de.tobiyas.racesandclasses.translation.languages.Keys.magic_change_spells;
import static de.tobiyas.racesandclasses.translation.languages.Keys.magic_dont_have_enough;
import static de.tobiyas.racesandclasses.translation.languages.Keys.magic_no_spells;
public abstract class AbstractMagicSpellTrait extends de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait implements de.tobiyas.racesandclasses.traitcontainer.interfaces.markerinterfaces.MagicSpellTrait {
    // static naming for YML elements
    public static final java.lang.String COST_TYPE_PATH = "costType";

    public static final java.lang.String COST_PATH = "cost";

    public static final java.lang.String ITEM_TYPE_PATH = "item";

    /**
     * This map is to prevent instant retriggers!
     */
    private static final java.util.Map<java.lang.String, java.lang.Long> lastCastMap = new java.util.HashMap<java.lang.String, java.lang.Long>();

    /**
     * The Cost of the Spell.
     *
     * It has the default Cost of 0.
     */
    protected double cost = 0;

    /**
     * The Material for casting with {@link CostType#ITEM}
     */
    protected org.bukkit.Material materialForCasting = org.bukkit.Material.FEATHER;

    /**
     * The CostType of the Spell.
     *
     * It has the Default CostType: {@link CostType#MANA}.
     */
    protected de.tobiyas.racesandclasses.traitcontainer.traits.magic.CostType costType = CostType.MANA;

    /**
     * The TraitHolder holding this trait.
     */
    protected de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.AbstractTraitHolder traitHolder;

    /**
     * The Plugin to call stuff on
     */
    protected final de.tobiyas.racesandclasses.RacesAndClasses plugin = de.tobiyas.racesandclasses.RacesAndClasses.getPlugin();

    @de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitEventsUsed(registerdClasses = { org.bukkit.event.player.PlayerInteractEvent.class })
    @java.lang.Override
    public void generalInit() {
        // We need nothing to be inited here.
    }

    @java.lang.Override
    public boolean canBeTriggered(org.bukkit.event.Event event) {
        if (canOtherEventBeTriggered(event))
            return true;

        if (!(event instanceof org.bukkit.event.player.PlayerInteractEvent))
            return false;

        org.bukkit.event.player.PlayerInteractEvent playerInteractEvent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
        org.bukkit.entity.Player player = playerInteractEvent.getPlayer();
        boolean playerHasWandInHand = checkWandInHand(player);
        // early out for not wand in hand.
        if (!playerHasWandInHand)
            return false;

        // check if the Spell is the current selected Spell
        if (this != plugin.getPlayerManager().getSpellManagerOfPlayer(player.getName()).getCurrentSpell())
            return false;

        return true;
    }

    /**
     * This is a pre-call to {@link #canBeTriggered(Event)}.
     * When returning true, true will be passed.
     *
     * @param event
     * 		that wants to be triggered
     * @return true if interested, false if not.
     */
    protected boolean canOtherEventBeTriggered(org.bukkit.event.Event event) {
        return false;
    }

    @java.lang.Override
    public boolean triggerButHasUplink(org.bukkit.event.Event event) {
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            org.bukkit.event.player.PlayerInteractEvent playerInteractEvent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
            Action action = isAir(event.getCurrentItem());
            org.bukkit.entity.Player player = playerInteractEvent.getPlayer();
            if ((action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                changeMagicSpell(player);
                return true;
            }
            if ((action == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (action == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
                boolean playerHasWandInHand = checkWandInHand(player);
                return playerHasWandInHand;
            }
        }
        return false;
    }

    /**
     * Checks if the Player has a Wand in his hand.
     *
     * @param player
     * 		to check
     * @return true if he has, false otherwise.
     */
    public boolean checkWandInHand(org.bukkit.entity.Player player) {
        return plugin.getPlayerManager().getSpellManagerOfPlayer(player.getName()).isWandItem(player.getItemInHand());
    }

    @java.lang.Override
    public void triggerButDoesNotHaveEnoghCostType(org.bukkit.entity.Player player, org.bukkit.event.Event event) {
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            org.bukkit.event.block.Action action = ((org.bukkit.event.player.PlayerInteractEvent) (event)).getAction();
            if ((action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                changeMagicSpell(player);
                return;
            }
        }
        java.lang.String costTypeString = getCostType().name();
        if (getCostType() == CostType.ITEM) {
            costTypeString = de.tobiyas.util.naming.MCPrettyName.getPrettyName(getCastMaterialType(), ((short) (0)), "en_US");
        }
        de.tobiyas.racesandclasses.APIs.LanguageAPI.sendTranslatedMessage(player, magic_dont_have_enough, "cost_type", costTypeString, "trait_name", getDisplayName());
    }

    @java.lang.Override
    public boolean trigger(org.bukkit.event.Event event) {
        if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
            org.bukkit.event.player.PlayerInteractEvent playerInteractEvent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
            org.bukkit.entity.Player player = playerInteractEvent.getPlayer();
            boolean playerHasWandInHand = checkWandInHand(player);
            // early out for not wand in hand.
            if (!playerHasWandInHand)
                return false;

            // check if the Spell is the current selected Spell
            if (this != plugin.getPlayerManager().getSpellManagerOfPlayer(player.getName()).getCurrentSpell())
                return false;

            org.bukkit.event.block.Action action = playerInteractEvent.getAction();
            if ((action == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (action == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
                java.lang.String playerName = player.getName();
                if (de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.lastCastMap.containsKey(playerName)) {
                    if ((java.lang.System.currentTimeMillis() - de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.lastCastMap.get(playerName)) < 100) {
                        // 2 casts directly after each other.
                        // lets cancle the second
                        return false;
                    } else {
                        de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.lastCastMap.remove(playerName);
                    }
                }
                de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.lastCastMap.put(player.getName(), java.lang.System.currentTimeMillis());
                boolean casted = magicSpellTriggered(player);
                if (casted) {
                    plugin.getPlayerManager().getSpellManagerOfPlayer(playerName).removeCost(this);
                }
                return casted;
            }
            if ((action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                changeMagicSpell(player);
                return false;
            }
        }
        return otherEventTriggered(event);
    }

    /**
     * This triggers when NO {@link PlayerInteractEvent} is triggered.
     *
     * @param event
     * 		that triggered
     * @return true if triggering worked and Mana should be drained.
     */
    protected boolean otherEventTriggered(org.bukkit.event.Event event) {
        return false;
    }

    /**
     * Changes the current magic spell.
     *
     * @param player
     * 		the player triggering the spell
     * @return true if the Spell could be changed, false if not.
     */
    protected boolean changeMagicSpell(org.bukkit.entity.Player player) {
        java.lang.String playerName = player.getName();
        if (plugin.getPlayerManager().getSpellManagerOfPlayer(playerName).getCurrentSpell() == null)
            return false;

        if (plugin.getPlayerManager().getSpellManagerOfPlayer(player.getName()).getSpellAmount() == 0) {
            de.tobiyas.racesandclasses.APIs.LanguageAPI.sendTranslatedMessage(player, magic_no_spells);
            return true;
        }
        de.tobiyas.racesandclasses.traitcontainer.interfaces.markerinterfaces.MagicSpellTrait nextSpell = plugin.getPlayerManager().getSpellManagerOfPlayer(player.getName()).changeToNextSpell();
        if (nextSpell != null) {
            java.text.DecimalFormat formatter = new java.text.DecimalFormat("###.#");
            java.lang.String costName = formatter.format(nextSpell.getCost());
            java.lang.String costTypeString = (nextSpell.getCostType() == CostType.ITEM) ? nextSpell.getCastMaterialType().name() : nextSpell.getCostType().name();
            java.lang.String newSpellName = ((de.tobiyas.racesandclasses.traitcontainer.interfaces.markerinterfaces.Trait) (nextSpell)).getDisplayName();
            de.tobiyas.racesandclasses.APIs.LanguageAPI.sendTranslatedMessage(player, magic_change_spells, "trait_name", newSpellName, "cost", costName, "cost_type", costTypeString);
            return true;
        } else {
            // switching too fast.
            return true;
        }
    }

    /**
     * This method is called, when the caster uses THIS magic spell.
     *
     * @param player
     * 		the Player triggering the spell.
     * @return true if spell summoning worked, false if failed.
     */
    protected abstract boolean magicSpellTriggered(org.bukkit.entity.Player player);

    // This is just for Mana + CostType
    @de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationNeeded(fields = { @de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationField(fieldName = de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_PATH, classToExpect = java.lang.Double.class, optional = true), @de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationField(fieldName = de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_TYPE_PATH, classToExpect = java.lang.String.class, optional = true), @de.tobiyas.racesandclasses.traitcontainer.interfaces.annotations.configuration.TraitConfigurationField(fieldName = de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.ITEM_TYPE_PATH, classToExpect = org.bukkit.Material.class, optional = true) })
    @java.lang.Override
    public void setConfiguration(java.util.Map<java.lang.String, java.lang.Object> configMap) throws de.tobiyas.racesandclasses.util.traitutil.TraitConfigurationFailedException {
        super.setConfiguration(configMap);
        if (configMap.containsKey(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_PATH)) {
            cost = ((java.lang.Double) (configMap.get(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_PATH)));
        }
        if (configMap.containsKey(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_TYPE_PATH)) {
            java.lang.String costTypeName = ((java.lang.String) (configMap.get(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.COST_TYPE_PATH)));
            costType = de.tobiyas.racesandclasses.traitcontainer.traits.magic.CostType.tryParse(costTypeName);
            if (costType == null) {
                throw new de.tobiyas.racesandclasses.util.traitutil.TraitConfigurationFailedException(getName() + " is incorrect configured. costType could not be read.");
            }
            if (costType == CostType.ITEM) {
                if (!configMap.containsKey(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.ITEM_TYPE_PATH)) {
                    throw new de.tobiyas.racesandclasses.util.traitutil.TraitConfigurationFailedException(getName() + " is incorrect configured. 'costType' was ITEM but no Item is specified at 'item'.");
                }
                materialForCasting = ((org.bukkit.Material) (configMap.get(de.tobiyas.racesandclasses.traitcontainer.traits.magic.AbstractMagicSpellTrait.ITEM_TYPE_PATH)));
                if (materialForCasting == null) {
                    throw new de.tobiyas.racesandclasses.util.traitutil.TraitConfigurationFailedException((((getName() + " is incorrect configured.") + " 'costType' was ITEM but the item read is not an Item. Items are CAPITAL. ") + "See 'https://github.com/Bukkit/Bukkit/blob/master/src/main/java/org/bukkit/Material.java' for all Materials. ") + "Alternative use an ItemID.");
                }
            }
        }
    }

    @java.lang.Override
    public double getCost() {
        return cost;
    }

    @java.lang.Override
    public de.tobiyas.racesandclasses.traitcontainer.traits.magic.CostType getCostType() {
        return costType;
    }

    @java.lang.Override
    public org.bukkit.Material getCastMaterialType() {
        return materialForCasting;
    }

    @java.lang.Override
    public boolean isStackable() {
        return false;
    }

    @java.lang.Override
    public boolean needsCostCheck(org.bukkit.event.Event event) {
        return event instanceof org.bukkit.event.player.PlayerInteractEvent;
    }
}