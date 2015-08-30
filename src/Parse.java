import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        Document document = Jsoup.connect(url+num).get();
        Element elements0 = document.getElementsByClass("responsive_page_template_content").first();
        Element elements = elements0.getElementsByClass("apphub_AppName").first();
        //Elements elements = document.getElementsByAttribute("apphub_AppName");
        return elements.text();
	}
	
	public File[] listImage(int num){
		File f = new File(gameList[num]+"\\screenshots");
		imageList = f.listFiles();
		return imageList;
	}
}
