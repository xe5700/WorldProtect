package mgt.xjboss.WorldProtect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Config config=new Config(this);
	Event pluginevent=new Event(this,config);
	public void onEnable(){
		System.out.println("[WorldProtect]World Protect System already!");
		System.out.println("[WorldProtect]Powered by xjboss");
		System.out.println("[WorldProtect]Blog Website http://xjboss.net");
		config.LoadConf();
		getServer().getPluginManager().registerEvents(pluginevent, this);
	}
	public void onDisable(){
		
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("WorldProtect")){
			if ((sender instanceof Player)) {
				Player P=(Player)sender;
				if(args.length==1){
					if(!P.hasPermission("WorldProtect.admin")&
							!P.hasPermission("WorldProtect.*")&
							!P.isOp()){
						P.sendMessage(config.L("command_per"));
						return true;
					}
					if(args[0].equals("help")){
						P.sendMessage(config.L("command_help_firstline"));
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect getU <world>"                  +config.L("command_help_getU")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect addworld <world>"                  +config.L("command_help_addworld")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect additem <world> <itemname>"        +config.L("command_help_additem")     );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect adduseblock <world> <world:subid>" +config.L("command_help_adduseblock") );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect build e/d"                         +config.L("command_help_build")       );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect damage e/d"                        +config.L("command_help_damage")      );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect break e/d "                        +config.L("command_help_use")         );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect use e/d"                           +config.L("command_help_useblock")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect useblock e/d"                      +config.L("command_help_itemmode")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect itemmode w/b"                      +config.L("command_help_useblockmode"));
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect useblockmode w/b"                  +config.L("command_help_addworld")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect reload "                           +config.L("command_help_reload")    );
						P.sendMessage("");
						P.sendMessage(ChatColor.GREEN+config.L("author")+":"+ChatColor.YELLOW+"xjboss ");
						P.sendMessage("");
						P.sendMessage(config.L("command_help_lastline"));
						
					}
					if(args[0].equals("reload")){
						config.LoadConf();
						P.sendMessage(config.L("reload_ok"));
					}
					if(args[0].equals("getU")){
						if(args.length==2){
							return true;
						}
						P.sendMessage(P.getWorld().getUID().toString());
					}
					return true;
				}
				
			}else{
				CommandSender P=sender;
				if(args[0].equals("help")){
					if(!P.hasPermission("WorldProtect.admin")&
							!P.hasPermission("WorldProtect.*")&
							!P.isOp())
						return false;
					P.sendMessage(config.L("command_help_firstline"));
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect addworld <world>"                   +config.L("command_help_addworld")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect additem <world> <itemname>"         +config.L("command_help_additem")     );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect adduseblock <world> <world:subid>"  +config.L("command_help_adduseblock") );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect build e/d"                          +config.L("command_help_build")       );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect damage e/d"                         +config.L("command_help_damage")      );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect break e/d "                         +config.L("command_help_use")         );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect use e/d"                            +config.L("command_help_useblock")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect useblock e/d"                       +config.L("command_help_itemmode")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect itemmode w/b"                       +config.L("command_help_useblockmode"));
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect useblockmode w/b"                   +config.L("command_help_addworld")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect reload "                            +config.L("command_help_reload")    );
					P.sendMessage("");
					P.sendMessage(ChatColor.GREEN+config.L("author")+":"+ChatColor.YELLOW+"xjboss "
					+ChatColor.GREEN+config.L("website")+ChatColor.YELLOW+":xjboss.net");
					P.sendMessage("");
					P.sendMessage(config.L("command_help_lastline"));
					
				}
				if(args[0].equals("reload")){
					config.LoadConf();
				}
			}
		}
		return true;
	}
}
