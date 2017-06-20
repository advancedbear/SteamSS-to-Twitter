/*The jsoup code-base (include source and compiled packages) are distributed
* under the open source MIT license as described below.
* 
* The MIT License
* Copyright © 2009 - 2016 Jonathan Hedley (jonathan@hedley.net)
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of
* this software and associated documentation files (the "Software"), to deal in
* the Software without restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
* Software, and to permit persons to whom the Software is furnished to do so,
* subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in 
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/

/*This copy of Jackson JSON processor streaming parser/generator is licensed under the
* Apache (Software) License, version 2.0 ("the License").
* See the License for details about distribution rights, and the
* specific rights regarding derivate works.
* 
* You may obtain a copy of the License at:
* 
* http://www.apache.org/licenses/LICENSE-2.0
*/

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

			InputStream is = process.getInputStream(); // �ｿｽW�ｿｽ�ｿｽ�ｿｽo�ｿｽ�ｿｽ
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
