package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import main.Game;
import screens.Screen;
import screens.Screen.page_ID;

public class PlayerData {

	String saveFile = "src/Data/Save/savefile.txt";
	Game game;
	private long miningExp,woodcuttingExp,fishingExp;
	private int miningLvl,woodcuttingLvl,fishingLvl;
	
	public PlayerData(Game game) {
		this.game = game;
		miningExp = woodcuttingExp = fishingExp = 0;
		loadData();
	}


	public void resetData() {
		miningExp = woodcuttingExp = fishingExp = 0;
		game.inventory=new Items();
		game.UT=new UpgradeTracker();
		saveData();
	}
	
	
	private void loadData() {
//		load save data
		try {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
//			exp
			miningExp = Long.parseLong(br.readLine());
			woodcuttingExp = Long.parseLong(br.readLine());
			fishingExp = Long.parseLong(br.readLine());
//			items
			for (int i = 0; i < game.inventory.itemList.length; i++) {
				game.inventory.itemList[i].Increase(Long.parseLong(br.readLine()));
			}
//			upgrade levels
			for (int i = 0; i < game.UT.upgradeList.length; i++) {
				for (int j = 0; j < game.UT.upgradeList[i].length; j++) {
					game.UT.upgradeList[i][j].setCurrentLevel(Integer.parseInt(br.readLine()));
				}
			}
			br.close();
			System.out.print("data loaded ");
		}
		catch(Exception e) {
			System.out.print("could not load data ");
		}
	}
	
	public void saveData() {
//		save data
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
//			exp
			bw.write(""+miningExp); bw.newLine();
			bw.write(""+woodcuttingExp); bw.newLine();
			bw.write(""+fishingExp); bw.newLine();
//			items
			for (int i = 0; i < game.inventory.itemList.length; i++) {
				bw.write(""+game.inventory.itemList[i].Quanity()); bw.newLine();
			}
//			upgrade levels
			for (int i = 0; i < game.UT.upgradeList.length; i++) {
				for (int j = 0; j < game.UT.upgradeList[i].length; j++) {
					bw.write(""+game.UT.upgradeList[i][j].getCurrentLevel());bw.newLine();
				}
			}
			bw.close();
			System.out.print("data saved ");
		}
		catch(Exception e) {
			System.out.print("could not save data ");
		}
	}
	
	public void addExp(Screen.page_ID skill, double exp) {
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
	
	public long getExp(Screen.page_ID skill) {
		if (skill == Screen.page_ID.MINING) {
			return miningExp;
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			return woodcuttingExp;
		}
		else if (skill == Screen.page_ID.FISHING) {
			return fishingExp;
		}
		else {
			return 0;
		}
	}
	
	public void incrementLevel(Screen.page_ID skill) {
		if (skill == Screen.page_ID.MINING) {
			miningExp = miningExp-getNextLevelReq(page_ID.MINING);
			miningLvl++;
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			woodcuttingExp = woodcuttingExp-getNextLevelReq(page_ID.WOODCUTTING);

			woodcuttingLvl++;

		}
		else if (skill == Screen.page_ID.FISHING) {
			fishingExp = fishingExp-getNextLevelReq(page_ID.FISHING);
			fishingLvl++;

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
	
	public long getNextLevelReq(Screen.page_ID skill) {
		long level = getLevel(skill);
		long result = 100;
		while (level!=0) {
			result += Math.ceil(result*0.1);
			level--;
		}
		return result;
	}

	
	
	
	
	
	
	
	
}
