
public class Cell {
	
	private int row, col;
	private char letter;

	public Cell(int row, int col, char letter){
		this.setRow(row);
		this.setCol(col);
		this.setLetter(letter);
	}


	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
