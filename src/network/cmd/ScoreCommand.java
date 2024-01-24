package network.cmd;


import server.main.ServerMain;
import server.session.SessionUtils;

import java.net.Socket;

public class ScoreCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private String playerToReceived;
	private String playerScored;

	public ScoreCommand(String playerToReceived, String playerScored) {
		super(CommandType.SCORE);
		this.playerScored = playerScored;
		this.playerToReceived = playerToReceived;
	}

	@Override
	public void executeServer(Socket client, String player) {
		SessionUtils.sendToOne(ServerMain.getSessions().get(playerToReceived).getClient(), new ScoreCommand(playerToReceived, playerScored));
	}
}
