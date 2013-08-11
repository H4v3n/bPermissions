package de.bananaco.permissions.commands;

import de.bananaco.permissions.ApiLayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Functions implements CommandExecutor {

    static enum FunctionType {
        PLAYER(new String[] {"pl", "player"}),
        PACKAGE(new String[] {"pa", "pack", "package"}),
        NULL(new String[0]);

        private final String[] aliases;
        private FunctionType(String[] aliases) {
            this.aliases = aliases;
        }
        public static FunctionType getType(String s) {
            for(FunctionType type : FunctionType.values()) {
                for(String al : type.aliases) {
                    if(s.equalsIgnoreCase(al)) {
                        return type;
                    }
                }
            }
            return null;
        }
    }

    static enum ActionType {
        ADD(new String[] {"ad", "pl", "add"}),
        REMOVE(new String[] {"rm", "remo", "remove", "remov"}),
        SET(new String[] {"s", "se", "set"}),
        LIST(new String[] {"l", "li", "lis", "list", "ls", "lst"}),
        NULL(new String[0]);

        private final String[] aliases;
        private ActionType(String[] aliases) {
            this.aliases = aliases;
        }
        public static ActionType getType(String s) {
            for(ActionType type : ActionType.values()) {
                for(String al : type.aliases) {
                    if(s.equalsIgnoreCase(al)) {
                        return type;
                    }
                }
            }
            return null;
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(ApiLayer.isGlobal()) {
            if(args.length < 2) {
                FunctionType type = args.length >= 1?FunctionType.getType(args[0]):FunctionType.NULL;
                ActionType action = args.length >= 2?ActionType.getType(args[1]):ActionType.NULL;
                return error(sender, type, action);
            } else {
                FunctionType type = FunctionType.getType(args[0]);
                ActionType action = ActionType.getType(args[1]);
                String value = args.length>=3?args[2]:null;
                String data = args.length>=4?args[3]:null;
                return executeGlobal(sender, type, action, value, data);
            }
        } else {
            if(args.length < 3) {
                FunctionType type = args.length >= 2?FunctionType.getType(args[1]):FunctionType.NULL;
                ActionType action = args.length >= 3?ActionType.getType(args[2]):ActionType.NULL;
                return error(sender, type, action);
            } else {
                String world = args[0];
                FunctionType type = FunctionType.getType(args[1]);
                ActionType action = ActionType.getType(args[2]);
                String value = args.length>=4?args[3]:null;
                String data = args.length>=5?args[4]:null;
                return executeWorld(sender, world, type, action, value, data);
            }
        }
    }

    public boolean error(CommandSender sender, FunctionType type, ActionType action) {
        sender.sendMessage(ChatColor.RED+"Sorry, "+ChatColor.AQUA+action.name().toLowerCase()+ChatColor.RED+" is not valid for "+ChatColor.AQUA+type.name().toLowerCase()+ChatColor.RED+" in the command you attempted.");
        return true;
    }

    public boolean executeGlobal(CommandSender sender, FunctionType type, ActionType action, String value, String data) {
        // error messages for letting people know about stuff
        if(type.equals(FunctionType.PACKAGE)) {
            if(value == null) {
                if(action.equals(ActionType.LIST)) {

                }
                sender.sendMessage(ChatColor.RED+"Please specify a package");
                return true;
            }
            if(action.equals(ActionType.LIST)) {

            }
            if(data == null) {
                sender.sendMessage(ChatColor.RED+"Please specify a permission to add");
                return true;
            }
            if(action.equals(ActionType.ADD)) {

            } else if(action.equals(ActionType.REMOVE)) {

            }
        }
        if(type.equals(FunctionType.PLAYER)) {
            if(value == null) {
                if(action.equals(ActionType.LIST)) {

                }
                sender.sendMessage(ChatColor.RED+"Please specify a player");
                return true;
            }
            if(action.equals(ActionType.LIST)) {

            }
            if(data == null) {
                sender.sendMessage(ChatColor.RED+"Please specify a package");
                return true;
            }
            if(action.equals(ActionType.ADD)) {

            } else if(action.equals(ActionType.REMOVE)) {

            } else if(action.equals(ActionType.SET)) {

            }

        }
        return error(sender, type, action);
    }

    public boolean executeWorld(CommandSender sender, String world, FunctionType type, ActionType action, String value, String data) {
        // error messages for letting people know about stuff
        if(type.equals(FunctionType.PACKAGE)) {
            return error(sender, type, action);
        }
        if(type.equals(FunctionType.PLAYER)) {
            return error(sender, type, action);
        }
        return error(sender, type, action);
    }

}
