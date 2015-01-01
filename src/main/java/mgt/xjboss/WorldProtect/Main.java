package mgt.xjboss.WorldProtect;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.World;
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
				if(args.length>=1){
					if(!P.hasPermission("WorldProtect.admin")&
							!P.hasPermission("WorldProtect.*")&
							!P.isOp()){
						P.sendMessage(config.L("command_per"));
						return true;
					}
					if(args[0].equals("help")){
						P.sendMessage(config.L("command_help_firstline"));
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect getU <world>"							);
						P.sendMessage(config.L("command_help_getU")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect PI <world>"							);
						P.sendMessage(config.L("command_help_PI") 	   );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect addworld <world>"						);
						P.sendMessage(config.L("command_help_addworld")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect additem <world> <itemname>"			);
						P.sendMessage(config.L("command_help_additem")     );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect adduseblock <world> <world:subid>"	);
						P.sendMessage(config.L("command_help_adduseblock") );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect build e/d <world>"					);
						P.sendMessage(config.L("command_help_build")       );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect damage e/d <world>"					);
						P.sendMessage(config.L("command_help_damage")      );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect break e/d <world>"					);
						P.sendMessage(config.L("command_help_use")         );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect use e/d <world>"					);
						P.sendMessage(config.L("command_help_useblock")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect useblock e/d <world>"				);
						P.sendMessage(config.L("command_help_itemmode")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect itemmode w/b <world>"				);
						P.sendMessage(config.L("command_help_useblockmode"));
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect useblockmode w/b <world>"			);
						P.sendMessage(config.L("command_help_addworld")    );
						P.sendMessage(ChatColor.DARK_GREEN+"/WorldProtect reload"								);
						P.sendMessage(config.L("command_help_reload")    );	
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
							World MCW=sender.getServer().getWorld(args[1]);
							P.sendMessage(MCW.getUID().toString());
							return true;
						}

						P.sendMessage(P.getWorld().getUID().toString());
					}
					if(args[0].equals("PI")){
						String WUUID="";
						World MCW;
						int WID=0;
						if(args.length==2){
							MCW=sender.getServer().getWorld(args[2]);
							WUUID= MCW.getUID().toString();


							return true;
						}else{
							MCW=P.getWorld();
							WUUID=P.getWorld().getUID().toString();
						}
						if(config.CheckWorld(WUUID,WID)){
							P.sendMessage("¡ìf[¡ì5"+MCW.getName()+"¡ì5]");
							P.sendMessage(config.L("Limit_use")+":"+config.Limit_use[WID].toString()+","+
									config.L("Limit_useblock")+":"+config.Limit_useblock[WID].toString()+","+
									config.L("Forbid_break")+":"+config.Forbid_break[WID].toString());
							int MAXXWI=config.WorldsItem.length;
							int MAXXWB=config.WorldsBlock.length;
							String Itemslist_="";
							for(int C=0;C<MAXXWI-1;MAXXWI++){
								String IT=config.WorldsItem[WID][C];
								Itemslist_=Itemslist_+"¡ìf[¡ìc"+IT+"¡ìf]"+"¡ìa,";
							}
							String Blockslist_="";
							for(int C=0;C<MAXXWB-1;MAXXWB++){
								String IT=config.WorldsBlock[WID][C];
								Blockslist_=Blockslist_+"¡ìf[¡ìc"+IT+"¡ìf]"+"¡ìa,";
							}
							P.sendMessage("¡ìf[¡ì4"+config.L("Items")+" "+config.L("Whitelist")+"]¡ìf  ("+Itemslist_+")");
							P.sendMessage("¡ìf[¡ì4"+config.L("Block")+" "+config.L("Whitelist")+"]¡ìf  ("+Blockslist_+")");
							return true;
						}else{
							P.sendMessage(config.L(("worldcheck_failed"),MCW.getName()));
						}
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
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect getU <world>"						);
					P.sendMessage(config.L("command_help_getU")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect PI <world>"							);
					P.sendMessage(config.L("command_help_PI") 	   );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect addworld <world>");
					P.sendMessage(config.L("command_help_addworld"));
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect additem <world> <itemname>"         );
					P.sendMessage(config.L("command_help_additem")     );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect adduseblock <world> <world:subid>"  );
					P.sendMessage(config.L("command_help_adduseblock") );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect build e/d <world>"                          );
					P.sendMessage(config.L("command_help_build")       );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect damage e/d <world>"                         );
					P.sendMessage(config.L("command_help_damage")      );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect break e/d <world>"                          );
					P.sendMessage(config.L("command_help_use")         );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect use e/d <world>"                            );
					P.sendMessage(config.L("command_help_useblock")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect useblock e/d <world>"                       );
					P.sendMessage(config.L("command_help_itemmode")    );
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect itemmode w/b <world>"                       );
					P.sendMessage(config.L("command_help_useblockmode"));
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect useblockmode w/b <world>"                   );
					P.sendMessage(config.L("command_help_addworld")    );
					
					P.sendMessage(ChatColor.DARK_GREEN+"WorldProtect reload"                             );
					P.sendMessage(config.L("command_help_reload")    );
					P.sendMessage("");
					P.sendMessage(ChatColor.GREEN+config.L("author")+":"+ChatColor.YELLOW+"xjboss "
					+ChatColor.GREEN+config.L("website")+ChatColor.YELLOW+":xjboss.net");
					P.sendMessage("");
					P.sendMessage(config.L("command_help_lastline"));
					
				}
				if(args[0].equals("reload")){
					config.LoadConf();
					P.sendMessage(config.L("reload_ok"));
					return true;
				}
				if(args[0].equals("getU")&args.length==2){
					World MCW=sender.getServer().getWorld(args[2]);
					P.sendMessage(MCW.getUID().toString());
					return true;
			}
			}
			sender.sendMessage(config.L("command_none"));
			return true;
		}
		return true;
	}
}
