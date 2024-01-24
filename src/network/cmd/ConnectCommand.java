package network.cmd;

import java.util.Arrays;
import java.util.List;

public class ConnectCommand extends Command {
	private static final long serialVersionUID = 2510692758746009498L;
	private List<String> players;
	private String yourPlayer;
	
	public ConnectCommand(String player) {
		super(CommandType.CONNECT);
		this.players = Arrays.asList(player);
	}
	
	public ConnectCommand(List<String> players) {
		super(CommandType.CONNECT);
		this.players = players;
	}

	public ConnectCommand(List<String> players, String yourPlayer) {
		super(CommandType.CONNECT);
		this.players = players;
		this.yourPlayer = yourPlayer;
	}

	public List<String> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public String getYourPlayer() {
		return yourPlayer;
	}
}