package server.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractConfiguration {    
    protected final Properties config;
            
    protected AbstractConfiguration() throws IOException {
        this.config = loadSettings();
    }         
        
    private Properties loadSettings() throws IOException{
        Properties settings = new Properties();
        
        System.out.println("Load config");
        
        try (InputStream in = getClass().getResourceAsStream("/config.properties")) {
        	settings.load(in);
        }
       
        return settings;
    }
    
    protected boolean getBoolean(String key){
        return Boolean.valueOf(getString(key));
    }
    
    protected byte getByte(String key){
        return Byte.valueOf(getString(key));
    }
    
    protected short getShort(String key){
        return Short.valueOf(getString(key));
    }
    
    protected int getInt(String key){
        return Integer.valueOf(getString(key));
    }
    
    protected long getLong(String key){
        return Long.valueOf(getString(key));
    }
    
    protected String getString(String key){
        return getString(key, null);
    }
    
    protected String getString(String key, String defaultValue){
        return config.getProperty(key, defaultValue);
    }
    
}
