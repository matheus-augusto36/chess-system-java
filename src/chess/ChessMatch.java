package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	/*
	 *Esta classe possui "turno" e "jogador atual", que serão usados
	 *para a criação de metodos capazes de gerenciar a troca de turnos
	 *entre os dois jogadores.
	 */
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
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
	/*NOTA*
	 *Com este metodo, a matriz "mat" irá receber todas as peças da 
	 *matriz "pieces". Dessa forma, as peças serão acessiveis para 
	 *a camada Chess, atraves da classe ChessMatch.
	 */
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	/*NOTA*
	 *Este método recebe como argumento uma posição de origem(ChessPosition)
	 *e verifica se existe alguma peça nesta posição, e se houver, verifica 
	 *quais são os possiveis movimentos especificos desta peça na matriz de 
	 *booleanos do metodo possibleMoves(), e retorna esta matriz.
	 */
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("The " + currentPlayer + " King it's in check!");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	/*
	 *Este método irá realizar uma captura de peça inimiga, utilizando
	 *o método auxiliar "makeMove()".
	 */
	
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
	/*
	 *Este metodo primeiramente remove a peça de origem e de
	 *destino(peça capturada), e depois adiciona a peça de origem no lugar 
	 *da peça capturada. Alem disso, ele tambem remove a peça capturada da
	 *lista de peças do tabuleiro, e adiciona esta peça na lista de peças 
	 *capturadas.
	 */
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	/*
	 *Este método desfaz um movimento. Inicialmente, a peça movimentada é
	 *retirada da posição de destino e é colocada novamente na origem. Depois,
	 *o método verifica se há alguma peça capturada, e se houver, esta peça é
	 *retirada da lista de peças capturadas e adicionada novamente dentro da
	 *lista de peças no tabuleiro.
	 */
	
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
	/*
	 *Este método possui a função de verificar se alguma peça existe em
	 *determinada posição, e se existir, verifica se existem possiveis 
	 *jogadas para esta peça, alem de verificar se a peça selecionada 
	 *pertence ao jogador.
	 */
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	/*
	 *A funçao deste método é receber uma posição de origem e destino,
	 *e verificar se existem possiveis movimentos baseados nessas duas
	 *posições dadas.
	 *O método irá acessar o tabuleiro, acessar a peça na posição de 
	 *origem, e verificar se a posiçao de destino é um possivel movimento
	 *para ela.
	 */
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	/*
	 *Se o jogador atual for da cor branca, a vez passara para o jogador de cor 
	 *preta. Caso contrario, a vez será do jogador de cor branca.
	 */
	private Color opponent(Color color) {
		return(color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if(p instanceof King) {
				return(ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " King on the board");
	}
	 /*Este método percorre a lista de peças no tabuleiro e verifica se há algum rei
	  *da cor passada como argumento, e retorna esse rei.
	  */
	
	private boolean testCheck(Color color) {
		Position KingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[KingPosition.getRow()][KingPosition.getColumn()]) {
				return true;
			} 
		}
		return false;
	}
	/* Este método verifica se algum rei está em xeque. Primeiramente, o metodo
	  *irá receber uma cor como argumento, e irá percorrer a lista de peças
	  *desta cor(atraves do metodo auxiliar King(color)) e irá armazenar a
	  *posição do rei na variavel KingPosition. Depois, o metodo ira percorrer
	  *a lista de peças adversarias, armazenando os movimentos possiveis destas
	  *peças em uma matriz de booleanos. Se a posição do rei(KingPosition) estiver
	  *inclusa nesta matriz, o metodo retorna verdadeiro(significa que o rei esta
	  *em xeque).
	  */
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	private void initialSetup() {
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));

        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
	}
}