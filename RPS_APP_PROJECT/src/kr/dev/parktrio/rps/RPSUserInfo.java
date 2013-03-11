package kr.dev.parktrio.rps;

import java.io.Serializable;

public class RPSUserInfo implements Serializable {
	private static final long serialVersionUID = 3138339024285724073L;

	private static final RPSUserInfo instance = new RPSUserInfo();

	private final int MAX_LEVEL = 50;

	private int id;
	private int level;
	private int maxCombo;
	private int totalGame;
	private int maxScore;
	//private RPSGameItem items;
	//private RPSUsedGameItemInfo;
	//private RPSUsedGameMoneyInfo;

	private RPSUserInfo() {}

	public static RPSUserInfo getInstance() {
		return instance;
	}

	public void setID( int id ) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void setLevel( int level ) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setMaxCombo( int maxCombo ) {
		this.maxCombo = maxCombo;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public void setTotalGame( int totalGame ) {
		this.totalGame = totalGame;
	}

	public int getTotalGame() {
		return totalGame;
	}

	public void setMaxScore( int maxScore ) {
		this.maxScore = maxScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

}
