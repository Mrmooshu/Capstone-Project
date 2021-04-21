package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

import main.Game;
import screens.Screen.page_ID;

public class UpgradeTracker {
		
	public enum bonus_type{
		BASE,ADDITIVE,MULTIPLICATIVE,FLAT;
	}
	
	public class Upgrade {
		private String text;
		private int levelCurrent;
		private int levelMax;
		private bonus_type bonusType;
		private double bonusPerLevel;
		private int[] itemCostperLevelID;
		private BigInteger[] itemCostperLevelQuantity;
		public Upgrade(String text, int[] itemCostperLevelID, BigInteger[] itemCostperLevelQuantity, double bonusPerLevel, bonus_type bonusType) {
			this.text=text;
			this.itemCostperLevelID = itemCostperLevelID;
			this.itemCostperLevelQuantity = itemCostperLevelQuantity;
			this.levelCurrent = 0;
			this.levelMax = itemCostperLevelID.length;
			this.bonusPerLevel=bonusPerLevel;
			this.bonusType=bonusType;
		}
		public Upgrade(String text, double bonusPerLevel, bonus_type bonusType, int levelMax) {
			this.text=text;
			this.levelCurrent = 0;
			this.levelMax = levelMax;
			this.bonusPerLevel=bonusPerLevel;
			this.bonusType=bonusType;
		}
		
		public String getText() {
			return text;
		}	
		public double getBonus() {
			return bonusPerLevel*levelCurrent;
		}		
		public int getCostID() {
			return itemCostperLevelID[levelCurrent];
		}
		public BigInteger getCostQuanity() {
			return itemCostperLevelQuantity[levelCurrent];
		}
		public int getCurrentLevel() {
			return levelCurrent;
		}
		public void setCurrentLevel(int n) {
			levelCurrent = n;
		}
		public int getLevelMax() {
			return levelMax;
		}
		public bonus_type getBonusType() {
			return bonusType;
		}
		public String getBonusText() {
			switch(bonusType) {
			case BASE:
				return (double)Math.round(getBonus()*100)/100+" base";
			case ADDITIVE:
				return (double)Math.round(getBonus()*100)/100+" additive";
			case MULTIPLICATIVE:
				return (double)Math.round(getBonus()*100)/100+" multiplicative";
			case FLAT:
				return (double)Math.round(getBonus()*100)/100+" flat";
				}
			return (double)Math.round(getBonus()*100)/100+"";
		}
	}
	
	public class Stat {
		private String name;
		private double base;
		private Upgrade[] upgrades;
		public Stat(String name, double base, Upgrade[] upgrades) {
			this.name=name;
			this.base=base;
			this.upgrades=upgrades;
		}
		public String getName() {
			return name;
		}
		public double getTotal() {
			double baseTotal = base;
			double additive = 1;
			double multiplicative = 1;
			double flat = 0;
			for (int i=0; i<upgrades.length; i++) {
				if (upgrades[i].getBonusType()==bonus_type.BASE) {
					baseTotal = baseTotal+upgrades[i].getBonus();
				}
				if (upgrades[i].getBonusType()==bonus_type.ADDITIVE) {
					additive = additive+upgrades[i].getBonus();
				}
				if (upgrades[i].getBonusType()==bonus_type.MULTIPLICATIVE) {
					multiplicative = multiplicative*(1+upgrades[i].getBonus());
				}
				if (upgrades[i].getBonusType()==bonus_type.FLAT) {
					flat = flat+upgrades[i].getBonus();
				}
			}
			return baseTotal*additive*multiplicative+flat;
		}
		public String getTotalText() {
			return (double)Math.round(getTotal()*100)/100+"";
		}
	}
	
//	stats
	public Stat miningPower,miningCritChance,miningCritMod,gemChance,miningSpeed;
	public Stat woodcuttingPower,quickChopChance,powerChopChance,woodcuttingSpeed;
	public Stat fishingPower,frenzyPower,frenzyDuration,fishingSpeed;

//  upgrades
	public Upgrade[][] upgradeList;
	
	
	public Upgrade miningPowerBase,woodcuttingPowerBase,fishingPowerBase;
	
	public Upgrade[] miningPowerUpgrades,miningSpeedUpgrades,miningCritChanceUpgrades,miningCritModUpgrades;
	public Upgrade[] woodcuttingPowerUpgrades,woodcuttingSpeedUpgrades,quickChopChanceUpgrades,powerChopChanceUpgrades;
	public Upgrade[] fishingPowerUpgrades,fishingSpeedUpgrades,frenzyPowerUpgrades,frenzyDurationUpgrades;
	
	public Upgrade labMining0,labMining1,labMining2;
	public Upgrade labWoodcutting0,labWoodcutting1,labWoodcutting2;
	public Upgrade labFishing0,labFishing1,labFishing2;
	
	public Upgrade miningPage0,miningPage1,miningPage2,miningPage3;
	public Upgrade woodcuttingPage0,woodcuttingPage1,woodcuttingPage2,woodcuttingPage3;
	public Upgrade fishingPage0,fishingPage1,fishingPage2,fishingPage3;

	private Game game;
	
	public UpgradeTracker(Game game) {
		this.game = game;
//		mining
		int[][] miningPowerItemData = loadItemData("src/Data/UpgradeData/mining/miningPowerItemLevel.csv");
		BigInteger[][] miningPowerCostData = loadQuanityData("src/Data/UpgradeData/mining/miningPowerCostLevel.csv");
		miningPowerUpgrades = new Upgrade[] {
				miningPowerBase = new Upgrade("miningpower",1,bonus_type.BASE, PlayerData.MINIGMAX),
				labMining0 = new Upgrade("miningpower",miningPowerItemData[0],miningPowerCostData[0],.05,bonus_type.ADDITIVE),
				miningPage0 = new Upgrade("miningpower",miningPowerItemData[1],miningPowerCostData[1],.01,bonus_type.MULTIPLICATIVE)
		};

		int[][] miningSpeedItemData = loadItemData("src/Data/UpgradeData/mining/miningSpeedItemLevel.csv");
		BigInteger[][] miningSpeedCostData = loadQuanityData("src/Data/UpgradeData/mining/miningSpeedCostLevel.csv");
		miningSpeedUpgrades = new Upgrade[] {
				miningPage1 = new Upgrade("miningspeed",miningSpeedItemData[0],miningSpeedCostData[0],1,bonus_type.BASE)
		};
		
		int[][] miningCritChanceItemData = loadItemData("src/Data/UpgradeData/mining/miningCritChanceItemLevel.csv");
		BigInteger[][] miningCritChanceCostData = loadQuanityData("src/Data/UpgradeData/mining/miningCritChanceCostLevel.csv");
		miningCritChanceUpgrades = new Upgrade[] {
				labMining1 = new Upgrade("miningCrit%",miningCritChanceItemData[0],miningCritChanceCostData[0],.01,bonus_type.BASE),
				miningPage2 = new Upgrade("miningCrit%",miningCritChanceItemData[1],miningCritChanceCostData[1],.01,bonus_type.BASE)
		};

		int[][] miningCritModItemData = loadItemData("src/Data/UpgradeData/mining/miningCritModItemLevel.csv");
		BigInteger[][] miningCritModCostData = loadQuanityData("src/Data/UpgradeData/mining/miningCritModCostLevel.csv");
		miningCritModUpgrades = new Upgrade[] {
				labMining2 = new Upgrade("miningCritMod",miningCritModItemData[0],miningCritModCostData[0],.01,bonus_type.BASE),
				miningPage3 = new Upgrade("miningCritMod",miningCritModItemData[1],miningCritModCostData[1],.01,bonus_type.BASE)
		};
		
//		woodcutting
		int[][] woodcuttingPowerItemData = loadItemData("src/Data/UpgradeData/woodcutting/woodcuttingPowerItemLevel.csv");
		BigInteger[][] woodcuttingPowerCostData = loadQuanityData("src/Data/UpgradeData/woodcutting/woodcuttingPowerCostLevel.csv");
		woodcuttingPowerUpgrades = new Upgrade[] {
				woodcuttingPowerBase = new Upgrade("woodcuttingpower",1,bonus_type.BASE,PlayerData.WOODCUTTINGMAX),
				labWoodcutting0 = new Upgrade("woodcuttingpower",woodcuttingPowerItemData[0],woodcuttingPowerCostData[0],.05,bonus_type.ADDITIVE),
				woodcuttingPage0 = new Upgrade("woodcuttingpower",woodcuttingPowerItemData[1],woodcuttingPowerCostData[1],.01,bonus_type.MULTIPLICATIVE)
		};
		
		int[][] woodcuttingSpeedItemData = loadItemData("src/Data/UpgradeData/woodcutting/woodcuttingSpeedItemLevel.csv");
		BigInteger[][] woodcuttingSpeedCostData = loadQuanityData("src/Data/UpgradeData/woodcutting/woodcuttingSpeedCostLevel.csv");
		woodcuttingSpeedUpgrades = new Upgrade[] {
				woodcuttingPage1 = new Upgrade("woodcuttingspeed",woodcuttingSpeedItemData[0],woodcuttingSpeedCostData[0],1,bonus_type.BASE)
		};
		
		int[][] quickChopChanceItemData = loadItemData("src/Data/UpgradeData/woodcutting/quickChopChanceItemLevel.csv");
		BigInteger[][] quickChopChanceCostData = loadQuanityData("src/Data/UpgradeData/woodcutting/quickChopChanceCostLevel.csv");
		quickChopChanceUpgrades = new Upgrade[] {
				labWoodcutting1 = new Upgrade("quickChopChance",quickChopChanceItemData[0],quickChopChanceCostData[0],.01,bonus_type.BASE),
				woodcuttingPage2 = new Upgrade("quickChopChance",quickChopChanceItemData[1],quickChopChanceCostData[1],.01,bonus_type.BASE)

		};
		
		int[][] powerChopChanceItemData = loadItemData("src/Data/UpgradeData/woodcutting/powerChopChanceItemLevel.csv");
		BigInteger[][] powerChopChanceCostData = loadQuanityData("src/Data/UpgradeData/woodcutting/powerChopChanceCostLevel.csv");
		powerChopChanceUpgrades = new Upgrade[] {
				labWoodcutting2 = new Upgrade("powerChopChance",powerChopChanceItemData[0],powerChopChanceCostData[0],.01,bonus_type.BASE),
				woodcuttingPage3 = new Upgrade("powerChopChance",powerChopChanceItemData[1],powerChopChanceCostData[1],.01,bonus_type.BASE)

		};
		
//		fishing
		int[][] fishingPowerItemData = loadItemData("src/Data/UpgradeData/fishing/fishingPowerItemLevel.csv");
		BigInteger[][] fishingPowerCostData = loadQuanityData("src/Data/UpgradeData/fishing/fishingPowerCostLevel.csv");
		fishingPowerUpgrades = new Upgrade[] {
				fishingPowerBase = new Upgrade("fishingPower",1,bonus_type.BASE,PlayerData.FISHINGMAX),
				labFishing0 = new Upgrade("fishingpower",fishingPowerItemData[0],fishingPowerCostData[0],.05,bonus_type.ADDITIVE),
				fishingPage0 = new Upgrade("fishingpower",fishingPowerItemData[1],fishingPowerCostData[1],.01,bonus_type.MULTIPLICATIVE)
		};
		
		int[][] fishingSpeedItemData = loadItemData("src/Data/UpgradeData/fishing/fishingSpeedItemLevel.csv");
		BigInteger[][] fishingSpeedCostData = loadQuanityData("src/Data/UpgradeData/fishing/fishingSpeedCostLevel.csv");
		fishingSpeedUpgrades = new Upgrade[] {
				fishingPage1 = new Upgrade("fishingspeed",fishingSpeedItemData[0],fishingSpeedCostData[0],1,bonus_type.BASE)

		};
		
		int[][] frenzyPowerItemData = loadItemData("src/Data/UpgradeData/fishing/frenzyPowerItemLevel.csv");
		BigInteger[][] frenzyPowerCostData = loadQuanityData("src/Data/UpgradeData/fishing/frenzyPowerCostLevel.csv");
		frenzyPowerUpgrades = new Upgrade[] {
				labFishing1 = new Upgrade("frenzyPower",frenzyPowerItemData[0],frenzyPowerCostData[0],.05,bonus_type.ADDITIVE),
				fishingPage2 = new Upgrade("frenzyPower",frenzyPowerItemData[1],frenzyPowerCostData[1],.01,bonus_type.MULTIPLICATIVE)

		};
		
		int[][] frenzyDurationItemData = loadItemData("src/Data/UpgradeData/fishing/frenzyDurationItemLevel.csv");
		BigInteger[][] frenzyDurationCostData = loadQuanityData("src/Data/UpgradeData/fishing/frenzyDurationCostLevel.csv");
		frenzyDurationUpgrades = new Upgrade[] {
				labFishing2 = new Upgrade("frenzyDuration",frenzyDurationItemData[0],frenzyDurationCostData[0],.05,bonus_type.ADDITIVE),
				fishingPage3 = new Upgrade("frenzyDuration",frenzyDurationItemData[1],frenzyDurationCostData[1],.01,bonus_type.MULTIPLICATIVE)

		};


		
		
		upgradeList = new Upgrade[][] {
			miningPowerUpgrades,miningSpeedUpgrades,miningCritChanceUpgrades,miningCritModUpgrades,
			woodcuttingPowerUpgrades,woodcuttingSpeedUpgrades,quickChopChanceUpgrades,powerChopChanceUpgrades,
			fishingPowerUpgrades,fishingSpeedUpgrades,frenzyPowerUpgrades,frenzyDurationUpgrades
			};
	}
	
	public void initialize() {
//		stats
		miningPower = new Stat("Mining Power", 0, miningPowerUpgrades);
		miningSpeed = new Stat("Mining Speed", 0, miningSpeedUpgrades);
		miningCritChance = new Stat("Mining Crit %", 0, miningCritChanceUpgrades);
		miningCritMod = new Stat("Mining Crit Power", 1, miningCritModUpgrades);

		woodcuttingPower = new Stat("Woodcutting Power", 0, woodcuttingPowerUpgrades);
		woodcuttingSpeed = new Stat("Woodcutting Speed", 200, woodcuttingSpeedUpgrades);
		quickChopChance = new Stat("Quick Chop Chance", 1, quickChopChanceUpgrades);
		powerChopChance = new Stat("Power Chop Chance", 0, powerChopChanceUpgrades);

		
		fishingPower = new Stat("Fishing Power", 0, fishingPowerUpgrades);
		fishingSpeed = new Stat("Fishing Speed", 0, fishingSpeedUpgrades);
		frenzyPower = new Stat("Frenzy Power", 5, frenzyPowerUpgrades);
		frenzyDuration = new Stat("Frenzy Duration", 300, frenzyDurationUpgrades);
//		other
		miningPowerBase.setCurrentLevel(game.PD.getLevel(page_ID.MINING));
		woodcuttingPowerBase.setCurrentLevel(game.PD.getLevel(page_ID.WOODCUTTING));
		fishingPowerBase.setCurrentLevel(game.PD.getLevel(page_ID.FISHING));
	}
	
	private int[][] loadItemData(String IDFile) {
		int[][] itemData = new int[2][];
		try {
			int i = 0;
			String row;
			BufferedReader br1 = new BufferedReader(new FileReader(IDFile));
			while ((row = br1.readLine()) != null) {
				String[] data = row.split(",");
				itemData[i] = new int[data.length];
				for (int j = 0; j < data.length; j++) {
					itemData[i][j] = Integer.parseInt(data[j]);
				}
				i++;
			}
			br1.close();			
		}
		catch(Exception e) {
			System.out.println("could not load item data");
		}
		return itemData;
	}
	
	private BigInteger[][] loadQuanityData(String costFile) {
		BigInteger[][] quanityData = new BigInteger[2][];
		try {
			int i = 0;
			String row;
			BufferedReader br2 = new BufferedReader(new FileReader(costFile));
			while ((row = br2.readLine()) != null) {
				String[] data = row.split(",");
				quanityData[i] = new BigInteger[data.length];
				for (int j = 0; j < data.length; j++) {
					quanityData[i][j] = new BigInteger(data[j]);
				}
				i++;
			}
			br2.close();
		}
		catch(Exception e) {
			System.out.println("could not load cost data");
		}
		return quanityData;
	}
	
}
