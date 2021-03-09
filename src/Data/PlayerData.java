package Data;

import screens.Screen;

public class PlayerData {
	
	private long miningExp,woodcuttingExp,fishingExp;
	private int miningLvl,woodcuttingLvl,fishingLvl;
	
	public PlayerData() {
		loadData();
	}
	
	
	private void loadData() {
//		load save data
	}
	
	public void saveData() {
//		save data
	}
	
	public void addExp(Screen.page_ID skill, int exp) {
		if (skill == Screen.page_ID.MINING) {
			miningExp += exp;
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			woodcuttingExp += exp;
		}
		else if (skill == Screen.page_ID.FISHING) {
			fishingExp += exp;
		}
	}
	
	public int getLevel(Screen.page_ID skill) {
		if (skill == Screen.page_ID.MINING) {
			return miningLvl;
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			return woodcuttingLvl;
		}
		else if (skill == Screen.page_ID.FISHING) {
			return fishingLvl;
		}
		else {
			return 0;
		}
	}
	
}
