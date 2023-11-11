package network.cmd;

import game.MatchRoom;
import server.main.ServerMain;

import java.net.Socket;

public class SearchRoomSucessCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private MatchRoom matchRoom;

	public SearchRoomSucessCommand(MatchRoom matchRoom) {
		super(CommandType.SEARCH_ROOM_SUCESS);
		this.matchRoom = matchRoom;
	}


}
