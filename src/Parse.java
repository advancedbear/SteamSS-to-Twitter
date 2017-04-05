import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Parse {
	String url, url2;
	String[] folders = { "C:\\Program Files (x86)\\Steam\\userdata", "C:\\Program Files\\Steam\\userdata" };
	File steamFolder, userFolder, gameFolder, image;
	File[] userList, gameList, imageList;

	public Parse() {
		url = "http://store.steampowered.com/app/";

		/*
		 * for(int i=0; i<folders.length; i++){ steamFolder = new
		 * File(folders[i]); if(steamFolder.exists()){ break; } else {
		 * steamFolder = null; } }
		 */

		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "reg", "query", "HKCU\\Software\\valve\\steam", "/v",
				"SteamPath");
		try {
			Process process = pb.start();
			process.waitFor();

			InputStream is = process.getInputStream(); // •W€o—Í
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			for (;;) {
				String line = br.readLine();
				System.out.println(line);
				if (line.indexOf("SteamPath")!=-1) {
					System.out.println("get enter to if");
					String[] result = line.split("    ");
					System.out.println(result[3]);
					steamFolder = new File(result[3]+"\\userdata");
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userList = steamFolder.listFiles();
		userFolder = new File(userList[0].getAbsolutePath() + "\\760\\remote");
		gameList = userFolder.listFiles();
	}

	public String getTitle(int num) throws IOException {
		Document document = Jsoup.connect(url + num).get();
		// Element elements0 =
		// document.getElementsByClass("responsive_page_template_content").first();
		// Element elements =
		// elements0.getElementsByClass("apphub_AppName").first();
		String elements = document.title();
		// return elements.text();
		return elements;
	}

	public File[] listImage(int num) {
		File f = new File(gameList[num] + "\\screenshots");
		imageList = f.listFiles();
		return imageList;
	}
}
