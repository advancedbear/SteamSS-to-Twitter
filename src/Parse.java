import java.io.*;
import java.net.URL;

import com.fasterxml.jackson.databind.*;

public class Parse {
	String url, url2;
	String[] folders = { "C:\\Program Files (x86)\\Steam\\userdata", "C:\\Program Files\\Steam\\userdata" };
	File steamFolder, userFolder, gameFolder, image;
	File[] userList, gameList, imageList;

	public Parse() {
		url = "http://store.steampowered.com/api/appdetails?appids=";

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

			InputStream is = process.getInputStream(); // �W���o��
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
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(new URL(url+num));
		String result = root.get(""+num).get("data").get("name").asText();
		System.out.println(result);
		// return elements.text();
		return result;
	}

	public File[] listImage(int num) {
		File f = new File(gameList[num] + "\\screenshots");
		imageList = f.listFiles();
		return imageList;
	}
}
