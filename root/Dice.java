
public class Dice {

	public int roll(int maxIndex){
		return (int) (0.5 + Math.random() * maxIndex);
	}
	
}
