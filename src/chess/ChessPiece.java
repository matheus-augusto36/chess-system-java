package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color color;
  //protected Position position;
  //private Board board;
	
	//*NOTA*
	// Por ser um atributo protegido, o Position position nao pode ser
	// acessado pela classe ChessPiece. Porem, a adaptação de Position 
	// para ChessPosition será feita atraves do metodo getChessPosition().

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	}
}
