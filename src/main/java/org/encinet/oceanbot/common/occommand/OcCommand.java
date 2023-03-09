package org.encinet.oceanbot.common.occommand;

import org.encinet.oceanbot.common.occommand.commands.*;
import org.encinet.oceanbot.file.Config;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OcCommand {
    static Map<String, BasicCommand> commands= new ConcurrentHashMap<>();
    
    public OcCommand() {
        register(new info());
    }
    
    private void register(BasicCommand command) {
        commands.put(command.getName(), command);
    }
    
    public void execute(String label, long qq, boolean color) {
        String commandHead = label.split(" ")[0];
        if (commands.containsKey(commandHead)) {
            BasicCommand command = commands.get(commandHead);
            if (command.getAdmin() ? hasPermission(qq) : true) {
                command.onCommand(label, qq, color);
            }
        }
    }
    
    private final boolean hasPermission(long qqNum) {
        for (Long qq : Config.admin) {
            if (qq.equals(qqNum)) {
                return true;
            }
        }
        return false;
    }
}