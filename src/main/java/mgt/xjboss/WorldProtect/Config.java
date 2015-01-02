package mgt.xjboss.WorldProtect;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.json.simple.*;
public class Config {
	String WorldsName[];
	String WorldsItem[][];
	String WorldsBlock[][];
	Boolean Limit_use[];
	Boolean Limit_useblock[];
	Boolean Forbid_break[];
	Boolean Back_Floor[];
	Boolean bantype[];
	Boolean Golbal_Limit_use;
	Boolean Golbal_Limit_useblock;
	Boolean Golbal_Forbid_break;
	Boolean Golbal_Back_Floor;
	String PM="mgt.xjboss.WorldProtect";
	JSONObject langinfo=null;
	JSONObject confj=new JSONObject();
	Main plugin=null;
	Config(Main plugin233){
		plugin=plugin233;
	}
	public void SaveConf(){
		
	}
	public void LoadConf(){
		File config_file= new File(plugin.getDataFolder(), "config.json");
		if(!config_file.exists()){
			try {
				File config_dir= new File(plugin.getDataFolder(), "");
				config_dir.mkdirs();
				config_file.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				config_file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JSONArray Jworlds=new JSONArray();
			JSONObject JWSET=new JSONObject();
			JSONObject testworld=new JSONObject();
			JSONArray Jitemwhitelist=new JSONArray();
			JSONArray UsedBlockwhitelist=new JSONArray();
			Jitemwhitelist.add(new String("AIR"));
			UsedBlockwhitelist.add(new String("SIGN"));
			UsedBlockwhitelist.add(new String("STONE_BUTTON"));
			UsedBlockwhitelist.add(new String("WOODEN_BUTTON"));
			UsedBlockwhitelist.add(new String("STONE_PRESSURE_PLATE"));
			UsedBlockwhitelist.add(new String("WOORDEN_PRESSURE_PLATE"));
			UsedBlockwhitelist.add(new String("LIGHT_WEIGHTED_WEIGHTED"));
			UsedBlockwhitelist.add(new String("HEARY_WEIGHTED_WEIGHTED"));
			Jworlds.add("testworld");
			confj.put("Confver", new String("1"));
			confj.put("Language", new String("en_US"));
			JWSET.put("WorldsList",Jworlds);
			testworld.put("Itemwhitelist", Jitemwhitelist);
			testworld.put("Limit_use", new Boolean(true));
			testworld.put("Forbid_break", new Boolean(true));
			testworld.put("Forbid_damged", new Boolean(true));
			testworld.put("Limit_useblock", new Boolean(false));
			testworld.put("UsedBlockwhitelist",UsedBlockwhitelist);
			JWSET.put("testworld", testworld);
			confj.put("WorldSetting", JWSET);
			String NewJ=confj.toJSONString();
			//System.out.println(NewJ);
			try {
				FileWriter fw=new FileWriter(config_file);
				fw.write(NewJ);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LoadWorldlist();
		}else{
			FileInputStream inputfile=null;
			
			try{
				inputfile=new FileInputStream(config_file);
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			byte inputbyte[]=null;
			try {
				inputbyte = new byte[inputfile.available()];
				inputfile.read(inputbyte);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				inputfile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			confj=(JSONObject)JSONValue.parse(new String(inputbyte));
			//System.out.println(new String(inputbyte));
			LoadWorldlist();
		}

	}
	public void LoadLanguage(String language){
		InputStream langf=this.getClass().getResourceAsStream("/lang/"+language+".langjson");
		BufferedReader langr=new BufferedReader(new InputStreamReader(langf,Charset.forName("utf-8")));
		String jsonI="";
		
		try {
			while(true){
				String I=langr.readLine();
				if(I!=null){
				jsonI=jsonI+I;
				}else{
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		try {
			langr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			langf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		langinfo=(JSONObject)(JSONValue.parse(jsonI));
		//System.out.println(langinfo.toString());
	}
	public void LoadItemwhitelist(JSONArray JItems,int wc){
		int Isize=JItems.size();
		int Icount=0;
		WorldsItem[wc]=new String[Isize];
		for(Icount=0;Icount<Isize;Icount++){
			WorldsItem[wc][Icount]=(String)(JItems.get(Icount));
		}
	}
	public void LoadBlockwhitelist(JSONArray JBlocks,int wc){
		int Isize=JBlocks.size();
		int Icount=0;
		WorldsBlock[wc]=new String[Isize];
		for(Icount=0;Icount<Isize;Icount++){
			WorldsBlock[wc][Icount]=(String)(JBlocks.get(Icount));
		}
	}
	public void LoadWorldlist(){
		JSONObject JWSET=(JSONObject)confj.get("WorldSetting");
		JSONArray Jworlds=(JSONArray)JWSET.get("WorldsList");

		int ws=Jworlds.size();
		int wc=0;
		WorldsName=new String[ws];
		WorldsItem=new String[ws][];
		WorldsBlock=new String[ws][];
		LoadLanguage((String)confj.get("Language"));
		for(wc=0;wc<ws;wc++){
			System.out.println(wc);
			WorldsName[wc]=Jworlds.get(wc).toString();
			JSONObject JWSETW=(JSONObject)JWSET.get(WorldsName[wc]);
			JSONArray JItems =(JSONArray)(JWSETW.get("Itemwhitelist"));
			JSONArray JBlocks =(JSONArray)(JWSETW.get("UsedBlockwhitelist"));
			LoadItemwhitelist(JItems,wc);
			LoadBlockwhitelist(JBlocks,wc);	
			Limit_use=new Boolean[ws];
			Limit_useblock=new Boolean[ws];
			Forbid_break=new Boolean[ws];
			ReadWorldSet(JWSETW,"Limit_use",Limit_use,wc);
			ReadWorldSet(JWSETW,"Limit_useblock",Limit_useblock,wc);
			ReadWorldSet(JWSETW,"Forbid_break",Forbid_break,wc);

			
		}
		
	}
	public void ReadWorldSet(JSONObject JS,String name,Boolean BLE[],int count){
		BLE[count]=(Boolean)(JS.get(name));
	}
	public boolean CheckWorld(String worldUUID){
		int count=0;
		int max=WorldsName.length;
		for(count=0;count<max;count++){
			if(WorldsName[count].equals(worldUUID))return true;
		}
		return false;
	}
	public boolean CheckWorld(String worldUUID,int worldID){
		int count=0;
		int max=WorldsName.length;
		for(count=0;count<max;count++){
			if(WorldsName[count].equals(worldUUID)){
				worldID=count;
				return true;
			}
		}
		worldID=-1;
		return false;
	}
	public boolean CheckItem(String worldUUID,String ItemName){
		int worldID=0;
		boolean WCheck=CheckWorld(worldUUID,worldID);
		if(WCheck){
			int count=0;
			int max=WorldsItem[worldID].length;
			for(count=0;count<max;count++){
				if(WorldsItem[worldID][count].equals(ItemName)|
						WorldsItem[worldID][count].equals(ItemName.split(":")[0]))
					return true;
			}
			return false;
		}
		worldID=-1;
		return true;
	}
	public boolean CheckItem(String worldUUID,String ItemName,int WID){
		int worldID=0;
		boolean WCheck=CheckWorld(worldUUID,worldID);
		if(WCheck){
			WID=worldID;
			int count=0;
			int max=WorldsItem[worldID].length;
			for(count=0;count<max;count++){
				if(WorldsItem[worldID][count].equals(ItemName)|
						WorldsItem[worldID][count].equals(ItemName.split(":")[0]))
					return true;
			}
			return false;
		}
		worldID=-1;
		return true;
	}
	public boolean CheckBlock(String worldUUID,String BlockName,int WID){
		int worldID=0;
		boolean WCheck=CheckWorld(worldUUID,worldID);
		if(WCheck){
			WID=worldID;
			int count=0;
			int max=WorldsBlock[worldID].length;
			for(count=0;count<max;count++){
				if((WorldsBlock[worldID][count].equals(BlockName))|
						(WorldsBlock[worldID][count].equals(BlockName.split(":")[0])))return true;
			}
			return false;
		}
		worldID=-1;
		return true;
	}
	public String L(String readinfo){
		String text= ((String)(langinfo.get(readinfo)));
		while(true){
		String T=text;
		text=text.replace("&4", ChatColor.DARK_RED.toString());
		text=text.replace("&c", ChatColor.RED.toString());
		text=text.replace("&6", ChatColor.GOLD.toString());
		text=text.replace("&a", ChatColor.GREEN.toString());
		text=text.replace("&e", ChatColor.YELLOW.toString());
		text=text.replace("&2", ChatColor.DARK_GREEN.toString());
		text=text.replace("&b", ChatColor.AQUA.toString());
		text=text.replace("&3", ChatColor.DARK_AQUA.toString());
		text=text.replace("&1", ChatColor.DARK_BLUE.toString());
		text=text.replace("&9", ChatColor.BLUE.toString());
		text=text.replace("&d", ChatColor.LIGHT_PURPLE.toString());
		text=text.replace("&5", ChatColor.DARK_PURPLE.toString());
		text=text.replace("&f", ChatColor.WHITE.toString());
		text=text.replace("&7", ChatColor.GRAY.toString());
		text=text.replace("&8", ChatColor.DARK_GRAY.toString());
		text=text.replace("&0", ChatColor.BLACK.toString());
		if(T==text)break;
		}
			return text;
	}
	public String L(String readinfo,String replacedinfo){
		String text=L(readinfo);
		if(text.isEmpty()){
			return "";
		}
		while(true){
			String T=text;
			text=text.replace("${replace1}", replacedinfo);
			if(T==text)break;
		}

		return text;
	}
	public String L(String readinfo,String replacedinfo[]){
		String text=L(readinfo);
		if(text.isEmpty()){
			return "";
		}
		while(true){
			String T=text;
			for(int i=0;i<replacedinfo.length;i++){
				text=text.replace("${replace"+(i+1)+"}", replacedinfo[i]);
			}
			if(T==text)break;
		}
		
		
		return text;
	}

}
