package Data;

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

	private Game game;
	
	public UpgradeTracker(Game game) {
		this.game = game;
//		mining
		miningPowerUpgrades = new Upgrade[] {
				miningPowerBase = new Upgrade("miningpower",1,bonus_type.BASE, PlayerData.MINIGMAX),
				labMining0 = new Upgrade("miningpower",new int[] {10,10,10},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE),
				miningPage0 = new Upgrade("miningpower",new int[] {10,10,10},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE)
		};

		miningSpeedUpgrades = new Upgrade[] {
				miningPage1 = new Upgrade("miningspeed",new int[] {10,10,10},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},1,bonus_type.BASE)
		};
		
		miningCritChanceUpgrades = new Upgrade[] {
				labMining1 = new Upgrade("miningCrit%",new int[] {11,11,11},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.01,bonus_type.BASE),
				miningPage2 = new Upgrade("miningCrit%",new int[] {11,11,11},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.01,bonus_type.BASE)
		};

		miningCritModUpgrades = new Upgrade[] {
				labMining2 = new Upgrade("miningCritMod",new int[] {12,12,12},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE),
				miningPage3 = new Upgrade("miningCritMod",new int[] {12,12,12},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE)
		};
		
//		woodcutting
		woodcuttingPowerUpgrades = new Upgrade[] {
				woodcuttingPowerBase = new Upgrade("woodcuttingpower",1,bonus_type.BASE,PlayerData.WOODCUTTINGMAX),
				labWoodcutting0 = new Upgrade("woodcuttingpower",new int[] {20,20,20},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE),
				woodcuttingPage0 = new Upgrade("woodcuttingpower",new int[] {20,20,20},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE)
		};
		woodcuttingSpeedUpgrades = new Upgrade[] {
				woodcuttingPage1 = new Upgrade("woodcuttingspeed",new int[] {20,20,20},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},1,bonus_type.BASE)
		};
		quickChopChanceUpgrades = new Upgrade[] {
				labWoodcutting1 = new Upgrade("quickChopChance",new int[] {21,21,21},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE),
				woodcuttingPage2 = new Upgrade("quickChopChance",new int[] {21,21,21},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE)

		};
		powerChopChanceUpgrades = new Upgrade[] {
				labWoodcutting2 = new Upgrade("powerChopChance",new int[] {22,22,22},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE),
				woodcuttingPage3 = new Upgrade("powerChopChance",new int[] {22,22,22},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.BASE)

		};
		
//		fishing
		fishingPowerUpgrades = new Upgrade[] {
				fishingPowerBase = new Upgrade("fishingPower",1,bonus_type.BASE,PlayerData.FISHINGMAX),
				labFishing0 = new Upgrade("fishingpower",new int[] {30,30,30},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE)
		};
		fishingSpeedUpgrades = new Upgrade[] {
		};
		frenzyPowerUpgrades = new Upgrade[] {
				labFishing1 = new Upgrade("frenzyChance",new int[] {31,31,31},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE)
		};
		frenzyDurationUpgrades = new Upgrade[] {
				labFishing2 = new Upgrade("frenzyDuration",new int[] {32,32,32},new BigInteger[] {new BigInteger("50"),new BigInteger("250"),new BigInteger("1250")},.05,bonus_type.ADDITIVE)
		};


		
		
		upgradeList = new Upgrade[][] {
			miningPowerUpgrades,miningSpeedUpgrades,miningCritChanceUpgrades,miningCritModUpgrades,
			woodcuttingPowerUpgrades,woodcuttingSpeedUpgrades,quickChopChanceUpgrades,powerChopChanceUpgrades,
			fishingPowerUpgrades,fishingSpeedUpgrades,frenzyPowerUpgrades,frenzyDurationUpgrades
			};
	}
	
	public void initialize() {
//		stats
		miningPower = new Stat("Mining Power", game.PD.getLevel(page_ID.MINING), miningPowerUpgrades);
		miningSpeed = new Stat("Mining Speed", 0, miningSpeedUpgrades);
		miningCritChance = new Stat("Mining Crit %", .0, miningCritChanceUpgrades);
		miningCritMod = new Stat("Mining Crit Power", 1.1, miningCritModUpgrades);

		woodcuttingPower = new Stat("Woodcutting Power", game.PD.getLevel(page_ID.WOODCUTTING), woodcuttingPowerUpgrades);
		woodcuttingSpeed = new Stat("Woodcutting Speed", 0, woodcuttingSpeedUpgrades);
		quickChopChance = new Stat("Quick Chop Chance", 0, quickChopChanceUpgrades);
		powerChopChance = new Stat("Power Chop Chance", 0, powerChopChanceUpgrades);

		
		fishingPower = new Stat("Fishing Power", game.PD.getLevel(page_ID.FISHING), fishingPowerUpgrades);
		fishingSpeed = new Stat("Fishing Speed", 0, fishingSpeedUpgrades);
		frenzyPower = new Stat("Frenzy Power", 5, frenzyPowerUpgrades);
		frenzyDuration = new Stat("Frenzy Duration", 300, frenzyDurationUpgrades);
	}
	
	
}
