package org.encinet.oceanbot.common.occommand;

public abstract class BasicCommand {
    String name;
    String[] alias;
    boolean admin;
    
    public BasicCommand(String name, String[] alias, boolean admin) {
        this.name = name;
        this.alias = alias;
        this.admin = admin;
    }
    
    public final String execute(long qq, boolean color) {
        // perm
        return onCommand(qq, color);
    }
    
    public abstract String onCommand(long qq, boolean color);
}