package mgt.xjboss.WorldProtect;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.simple.*;
public class Config {
	String WorldsName[];
	String WorldsItem[][];
	String WorldsBlock[][];
	Boolean Limit_use[];
	Boolean Forbid_break[];
	Boolean Forbid_damged[];
	Boolean Back_Floor[];
	JSONObject confj=new JSONObject();
	Main plugin=null;
	Config(Main plugin233){
		plugin=plugin233;
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
			Jitemwhitelist.add(new String("AIR:*"));
			UsedBlockwhitelist.add(new String("SIGN"));
			UsedBlockwhitelist.add(new String("STONE_BUTTON"));
			UsedBlockwhitelist.add(new String("WOODEN_BUTTON"));
			UsedBlockwhitelist.add(new String("STONE_PRESSURE_PLATE"));
			UsedBlockwhitelist.add(new String("WOORDEN_PRESSURE_PLATE"));
			UsedBlockwhitelist.add(new String("LIGHT_WEIGHTED_WEIGHTED"));
			UsedBlockwhitelist.add(new String("HEARY_WEIGHTED_WEIGHTED"));
			Jworlds.add("testworld");
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
			
			String inputinfo=null;
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
	public void LoadItemwhitelist(JSONArray JItems,int wc){
		int Isize=JItems.size();
		int Icount=0;
		WorldsItem[wc]=new String[Isize];
		for(Icount=0;Icount<Isize-1;Icount++){
			WorldsItem[wc][Icount]=JItems.get(Icount).toString();
		}
	}
	public void LoadWorldlist(){
		JSONObject JWSET=(JSONObject)confj.get("WorldSetting");
		JSONArray Jworlds=(JSONArray)JWSET.get("WorldsList");

		int ws=Jworlds.size();
		int wc=0;
		WorldsName=new String[ws];
		WorldsItem=new String[ws][];
		for(wc=0;wc<ws-1;wc++){
			System.out.println(wc);
			WorldsName[wc]=Jworlds.get(wc).toString();
			JSONObject JWSETW=(JSONObject)JWSET.get(WorldsName[wc]);
			JSONArray JItems =(JSONArray)(JWSETW.get("Itemwhitelist"));
			LoadItemwhitelist(JItems,wc);
			ReadWorldSet(JWSETW,"Limit_use",Limit_use,wc);
			ReadWorldSet(JWSETW,"Forbid_break",Forbid_break,wc);
			ReadWorldSet(JWSETW,"Forbid_damged",Forbid_damged,wc);
		}
		
	}
	public void ReadWorldSet(JSONObject JS,String name,Boolean BLE[],int count){
		BLE[count]=(Boolean)(JS.get(name));
	}
	public boolean CheckWorld(String WorldName){
		int count=0;
		int max=WorldName.length();
		for(count=0;count<max-1;count++){
			if(WorldsName[count]==WorldName)return true;
		}
		return false;
	}
	public boolean CheckWorld(String WorldName,int worldID){
		int count=0;
		int max=WorldName.length();
		for(count=0;count<max-1;count++){
			if(WorldsName[count]==WorldName){
				worldID=count;
				return true;
			}
		}
		return false;
	}
	public boolean CheckItem(String WorldName,String ItemName){
		int worldID=0;
		boolean WCheck=CheckWorld(WorldName,worldID);
		if(WCheck){
			int count=0;
			int max=WorldsItem[worldID].length;
			for(count=0;count<max-1;count++){
				if(WorldsItem[worldID][count]==ItemName)return true;
			}
		}
		return false;
	}
	public boolean CheckItem(String WorldName,String ItemName,int WID){
		int worldID=0;
		boolean WCheck=CheckWorld(WorldName,worldID);
		if(WCheck){
			WID=worldID;
			int count=0;
			int max=WorldsItem[worldID].length;
			for(count=0;count<max-1;count++){
				if(WorldsItem[worldID][count]==ItemName)return true;
			}
		}
		return false;
	}
	public boolean CheckBlock(String WorldName,String BlockName,int WID){
		int worldID=0;
		boolean WCheck=CheckWorld(WorldName,worldID);
		if(WCheck){
			WID=worldID;
			int count=0;
			int max=WorldsBlock[worldID].length;
			for(count=0;count<max-1;count++){
				if(WorldsBlock[worldID][count]==BlockName)return true;
			}
		}
		return false;
	}
}
