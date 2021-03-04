package Data;

import java.awt.image.BufferedImage;

import resources.Images;

public class Items {
	
	public class Item {
		private int Quanity;
		private BufferedImage Icon;
		private int ID;
		private String name;
		
		public Item(BufferedImage Icon, String name, int ID) {
			this.Icon = Icon;
			this.name = name;
			this.ID = ID;
			Quanity = 0;
		}
		public void Increase(int amount) {
			Quanity += amount;
		}
		public void Decrease(int amount) {
			Quanity -= amount;
		}
		public int Quanity() {
			return Quanity;
		}
		public String Name() {
			return name;
		}
		public BufferedImage Icon() {
			return Icon;
		}
		public int ID() {
			return ID;
		}
	}
	public Item[] misc_list;
	public Item[] mining_list;
	public Item[] woodcutting_list;
	public Item[] fishing_list;

	
	public Items() {
		misc_list = new Item[] {new Item(Images.miscItems[0],"power",0)};
		
		mining_list = new Item[] {new Item(Images.miningItems[0],"copper",0),new Item(Images.miningItems[1],"iron",1),
				new Item(Images.miningItems[2],"copper",2),new Item(Images.miningItems[3],"copper",3),
				new Item(Images.miningItems[4],"copper",4),new Item(Images.miningItems[5],"copper",5)};
		
		woodcutting_list = new Item[] {new Item(Images.woodcuttingItems[0],"oak",0),new Item(Images.woodcuttingItems[1],"birch",1),
				new Item(Images.woodcuttingItems[2],"teak",2),new Item(Images.woodcuttingItems[3],"willow",3),
				new Item(Images.woodcuttingItems[4],"mahogany",4),new Item(Images.woodcuttingItems[5],"maple",5)};
		
		fishing_list = new Item[] {new Item(Images.fishingItems[0],"fish1",0),new Item(Images.fishingItems[1],
				"fish2",1),new Item(Images.fishingItems[2],"fish3",2),new Item(Images.fishingItems[3],"fish4",
						3),new Item(Images.fishingItems[4],"fish5",4),new Item(Images.fishingItems[5],"fish6",5)};
		mining_list[0].Increase(325948);
		woodcutting_list[5].Increase(69);
		fishing_list[3].Increase(84750);
	}	
}




