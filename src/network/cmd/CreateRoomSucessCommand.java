package network.cmd;

public class CreateRoomSucessCommand extends Command {
	private static final long serialVersionUID = 1094719716851217045L;
	private String roomId;

	public CreateRoomSucessCommand(String roomId) {
		super(CommandType.CREATE_ROOM_SUCESS);
		this.roomId = roomId;
	}

}
