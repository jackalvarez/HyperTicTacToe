package tictactoe;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * tictactoe - Standare 2D Tic Tac Toe game.
 * 
 * This class provides a functioning 2D Tic-Tac-Toe game object intended to be
 * instantiated in HyperTicTacToe.
 * 
 * SIZE^2 determines 2D array size, board is the 2D array, winner holds the
 * winning char, rowIndex and colIndex are used to set different board indices
 * for testing. moves contains the number of moves on the board and is
 * incremented each successful turn.
 * 
 * @author Charles Franklin
 * @version 42
 * @see HyperTicTacToe
 */
public class TicTacToe {

	private static final int SIZE = 3;
	private char[][] board;
	private char winner;
	private char[] rowIndex;
	private int[] colIndex;
	private int moves;

	/**
	 * Default constructor, initializes array, clears the board, and does not
	 * produce any visual row/column indices.
	 */
	public TicTacToe() {
		moves = 0;
		winner = ' ';
		board = new char[SIZE][SIZE];
		setBoard(' ');
		rowIndex = new char[1];
		colIndex = new int[1];
		rowIndex[0] = ' ';
		colIndex[0] = -1;
	}

	/**
	 * Same as default constructor except sets row and column index information
	 * 
	 * @param rowValues
	 *            list of char indices, need to programatically generate them in
	 *            the future.
	 * @param colStart
	 *            the integer value to begin the column indices with
	 */
	public TicTacToe(String rowValues, int colStart) {
		moves = 0;
		winner = ' ';
		board = new char[SIZE][SIZE];
		rowIndex = new char[SIZE];
		colIndex = new int[SIZE];
		setBoard(' ');

		rowValues = rowValues.toUpperCase();
		for (int i = 0; i < SIZE; i++) {
			rowIndex[i] = rowValues.charAt(i);
			colIndex[i] = colStart + i;
		}
	}

	/**
	 * Generates a list of empty spaces for use in minimax()
	 * 
	 * @return List<int[]>, list of remaining empty spaces [[row0,col0],
	 *         [row1,col1], ...]
	 * 
	 * 
	 */
	public List<int[]> generateMoves() {
		List<int[]> moves = new ArrayList<int[]>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == ' ')
					moves.add(new int[] { i, j });
			}
		}
		return moves;
	}

	public int[] minimax(int level, char currentPlayer) {
		int best = 0;
		int score;
		int[] pos = { -1, -1 };
		char nextPlayer;

		if (currentPlayer == 'X') {
			nextPlayer = 'O';
			best = Integer.MIN_VALUE;
		} else {
			nextPlayer = 'X';
			best = Integer.MAX_VALUE;
		}

		if (winner != ' ' || level == 0)
			best = evalScore();
		else {
			List<int[]> moves = generateMoves();
			for (int[] move : moves) {
				setField(move[0], move[1], currentPlayer, false);
				this.moves--;

				score = minimax( level - 1, nextPlayer)[0];
				if (currentPlayer == 'X' && score > best) {
					best = score;
					pos[0] = move[0];
					pos[1] = move[1];
				} else if (currentPlayer == 'O' && score < best) {
					best = score;
					pos[0] = move[0];
					pos[1] = move[1];
				}

				setField(move[0], move[1], ' ', false);
				this.moves--;
			}
		}
		return new int[] { best, pos[0], pos[1] };
	}

	/**
	 * Evaluates heuristic score, checks row and column, then
	 * diagonals if applicable
	 * 
	 * Checks: both diagonals, row, and column for center row, column, and minor
	 * OR major diagonal for corners row, and column for "middles"
	 * 
	 * @param x, row
	 * @param y, column
	 * @return int score
	 */
	public int evalScore() {
		int score = 0;
		int rowX, rowO, colX, colO, majX, majO, minX, minO;

		majX = 0;
		majO = 0;

		minX = 0;
		minO = 0;

		for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {

			switch (board[i][i]) {
			case 'X':
				majX++;
				break;
			case 'O':
				majO++;
				break;
			}

			switch (board[i][j]) {
			case 'X':
				minX++;
				break;
			case 'O':
				minO++;
				break;
			}

			rowX = 0;
			colX = 0;

			rowO = 0;
			colO = 0;

			for (int k = 0; k < SIZE; k++) {
				switch (board[i][k]) {
				case 'X':
					rowX++;
					break;
				case 'O':
					rowO++;
					break;
				}

				switch (board[k][i]) {
				case 'X':
					colX++;
					break;
				case 'O':
					colO++;
					break;
				}
			}

			if (rowX > 0 && rowO > 0)
				score += 0;
			else {
				switch (rowX) {
				case 1:
					score += 1;
					break;
				case 2:
					score += 10;
					break;
				case 3:
					score = 100;
					break;
				}

				switch (rowO) {
				case 1:
					score -= 1;
					break;
				case 2:
					score -= 10;
					break;
				case 3:
					score = -100;
					break;
				}
			}// rows

			if (colX > 0 && colO > 0)
				score += 0;
			else {
				switch (colX) {
				case 1:
					score += 1;
					break;
				case 2:
					score += 10;
					break;
				case 3:
					score = 100;
					break;
				}

				switch (colO) {
				case 1:
					score -= 1;
					break;
				case 2:
					score -= 10;
					break;
				case 3:
					score = -100;
					break;
				}
			}// columns
		}// for(int i = 0, j = SIZE-1; i < SIZE; i++, j--)

		if (majX > 0 && majO > 0)
			score += 0;
		else {
			switch (majX) {
			case 1:
				score += 1;
				break;
			case 2:
				score += 10;
				break;
			case 3:
				score = 100;
				break;
			}

			switch (majO) {
			case 1:
				score -= 1;
				break;
			case 2:
				score -= 10;
				break;
			case 3:
				score = -100;
				break;
			}
		}// majorDiag

		if (minX > 0 && minO > 0)
			score += 0;
		else {
			switch (minX) {
			case 1:
				score += 1;
				break;
			case 2:
				score += 10;
				break;
			case 3:
				score = 100;
				break;
			}

			switch (minO) {
			case 1:
				score -= 1;
				break;
			case 2:
				score -= 10;
				break;
			case 3:
				score = -100;
				break;
			}
		}// minorDiag

		return score;
	}

	/**
	 * Evaluates row, columns, major and minor diagonals for a winner.
	 */
	public int evalBoard(boolean live) {

		// check for winner

		// check for any 3 identical chars in each row/column
		for (int i = 0; i < SIZE; i++) {
			// for each row...
			if (board[i][0] != ' ') {
				if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
					if (live)
						winner = board[i][0];

					if (winner == 'X')
						return 30;
					else if (winner == 'O')
						return -30;
				}
			}
			// for each column...
			if (board[0][i] != ' ') {
				if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
					if (live)
						winner = board[0][i];

					if (winner == 'X')
						return 30;
					else if (winner == 'O')
						return -30;
				}
			}
		}

		// check major diag for 3 identical chars
		if (board[0][0] != ' ') {
			if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
				if (live)
					winner = board[0][0];

				if (winner == 'X')
					return 30;
				else if (winner == 'O')
					return -30;
			}
		}

		// check minor diag for 3 identical chars
		if (board[0][2] != ' ') {
			if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
				if (live)
					winner = board[0][2];

				if (winner == 'X')
					return 30;
				else if (winner == 'O')
					return -30;
			}
		}
		if (moves == 9 && live) 
			winner = '*';

		return 0;
	}

	/**
	 * Translates char/integer addresses to ordinal values
	 * 
	 * @param row
	 *            character representation of row, (A,B,C,D,etc...)
	 * @param col
	 *            cardinal column value
	 * @return integer array containing ordinal addresses
	 */

	public int[] getAddress(char row, int col) {

		int[] address = { -1, -1 };
		int i;
		for (i = 0; i < SIZE; i++) {
			if (rowIndex[i] == row)
				address[0] = i;
		}

		for (i = 0; i < SIZE; i++) {
			if (colIndex[i] == col)
				address[1] = i;
		}

		return address;
	}

	/**
	 * Sets the indicated field to c
	 * 
	 * @param row
	 *            ordinal field row
	 * @param col
	 *            ordinal field column
	 * @param c
	 */

	public void setField(int row, int col, char c, boolean live) {

		c = Character.toUpperCase(c);
		board[row][col] = c;
		moves++;
		evalBoard(live);
			if (live && winner != ' ')
				setBoard(winner);
		
	}

	/**
	 * Returns character at ordinal [row][col]
	 * 
	 * @param row
	 * @param col
	 * @return
	 */

	public char getField(int row, int col) {

		return board[row][col];
	}

	/**
	 * 
	 * @param c - winner, ex: 'X', 'O', etc.
	 */
	public void setWinner(char c){
		this.winner = c;
	}
	
	
	/**
	 * Returns winning char
	 * 
	 * @return winner
	 */

	public char getWinner() {
		return winner;

	}

	/**
	 * Return total number of moves played
	 * 
	 * @return
	 */

	public int getMoves() {
		return moves;
	}

	/**
	 * Prints 60 lines to clear console
	 */
	public void clear() {
		for (int i = 0; i < 60; i++)
			System.out.println("");
	}

	/**
	 * Sets all fields on the board to c
	 * 
	 * @param c
	 *            character to set fields to
	 */
	public void setBoard(char c) {

		int i, j;
		for (i = 0; i < SIZE; i++) {
			for (j = 0; j < SIZE; j++)
				board[i][j] = c;
		}
	}

	/**
	 * Prints board to console with or without left,right,top, or bottom
	 * borders. The borders printed are determined by a String parameter. E.G
	 * displayBoard( "lr" ); <-- will only print borders on left and right
	 * 
	 * @param borders
	 * @return
	 */
	public String displayBoard(String borders) {

		StringBuilder buffer = new StringBuilder();

		int i, j;

		// init booleans so as to minimize internal loops
		borders = borders.toLowerCase();
		boolean top = borders.contains("t");
		boolean bottom = borders.contains("b");
		boolean left = borders.contains("l");
		boolean right = borders.contains("r");
		boolean index = (rowIndex[0] != ' ');

		// bar length varies with left/right borders
		String bar = "-----------";
		if (left)
			bar += "-";
		if (right)
			bar += "-";

		// print top border?
		if (top) {
			if (index) {
				buffer.append("  ");
				for (int x : colIndex)
					buffer.append("  " + x + " ");
				buffer.append("\n" + "  ");
			}
			buffer.append(bar + "\n");
		}

		// for each row...
		for (i = 0; i < SIZE; i++) {

			if (index)
				buffer.append(rowIndex[i] + " ");

			// print left border?
			if (left)
				buffer.append("|");

			// for each column...
			for (j = 0; j < SIZE; j++) {

				// print left and right border of middle cell...
				if (j == 1)
					buffer.append("| " + board[i][j] + " |");
				else
					buffer.append(" " + board[i][j] + " ");
			}

			// print right border?
			if (right)
				buffer.append("|");

			// print top and bottom border of middle row...
			if (i <= 1) {
				buffer.append("\n");
				if (index)
					buffer.append("  ");
				buffer.append(bar + "\n");
			} else
				buffer.append("\n");
		}

		// print bottom border?
		if (bottom) {
			if (index)
				buffer.append("  ");
			buffer.append(bar);
		}
		return buffer.toString();
	}

	/**
	 * While the board doesn't have a winner and the total moves is less than,
	 * it continually asks player for row/column.
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		TicTacToe t = new TicTacToe("abc", 1);

		Scanner input = new Scanner(System.in);
		int[] address;
		char row;
		int col;
		char player;
		while (t.getMoves() < 9 && t.getWinner() == ' ') {
			player = 'O';
			if (t.getMoves() % 2 == 0)
				player = 'X';
			t.clear();

			System.out.print(t.displayBoard("tblr"));

			int[] b = t.minimax(2, player);
			System.out.printf("\n\nbest: %s, row: %d, col: %d", b[0], b[1],
					b[2]);
			List<int[]> moves = t.generateMoves();

			System.out.printf("\n\n");
			System.out.println(t.evalScore());
			for (int[] move : moves) {
				System.out.printf("(%d, %d) ", move[0], move[1]);
			}

			System.out.printf("\n\n%c Enter row: ", player);

			row = input.next().charAt(0);
			row = Character.toUpperCase(row);
			t.clear();

			System.out.print(t.displayBoard("tblr"));

			System.out.printf("\n\n%c Enter column: ", player);
			col = input.nextInt();
			t.clear();

			System.out.print(t.displayBoard("tblr"));

			address = t.getAddress(row, col);

			if (t.getField(address[0], address[1]) == ' ')
				t.setField(address[0], address[1], player, true);

			t.evalBoard(true);
		}
		input.close();
		t.clear();
		System.out.print(t.displayBoard("tblr"));
		if (t.getWinner() == '*') {
			System.out.printf("\n\nDraw!");
			System.exit(0);
		}
		System.out.printf("\n\n%c is the Winner!\n", t.getWinner());

	}
}
