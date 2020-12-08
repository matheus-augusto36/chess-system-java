package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		 System.out.print("\033[H\033[2J");
		 System.out.flush();
		} 

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
	}
	/*NOTA*
	 *Este metodo irá criar uma ChessPosition. Primeiramente, será 
	 *feita uma entrada de dados(linha = "a" até "h" e coluna = 1 a 8)
	 *como argumento, e esses argumentos serao recortados e serao 
	 *atribuidos às variaveis column e row. Estas variaveis serao usadas
	 *para instanciar um ChessPosition atraves do construtor.
	 */
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		if(!chessMatch.getCheckMate()) {
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if(chessMatch.getCheck() == true) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + chessMatch.getCurrentPlayer());
		}
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		for(int i=0; i<pieces.length; i++) {
			System.out.print((8-i) + " ");
			for(int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	/* 
	 *Este método irá percorrer a matriz "pieces"(contida em Board)
	 *e irá imprimir todas as peças contidas nela.
	 */
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for(int i=0; i<pieces.length; i++) {
			System.out.print((8-i) + " ");
			for(int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	/*
     **SOBRECARGA* -> A função deste método é imprimir o tabuleiro, porem
	 *imprimindo tambem os possiveis movimentos para as peças.
	 */
	
	private static void printPiece(ChessPiece piece, boolean backGround) {
		if(backGround == true) {
			System.out.print(ANSI_GREEN_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	/*
	 *Se a peça for nula(nao existir), será impresso um "-".
	 *Senao, a inicial da peça será imprimida com seu fundo colorido.
	 *O espaço em branco serve para impedir que as peças fiquem grudadas 
	 *umas nas outras.
	 */
	private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
		List<ChessPiece> white = capturedPieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> black = capturedPieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
	 /* Este método recebe uma lista com todas as peças capturadas, e faz uma 
	 * filtragem, armazenando as peças brancas em uma lista, e as pretas em outra.
	 * Por fim, as listas sao imprimidas na tela durante a partida.
	 * 
	 */
}

