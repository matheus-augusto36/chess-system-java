package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	//*NOTA*
	// Ao instanciar o tabuleiro, automaticamente instancio a
	// a matriz de peças atraves do construtor.
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	//*NOTA*
	// Este metodo será usado para movimentar as peças, pois sua
	// logica permite que eu acesse a posição da peça e que eu
	// altere essa posição utilizando outra como argumento.
}
