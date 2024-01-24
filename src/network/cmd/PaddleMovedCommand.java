package network.cmd;


import server.main.ServerMain;
import server.session.SessionUtils;

import java.net.Socket;

public class PaddleMovedCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private float y;
	private String playerToReceived;
	private String playerMoved;

	public PaddleMovedCommand(float y, String playerToReceived, String playerMoved) {
		super(CommandType.PADDLE_MOVE);
		this.y = y;
		this.playerToReceived = playerToReceived;
		this.playerMoved = playerMoved;
	}

	@Override
	public void executeServer(Socket client, String player) {
		SessionUtils.sendToOne(ServerMain.getSessions().get(playerToReceived).getClient(), new PaddleMovedCommand(y, playerToReceived, playerMoved));
	}
}
