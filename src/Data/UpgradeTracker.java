package Data;
 

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
		private int[] itemCostperLevelQuantity;
		public Upgrade(String text, int[] itemCostperLevelID, int[] itemCostperLevelQuantity, double bonusPerLevel, bonus_type bonusType) {
			this.text=text;
			this.itemCostperLevelID = itemCostperLevelID;
			this.itemCostperLevelQuantity = itemCostperLevelQuantity;
			this.levelCurrent = 0;
			this.levelMax = itemCostperLevelID.length;
			this.bonusPerLevel=bonusPerLevel;
			this.bonusType=bonusType;
		}
		
		public String getText() {
			return text;
		}	
		public double getBonus() {
			return bonusPerLevel*levelCurrent;
		}		
		public int[] getCost() {
			return new int[] {itemCostperLevelID[levelCurrent],itemCostperLevelQuantity[levelCurrent]};
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
	public Stat fishingPower,frenzyChance,frenzyDuration,fishingSpeed;

//  upgrades
	public Upgrade[][] upgradeList;
	
	public Upgrade labMining0,labMining1,labMining2;
	public Upgrade labWoodcutting0,labWoodcutting1,labWoodcutting2;
	public Upgrade labFishing0,labFishing1,labFishing2;
	
	public Upgrade miningPage0,miningPage1,miningPage2,miningPage3;
	
	public Upgrade woodcuttingPage0,woodcuttingPage1,woodcuttingPage2,woodcuttingPage3;

	
	
	public UpgradeTracker() {
//		mining
		Upgrade[] miningPowerUpgrades = new Upgrade[] {
				labMining0 = new Upgrade("miningpower",new int[] {10,10,10},new int[] {50,250,1250},.05,bonus_type.ADDITIVE),
				miningPage0 = new Upgrade("miningpower",new int[] {10,10,10},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)
		};

		Upgrade[] miningSpeedUpgrades = new Upgrade[] {
				miningPage1 = new Upgrade("miningspeed",new int[] {10,10,10},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)
		};
		
		Upgrade[] miningCritChanceUpgrades = new Upgrade[] {
				labMining1 = new Upgrade("miningCrit%",new int[] {11,11,11},new int[] {40,200,1000},.01,bonus_type.BASE),
				miningPage2 = new Upgrade("miningCrit%",new int[] {11,11,11},new int[] {40,200,1000},.01,bonus_type.BASE)
		};

		Upgrade[] miningCritModUpgrades = new Upgrade[] {
				labMining2 = new Upgrade("miningCritMod",new int[] {12,12,12},new int[] {30,150,750},.05,bonus_type.BASE),
				miningPage3 = new Upgrade("miningCritMod",new int[] {12,12,12},new int[] {30,150,750},.05,bonus_type.BASE)
		};
		
//		woodcutting
		Upgrade[] woodcuttingPowerUpgrades = new Upgrade[] {
				labWoodcutting0 = new Upgrade("woodcuttingpower",new int[] {20,20,20},new int[] {50,250,1250},.05,bonus_type.ADDITIVE),
				woodcuttingPage0 = new Upgrade("woodcuttingpower",new int[] {20,20,20},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)
		};
		Upgrade[] woodcuttingSpeedUpgrades = new Upgrade[] {
				woodcuttingPage1 = new Upgrade("woodcuttingspeed",new int[] {20,20,20},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)
		};
		Upgrade[] quickChopChanceUpgrades = new Upgrade[] {
				labWoodcutting1 = new Upgrade("quickChopChance",new int[] {21,21,21},new int[] {40,200,1000},.05,bonus_type.ADDITIVE),
				woodcuttingPage2 = new Upgrade("quickChopChance",new int[] {20,20,20},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)

		};
		Upgrade[] powerChopChanceUpgrades = new Upgrade[] {
				labWoodcutting2 = new Upgrade("powerChopChance",new int[] {22,22,22},new int[] {30,150,750},.05,bonus_type.ADDITIVE),
				woodcuttingPage3 = new Upgrade("powerChopChance",new int[] {20,20,20},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)

		};
		
//		fishing
		Upgrade[] fishingPowerUpgrades = new Upgrade[] {
				labFishing0 = new Upgrade("fishingpower",new int[] {30,30,30},new int[] {50,250,1250},.05,bonus_type.ADDITIVE)
		};
		Upgrade[] fishingSpeedUpgrades = new Upgrade[] {
		};
		Upgrade[] FrenzyChanceUpgrades = new Upgrade[] {
				labFishing1 = new Upgrade("frenzyChance",new int[] {31,31,31},new int[] {40,200,1000},.05,bonus_type.ADDITIVE)
		};
		Upgrade[] FrenzyDurationUpgrades = new Upgrade[] {
				labFishing2 = new Upgrade("frenzyDuration",new int[] {32,32,32},new int[] {30,150,750},.05,bonus_type.ADDITIVE)
		};

//		stats
		miningPower = new Stat("Mining Power", 100, miningPowerUpgrades);
		miningSpeed = new Stat("Mining Speed", 0, miningSpeedUpgrades);
		miningCritChance = new Stat("Mining Crit %", .0, miningCritChanceUpgrades);
		miningCritMod = new Stat("Mining Crit Power", 1.1, miningCritModUpgrades);

		woodcuttingPower = new Stat("Woodcutting Power", 100, woodcuttingPowerUpgrades);
		woodcuttingSpeed = new Stat("Woodcutting Speed", 0, woodcuttingSpeedUpgrades);
		quickChopChance = new Stat("Quick Chop Chance", .0, quickChopChanceUpgrades);
		powerChopChance = new Stat("Power Chop Chance", .0, powerChopChanceUpgrades);

		
		fishingPower = new Stat("Fishing Power", 1, fishingPowerUpgrades);
		fishingSpeed = new Stat("Fishing Speed", 0, fishingSpeedUpgrades);
		frenzyChance = new Stat("Frenzy Chance", 1, FrenzyChanceUpgrades);
		frenzyDuration = new Stat("Frenzy Duration", 1, FrenzyDurationUpgrades);
		
		
		upgradeList = new Upgrade[][] {
			miningPowerUpgrades,miningSpeedUpgrades,miningCritChanceUpgrades,miningCritModUpgrades,
			woodcuttingPowerUpgrades,woodcuttingSpeedUpgrades,quickChopChanceUpgrades,powerChopChanceUpgrades,
			fishingPowerUpgrades,fishingSpeedUpgrades,FrenzyChanceUpgrades,FrenzyDurationUpgrades
			};
	}
	
	
}
