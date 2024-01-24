package network.cmd;

import server.main.ServerMain;
import server.session.SessionUtils;

import java.net.Socket;

public class CreateRoomCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;

	public CreateRoomCommand() {
		super(CommandType.CREATE_ROOM);
	}

	@Override
	public void executeServer(Socket client, String player) {
		SessionUtils.sendToOne(client, new CreateRoomSucessCommand(ServerMain.createNewMatch(player)));
	}
}
