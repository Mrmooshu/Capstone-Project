package Data;
 

public class UpgradeTracker {
	
	public enum bonus_type{
		FLAT,ADDITIVE,MULTIPLICATIVE;
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
			case FLAT:
				return text+getBonus()+"flat";
			case ADDITIVE:
				return text+getBonus()+"additive";
			case MULTIPLICATIVE:
				return text+getBonus()+"multiplicative";
			}
			return text+getBonus();
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
//		overide this
		public double getTotal() {
			double total = base;
			for (int i=0; i<upgrades.length; i++) {
				total = total+upgrades[i].getBonus();
			}
			return total;
		}
	}
	
//	stats
	public Stat miningPower,miningCritChance,miningCritMod,gemChance;
	public Stat woodcuttingPower;
	public Stat fishingPower;

//  upgrades
	public Upgrade[][] upgradeList;
	public Upgrade labMining0,labMining1,labMining2;
	public Upgrade labWoodcutting0,labWoodcutting1,labWoodcutting2;
	public Upgrade labFishing0,labFishing1,labFishing2;
	
	
	public UpgradeTracker() {
		
		Upgrade[] miningPowerUpgrades = new Upgrade[] {
				labMining0 = new Upgrade("miningpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
		};
		
		Upgrade[] miningCritChanceUpgrades = new Upgrade[] {
				labMining1 = new Upgrade("miningCrit%+",new int[] {11,11,11},new int[] {20,40,60},.5,bonus_type.ADDITIVE),
		};

		Upgrade[] miningCritModUpgrades = new Upgrade[] {
				labMining2 = new Upgrade("miningCritMod+",new int[] {12,12,12},new int[] {10,20,30},5,bonus_type.ADDITIVE)
		};
		
		Upgrade[] woodcuttingPowerUpgrades = new Upgrade[] {
				labWoodcutting0 = new Upgrade("woodcuttingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
				labWoodcutting1 = new Upgrade("woodcuttingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
				labWoodcutting2 = new Upgrade("woodcuttingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
		};
		Upgrade[] fishingPowerUpgrades = new Upgrade[] {
				labFishing0 = new Upgrade("fishingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
				labFishing1 = new Upgrade("fishingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
				labFishing2 = new Upgrade("fishingpower+",new int[] {10,10,10},new int[] {50,100,150},1,bonus_type.ADDITIVE),
		};

		
		miningPower = new Stat("Mining Power", 1, miningPowerUpgrades);
		
		
		upgradeList = new Upgrade[][] {
			miningPowerUpgrades,miningCritChanceUpgrades,miningCritModUpgrades,
			woodcuttingPowerUpgrades,
			fishingPowerUpgrades
			};
	}
	
	
}
