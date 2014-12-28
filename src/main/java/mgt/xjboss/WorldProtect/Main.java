package mgt.xjboss.WorldProtect;

import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {
	Config pluginconfig=new Config(this);
	Event pluginevent=new Event(this,pluginconfig);
	public void onEnable(){
		System.out.println("[WorldProtect]World Protect System already!");
		System.out.println("[WorldProtect]Powered by xjboss");
		System.out.println("[WorldProtect]Blog Website http://xjboss.net");
		pluginconfig.LoadConf();
		getServer().getPluginManager().registerEvents(pluginevent, this);
	}
	public void onDisable(){

	}
}
