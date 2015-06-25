package tictactoe;

/**
 * This class provides a simple Player object to associate a name and team with
 * a single player for use in HyperTicTacToe.
 * 
 * @author Charles Franklin
 * @version 42
 * @see HyperTicTacToe
 */
public class Player {
	private String name;
	private char team;

	/**
	 * Default constructor, doesn't initialize any private variables.
	 */
	public Player() {

	}

	/**
	 * Sets player name
	 * 
	 * @param _name
	 *            player name
	 */
	public void setName(String _name) {
		name = _name;
	}

	/**
	 * Accessor for player name
	 * 
	 * @return player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets player team, ('X' or 'O', but could be any char)
	 * 
	 * @param _team
	 */
	public void setTeam(char _team) {
		team = Character.toUpperCase(_team);
	}

	/**
	 * Accessor for player team
	 * 
	 * @return player team
	 */
	public char getTeam() {
		return team;
	}

}