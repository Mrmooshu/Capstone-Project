package Data;

import java.awt.image.BufferedImage;
import java.math.BigInteger;

import resources.Images;

public class Items {
	
	public class Item {
		private BigInteger Quanity;
		private BufferedImage Icon;
		
		public Item(int ID) {
			Icon = Images.items[ID];
			Quanity = new BigInteger("0");
		}
		public void Increase(BigInteger amount) {
			Quanity = Quanity.add(amount);
		}
		public void Increase(String amount) {
			Quanity = Quanity.add(new BigInteger(amount));
		}
		public void Increase(Long amount) {
			Quanity = Quanity.add(new BigInteger(""+amount));
		}
		public void Increase(int amount) {
			Quanity = Quanity.add(new BigInteger(""+amount));
		}
		public void Decrease(BigInteger amount) {
			Quanity = Quanity.subtract(amount);
		}
		public void Decrease(String amount) {
			Quanity = Quanity.subtract(new BigInteger(amount));
		}
		public void Decrease(Long amount) {
			Quanity = Quanity.subtract(new BigInteger(""+amount));
		}
		public void Decrease(int amount) {
			Quanity = Quanity.subtract(new BigInteger(""+amount));
		}
		public BigInteger Quanity() {
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
		
		itemList[10].Increase("1000");
		itemList[11].Increase("1000000");
		itemList[12].Increase("1000000000");
		itemList[13].Increase("1000000000000");
		itemList[14].Increase("1000000000000000");
		itemList[15].Increase("1000000000000000000");
		itemList[16].Increase("1000000000000000000000");

	}	
}




