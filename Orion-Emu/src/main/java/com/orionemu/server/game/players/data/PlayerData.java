package com.orionemu.server.game.players.data;

import com.orionemu.api.game.players.data.IPlayerData;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.utilities.validator.PlayerFigureValidator;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queue.types.PlayerDataStorageQueue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class PlayerData implements PlayerAvatar, IPlayerData {
    public static final String DEFAULT_FIGURE = "hr-100-61.hd-180-2.sh-290-91.ch-210-66.lg-270-82";

    LocalDate now = LocalDate.now();

    private int id;
    private int rank;

    private String username;
    private String motto;
    private String figure;
    private String gender;
    private String email;

    private String ipAddress;

    private int credits;
    private int vipPoints;
    private int activityPoints;

    private String regDate;
    private int lastVisit;
    private LocalDate LastDay;
    private int regTimestamp;
    private int achievementPoints;

    private int favouriteGroup;

    private String temporaryFigure;

    private boolean vip;
    private int questId;
    private double lastUseEha;
    private double lastUseNa;

    private int timeMuted;

    private boolean changingName = false;

    private boolean flaggingUser = false;

    private int nuxInstructions;

    private String nameTag;
    private int fastfoodScore;

    private String tagColor;
    private int betAmount = 0;
    private int jackpotAmount = 0;
    private boolean lira = false;


    private int currentNumber = 0;
    private int oldNumber = 0;
    private boolean ask = false;
    private int daysInRow = 1;
    private int dailyCounter = 0;

    private int jackpotRiskAmount = 0;

    private int snowXp;

    private boolean inHotelView;
    private boolean isOHF = false;

    public PlayerData(int id, String username, String motto, String figure, String gender, String email, int rank, int credits, int vipPoints, int activityPoints, String reg, int lastVisit, boolean vip, int achievementPoints, int regTimestamp, int favouriteGroup, String ipAddress, int questId, int timeMuted, boolean nux, int fastfoodScore, int snowXp) {
        this.inHotelView = false;
        this.id = id;
        this.username = username;
        this.motto = motto;
        this.figure = figure;
        this.rank = rank;
        this.credits = credits;
        this.vipPoints = vipPoints;
        this.activityPoints = activityPoints;
        this.gender = gender;
        this.vip = vip;
        this.achievementPoints = achievementPoints;
        this.email = email;
        this.regDate = reg;
        this.lastVisit = lastVisit;
        this.regTimestamp = regTimestamp;
        this.favouriteGroup = favouriteGroup;
        this.ipAddress = ipAddress;
        this.questId = questId;
        this.timeMuted = timeMuted;
        this.nuxInstructions = nux ? 1 : 0;
        this.fastfoodScore = fastfoodScore;
        this.snowXp = snowXp;
        if (this.figure != null) {
            if (!PlayerFigureValidator.isValidFigureCode(this.figure, this.gender.toLowerCase())) {
                this.figure = DEFAULT_FIGURE;
            }
        }
    }

    public PlayerData(ResultSet data) throws SQLException {
        this(data.getInt("playerId"), data.getString("playerData_username"), data.getString("playerData_motto"), data.getString("playerData_figure"), data.getString("playerData_gender"), data.getString("playerData_email"), data.getInt("playerData_rank"), data.getInt("playerData_credits"), data.getInt("playerData_vipPoints"), data.getInt("playerData_activityPoints"), data.getString("playerData_regDate"), data.getInt("playerData_lastOnline"), data.getString("playerData_vip").equals("1"), data.getInt("playerData_achievementPoints"), data.getInt("playerData_regTimestamp"), data.getInt("playerData_favouriteGroup"), data.getString("playerData_lastIp"), data.getInt("playerData_questId"), data.getInt("playerData_timeMuted"), data.getString("playerData_nux").equals("1"), data.getInt("playerData_fastfood"), data.getInt("playerData_snow"));
    }

    public void save() {
        if (OrionSettings.storagePlayerQueueEnabled) {
            PlayerDataStorageQueue.getInstance().queueSave(this);
        } else {
            this.saveNow();
        }
    }

    public void saveNow() {
        PlayerDao.updatePlayerData(id, username, motto, figure, credits, vipPoints, gender, favouriteGroup, activityPoints, questId, achievementPoints);
    }

    public void decreaseCredits(int amount) {
        this.credits -= amount;
    }

    public void increaseCredits(int amount) {
        this.credits += amount;
    }

    public void decreasePoints(int points) {
        this.vipPoints -= points;
    }

    public void increasePoints(int points) {
        this.vipPoints += points;
    }

    public void increaseActivityPoints(int points) {
        this.activityPoints += points;
    }

    public void decreaseActivityPoints(int points) {
        this.activityPoints -= points;
    }

    public void increaseAchievementPoints(int points) {
        this.achievementPoints += points;
    }

    public void setPoints(int points) {
        this.vipPoints = points;
    }

    public void setLastUseNa(double lastUseEha) {
        this.lastUseNa = lastUseEha;
    }

    public int getId() {
        return this.id;
    }

    public int getRank() {
        return this.rank;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAchievementPoints() {
        return this.achievementPoints;
    }

    public String getMotto() {
        return this.motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getFigure() {
        return this.figure;
    }

    public String getGender() {
        return this.gender;
    }

    public int getCredits() {
        return this.credits;
    }

    public double getLastUseEha() {
        return this.lastUseEha;
    }

    public double getLastUseNa() {
        return this.lastUseNa;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getVipPoints() {
        return this.vipPoints;
    }

    public int getLastVisit() {
        return this.lastVisit;
    }

    public LocalDate getLastDay() {
        return this.LastDay;
    }

    public String getRegDate() {
        return this.regDate;
    }

    public boolean isVip() {
        return this.vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public void setLastUseEha(double lastUseEha) {
        this.lastUseEha = lastUseEha;
    }

    public void setLastDay(LocalDate now) {
        this.LastDay = now;
    }

    public void setLastVisit(long time) {
        this.lastVisit = (int) time;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRegTimestamp() {
        return regTimestamp;
    }

    public void setRegTimestamp(int regTimestamp) {
        this.regTimestamp = regTimestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFavouriteGroup() {
        return favouriteGroup;
    }

    public void setFavouriteGroup(int favouriteGroup) {
        this.favouriteGroup = favouriteGroup;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(int activityPoints) {
        this.activityPoints = activityPoints;
    }

    public void setVipPoints(int vipPoints) {
        this.vipPoints = vipPoints;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTemporaryFigure() {
        return temporaryFigure;
    }

    public void setTemporaryFigure(String temporaryFigure) {
        this.temporaryFigure = temporaryFigure;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getTimeMuted() {
        return this.timeMuted;
    }

    public void setTimeMuted(int Time) {
        this.timeMuted = Time;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public boolean getChangingName() {
        return this.changingName;
    }

    public void setChangingName(boolean changingName) {
        this.changingName = changingName;
    }

    public boolean getFlaggingUser() {
        return this.flaggingUser;
    }

    public void setFlaggingUser(boolean flaggingUser) {
        this.flaggingUser = flaggingUser;
    }

    public int getNuxInstructions() {
        return this.setNuxInstructions(nuxInstructions);
    }

    public String getNameTag() {
        return setNameTag(nameTag);
    }

    public String setNameTag(String tag) {
        return nameTag = tag;
    }

    public String getTagColor() {
        return setTagColor(tagColor);
    }

    public String setTagColor(String color) {
        return this.tagColor = color;
    }

    public int setNuxInstructions(int instruction) {
        return this.nuxInstructions = instruction;
    }

    public int getFastfoodScore() {
        return fastfoodScore;
    }

    public int getJackpotAmount() {
        return this.jackpotAmount;
    }

    public int getBetAmount() {
        return this.betAmount;
    }

    public boolean getLira() {
        return this.lira;
    }

    public void setLira(boolean lira) {
        this.lira = lira;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public void setJackpotAmount(int jackpotAmount) {
        this.jackpotAmount = jackpotAmount;
    }

    public int getCurrentNumber() {
        return this.currentNumber;
    }

    public int getOldNumber() {
        return this.oldNumber;
    }

    public int getDaysInRow() {
        return this.daysInRow;
    }

    public int getDailyCounter() {
        return this.dailyCounter;
    }

    public boolean getAsk() {
        return this.ask;
    }

    public void setAsk(boolean ask) {
        this.ask = ask;
    }

    public void setDailyCounter(int dailyCounter) {
        this.dailyCounter = dailyCounter;
    }

    public void setDaysInRow(int daysInRow) {
        this.daysInRow = daysInRow;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public void setOldNumber(int oldNumber) {
        this.oldNumber = oldNumber;
    }

    public boolean isInHotelView() {
        return this.inHotelView;
    }

    public void setInHotelView(boolean inHotelView) {
        this.inHotelView = inHotelView;
    }

    public boolean isOHF() {
        return isOHF;
    }

    public void setOHF(boolean OHF) {
        isOHF = OHF;
    }

    public int getSnowXp() {
        return snowXp;
    }

    public void increaseSnowXp(int snowXp) {
        this.snowXp += snowXp;
    }

    public int getLevel() {
        if (this.getSnowXp() >= 2000)
            return 10;
        if (this.getSnowXp() >= 1600)
            return 9;
        if (this.getSnowXp() >= 1300)
            return 8;
        if (this.getSnowXp() >= 1000)
            return 7;
        if (this.getSnowXp() >= 750)
            return 6;
        if (this.getSnowXp() >= 500)
            return 5;
        if (this.getSnowXp() >= 300)
            return 4;
        if (this.getSnowXp() >= 150)
            return 3;
        if (this.getSnowXp() >= 50)
            return 2;
        return 1;
    }

    public int diamondsToGive() {
        if (this.getLevel() <= 5) {
            return 5;
        } else if (this.getLevel() > 5 && this.getLevel() != 10){
            return 10;
        }
        return 30;
    }
}