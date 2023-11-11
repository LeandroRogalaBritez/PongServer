package network.cmd;

import server.main.ServerMain;

import java.net.Socket;

public class SearchRoomCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private String roomId;

	public SearchRoomCommand(String roomId) {
		super(CommandType.SEARCH_ROOM);
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	@Override
	public void executeServer(Socket client, String player) {
		ServerMain.searchMatch(client, player, roomId);
	}
}
