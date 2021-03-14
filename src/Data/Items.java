package Data;

import java.awt.image.BufferedImage;

import resources.Images;

public class Items {
	
	public class Item {
		private long Quanity;
		private BufferedImage Icon;
		
		public Item(int ID) {
			Icon = Images.items[ID];
			Quanity = 0;
		}
		public void Increase(Long amount) {
			Quanity += amount;
		}
		public void Increase(int amount) {
			Quanity += amount;
		}
		public void Decrease(Long amount) {
			Quanity -= amount;
		}
		public void Decrease(int amount) {
			Quanity -= amount;
		}
		public long Quanity() {
			return Quanity;
		}
		public BufferedImage Icon() {
			return Icon;
		}
	}
	
	int miscStart=0,miscEnd=9,miningStart=10,miningEnd=19,woodcuttingStart=20,woodcuttingEnd=29,fishingStart=30,fishingEnd=39,smithingStart=40,smithingEnd=49,craftingStart=50,craftingEnd=59,cookingStart=60,cookingEnd=69;
	
	public Item[] itemList;
	public int itemCount = 100;

	
	public Items() {
		
		itemList = new Item[itemCount];
		for (int i = 0; i < itemList.length; i++) {
			itemList[i] = new Item(i);
		}
		
	}	
}




