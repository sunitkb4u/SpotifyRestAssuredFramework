package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader(){
        properties = PropertyUtils.propertiesLoader("src/test/resorces/data.properties");
    }

    public static DataLoader getInstance(){
        if(dataLoader == null){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if (prop!=null) return prop;
        else throw  new RuntimeException("property client_id is not specified in the config.properties file");
    }

    public String updatePlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if (prop!=null) return prop;
        else throw  new RuntimeException("property client_id is not specified in the config.properties file");
    }

}
