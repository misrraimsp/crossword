import java.util.ArrayList;

public class Word {

	private byte dir;	// (0: [Vertical] grow in row) (1: [Horizontal] grow in column)
	private int startRow, startCol;
	//private Cell init;
	private String str;
	
	
	
	public Word(int row, int col, byte dir, String word){
		setDir(dir);
		setStartRow(row);
		setStartCol(col);
		setStr(word);
	}
	
	public ArrayList<Cell> getInfluenceCells(){
		ArrayList<Cell> cells = new ArrayList<Cell>();
		//	vertical word
		if(dir == 0){
			for (int i = 0; i < str.length(); i++){
				cells.add(new Cell(startRow + i, startCol - 1, ' '));
				cells.add(new Cell(startRow + i, startCol, str.charAt(i)));
				cells.add(new Cell(startRow + i, startCol + 1, ' '));
			}
			cells.add(new Cell(startRow - 1, startCol, ' '));
			cells.add(new Cell(startRow + str.length(), startCol, ' '));
		}
		//	horizontal word
		else {
			for (int i = 0; i < str.length(); i++){
				cells.add(new Cell(startRow - 1, startCol + i, ' '));
				cells.add(new Cell(startRow, startCol + i, str.charAt(i)));
				cells.add(new Cell(startRow + 1, startCol + i, ' '));
			}
			cells.add(new Cell(startRow, startCol - 1, ' '));
			cells.add(new Cell(startRow, startCol + str.length(), ' '));
		}
		return cells;
	}
	
	public ArrayList<Cell> getCoreCells(){
		ArrayList<Cell> core = new ArrayList<Cell>();
		//	vertical word
		if (dir == 0){
			for (int i = 0; i < str.length(); i++){
				core.add(new Cell(startRow + i, startCol, str.charAt(i)));
			}
		}
		//	horizontal word
		else {
			for (int i = 0; i < str.length(); i++){
				core.add(new Cell(startRow, startCol + i, str.charAt(i)));
			}
		}
		return core;
	}
	
	public ArrayList<Cell> getBorderCells(){
		ArrayList<Cell> border = new ArrayList<Cell>();
		//	vertical word
		if(dir == 0){
			for (int i = 0; i < str.length(); i++){
				border.add(new Cell(startRow + i, startCol - 1, ' '));
				border.add(new Cell(startRow + i, startCol + 1, ' '));
			}
			border.add(new Cell(startRow - 1, startCol, ' '));
			border.add(new Cell(startRow + str.length(), startCol, ' '));
		}
		//	horizontal word
		else {
			for (int i = 0; i < str.length(); i++){
				border.add(new Cell(startRow - 1, startCol + i, ' '));
				border.add(new Cell(startRow + 1, startCol + i, ' '));
			}
			border.add(new Cell(startRow, startCol - 1, ' '));
			border.add(new Cell(startRow, startCol + str.length(), ' '));
		}
		return border;
	}
	
	public boolean isCompatible(Word w){
		if(this.getStr().equals(w.getStr())) return false; //	trivial case
		ArrayList<Cell> wCore = w.getCoreCells();
		//	perpendicular words
		if (this.getDir() != w.getDir()){
			ArrayList<Cell> coreIntersec = getIntersec(this.getCoreCells(), wCore);
			if(!coreIntersec.isEmpty()){
				return (coreIntersec.get(0).getLetter() == coreIntersec.get(1).getLetter());
			}
			ArrayList<Cell> borderIntersec = getIntersec(this.getBorderCells(), wCore);
			return borderIntersec.isEmpty();
		}
		//	parallel words
		else {
			ArrayList<Cell> intersec = getIntersec(this.getInfluenceCells(), wCore);
			return intersec.isEmpty();
		}
		
	}

	private ArrayList<Cell> getIntersec(ArrayList<Cell> c1, ArrayList<Cell> c2) {
		ArrayList<Cell> intersec = new ArrayList<Cell>();
		for (Cell cell1 : c1){
			for (Cell cell2 : c2){
				if (cell1.getRow() == cell2.getRow() && cell1.getCol() == cell2.getCol()){
					intersec.add(cell1);
					intersec.add(cell2);
				}
			}
		}
		return intersec;
	}

	public byte getDir() {
		return dir;
	}

	public void setDir(byte dir) {
		this.dir = dir;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	public boolean equals(Word w){
		return (this.dir == w.getDir() &&
				this.startRow == w.getStartRow() &&
				this.startCol == w.getStartCol() &&
				this.str.equals(w.getStr()));
	}
}
