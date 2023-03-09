package org.encinet.oceanbot.common.occommand;

public abstract class BasicCommand {
    String name;
    String[] alias;
    String description;
    boolean admin;
    
    public BasicCommand(String name, String alias, String description, boolean admin) {
        this.name = name;
        this.alias = new String[] {alias};
        this.description = description;
        this.admin = admin;
    }
    public BasicCommand(String name, String[] alias, String description, boolean admin) {
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.admin = admin;
    }
    
    public abstract String onCommand(String label, long qq, boolean color);
    public abstract String onTab(String[] args, long qq);
    
    public final String getName() {
        return this.name;
    }
    public final String[] getAlias() {
        return this.alias;
    }
    public final String getDescription() {
        return this.description;
    }
    public final boolean getAdmin() {
        return this.admin;
    }
}