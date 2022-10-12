package me.desht.buster2;

/*
    This file is part of Buster

    Buster is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Buster is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Buster.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.MassBlockUpdate;
import org.bukkit.block.MassBlockUpdate.BlockUpdateRecord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_5_R2.block.CraftMassBlockUpdate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class buster2 extends JavaPlugin implements Runnable, Iterable<BlockUpdateRecord> {
	private enum UpdateType { NONE, BUKKIT, BUKKIT_NOPHYSICS, BLEEDING, MBU, MBU_STATIC, MBU_PROVIDER, };

	private UpdateType updateType;
	private int blockSize;
	private Location loc;
	private int count;
	private List<Long> times = new ArrayList<Long>();
	private CommandSender sender;
	private int maxIters;

	// some random solid blocks
//	private static int[] blockIds = new int[] { 0, 1, 2, 3, 4, 5, 12, 13, 14, 15, 16, 20, 21, 22, 24, 53, 67, 86, 49, 80, 82, 73, 56, 57, 89, 91 };
	private static int[] blockIds = new int[] { 0, 1, 1, 1, 2, 3, 4, 5, 14, 15, 16, 20, 21, 22, 24, 53, 67, 86, 49, 73, 56, 57 };

	public void onDisable() {
	}

	public void onEnable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = sender instanceof Player ? (Player) sender : null;

		if (cmd.getName().equals("buster2")) {
			if (player != null && args.length < 3 || player == null && args.length < 7) {
				return false;
			}

			updateType = UpdateType.valueOf(args[0].toUpperCase());
			if (updateType == null) {
				return false;
			}

			blockSize = Integer.parseInt(args[1]);
			maxIters = Integer.parseInt(args[2]);

			loc = null;
			if (args.length >= 7) {
				loc = new Location(Bukkit.getWorld(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]),Integer.parseInt(args[6]));
			} else if (player != null) {
				Block b = player.getTargetBlock(null, 140);
				loc = b.getLocation();
			} else {
				return false;
			}

			this.sender = sender;
			times.clear();
			count = 0;
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, this);

			return true;
		}
		return false;
	}

	@Override
	public void run() {
		count++;

		sender.sendMessage(updateType + ": iteration " + count + " starting");

		long now = System.nanoTime();
		switch (updateType) {
		case BUKKIT:
			updateBlocksBukkit(true);
			break;
		case BUKKIT_NOPHYSICS:
			updateBlocksBukkit(false);
			break;
		case MBU:
			updateBlocksMBU(-1, false);
			break;
		case MBU_PROVIDER:
			updateBlocksMBU(-1, true);
			break;
		case MBU_STATIC:
			updateBlocksMBU(15, false);
			break;
		}
		long elapsed = System.nanoTime() - now;
		times.add(elapsed);

		sender.sendMessage(updateType + ": iteration " + count + " done, time = " + elapsed + "ns");

		if (count < maxIters) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, this, 20L);
		} else {
			finishUp();
		}
	}

	private void finishUp() {
		long tot = 0L;
		for (long l : times) {
			tot += l;
		}
		long avg = tot / times.size();
		int nBlocks = blockSize * blockSize * blockSize;
		long perBlock = avg / nBlocks;

		sender.sendMessage("all done! see server log for results");

		getLogger().log(Level.INFO, "average update time for " + updateType + " = " + avg + "ns over " + maxIters + " iterations");
		getLogger().log(Level.INFO, "blocks updated per iteration = " + nBlocks);
		getLogger().log(Level.INFO, "average update time per block = " + perBlock + "ns");
	}

	/**
	 * Plain old Bukkit.
	 */
	private void updateBlocksBukkit(boolean physics) {
		int xMin = loc.getBlockX() - (blockSize / 2);
		int yMin = loc.getBlockY() - (blockSize / 2);
		int zMin = loc.getBlockZ() - (blockSize / 2);
		int xMax = xMin + blockSize;
		int yMax = yMin + blockSize;
		int zMax = zMin + blockSize;
		World w = loc.getWorld();
		Random r = new Random();
		for (int x = xMin; x < xMax; x++) {
			for (int y = yMin; y < yMax; y++) {
				for (int z = zMin; z < zMax; z++) {
					int n = r.nextInt(blockIds.length);
					w.getBlockAt(x, y, z).setTypeId(blockIds[n], physics);
				}
			}
		}
	}

	/**
	 * desht's MassBlockUpdate class, using direct chunk access and consolidated lighting recalculation
	 * @param strategy 
	 */
	private void updateBlocksMBU(int lightLevel, boolean provider) {
		MassBlockUpdate mbu = Bukkit.createMassBlockUpdate(this);
		mbu.setNotifyOnCompletion(new CompletionNotifier(mbu));
		mbu.setMaxUpdateTimePerTick(15);
		if (lightLevel >= 0) {
			mbu.setStaticLightLevel(lightLevel);
		}
		if (provider) {
			// use a custom block provider; lazy evaluation - much lighter on memory
			mbu.setBlockUpdateProvider(this);
		} else {
			// prepare block updates directly; uses space in memory for the pending changes
			mbu.setUpdateBufferSize(blockSize * blockSize * blockSize);
			int xMin = loc.getBlockX() - (blockSize / 2);
			int yMin = loc.getBlockY() - (blockSize / 2);
			int zMin = loc.getBlockZ() - (blockSize / 2);
			int xMax = xMin + blockSize;
			int yMax = yMin + blockSize;
			int zMax = zMin + blockSize;
			Random r = new Random();
			for (int x = xMin; x < xMax; x++) {
				for (int y = yMin; y < yMax; y++) {
					for (int z = zMin; z < zMax; z++) {
						mbu.addBlockUpdate(new BlockUpdateRecord(new Location(loc.getWorld(), x, y, z), Material.getMaterial(blockIds[r.nextInt(blockIds.length)])));
					}
				}
			}
		}

		mbu.applyUpdates();
	}

	@Override
	public Iterator<BlockUpdateRecord> iterator() {
		return new BusterUpdateProvider(loc, blockSize);
	}

	private class CompletionNotifier implements Runnable {
		private final MassBlockUpdate mbu;
		private CompletionNotifier(MassBlockUpdate mbu) {
			this.mbu = mbu;
		}
		@Override
		public void run() {
			getLogger().log(Level.INFO, "MBU update complete");
			CraftMassBlockUpdate cmbu = (CraftMassBlockUpdate) mbu;
			int count = blockSize * blockSize * blockSize;
			getLogger().log(Level.INFO, "Time spent preparing: " + cmbu.getPrepareTime() + "ns (" + cmbu.getPrepareTime() / count + "ns per block)");
			getLogger().log(Level.INFO, "Time spent updating:  " + cmbu.getUpdateTime() + "ns (" + cmbu.getUpdateTime() / count + "ns per block)");
		}
	}

	private class BusterUpdateProvider implements Iterator<BlockUpdateRecord> {
		private final int blockSize;
		private final BlockUpdateRecord rec;
		private final int baseX, baseY, baseZ;
		private final Random r;
		private int x, y, z;

		public BusterUpdateProvider(Location loc, int blockSize) {
			this.blockSize = blockSize;
			this.rec = new BlockUpdateRecord(loc, Material.AIR);
			this.r = new Random();
			this.baseX = loc.getBlockX();
			this.baseY = loc.getBlockY();
			this.baseZ = loc.getBlockZ();
			this.x = this.y = this.z = 0;
		}

		@Override
		public boolean hasNext() {
			return y < blockSize;
		}

		@Override
		public BlockUpdateRecord next() {
			// NOTE: reusable BlockUpdateRecord saves memory
			rec.getLocation().setX(baseX + x);
			rec.getLocation().setY(baseY + y);
			rec.getLocation().setZ(baseZ + z);
			rec.setMaterialId(blockIds[r.nextInt(blockIds.length)]);
			rec.setData((byte) 0);
			if (++x >= blockSize) {
				x = 0;
				if (++z >= blockSize) {
					z = 0;
					++y;
				}
			}
			return rec;
		}

		@Override
		public void remove() {
			// nop
		}
	}
}
