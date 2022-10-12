package za.dats.bukkit.memorystone;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.ConfigurationNode;
import za.dats.bukkit.memorystone.listeners.MemoryStoneSignListener;
import za.dats.bukkit.memorystone.util.StructureListener;
import za.dats.bukkit.memorystone.util.structure.Rotator;
import za.dats.bukkit.memorystone.util.structure.Structure;
import za.dats.bukkit.memorystone.util.structure.StructureType;
public class MemoryStoneManager implements za.dats.bukkit.memorystone.util.StructureListener {
    private za.dats.bukkit.memorystone.listeners.MemoryStoneSignListener signListener;

    private final za.dats.bukkit.memorystone.MemoryStonePlugin memoryStonePlugin;

    private static za.dats.bukkit.memorystone.MemoryStone memorized;

    private java.util.HashMap<za.dats.bukkit.memorystone.util.structure.Structure, za.dats.bukkit.memorystone.MemoryStone> structureMap = new java.util.HashMap<za.dats.bukkit.memorystone.util.structure.Structure, za.dats.bukkit.memorystone.MemoryStone>();

    public MemoryStoneManager(za.dats.bukkit.memorystone.MemoryStonePlugin memoryStonePlugin) {
        this.memoryStonePlugin = memoryStonePlugin;
    }

    public void registerEvents() {
        org.bukkit.plugin.PluginManager pm;
        pm = memoryStonePlugin.getServer().getPluginManager();
        signListener = new za.dats.bukkit.memorystone.listeners.MemoryStoneSignListener(memoryStonePlugin);
        pm.registerEvent(org.bukkit.event.Event.Type, signListener, org.bukkit.event.Event.Priority, memoryStonePlugin);
        pm.registerEvent(org.bukkit.event.Event.Type, signListener, org.bukkit.event.Event.Priority, memoryStonePlugin);
        pm.registerEvent(org.bukkit.event.Event.Type, new org.bukkit.event.player.PlayerListener() {
            @java.lang.Override
            public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
                if (event.getPlayer().getItemInHand().getType() != org.bukkit.Material.COMPASS) {
                    return;
                }
                if () {
                    if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
                        org.bukkit.block.Sign state = ((org.bukkit.block.Sign) (event.getClickedBlock().getState()));
                        za.dats.bukkit.memorystone.MemoryStoneManager.memorized = getMemoryStructureBehind(state);
                        if ((za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) && (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign() != null)) {
                            event.getPlayer().sendMessage("Memorized " + state.getLine(1));
                        }
                        return;
                    }
                }
                if () {
                    if (za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) {
                        org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign()));
                        if (sign == null) {
                            return;
                        }
                        event.getPlayer().sendMessage("Recalling");
                        org.bukkit.material.Sign aSign = ((org.bukkit.material.Sign) (sign.getData()));
                        org.bukkit.block.Block infront = sign.getBlock().getRelative(aSign.getFacing());
                        // new Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ());
                        event.getPlayer().teleport(infront.getLocation());
                    } else {
                        event.getPlayer().sendMessage("No Memorized recalling");
                    }
                }
                __SmPLUnsupported__(0).onPlayerInteract(event);
            }
        }, Priority.Normal, memoryStonePlugin);
    }

    public void structurePlaced(org.bukkit.event.block.BlockPlaceEvent event, za.dats.bukkit.memorystone.util.structure.Structure structure) {
        za.dats.bukkit.memorystone.MemoryStone stone = new za.dats.bukkit.memorystone.MemoryStone();
        stone.setStructure(structure);
        structureMap.put(structure, stone);
        event.getPlayer().sendMessage("Built Memory Stone!");
    }

    public void structureDestroyed(org.bukkit.event.block.BlockBreakEvent event, za.dats.bukkit.memorystone.util.structure.Structure structure) {
        za.dats.bukkit.memorystone.MemoryStone stone = structureMap.get(structure);
        org.bukkit.block.Sign sign = stone.getSign();
        if (sign != null) {
            org.bukkit.block.BlockState state = new org.bukkit.Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ()).getBlock().getState();
            if (state instanceof org.bukkit.block.Sign) {
                org.bukkit.block.Sign newSign = ((org.bukkit.block.Sign) (new org.bukkit.Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ()).getBlock().getState()));
                newSign.setLine(0, za.dats.bukkit.memorystone.Utility.color("&C") + "Memory Stone");
                newSign.setLine(1, za.dats.bukkit.memorystone.Utility.color("&C[Broken]"));
                newSign.update(true);
            }
            stone.setSign(null);
        }
        structureMap.remove(structure);
        event.getPlayer().sendMessage("Destroyed Memory Stone!");
    }

    public void structureLoaded(za.dats.bukkit.memorystone.util.structure.Structure structure, org.bukkit.util.config.ConfigurationNode node) {
        java.lang.System.out.println("Loading Memory Stone Structure");
        za.dats.bukkit.memorystone.MemoryStone stone = new za.dats.bukkit.memorystone.MemoryStone();
        stone.setStructure(structure);
        structureMap.put(structure, stone);
        if (node.getProperty("signx") != null) {
            org.bukkit.block.Sign newSign = ((org.bukkit.block.Sign) (new org.bukkit.Location(structure.getWorld(), node.getInt("signx", 0), node.getInt("signy", 0), node.getInt("signz", 0)).getBlock().getState()));
            stone.setSign(newSign);
        }
    }

    public void structureSaving(za.dats.bukkit.memorystone.util.structure.Structure structure, java.util.Map<java.lang.String, java.lang.Object> yamlMap) {
        za.dats.bukkit.memorystone.MemoryStone memoryStone = structureMap.get(structure);
        if (memoryStone.getSign() != null) {
            org.bukkit.block.Sign sign = memoryStone.getSign();
            yamlMap.put("signx", sign.getX());
            yamlMap.put("signy", sign.getY());
            yamlMap.put("signz", sign.getZ());
        }
    }

    public void generatingDefaultStructureTypes(java.util.List<za.dats.bukkit.memorystone.util.structure.StructureType> types) {
        za.dats.bukkit.memorystone.util.structure.StructureType structuretype;
        za.dats.bukkit.memorystone.util.structure.StructureType.Prototype proto;
        proto = new za.dats.bukkit.memorystone.util.structure.StructureType.Prototype();
        proto.addBlock(0, 0, 0, org.bukkit.Material.COBBLESTONE);
        proto.addBlock(0, 1, 0, org.bukkit.Material.COBBLESTONE);
        proto.addBlock(0, 2, 0, org.bukkit.Material.DIRT);
        proto.setName("Memory Stone");
        proto.setRotator(Rotator.NONE);
        structuretype = new za.dats.bukkit.memorystone.util.structure.StructureType(proto);
        types.add(structuretype);
    }

    public za.dats.bukkit.memorystone.MemoryStone getMemoryStoneAtBlock(org.bukkit.block.Block behind) {
        java.util.Set<za.dats.bukkit.memorystone.util.structure.Structure> structuresFromBlock = memoryStonePlugin.getStructureManager().getStructuresFromBlock(behind);
        if ((structuresFromBlock == null) || (structuresFromBlock.size() != 1)) {
            return null;
        }
        for (za.dats.bukkit.memorystone.util.structure.Structure structure : structuresFromBlock) {
            if (!structureMap.containsKey(structure)) {
                break;
            }
            za.dats.bukkit.memorystone.MemoryStone result = structureMap.get(structure);
            return result;
        }
        return null;
    }

    public za.dats.bukkit.memorystone.MemoryStone getMemoryStructureBehind(org.bukkit.block.Sign sign) {
        org.bukkit.material.Sign aSign = ((org.bukkit.material.Sign) (sign.getData()));
        org.bukkit.block.Block behind = sign.getBlock().getRelative(aSign.getFacing().getOppositeFace());
        za.dats.bukkit.memorystone.MemoryStone stone = memoryStonePlugin.getMemoryStoneManager().getMemoryStoneAtBlock(behind);
        return stone;
    }
}