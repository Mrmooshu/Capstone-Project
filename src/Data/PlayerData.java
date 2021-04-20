package Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import Data.Items.Item;
import main.Game;
import screens.FishingScreen;
import screens.MiningScreen;
import screens.Screen;
import screens.Screen.page_ID;
import screens.WoodcuttingScreen;

public class PlayerData {

	String saveFile = "src/Data/Save/savefile.txt";
	Game game;
	String lastTimePlayed;
	public BigInteger miningExp,woodcuttingExp,fishingExp;
	private int miningLvl,woodcuttingLvl,fishingLvl;
	public static final int MINIGMAX=1000,WOODCUTTINGMAX=1000,FISHINGMAX=1000;
	public boolean saveToggle = true;
	
	public Item[] miningItems;
	public Item[] woodcuttingItems;
	public Item[] fishingItems;
	
	public MiningScreen.Ore oreSelected;
	public WoodcuttingScreen.Wood treeSelected;
	public FishingScreen.Fish fishSelected;
	public int quickChopStack;
	public int frenzyMeter;
	public int frenzyLevel;
	
	public PlayerData(Game game) {
		this.game = game;
		miningExp = new BigInteger("0");
		woodcuttingExp = new BigInteger("0");
		fishingExp = new BigInteger("0");
		miningLvl = woodcuttingLvl = fishingLvl = 1;
		quickChopStack = 0;
		frenzyMeter = 0;
		frenzyLevel = 0;
		oreSelected = MiningScreen.Ore.COPPER;
		treeSelected = WoodcuttingScreen.Wood.OAK;
		fishSelected = FishingScreen.Fish.FISH1;
	}
	
	public void initialize() {
		loadData();
	}


	public void resetData() {
		game.inventory=new Items();
		game.UT=new UpgradeTracker(game);
		game.UT.initialize();
		game.PD = new PlayerData(game);
		saveData();
	}
	
	
	private void loadData() {
//		load save data
		if (saveToggle) {
			try {

				BufferedReader br = new BufferedReader(new FileReader(saveFile));
//				exact time since when game last saved
				lastTimePlayed = br.readLine();
//				selections
				oreSelected = oreRead(br.readLine());
				treeSelected = treeRead(br.readLine());
				fishSelected = fishRead(br.readLine());
//				exp
				miningExp = new BigInteger(br.readLine());
				woodcuttingExp = new BigInteger(br.readLine());
				fishingExp = new BigInteger(br.readLine());
//				other
				quickChopStack = Integer.parseInt(br.readLine());
				frenzyMeter = Integer.parseInt(br.readLine());
				frenzyLevel = Integer.parseInt(br.readLine());
//				items
				for (int i = 0; i < game.inventory.itemList.length; i++) {
					game.inventory.itemList[i].Increase(new BigInteger((br.readLine())));
				}
//				upgrade levels
				for (int i = 0; i < game.UT.upgradeList.length; i++) {
					for (int j = 0; j < game.UT.upgradeList[i].length; j++) {
						game.UT.upgradeList[i][j].setCurrentLevel(Integer.parseInt(br.readLine()));
					}
				}
				br.close();
				System.out.println("data loaded ");
			}
			catch(Exception e) {
				lastTimePlayed = LocalDateTime.now().toString();
				System.out.println("could not load data ");
			}
		}

	}
	
	public void saveData() {
//		save data
		try {
			lastTimePlayed = LocalDateTime.now().toString();
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
//			exact time when game saves
			bw.write(lastTimePlayed); bw.newLine();
//			selections
			bw.write(oreSelected.name()); bw.newLine();
			bw.write(treeSelected.name()); bw.newLine();
			bw.write(fishSelected.name()); bw.newLine();
//			exp
			bw.write(""+miningExp); bw.newLine();
			bw.write(""+woodcuttingExp); bw.newLine();
			bw.write(""+fishingExp); bw.newLine();
//			other
			bw.write(""+quickChopStack); bw.newLine();
			bw.write(""+frenzyMeter); bw.newLine();
			bw.write(""+frenzyLevel); bw.newLine();
//			items
			for (int i = 0; i < game.inventory.itemList.length; i++) {
				bw.write(game.inventory.itemList[i].Quanity().toString()); bw.newLine();
			}
//			upgrade levels
			for (int i = 0; i < game.UT.upgradeList.length; i++) {
				for (int j = 0; j < game.UT.upgradeList[i].length; j++) {
					bw.write(""+game.UT.upgradeList[i][j].getCurrentLevel());bw.newLine();
				}
			}
			bw.close();
			System.out.println("data saved ");
		}
		catch(Exception e) {
			System.out.println("could not save data ");
		}
	}
	
	public String calculateAwayTime() {	
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = LocalDateTime.parse(lastTimePlayed);
		long years = ChronoUnit.YEARS.between(start, end);
		start = start.plusYears(years);
		long days = ChronoUnit.DAYS.between(start, end);
		start = start.plusDays(days);
		long hours = ChronoUnit.HOURS.between(start, end);
		start = start.plusHours(hours);
		long minutes = ChronoUnit.MINUTES.between(start, end);
		start = start.plusMinutes(minutes);
		long seconds = ChronoUnit.SECONDS.between(start, end);
		System.out.println(years+" "+days+" "+hours+" "+minutes+" "+seconds);
		miningItems = MiningScreen.simulateMineOre(game, years, days, hours, minutes);
		woodcuttingItems = WoodcuttingScreen.simulateChopWood(game, years, days, hours, minutes);
		fishingItems = FishingScreen.simulateCatchFish(game, years, days, hours, minutes);
		String returnString = "";
		if (years > 0) {
			returnString+="Years:"+years;
		}
		if (days > 0) {
			returnString+=" Days:"+days;
		}
		if (hours > 0) {
			returnString+=" Hours:"+hours;
		}
		if (minutes > 0) {
			returnString+=" Minutes:"+minutes;
		}
		if (returnString.equals("")) {
			returnString = "less than a minute";
		}
		return returnString;
	}
	
	public void addExp(Screen.page_ID skill, BigInteger exp) {
		if (skill == Screen.page_ID.MINING) {
			miningExp = miningExp.add(exp);
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			woodcuttingExp = woodcuttingExp.add(exp);
		}
		else if (skill == Screen.page_ID.FISHING) {
			fishingExp = fishingExp.add(exp);
		}
	}
	
	public BigInteger getExp(Screen.page_ID skill) {
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
			return new BigInteger("0");
		}
	}
	
	public void incrementLevel(Screen.page_ID skill) {
		if (skill == Screen.page_ID.MINING) {
			miningExp = miningExp.subtract(getNextLevelReq(page_ID.MINING));
			miningLvl++;
			game.UT.miningPowerBase.setCurrentLevel(miningLvl);
			saveData();
		}
		else if (skill == Screen.page_ID.WOODCUTTING) {
			woodcuttingExp = woodcuttingExp.subtract(getNextLevelReq(page_ID.WOODCUTTING));
			woodcuttingLvl++;
			game.UT.woodcuttingPowerBase.setCurrentLevel(woodcuttingLvl);
			saveData();

		}
		else if (skill == Screen.page_ID.FISHING) {
			fishingExp = fishingExp.subtract(getNextLevelReq(page_ID.FISHING));
			fishingLvl++;
			game.UT.fishingPowerBase.setCurrentLevel(fishingLvl);
			saveData();
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
	
	public BigInteger getNextLevelReq(Screen.page_ID skill) {
		long level = getLevel(skill);
		BigInteger result = new BigInteger("100");
		while (level!=0) {
			result.add(result.divide(new BigInteger("10")));
			level--;
		}
		return result;
	}
	
	private MiningScreen.Ore oreRead(String name){
		MiningScreen.Ore[] ores = MiningScreen.Ore.values();
		for (MiningScreen.Ore ore: ores) {
			if (ore.name().equals(name)) {
				return ore;
			}
		}
		return MiningScreen.Ore.COPPER;
	}
	private WoodcuttingScreen.Wood treeRead(String name){
		WoodcuttingScreen.Wood[] trees = WoodcuttingScreen.Wood.values();
		for (WoodcuttingScreen.Wood tree: trees) {
			if (tree.name().equals(name)) {
				return tree;
			}
		}
		return WoodcuttingScreen.Wood.OAK;
	}
	private FishingScreen.Fish fishRead(String name){
		FishingScreen.Fish[] fishies = FishingScreen.Fish.values();
		for (FishingScreen.Fish fish: fishies) {
			if (fish.name().equals(name)) {
				return fish;
			}
		}
		return FishingScreen.Fish.FISH1;
	}

	
	
	
	
	
	
	
	
}
