package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	//*NOTA*
	// RELAÇÃO DE COMPOSIÇÃO:
	// Cada peça Piece terá um tabuleiro Board, assim como
	// tambem cada tabuleiro tera uma matriz de peças Pieces
	// -> Board contém Pieces(matriz), que ira conter peças, 
	// e cada uma dessas peças irá conter um tabuleiro Board
	// (que é o mesmo que as contém).

	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
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
	public int getColumns() {
		return columns;
	}
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("position not on the board");
		}
		return pieces[row][column];
	}
	//*NOTA*
	// Este metodo me permite chamar uma peça na matriz pieces 
	// inserindo linha e coluna diretamente. É como se fosse um
	// "getPiece()".
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	//*NOTA
	// -> SOBRECARGA
	// Diferentemente do metodo acima, este metodo me permite
	// pesquisar uma peça na matriz inserindo uma posição Position.
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	//*NOTA*
	// Este metodo será usado para movimentar as peças, pois sua
	// logica permite que eu acesse a matriz "pieces" e que eu
	// mova uma peça para a posição desejada nesta matriz, atraves
	// do "position".
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("position not on the board");
		}
		if(piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	//*NOTA*
	// Este método remove uma peça: Primeiramente ele irá acessar
	// a matriz pieces e irá anular a peça em si, depois ele irá
	// anular a posição desta peça na matriz(matriz pieces = tabuleiro).
	
	private boolean positionExists(int row, int column) {
		return row >=0 && row< rows && column >=0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("position not on the board");
		}
		return piece(position) != null;
	}
}
