package tictactoe;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * HyperTicTacToe
 * 
 * This class contains methods to create a 2D array of standard TicTacToe
 * objects, e.g. a 4 dimensional array. It contains all the necessary methods to
 * determine all of the available information about each individual game of
 * TicTacToe as well as some logic for governing it's own gameplay.
 * 
 * @author Charles Franklin
 * @version 42
 * 
 * 
 *          SIZE^2 determines the size of the 2D array. It is mostly used as a
 *          constant for loops. This *SHOULD* be able to produce a workable (or
 *          at least not very broken) 4x4, 5x5, etc... game of HyperTicTacToe.
 * 
 *          winner is to store the winning char, 'X', 'O', or 'D' in the case of
 *          a draw.
 * 
 *          moves is currently unused in HyperTicTacToe but could be useful
 *          later. Its a hold over from the standard TicTacToe game which uses
 *          moves to determine whether there is a draw or not.
 * 
 */
public class HyperTicTacToe {
	private static final int SIZE = 3;
	private TicTacToe[][] board = new TicTacToe[SIZE][SIZE];
	private char winner = ' ';
	private int moves = 0;

	/**
	 * Default and only constructor, populates the array.
	 */
	public HyperTicTacToe() {
		int i, j;
		for (i = 0; i < SIZE; i++) {
			for (j = 0; j < SIZE; j++)
				board[i][j] = new TicTacToe();
		}

	}

	/**
	 * Generates list of "un-won" boards
	 * 
	 */
	public List<int[]> winnableBoards() {
		List<int[]> moves = new ArrayList<int[]>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j].getWinner() == ' ')
					moves.add(new int[] { i, j });
			}
		}
		return moves;
	}

	public int evalScore() {
		int score = 0;
		int rowX, rowO, colX, colO, majX, majO, minX, minO;

		majX = 0;
		majO = 0;

		minX = 0;
		minO = 0;

		for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {

			switch (board[i][i].getWinner()) {
			case 'X':
				majX++;
				break;
			case 'O':
				majO++;
				break;
			}

			switch (board[i][j].getWinner()) {
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
				switch (board[i][k].getWinner()) {
				case 'X':
					rowX++;
					break;
				case 'O':
					rowO++;
					break;
				}

				switch (board[k][i].getWinner()) {
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
	 * unnecessary
	 *
	 */
	public int[] minimax(int level, char currentPlayer) {
		int best;
		int score = 0;

		int[] b = {-1, -1};
		char nextPlayer;
		
		if (currentPlayer == 'X') {
			best = -1000000;
			nextPlayer = 'O';
		}

		else {
			best = 1000000;
			nextPlayer = 'X';
		}
		
		if(level == 0 || this.winner != ' '){
			best = evalScore();
			this.setWinner(' ');
		}
		
		List<int[]> games = winnableBoards();
		for (int[] game : games) {

			// set Winner (move)
			board[game[0]][game[1]].setWinner(currentPlayer);
			evalBoard(false);
			
			// recurse
			score = minimax(level -1, nextPlayer)[0];

			// min or max?
			if(currentPlayer == 'X' && score > best){
				best = score;
				b[0] = game[0];
				b[1] = game[1];
			}
			else if(currentPlayer == 'O' && score < best){
				best = score;
				b[0] = game[0];
				b[1] = game[1];
			}
			
			// unset winner
			board[game[0]][game[1]].setWinner(' ');
		}
		
		//		return new int[] { best, b[0], b[1] };
		return new int[] {best, b[0], b[1]};
	}

	/**
	 * 
	 * @param currentPlayer
	 * @return char[], ex: {'A', '5'}
	 */
	public char[] tip(char currentPlayer) {
		
		int[] best = minimax(2, currentPlayer);
		int[] inner = board[best[1]][best[2]].minimax(2, currentPlayer);
		
		char[] tmp = toAddress(best[1], best[2], inner[1], inner[2]);
		
		return new char[] {tmp[0], tmp[1]};
	}

	/**
	 * 
	 * @return moves - total number of moves on board, currently unused.
	 */
	public int getMoves() {
		return moves;
	}

	/**
	 * 
	 * @return winner - 'X', 'O', or 'D' in the event of a draw.
	 */
	public char getWinner() {
		return winner;
	}

	/**
	 * 
	 * @param c
	 *            the winning character.
	 */
	public void setWinner(char c) {
		winner = c;
	}

	/**
	 * Prints 60 lines to clear the console.
	 */
	public void clear() {
		for (int i = 0; i < 60; i++)
			System.out.println("");
	}

	/**
	 * This sets all fields of the inner board at position [row][col] to c.
	 * 
	 * @param row
	 *            row on outer board
	 * @param col
	 *            column on outer board
	 * @param c
	 *            character to fill inner board with
	 */
	public void setInnerBoard(int row, int col, char c) {
		board[row][col].setBoard(c);
	}

	/**
	 * You guessed it, this fills the entire HyperTicTacToe board with c. Used
	 * to display winning character.
	 * 
	 * @param c
	 *            character to fill outer board with
	 */
	public void setOuterBoard(char c) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++)
				setInnerBoard(i, j, c);
		}
	}

	/**
	 * This maps the row indices (A,B,C,D,etc...) and column indices (1,2,3,..)
	 * to the 4 digit ordinal addresses of the appropriate field to set. Returns
	 * the 4 digits as an array for use in setField() and getField(). Think
	 * BattleShip.
	 * 
	 * @param row
	 *            character representation of row, (A,B,C,D,etc...)
	 * @param col
	 *            cardinal row index (1,2,3,4,etc...)
	 * @return Integer array containing the ordinal address of a cell.
	 */
	public int[] getAddress(char row, int col) {
		int[] field = new int[4];

		// outer row
		if ((int) row < 68)
			field[0] = 0;
		else if ((int) row < 71)
			field[0] = 1;
		else
			field[0] = 2;

		// outer column
		if (col < 4)
			field[1] = 0;
		else if (col < 7)
			field[1] = 1;
		else
			field[1] = 2;

		field[2] = ((int) row - 65) % 3; // inner row
		field[3] = (col - 1) % 3; // inner column

		return field;
	}

	public char[] toAddress(int outerR, int outerC, int innerR, int innerC){
		char row = 65;
		char col = 49;
		
		row += innerR;
		col += innerC;
		
		switch(outerR){
		case 1: row += 3; break;
		case 2: row += 6; break;
		}
		
		switch(outerC){
		case 1: col += 3; break;
		case 2: col += 6; break;
		}
		
		return new char[] {row, col};
	}
	
	
	/**
	 * Used to mark a field according to player selection. Uses getAddress() to
	 * lookup ordinal indices.
	 * 
	 * @param row
	 *            character representation of row, (A,B,C,D,etc...)
	 * @param col
	 *            cardinal row index, (1,2,3,4,etc...)
	 * @param c
	 *            character to set field with
	 */
	public void setField(char row, int col, char c) {
		int[] address = getAddress(row, col);
		board[address[0]][address[1]].setField(address[2], address[3], c, true);
	}

	/**
	 * Returns the character at given location. Used to check if a player has
	 * already played on the position or not.
	 * 
	 * @param row
	 *            character representation of row, (A,B,C,D,etc...)
	 * @param col
	 *            cardinal row index, (1,2,3,4,etc...)
	 * @return character at given position
	 */
	public char getField(char row, int col) {
		int[] address = getAddress(row, col);
		char c;
		c = board[address[0]][address[1]].getField(address[2], address[3]);
		return c;
	}

	/**
	 * Prints the HyperTicTacToe board to the console. Currently generates a
	 * ternary number system to sequence fields using 4 nested loops. Awfully
	 * hacky.
	 */
	public void displayBoard() {
		/*
		 * There MUST be a better way to accomplish this...
		 */
		int c = 0; // <-- character offset for row indices
		int i, j, k, l; // <-- loop counters

		// create divider
		String div = "";
		for (int t = 0; t < 37; t++)
			div += '-';

		// padding
		System.out.printf("%3c", ' ');

		// print column indices
		for (int t = 1; t < 10; t++)
			System.out.printf("%3d ", t);
		System.out.printf("\n");

		// begin chaos!
		// for each row in outer board...
		for (i = 0; i < SIZE; i++) {
			// System.out.printf( "%3c%s\n" , ' ', div );

			// for each row in inner board...
			for (j = 0; j < SIZE; j++) {

				// print padded divider
				System.out.printf("%3c%s\n", ' ', div);

				// print row index
				System.out.printf("%2c |", 65 + c);
				c++; // <-- increment offset

				// for each column in outer row...
				for (k = 0; k < SIZE; k++) {

					// for each column in inner row...
					for (l = 0; l < SIZE; l++) {

						// print cell value
						if (l - 2 % 3 == 0)
							System.out.printf("%2c |",
									board[i][k].getField(j, l));
						else
							System.out.printf("%2c |",
									board[i][k].getField(j, l));

					}
				}
				System.out.printf("\n");
			}
		}
		// print padded divider
		System.out.printf("%3c%s\n", ' ', div);
	}

	/**
	 * Evaluates the major diagonal on the outer board for potential winner
	 */
	public char evalMajor() {
		if (winner != ' ')
			return winner;
		char candidate = ' ';
		char[] f = new char[SIZE];

		// check major diagonal...
		if (board[0][0].getWinner() != ' ') {
			for (int j = 0; j < SIZE; j++) {
				f[j] = board[j][j].getWinner();
				if (f[j] != 'D' && f[j] != ' ')
					candidate = f[j];
			}

			if (candidate != ' ') {
				for (int j = 0; j < SIZE; j++) {
					if (f[j] == candidate || f[j] == 'D')
						continue;
					else
						return ' ';
				}
				return candidate;
				//				setOuterBoard(candidate);
				//				return;
			}
		}
		return candidate;
	}

	/**
	 * Evaluates the minor diagonal of the outer board for potential winner
	 */
	public char evalMinor() {
		if (winner != ' ')
			return winner;
		char candidate = ' ';
		char[] f = new char[SIZE];

		// check minor diagonal...
		if (board[0][SIZE - 1].getWinner() != ' ') {
			for (int j = 0, k = SIZE - 1; j < SIZE; j++, k--) {
				f[j] = board[j][k].getWinner();
				if (f[j] != 'D' && f[j] != ' ') {
					candidate = f[j];
				}
			}

			if (candidate != ' ') {
				for (int j = 0; j < SIZE; j++) {
					if (f[j] == candidate || f[j] == 'D')
						continue;
					else
						return ' ';
				}
				return candidate;
				//				setOuterBoard(candidate);
				//				return;
			}
		}
		return candidate;
	}

	/**
	 * Evaluates each row in outer board for winner
	 */
	public char evalRow() {
		if (winner != ' ')
			return winner;
		int i;
		char candidate = ' ';
		char[] f = new char[SIZE];

		// check for any 3 identical chars in each row/column
		for (i = 0; i < SIZE; i++) {

			// for each row...

			// if winner
			if (board[i][0].getWinner() != ' ') {
				for (TicTacToe t : board[i]) {

					// at least one board cannot be a draw
					if (t.getWinner() != 'D' && t.getWinner() != ' ')
						candidate = t.getWinner();
				}

				// if not 3 draws in a row
				if (candidate != ' ') {

					for (int j = 0; j < SIZE; j++) {
						f[j] = board[i][j].getWinner();
						if (f[j] == candidate || f[j] == 'D')
							continue;
						else
							return ' ';
					}
					return candidate;
					//					setOuterBoard(candidate);
					//					return;
				}
			}
		}
		return candidate;
	}

	/**
	 * Evaluates each column in outer board for winner
	 */
	public char evalColumn() {
		if (winner != ' ')
			return winner;
		int i;
		char candidate = ' ';
		char[] f = new char[SIZE];

		// check for any 3 identical chars in each row/column
		for (i = 0; i < SIZE; i++) {

			// for each column...
			if (board[0][i].getWinner() != ' ') {

				for (int j = 0; j < SIZE; j++) {
					f[j] = board[j][i].getWinner();
					if (f[j] != 'D' && f[j] != ' ')
						candidate = f[j];
				}

				// if not 3 draws in a column...
				if (candidate != ' ') {
					for (int j = 0; j < SIZE; j++) {
						if (f[j] == candidate || f[j] == 'D')
							continue;
						else
							return ' ';
					}
					return candidate;
					//					setOuterBoard(candidate);
					//					return;
				}
			}
		}
		return candidate;
	}

	/**
	 * Calls evalMajor(), evalMinor(), evalRow(), and evalColumn()
	 */
	public void evalBoard(boolean live) {
		char[] values = new char[4];
		char candidate = ' ';

		values[0] = evalMajor();
		values[1] = evalMinor();
		values[2] = evalRow();
		values[3] = evalColumn();


		for(int i = 0; i < 4; i++){
			if (values[i] != ' '){
				candidate = values[i];
				break;
			}
		}

		this.setWinner(candidate);

		if(live && candidate != ' '){
			setOuterBoard(winner);
		}


		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j].getWinner() != ' ')
					continue;
				else
					return;
			}
		}
		
		this.setWinner('D');
		if(live)
			this.setOuterBoard('D');
		return;
	}

	/**
	 * Creates 2 Player objects in an array. Prompts for their respective names,
	 * then randomly assigns 'X' or 'O' as team. Then loops until winner is set
	 * or 'q' is entered continually prompting for row and column from each
	 * player. Does not need be a legitimate winner. If for example winner =
	 * 'D', it will declare a draw.
	 * 
	 * @param args
	 */
	public static void startGame() {
		HyperTicTacToe h = new HyperTicTacToe();
		Scanner input = new Scanner(System.in);
		char row, col;

		// create 2 new players
		Player[] players = new Player[2];
		for (int i = 0; i < 2; i++)
			players[i] = new Player();

		// randomly assign teams
		Random gen = new Random();
		int i; // counter for alternating player turns in while() below

		if (gen.nextInt(2) == 0) {
			players[0].setTeam('X');
			players[1].setTeam('O');
			i = 0; // X goes first
		} else {
			players[0].setTeam('O');
			players[1].setTeam('X');
			i = 1; // X goes first
		}

		// enter player names
		System.out.printf("Player 1 enter name: ");
		players[0].setName(input.nextLine());

		System.out.printf("Player 2 enter name: ");
		players[1].setName(input.nextLine());

		h.clear();
		h.displayBoard();

		while (h.getWinner() == ' ') {

			System.out.printf("%s (Player %d) enter row ('t' or 'T' for Tip): ",
					players[i % 2].getName(), (i % 2) + 1);

			try {
				row = input.nextLine().charAt(0);
			} catch (StringIndexOutOfBoundsException err) {
				System.out.printf("%s (Player %d) enter row ('t' or 'T' for Tip): ",
						players[i % 2].getName(), (i % 2) + 1);
				row = input.nextLine().charAt(0);
			}

			if (Character.toUpperCase(row) == 'T'){
				h.clear();
				h.displayBoard();
				char[] hint = h.tip(players[i%2].getTeam());
				System.out.printf("Tip: %c %c\n", hint[0],hint[1]);
				continue;
			}
			if (Character.toUpperCase(row) == 'Q') // check for quit
				System.exit(0);

			try {

				// fixed input validation
				
				System.out.printf("Player %d enter column: ", (i % 2) + 1);
				col = input.nextLine().charAt(0);
				
				if (Character.toUpperCase(col) == 'Q') // check for quit
					System.exit(0);
				
				int tmp = (int) col;
				while (tmp < 49 || tmp > 57){
					System.out.printf("Player %d enter column: ", (i % 2) + 1);
					col = input.nextLine().charAt(0);
					tmp = (int) col;
				}
								
				
			} catch (StringIndexOutOfBoundsException err) {
				System.out.printf("Player %d enter column: ", (i % 2) + 1);
				col = input.nextLine().charAt(0);
			}

			if (Character.toUpperCase(col) == 'Q') // check for quit
				System.exit(0);
			// convert char to int value
			int colVal = Integer.parseInt(Character.toString(col));

			// upperCase all row entries
			row = Character.toUpperCase(row);

			// if not A-I or 1-9: try again
			if ((int) row < 65 || (int) row > 73 || colVal < 1 || colVal > 9) {
				h.clear();
				h.displayBoard();
				System.out.printf("Invalid location. Please try again.\n");
				continue;
			}

			// if blank space
			if (h.getField(row, colVal) == ' ') {
				h.setField(row, colVal, players[i % 2].getTeam());
				h.clear();
				h.evalBoard(true);
				h.displayBoard();
			}

			// if not blank, decrement i and try again
			else {
				h.clear();
				h.displayBoard();
				System.out.printf("Invalid location. Please try again.\n");
				i--;
			}
			i++; // increment i (next turn)

		} // end while( no winner )
		for (Player p : players) {
			if (p.getTeam() == h.getWinner()) {
				System.out.printf("%s Wins!!!\n\n", p.getName());
				System.exit(0);
			}
		}
		if (h.getWinner() == 'D')
			System.out.printf("Draw!!!");

		input.close();
	}

	public static char startSim() {
		HyperTicTacToe h = new HyperTicTacToe();

		// create 2 new players
		Player[] players = new Player[2];
		for (int i = 0; i < 2; i++)
			players[i] = new Player();

		// randomly assign teams
		Random gen = new Random();
		int i; // counter for alternating turns

		if (gen.nextInt(2) == 0) {
			players[0].setTeam('X');
			players[1].setTeam('O');
			i = 0;
		} else {
			players[0].setTeam('O');
			players[1].setTeam('X');
			i = 1;
		}
		while (h.getWinner() == ' ') {
			char row = (char) (gen.nextInt(9) + 65);
			int col = gen.nextInt(9) + 1;
			if (h.getField(row, col) == ' ') {
				h.setField(row, col, players[i % 2].getTeam());
				h.clear();
				h.displayBoard();
				h.evalBoard(true);
				i++;
			}

		}

		return h.getWinner();
	} // end startSim()

	public static void monteCarlo(int numGames) {
		int x = 0;
		int o = 0;
		int d = 0;

		for (int i = 0; i < numGames; i++) {

			// char winner = startSim();
			switch (startSim()) {
			case 'X':
				x++;
				break;
			case 'O':
				o++;
				break;
			case 'D':
				d++;
				break;
			}
			int mod = numGames / 10;

			if (i < 1000 && (i + 1) % (1000 / 10) == 0) {
				System.out.printf("Game: %d  X: %d  O: %d  D: %d\n", (i + 1),
						x, o, d);
			} else if ((i + 1) % mod == 0)
				System.out.printf("Game: %d  X: %d  O: %d  D: %d\n", (i + 1),
						x, o, d);
		}

	}

	private static void usage() {
		System.out.printf("\nHyperTicTacToe\n\n");
		System.out.printf("\t-h (help)\n");
		System.out.printf("\t\tThis screen.\n\n");
		System.out.printf("\t-m <Count>\n");
		System.out.printf("\t\tMonte Carlo Simulation");
		System.out.printf("- Simulates 2 computer players pseudo-randomly.\n");
		System.out.printf("\t\t<Count> - Integer between 1 and 1,000,000\n");
		System.out
				.printf("\t\tIf no value is supplied, user will be prompted.\n");
		System.out.printf("\tExamples:\n\n");
		System.out.printf("\t\tHyperTicTacToe -m\n");
		System.out.printf("\t\tHyperTicTacToe -m 50000\n");
		System.out.printf("\t\tHyperTicTacToe -m 50,000\n\n");
	}

	public static void main(String... args) {

		Scanner console = new Scanner(System.in);

		if (args.length >= 1) {
			if (args[0].contains("-h")) {
				usage();
				System.exit(0);
			} else if (args[0].contains("-m")) {

				try {
					String s = args[1].replace(",", "");
					int numGames = Integer.parseInt(s);
					if (numGames <= 10000000)
						monteCarlo(numGames);
					else {
						System.out.printf("Number of games should ");
						System.out.printf("not exceed 10,000,000.\n\n");
					}
					console.close();
					System.exit(0);
				} // end try

				catch (ArrayIndexOutOfBoundsException e) {
				} catch (NumberFormatException
						| java.util.InputMismatchException e) {
					System.out.printf("Value must not contain a decimal.\n");
				}

				finally {
					System.out.printf("\nMonte Carlo Mode\n");
					System.out.printf("================\n\n");
					System.out.printf("Enter number of games: ");
					int numGames = 0;
					try {
						numGames = console.nextInt();
					} catch (NumberFormatException
							| java.util.InputMismatchException e) {
						System.out
								.printf("Value must not contain a decimal.\n");
						console.close();
						System.exit(0);
					}

					if (numGames <= 1000000)
						monteCarlo(numGames);
					else {
						System.out.printf("Number of games should");
						System.out.printf("not exceed 1,000,000.\n\n");
					}
					console.close();
					System.exit(0);
				} // end finally
			} // end else if ... contains -m
		} // end if ... length >= 1

		startGame();

	} // end main()

} // end class
