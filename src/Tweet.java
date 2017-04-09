import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Tweet {
	Twitter twitter;
	AccessToken accessToken = null;
	RequestToken requestToken;

	public Tweet() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthAccessToken(accessToken);
	}

	public void TwitterAuth() {
		try {
			Desktop desktop = Desktop.getDesktop();
			requestToken = twitter.getOAuthRequestToken();
			URI uri = new URI(requestToken.getAuthorizationURL());
			desktop.browse(uri);
		} catch (TwitterException e) {
			System.out.println("Error");
		} catch (URISyntaxException e) {
			System.out.println("Error");
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public void authorization(String pin) {
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			twitter.setOAuthAccessToken(accessToken);
		} catch (TwitterException e) {
			System.out.println("Authorization Error!");
		}
	}

	public void authorization(AccessToken token) {
		twitter.setOAuthAccessToken(token);
	}
	
	public void tweet(String t, File f){
			try {
				twitter.updateStatus(new StatusUpdate(t).media(f));
			} catch (TwitterException e) {
				e.printStackTrace();
				if (e.getStatusCode() == 403) {
					System.out.println("140文字を超えています。");
				} else if (e.getStatusCode() == 400) {
					System.out.println("タグ形式が不正です。");
				}
			}
	}
	/* 複数枚画像アップロードメソッドは未完成。
	 * http://qiita.com/takke/items/2d9a426efcedeee5f02f を参考に。
	public void tweet(String t, File[] fs){
		try {
			for(int i=0;i< fs.length; i++){
				String medias = "media"+i;
				twitter.uploadMedia(fs[i]);
			}
			StatusUpdate status = new StatusUpdate(t);
			status.setMediaIds(new long[fs.length]{});
			twitter.updateStatus(new StatusUpdate(t));
		} catch (TwitterException e) {
			e.printStackTrace();
			if (e.getStatusCode() == 403) {
				System.out.println("140文字を超えています。");
			} else if (e.getStatusCode() == 400) {
				System.out.println("タグ形式が不正です。");
			}
		}
	}
	*/
}