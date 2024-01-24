package network.cmd;

import java.net.Socket;

public class SearchRoomFailedCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;

	public SearchRoomFailedCommand() {
		super(CommandType.SEARCH_ROOM_FAILED);
	}


}
