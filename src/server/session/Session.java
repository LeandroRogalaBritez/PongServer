package server.session;

import network.cmd.Command;
import network.cmd.DisconnectCommand;
import server.main.ServerMain;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Session extends Thread {
	protected Socket client;
	protected boolean active;
	protected String threadName;
	protected boolean searchGame;

	@Override
	public void run() {
		this.active = true;
		while (active) {
			try {
				ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
				Command comando = (Command) entrada.readObject();
				System.out.println(comando.toString());
				comando.executeServer(client, threadName);
			} catch (Throwable t) {
				t.printStackTrace();
				disconnectClient();
				ServerMain.getSessions().remove(getThreadName());
				ServerMain.removePlayersMatch(getThreadName());
				SessionUtils.sendToAll(ServerMain.getSessions(), new DisconnectCommand(getThreadName()));
			}
		}
	}

	public Session(Socket client, String threadName) {
		this.client = client;
		this.active = false;
		this.threadName = threadName;
		this.start();
	}
	
	public String getThreadName() {
		return threadName;
	}
	
	public Socket getClient() {
		return client;
	}

	public synchronized void disconnectClient(){
        if(this.active){
            this.active = false;
            try{
                this.client.close();
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
	
	public void setSearchGame(boolean searchGame) {
		this.searchGame = searchGame;
	}
	
	public boolean isSearchGame() {
		return searchGame;
	}

}
