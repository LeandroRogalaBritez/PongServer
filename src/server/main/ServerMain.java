package server.main;


import game.MatchRoom;
import network.cmd.ConnectCommand;
import network.cmd.SearchRoomFailedCommand;
import network.cmd.SearchRoomSucessCommand;
import server.config.Configuration;
import server.session.Session;
import server.session.SessionUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
	private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
	private static Map<String, MatchRoom> matches = new HashMap<>();
	private static Map<String, String> matchsPlayers = new HashMap<>();
	private static Random rand = new Random();
	public static final Logger logger = Logger.getLogger(ServerMain.class.getName());

	public void start() throws IOException {
		logger.log(Level.INFO, "Iniciando conexão");
		ServerSocket server = new ServerSocket(Configuration.getInstance().getPort());
		logger.log(Level.INFO, "Servidor Iniciado na porta: {0}", Configuration.getInstance().getPort());
		while(true) {
			logger.log(Level.INFO, "Esperando conexão do cliente");
	        Socket client = server.accept();
	        String threadName = String.format("%s:%d", client.getInetAddress().getHostAddress(), client.getPort());
			logger.log(Level.INFO, "Cliente conectado: {0}", threadName);
	        sendPlayersToClient(client, threadName);
			logger.log(Level.INFO, "Players conectados: {0}", sessions.size());
	    }
	}
	
	private void sendPlayersToClient(Socket client, String threadName) {
        SessionUtils.sendToAll(sessions, new ConnectCommand(threadName));
		sessions.put(threadName, new Session(client, threadName));
		SessionUtils.sendToOne(client, new ConnectCommand(getAllKeySessions(), threadName));
	}

	private List<String> getAllKeySessions() {
		List<String> sessionsToReturn = new ArrayList<>();
		sessions.entrySet().stream().forEach(entry -> sessionsToReturn.add(entry.getKey()));
		return sessionsToReturn;
	}

	public static Map<String, Session> getSessions() {
		return sessions;
	}
	
	public static Map<String, MatchRoom> getMatches() {
		return matches;
	}

	public static String createNewMatch(String player) {
		while(true) {
			String roomId = "" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			if (matches.containsKey(roomId)) {
				continue;
			}
			matches.put(roomId, new MatchRoom(player));
			logger.log(Level.INFO, "Criado nova sala: {0}}", roomId);
			matchsPlayers.put(player, roomId);
			return roomId;
		}
	}

	public static void removePlayersMatch(String player) {
		matches.remove(matchsPlayers.get(player));
		matchsPlayers.remove(player);
	}

	public static void searchMatch(Socket client, String player, String roomId) {
		if (matches.containsKey(roomId)) {
			MatchRoom matchRoom = matches.get(roomId);
			matchRoom.setPlayerTwo(player);
			SessionUtils.sendToOne(client, new SearchRoomSucessCommand(matchRoom));
			SessionUtils.sendToOne(sessions.get(matchRoom.getPlayerOne()).getClient(), new SearchRoomSucessCommand(matchRoom));
			return;
		}
		SessionUtils.sendToOne(client, new SearchRoomFailedCommand());
	}

}