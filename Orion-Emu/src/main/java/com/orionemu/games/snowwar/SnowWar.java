package com.orionemu.games.snowwar;

import com.orionemu.games.GameBase;

/*
 * ****************
 * @author capos *
 * ****************
 */

public class SnowWar extends GameBase {
	public static SnowWarArenaBase[] ArenaTypes = { 
		new SnowWarArena8(),
		new SnowWarArena9(),
		new SnowWarArena11()
	};

	// GAME VALUES
	public static final int GAMESECONDS = 120;
	public static final int GAMETURNMILLIS = 150;
	public static final int GAMETURNS = (GAMESECONDS * 1000) / GAMETURNMILLIS;

	// PLAYERS VALUES
	public static final int MINPLAYERS = 2;
	public static final int MAXPLAYERS = 10;

	// PLAYER STATUS
	public static final int INLOBBY = 0;
	public static final int INARENA = 1;

	// ROOM STATUS
	public static final int CLOSE = 0;
	public static final int TIMER_TOLOBBY = 1;
	public static final int STAGE_LOADING = 2;
	public static final int STAGE_STARTING = 3;
	public static final int STAGE_RUNNING = 4;
	public static final int ARENA = 5;
	public static final int ARENA_END = 6;

	// TEAMS VALUES
	public static final int TEAM_BLUE = 1;
	public static final int TEAM_RED = 2;
	public static final int[] TEAMS = { 
		SnowWar.TEAM_BLUE,
		SnowWar.TEAM_RED
	};

	public SnowWar() {
		super(0, "snowwar", "93d4f3", "", "http://dcr.lavvos.pl/lavvos/c_images/gamecenter_snowwar/");
	}
}
