package boardgame;

public abstract class Piece {
	
	protected Position position;
	//*NOTA*
	// Position ainda nao é a posição de xadrez, mas sim a
	// a "linguagem" que me permitira acessar a matriz inserindo
	// uma posição em algum metodo com esta função especifica.
	
	private Board board;
	//*NOTA*
	// RELAÇÃO DE COMPOSIÇÃO:
	// Cada peça Piece terá um tabuleiro Board, assim como
	// tambem cada tabuleiro tera uma matriz de peças Pieces
	// -> Chega a ser um ciclo: Board contém Pieces(matriz),
	// que ira conter peças, e cada uma dessas peças irá conter
	// um tabuleiro Board (que é o mesmo que as contém).
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	//*NOTA*
	// O Board sera o tabuleiro somente da camada Chess layer,
	// entao ele será "protected"
	
	public abstract boolean[][] possibleMoves();
		
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	//*NOTA*
	// Este método irá receber uma posiçao Position como argumento, e irá verificar
	// se existem movimentos possiveis para esta posiçao na matriz possibleMoves()
	// (que ja possui as posiçoes possiveis marcadas como verdadeiro).
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j] == true) {
					return true;
				}
			}
		}
		return false;
	}
	//*NOTA*
	// Este método, diferentemente do outro, apenas verifica se existe algum
	// movimento possivel na matriz de possibleMoves(). O metodo nao especifica 
	// as posições possiveis, ele somente diz se existe posição possivel ou não.
	// Se sim, ele retorna verdadeiro. Senao, ele retorna falso.
}
