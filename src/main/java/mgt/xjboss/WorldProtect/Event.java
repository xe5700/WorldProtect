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
	public void BuildEvent(){
		
	}
	@EventHandler
	public void BreakEvent(BlockBreakEvent event){
		String worldname=event.getBlock().getWorld().getName();
		String blockname=event.getBlock().getType().name()+":"+event.getBlock().getData();
		int worldID=0;
		if(Config.CheckWorld(worldname,worldID)){
			if(Config.Forbid_break[worldID].booleanValue()==false)return;
			if(event.getPlayer().isOp())return;
			if(event.getPlayer().hasPermission(Config.PM+".*"))return;
			if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.break"))return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(Config.L("break_failed",new String[]{blockname,worldname}));
		}
	}
	@EventHandler
	public void UseEvent(PlayerInteractEvent event){
		
		if ((event.getAction()==Action.RIGHT_CLICK_AIR)|(event.getAction()==Action.RIGHT_CLICK_BLOCK)){
			String worldname=event.getPlayer().getWorld().getName();
			String itemname=event.getPlayer().getItemInHand().getType().name()+":"+event.getPlayer().getItemInHand().getData().getData();
			int worldID=0;
			if(!Config.CheckItem(worldname, itemname,worldID)){
				
				if(Config.Limit_use[worldID].booleanValue()==false)return;
				if(event.getPlayer().isOp())return;
				if(event.getPlayer().hasPermission(Config.PM+".*"))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.use.*"))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.use."+itemname))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.use."+itemname.split(":")[0]))return;
				event.setCancelled(true);
				event.getPlayer().sendMessage(Config.L("use_failed",new String[]{itemname,worldname}));
			}
			}
		}
	public void UsedBlockEvent(PlayerInteractEvent event){
		
		if ((event.getAction()==Action.RIGHT_CLICK_AIR)|(event.getAction()==Action.RIGHT_CLICK_BLOCK)){
			String worldname=event.getPlayer().getWorld().getName();
			String blockname=event.getClickedBlock().getType().name()+":"+event.getClickedBlock().getData();
			int worldID=0;
			if(!Config.CheckItem(worldname, blockname,worldID)){
				if(Config.Limit_use[worldID].booleanValue()==false)return;
				if(event.getPlayer().isOp())return;
				if(event.getPlayer().hasPermission(Config.PM+".*"))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.*"))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.usedblock.*"))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.usedblock."+blockname))return;
				if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.usedblock."+blockname.split(":")[0]))return;
				event.setCancelled(true);
				event.getPlayer().sendMessage(Config.L("use_failed",new String[]{blockname,worldname}));
			}
			}
		}
	public void DamageEvent(BlockDamageEvent event){
		String worldname =event.getBlock().getWorld().getName();
		String blockname=event.getBlock().getType().name()+":"+event.getBlock().getData();
		int worldID=0;
		if(!Config.CheckWorld(worldname,worldID)){
			if(Config.Forbid_damged[worldID].booleanValue()==false)return;
			if(event.getPlayer().isOp())return;
			if(event.getPlayer().hasPermission(Config.PM+".*"))return;
			if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.*"))return;
			if(event.getPlayer().hasPermission(Config.PM+"."+worldname+".allow.damage"))return;
		
			event.setCancelled(true);
			event.getPlayer().sendMessage(Config.L("break_failed",new String[]{blockname,worldname}));
		}
	}
	
}
