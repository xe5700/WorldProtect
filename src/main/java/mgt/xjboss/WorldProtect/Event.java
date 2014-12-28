package mgt.xjboss.WorldProtect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mgt.xjboss.WorldProtect.Config;

import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
public class Event implements Listener {
	Main Main;
	Config Config;
	Event(Main mainC,Config configC){
		Main=mainC;
		Config=configC;
	}
	@EventHandler
	public void BreakEvent(BlockBreakEvent event){
		String worldname=event.getBlock().getWorld().getName();
		int worldID=0;
		if(Config.CheckWorld(worldname,worldID)){
			if(Config.Forbid_break[worldID].booleanValue()==false)return;
		event.setCancelled(true);
		}
	}
	@EventHandler
	public void UseEvent(PlayerInteractEvent event){
		
		if ((event.getAction()==Action.RIGHT_CLICK_AIR)|(event.getAction()==Action.RIGHT_CLICK_BLOCK)){
			String worldname=event.getPlayer().getWorld().getName();
			String itemname=event.getPlayer().getItemInHand().getType().name();
			int worldID=0;
			if(!Config.CheckItem(worldname, itemname,worldID)){
				if(Config.Limit_use[worldID].booleanValue()==false)return;
				event.setCancelled(true);
			}
			}
		}
	public void DamageEvent(BlockDamageEvent event){
		
	}
	
}
