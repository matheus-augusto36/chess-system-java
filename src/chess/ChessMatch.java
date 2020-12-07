package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	//*nota*
	// Esta classe possui "turno" e "jogador atual", que serão usados
	// para a criação de metodos capazes de gerenciar a troca de turnos
	// entre os dois jogadores.
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	//*NOTA*
	// Com este metodo, a matriz "mat" irá receber todas as peças da 
	// matriz "pieces". Dessa forma, as peças serão acessiveis para 
	// a camada Chess, atraves da classe ChessMatch.
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	//*NOTA*
	// Este método recebe como argumento uma posição de origem(ChessPosition)
	// e verifica se existe alguma peça nesta posição, e se houver, verifica 
	// quais são os possiveis movimentos especificos desta peça na matriz de 
	// booleanos do metodo possibleMoves(), e retorna esta matriz.
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	//*NOTA*
	// Este método irá realizar uma captura de peça inimiga, utilizando
	// o método auxiliar "makeMove()".
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	//*NOTA*
	// Este metodo primeiramente remove a peça de origem e de
	// destino(peça capturada), e depois adiciona a peça de origem no lugar 
	// da peça capturada. Alem disso, ele tambem remove a peça capturada da
	// lista de peças do tabuleiro, e adiciona esta peça na lista de peças 
	// capturadas.
	
	public void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("This piece isn't yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	//*NOTA* 
	// Este método possui a função de verificar se alguma peça existe em
	// determinada posição, e se existir, verifica se existem possiveis 
	// jogadas para esta peça, alem de verificar se a peça selecionada 
	// pertence ao jogador.
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	//*NOTA*
	// A funçao deste método é receber uma posição de origem e destino,
	// e verificar se existem possiveis movimentos baseados nessas duas
	// posições dadas.
	// O método irá acessar o tabuleiro, acessar a peça na posição de 
	// origem, e verificar se a posiçao de destino é um possivel movimento
	// para ela.
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	//*NOTA*
	// Se o jogador atual for da cor branca, a vez passara para o jogador de cor 
	// preta. Caso contrario, a vez será do jogador de cor branca.
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 3, new Rook(board, Color.WHITE));
        placeNewPiece('d', 3, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 4, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}