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

public class ServerMain {
	private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
	private static Map<String, MatchRoom> matches = new HashMap<>();
	private static Map<String, String> matchsPlayers = new HashMap<>();

	public void start() throws IOException {
		System.out.println("Iniciando conexão");
		ServerSocket server = new ServerSocket(Configuration.getInstance().getPort());
		System.out.println("Servidor Iniciado na porta: " + Configuration.getInstance().getPort());
		while(true) {
			System.out.println("Esperando conexão do cliente");
	        Socket client = server.accept();
	        String threadName = String.format("%s:%d", client.getInetAddress().getHostAddress(), client.getPort());	
	        System.out.println("Cliente conectado: " + threadName);
	        sendPlayersToClient(client, threadName);
			System.out.println("Players conectados: " + sessions.size());
	    }
	}
	
	private void sendPlayersToClient(Socket client, String threadName) {
        SessionUtils.sendToAll(sessions, new ConnectCommand(threadName));
		sessions.put(threadName, new Session(client, threadName));
		SessionUtils.sendToOne(client, new ConnectCommand(getAllKeySessions(), threadName));
	}

	private List<String> getAllKeySessions() {
		List<String> sessions = new ArrayList<>();
		this.sessions.entrySet().stream().forEach(entry -> sessions.add(entry.getKey()));
		return sessions;
	}

	public static Map<String, Session> getSessions() {
		return sessions;
	}
	
	public static Map<String, MatchRoom> getMatches() {
		return matches;
	}

	public static String createNewMatch(String player) {
		while(true) {
			Random rand = new Random();
			String roomId = "" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			if (matches.containsKey(roomId)) {
				continue;
			}
			matches.put(roomId, new MatchRoom(player));
			System.out.println("Criado nova sala: " + roomId);
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