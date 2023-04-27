package org.encinet.kitebot.file;

public class FileManager {
    public static File modDir = new File(FabricLoader.getInstance().getConfigDirectory(), "kitebot");
    
    public static Config config() throws IOException {
        return new Config(modDir);
    }
    
    public static Whitelist whitelist() throws IOException {
        return new whitelist(modDir.getPath());
    }
}