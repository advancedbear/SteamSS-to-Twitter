import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Parse {
	String url, url2;
	String[] folders = {"C:\\Program Files (x86)\\Steam\\userdata", "C:\\Program Files\\Steam\\userdata"};
	File steamFolder, userFolder, gameFolder, image;
	File[] userList, gameList, imageList;
	
	public Parse(){
	    	url = "http://store.steampowered.com/app/";
	    	
	    	for(int i=0; i<folders.length; i++){
	    		steamFolder = new File(folders[i]);
	    		if(steamFolder.exists()){
	    			break;
	    		}  else {
	    			steamFolder = null;
	    		}
	    	}
	    	
	    	userList = steamFolder.listFiles();
	    	userFolder = new File(userList[0].getAbsolutePath()+"\\760\\remote");
	    	gameList = userFolder.listFiles();
	}
	
	public String getTitle(int num) throws IOException {
		url += num;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("div.apphub_AppName");
        return elements.text();
	}
	
	public String getName(int num) throws IOException {
		url2 += num;
        Document document = Jsoup.connect(url2).get();
        Elements elements = document.select("div.apphub_AppName");
        return elements.text();
	}
}
