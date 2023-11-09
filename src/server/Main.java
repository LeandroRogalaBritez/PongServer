package server;

import server.config.Configuration;
import server.main.ServerMain;

public class Main {
    public static void main(String[] args) {
        try {
            new Configuration();
            new ServerMain().start();
        } catch (Exception e) {
            System.out.println("Erro de inicialização: ");
            e.printStackTrace();
        }
    }
}
