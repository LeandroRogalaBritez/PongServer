package server.config;

import java.io.IOException;

public final class Configuration extends AbstractConfiguration {
    private static Configuration instance;
    private int port;

    public Configuration() throws IOException {
        super();
        this.instance = this;
        this.port = getInt("port");
    }
    
    public int getPort() {
		return port;
	}

    public static Configuration getInstance() {
		return instance;
	}
    
}