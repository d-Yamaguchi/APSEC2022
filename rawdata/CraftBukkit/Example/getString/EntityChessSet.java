package me.desht.chesscraft.chess.pieces;

import java.util.HashMap;
import java.util.Map;

import me.desht.chesscraft.ChessPersistence;
import me.desht.chesscraft.ChessValidate;
import me.desht.chesscraft.chess.ChessBoard;
import me.desht.chesscraft.enums.BoardRotation;
import me.desht.chesscraft.exceptions.ChessException;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import chesspresso.Chess;
import chesspresso.position.Position;

import com.google.common.base.Joiner;

/**
 * @author des
 *
 */
public class EntityChessSet extends ChessSet {

	private static final String[] CHESS_SET_HEADER_LINES = new String[] {
		"ChessCraft entity piece style definition file",
		"See http://dev.bukkit.org/server-mods/chesscraft/pages/piece-styles",
		"",
		"'name' is the name for this set, and should match the filename",
		"",
		"'comment' is a freeform comment about the set (can be multi-line)",
		"",
		"'pieces.<colour>.<X>' defines the NPC used for a chess piece," +
				" where <colour> is one of black, white and <X> is one of P,R,N,B,Q,K",
				" The piece definition is a Bukkit EntityType - see",
				" http://jd.bukkit.org/dev/apidocs/org/bukkit/entity/EntityType.html",
	};

	// stores which piece is standing on which chess square
	private final EntityChessStone[] stones;
	// map piece name to NPC entity type
	private Map<Integer,ConfigurationSection> stoneTypeMap;

	public EntityChessSet(Configuration c, boolean isCustom) {
		super(c, isCustom);

		ChessPersistence.requireSection(c, "pieces.white");
		ChessPersistence.requireSection(c, "pieces.black");

		this.stones = new EntityChessStone[Chess.NUM_OF_SQUARES];
		try {
			this.stoneTypeMap = loadPieces(c.getConfigurationSection("pieces"));
		} catch (Exception e) {
			throw new ChessException(e.getMessage());
		}
	}

	private Map<Integer, ConfigurationSection> loadPieces(ConfigurationSection cs) {
		Map<Integer,ConfigurationSection> map = new HashMap<Integer, ConfigurationSection>();
		loadPieces(map, Chess.WHITE, cs.getConfigurationSection("white"));
		loadPieces(map, Chess.BLACK, cs.getConfigurationSection("black"));
		return map;
	}

	private void loadPieces(Map<Integer, ConfigurationSection> map, int colour, ConfigurationSection cs) {
		for (int piece = Chess.MIN_PIECE + 1; piece <= Chess.MAX_PIECE; piece++) {
			String s = Character.toString(Chess.pieceToChar(piece));
			ConfigurationSection details = cs.getConfigurationSection(s);
			ChessValidate.notNull(details, "missing definition for chess piece: " + s);
			ChessPersistence.requireSection(details, "entity");
			details.set("_entity", EntityType.valueOf(details.getString("entity").toUpperCase()));
			map.put(Chess.pieceToStone(piece, colour), details);
		}
	}

	@Override
	protected String getHeaderText() {
		return Joiner.on("\n").join(CHESS_SET_HEADER_LINES);
	}

	@Override
	protected String getType() {
		return "entity";
	}

	@Override
	protected void addSaveData(Configuration c) {
		for (int piece = 1; piece <= Chess.MAX_PIECE; piece++) {
			char pc = Chess.pieceToChar(piece);
			c.set("pieces.white." + pc, stoneTypeMap.get(Chess.pieceToStone(piece, Chess.WHITE)));
			c.set("pieces.black." + pc, stoneTypeMap.get(Chess.pieceToStone(piece, Chess.BLACK)));
		}
	}

	@Override
	public ChessStone getStone(int stone, BoardRotation direction) {
		throw new UnsupportedOperationException("Entity chess sets don't track pieces by stone ID");
	}

	@Override
	public ChessStone getStoneAt(int sqi) {
		return stones[sqi];
	}

	@Override
	public boolean canRide() {
		return false;
	}

	@Override
	public boolean hasMovablePieces() {
		return true;
	}

	@Override
	public void movePiece(int fromSqi, int toSqi, Location to, int promoteStone) {
		EntityChessStone stone = (EntityChessStone) getStoneAt(fromSqi);
		EntityChessStone captured = (EntityChessStone) getStoneAt(toSqi);
		if (stone != null) {
			if (promoteStone != Chess.NO_STONE) {
				Location loc = stone.getBukkitEntity().getLocation();
				stone.cleanup();
				stone = new EntityChessStone(promoteStone, stoneTypeMap.get(promoteStone), loc, loc.getYaw());
			}
			stone.move(fromSqi, toSqi, to, captured);
			stones[fromSqi] = null;
			stones[toSqi] = stone;
		}
	}

	@Override
	public void syncToPosition(Position pos, final ChessBoard board) {
		for (int sqi = 0; sqi < Chess.NUM_OF_SQUARES; sqi++) {
			if (stones[sqi] != null) {
				stones[sqi].cleanup();
				stones[sqi] = null;
			}
			if (pos != null && pos.getStone(sqi) != Chess.NO_STONE) {
				int stone = pos.getStone(sqi);
				Location loc = board.getSquare(Chess.sqiToRow(sqi), Chess.sqiToCol(sqi)).getCenter().add(0, 0.5, 0);
				float yaw = board.getRotation().getYaw();
				if (Chess.stoneToColor(stone) == Chess.BLACK) {
					yaw = (yaw + 180) % 360;
				}
				stones[sqi] = new EntityChessStone(stone, stoneTypeMap.get(stone), loc, yaw);
			}
		}
	}
}
