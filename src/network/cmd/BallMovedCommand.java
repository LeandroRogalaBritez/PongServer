package network.cmd;


import server.main.ServerMain;
import server.session.SessionUtils;

import java.net.Socket;

public class BallMovedCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private float x;
	private float y;
	private float speedX;
	private float speedY;
	private String playerToReceived;

	public BallMovedCommand(float x, float y, float speedX, float speedY, String playerToReceived) {
		super(CommandType.BALL_MOVE);
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.playerToReceived = playerToReceived;
	}

	@Override
	public void executeServer(Socket client, String player) {
		SessionUtils.sendToOne(ServerMain.getSessions().get(playerToReceived).getClient(), new BallMovedCommand(x, y, speedX, speedY, playerToReceived));
	}
}
