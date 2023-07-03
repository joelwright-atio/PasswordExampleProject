package de.atio;

public class MainApp {

    public static void main(String[] args) throws Exception{

        ConfigFileHandler configHandler = new ConfigFileHandler("config.json");
        // get and print the ThingWorx server password
        String password = configHandler.GetStringValue("thingworx,serverSettings,appKey");
        System.out.println(password);
    }

}
