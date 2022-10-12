/*
 * Arena API
 *
 * Created by Ultimate 
 *
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Burn Games
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.ultimate.ArenaAPI.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ultimate.ArenaAPI.ArenaAPI;
import me.ultimate.ArenaAPI.ArenaAPIPlugin;
import me.ultimate.ArenaAPI.ArenaEvents.ArenaEndEvent;
import me.ultimate.ArenaAPI.ArenaEvents.ArenaStartEvent;
import me.ultimate.ArenaAPI.ArenaEvents.PlayerJoinArenaEvent;
import me.ultimate.ArenaAPI.ArenaEvents.PlayerLeaveArenaEvent;
import me.ultimate.ArenaAPI.Objects.ArenaWarps.WarpType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

/**
 * The Class Arena, which represents an Arena
 */
public class Arena {
    
    /** The name of the arena. */
    private String name;
    
    /** The world the arena is in. */
    private World world;
    
    /** The first location. */
    private Location l1;
    
    /** The second location. */
    private Location l2;
    
    /** The warps. */
    private ArenaWarps warps;
    
    /** The settings. */
    private ArenaSettings settings;
    
    /** The players. */
    private List<String> players = new ArrayList<String>();
    
    private boolean running = false;
    
    /** The blocks. */
    private HashMap<Block, BlockState> blocks = new HashMap<Block, BlockState>();
    
    /**
     * Instantiates a new arena.
     * 
     * @param name the name
     * @param world the world
     * @param l1 the first location
     * @param l2 the second location
     */
    public Arena(String name, World world, Location l1, Location l2) {
        this.name = name;
        this.world = world;
        this.l1 = l1;
        this.l2 = l2;
        this.warps = new ArenaWarps();
        settings = new ArenaSettings();
        ArenaAPI.registerArena(this);
    }
    
    /**
     * Gets the name of the arena.
     * 
     * @return the name
     */
    public String getName() {
        return this.name;
    }
    
    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<Block>();
        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        
        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        
        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        
        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = l1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
    
    /**
     * Gets the world the arena is in.
     * 
     * @return the world
     */
    public World getWorld() {
        return this.world;
    }
    
    public void reloadWorld(boolean randomSeed) {
        getWorld().setAutoSave(false);
        final String wName = getWorld().getName();
        long seed = getWorld().getSeed();
        Bukkit.getServer().unloadWorld(getWorld(), false);
        WorldCreator c = new WorldCreator(wName);
        if (!randomSeed) {
            c.seed(seed);
        }
        Bukkit.getScheduler().runTaskAsynchronously(ArenaAPIPlugin.instance, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().createWorld(new WorldCreator(wName));
            }
        });
        this.world = Bukkit.getWorld(wName);
        getWorld().setAutoSave(true);
    }
    
    /**
     * Gets the arena settings.
     * 
     * @return the settings
     */
    public ArenaSettings getSettings() {
        return this.settings;
    }
    
    /**
     * Sets the arena settings.
     * 
     * @param settings the new settings
     */
    public void setSettings(ArenaSettings settings) {
        this.settings = settings;
    }
    
    /**
     * Checks if a block is inside the arena.
     * 
     * @param b the block
     * @return true, if successful
     */
    public boolean insideArena(Block b) {
        return insideArena(b.getLocation());
    }
    
    /**
     * Checks if a player is inside the arena.
     * 
     * @param p the player
     * @return true, if successful
     */
    public boolean insideArena(Player p) {
        return players.contains(p.getName());
    }
    
    /**
     * Gets the specific warp
     * 
     * @param type the warp type
     * @return the warp location
     */
    public Location getWarp(WarpType type) {
        return warps.getWarp(type);
    }
    
    public void setWarp(WarpType type, Location l) {
        warps.setWarp(type, l);
    }
    
    /**
     * Adds the player to the arena.
     * 
     * @param p the player
     */
    public void addPlayer(Player p) {
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(p, this);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            players.add(p.getName());
            p.teleport(getWarp(WarpType.LOBBY));
        }
    }
    
    /**
     * Removes the player from the arena.
     * 
     * @param p the player
     */
    public void removePlayer(Player p) {
        PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(p, this);
        Bukkit.getPluginManager().callEvent(event);
        players.remove(p.getName());
        p.teleport(getWarp(WarpType.LOSE));
    }
    
    /**
     * Starts the arena.
     */
    public void startGame() {
        running = true;
        ArenaStartEvent event = new ArenaStartEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            for (String pName : players) {
                Player p = Bukkit.getPlayerExact(pName);
                p.teleport(getWarp(WarpType.SPAWN));
            }
        } else {
            endGame();
        }
    }
    
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Ends the arena.
     */
    public void endGame() {
        running = false;
        ArenaEndEvent event = new ArenaEndEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        for (String pName : players) {
            Player p = Bukkit.getPlayerExact(pName);
            p.teleport(getWarp(WarpType.LOSE));
        }
        players.clear();
    }
    
    /**
     * End the arena with a winner.
     * 
     * @param p the player
     */
    public void endGameWithWinner(Player p) {
        endGame();
        p.teleport(getWarp(WarpType.WIN));
    }
    
    /**
     * Teleports a player to the spectation spot
     * 
     * @param p the p
     */
    public void teleportSpectator(Player p) {
        p.teleport(getWarp(WarpType.SPECTATE));
    }
    
    /**
     * Checks if a location is inside the arena.
     * 
     * @param l the location
     * @return true, if successful
     */
    public boolean insideArena(Location l) {
        if (l.getWorld().getName() == l2.getWorld().getName()) {
            return l.toVector().isInAABB(l1.toVector(), l2.toVector());
        }
        return false;
    }
    
    /**
     * Save the arena blocks.
     */
    public void saveArena() {
        blocks.clear();
        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = l1.getWorld().getBlockAt(x, y, z);
                    BlockState state = block.getState();
                    
                    blocks.put(block, state);
                }
            }
        }
    }
    
    /**
     * Reloads all of the arena blocks.
     */
    @SuppressWarnings("deprecation")
    public void loadArena() {
        for (Map.Entry<Block, BlockState> entry : blocks.entrySet()) {
            Block b = entry.getKey();
            BlockState state = entry.getValue();
            
            b.setType(state.getType());
            b.setData(state.getRawData());
            state.update();
        }
    }
}