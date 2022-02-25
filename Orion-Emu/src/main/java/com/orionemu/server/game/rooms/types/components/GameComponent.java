package com.orionemu.server.game.rooms.types.components;

import com.orionemu.server.game.blibJackpot.BlibJackpot;
import com.orionemu.server.game.blibJackpot.JackpotGame;
import com.orionemu.server.game.blibV75.BlibV75;
import com.orionemu.server.game.blibV75.V75Game;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.football.FootballScoreFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerScoreAchieved;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.games.GameTeam;
import com.orionemu.server.game.rooms.types.components.games.GameType;
import com.orionemu.server.game.rooms.types.components.games.RoomGame;
import com.orionemu.server.game.rooms.types.components.games.V75AbstractGame;
import com.orionemu.server.game.rooms.types.components.games.banzai.BanzaiGame;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class GameComponent {
    private Room room;
    private RoomGame instance;
    private V75AbstractGame v75Instance;

    private Map<GameTeam, List<Integer>> teams;
    private Map<GameTeam, Integer> scores;
    private Map<BlibJackpot, Integer> betters;
    private List<String> bettersUsernames;

    private Map<BlibV75, String> v75StringMap;
    private List<String> v75Usernames;

    private boolean active = false;
    private boolean v75Active = false;

    public GameComponent(Room room) {
        this.teams = new HashMap<GameTeam, List<Integer>>() {{
            put(GameTeam.BLUE, Lists.newArrayList());
            put(GameTeam.YELLOW, Lists.newArrayList());
            put(GameTeam.RED, Lists.newArrayList());
            put(GameTeam.GREEN, Lists.newArrayList());
        }};

        this.betters = new HashMap<>();
        this.bettersUsernames = new LinkedList<>();
        this.v75StringMap = new HashMap<>();
        this.v75Usernames = new LinkedList<>();

        this.resetScores();
        this.room = room;
    }

    public void resetScores() {
        if (this.scores != null)
            this.scores.clear();

        this.scores = new ConcurrentHashMap<GameTeam, Integer>() {{
            put(GameTeam.BLUE, 0);
            put(GameTeam.YELLOW, 0);
            put(GameTeam.GREEN, 0);
            put(GameTeam.RED, 0);
        }};
    }

    public void dispose() {
        for (Map.Entry<GameTeam, List<Integer>> entry : this.teams.entrySet()) {
            entry.getValue().clear();
        }

        this.teams.clear();
    }

    public void stop() {
        if (this.instance != null) {
            this.instance.stop();
        }

        this.instance = null;
    }

    public void stopV75() {
        if (this.v75Instance != null) {
            this.v75Instance.stop();
        }

        this.v75Instance = null;
    }

    public void createNew(GameType game) {
        if (game == GameType.BANZAI) {
            this.instance = new BanzaiGame(this.room);
        } else if (game == GameType.FREEZE) {
            this.instance = null; // TODO: freeze game
        } else if (game == GameType.JACKPOT) {
            this.instance = new JackpotGame(this.room);
        } else if (game == GameType.V75) {
            this.v75Instance = new V75Game(this.room);
        }
    }

    public boolean isTeamed(int id) {
        return this.getTeam(id) != GameTeam.NONE;
    }

    public void removeFromTeam(GameTeam team, Integer id) {
        if (this.teams.get(team).contains(id))
            this.teams.get(team).remove(id);
    }

    public GameTeam getTeam(int userId) {
        for (Map.Entry<GameTeam, List<Integer>> entry : this.getTeams().entrySet()) {
            if (entry.getValue().contains(userId)) {
                return entry.getKey();
            }
        }

        return GameTeam.NONE;
    }

    public void addBet(BlibJackpot jackpot) {
        this.betters.put(jackpot, jackpot.getAmount());
    }

    public void increaseScore(GameTeam team, int amount) {
        if (!this.scores.containsKey(team)) {
            return;
        }

        this.scores.replace(team, this.scores.get(team) + amount);

        for (RoomItemFloor scoreItem : this.getRoom().getItems().getByClass(FootballScoreFloorItem.class)) {
            scoreItem.sendUpdate();
        }

        for (RoomItemFloor scoreboard : this.getRoom().getItems().getByInteraction("%_score")) {
            if (team == null || scoreboard.getDefinition().getInteraction().toUpperCase().startsWith(team.name())) {
                scoreboard.setExtraData(team == null ? "0" : this.getScore(team) + "");
                scoreboard.sendUpdate();
            }
        }

        WiredTriggerScoreAchieved.executeTriggers(this.getRoom().getGame().getScore(team), team, this.getRoom());
    }

    public int getScore(GameTeam team) {
        return this.scores.get(team);
    }

    public Map<GameTeam, List<Integer>> getTeams() {
        return teams;
    }

    public RoomGame getInstance() {
        return this.instance;
    }

    public V75AbstractGame getV75Instance() { return this.v75Instance; }

    public Room getRoom() {
        return this.room;
    }

    public Map<GameTeam, Integer> getScores() {
        return scores;
    }

    public Map<BlibJackpot, Integer> getBetters() {
        return betters;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getBettersUsernames() {
        return this.bettersUsernames;
    }

    public Map<BlibV75, String> getV75StringMap() {
        return this.v75StringMap;
    }

    public List<String> getV75Usernames() {
        return this.v75Usernames;
    }

    public boolean isV75Active() {
        return v75Active;
    }

    public void setV75Active(boolean v75Active) {
        this.v75Active = v75Active;
    }
}
