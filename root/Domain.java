import java.util.ArrayList;

public class Domain {

	private ArrayList<Word> values;
	
	
	public Domain(int rowNum, int colNum, ArrayList<String> words){
		values = new ArrayList<Word>();
		int size = 0;
		for (String w : words){
			size = w.length() - 1;
			for (int i = 1; i <= rowNum; i++){
				for (int j = 1; j <= colNum; j++){
					if((i + size) <= rowNum){ //	check vertical availability
						values.add(new Word(i, j, (byte) 0, w));
					}
					if((j + size) <= colNum){ //	check horizontal availability
						values.add(new Word(i, j, (byte) 1, w));
					}
				}
			}
		}
	}
	
	//	copy constructor
	public Domain(Domain d){
		values = new ArrayList<Word>(d.getValues());
	}
	
	//	constructor
	public Domain(){
		values = new ArrayList<Word>();
	}

	
	public ArrayList<Word> getValues() {
		return values;
	}
	
	public boolean contains(Word w){
		for (Word word : values){
			if (word.equals(w)) return true;
		}
		return false;
	}
	
	
	//toString
	public void print(){}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public int getSize() {
		return values.size();
	}

	public Word getValue(int index) {
		return values.get(index);
	}

	public void remove(int index) {
		values.remove(index);
		
	}

	public void addValue(Word word) {
		values.add(word);
	}

}
