package boardgame;

public class Position {
	//*NOTA*
	// Esta classe será uma especie de intermediario entre
	// o programador e a matriz do tabuleiro(pieces), pois 
	// ela "fala a mesma lingua que a matriz 'pieces'(linhas
	// e colunas)", permitindo comunicações e operaçoes.
	
	private int row;
	private int column;
	
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {
		return row + ", " + column;
	}
}
