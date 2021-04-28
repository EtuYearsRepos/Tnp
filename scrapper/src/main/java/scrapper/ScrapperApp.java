package scrapper;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

import scrapper.readcomiconline.ReadComicOnlineScrapper;

public class ScrapperApp {

    public static WebClient client;
    public static final String TARGET_API_URL = "https://totaly-not-p.oups.net/admin/api/comic";

    final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
    final static String LOGIN_ACTION_URL = "https://totaly-not-p.oups.net/perform_login";
    final static String USERNAME = "admin";
    final static String PASSWORD = "admin";

    public static void main(String[] args) {

        initClient();
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

        ReadComicOnlineScrapper rcoScrapper = new ReadComicOnlineScrapper();

        System.out.println("Welcome to the totaly-not-p scrapper service");

        try {
            Connection.Response loginForm = Jsoup.connect(LOGIN_ACTION_URL).data("username", USERNAME)
                    .data("password", PASSWORD).method(Connection.Method.POST).userAgent(USER_AGENT).execute();

            HashMap<String, String> cookies = new HashMap<>(loginForm.cookies());

            int total = 0;
            for (int n = 0; n < 100; ++n) {
                for (ReadComicOnlineScrapper.ComicLink c : rcoScrapper
                        .getAllComicsFromPage(rcoScrapper.getPopularPageURL(n))) {

                    ReadComicOnlineScrapper.Comic i = rcoScrapper.getAllInfoFromPage(c.URL);
                    i.title = c.title;
                    client.close();

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("title", i.title);
                    map.put("publisher", i.publisher);
                    map.put("writer", i.writer);
                    map.put("artist", i.artist);
                    map.put("publicationDate", i.publicationDate);
                    map.put("status", i.status);
                    map.put("summary", i.summary);
                    map.put("issues", i.issues);

                    JSONObject obj = new JSONObject(map);

                    Connection.Response execute = Jsoup.connect(TARGET_API_URL)
                            .header("Content-Type", "application/json").header("Accept", "application/json")
                            .followRedirects(true).ignoreHttpErrors(true).cookies(cookies).ignoreContentType(true)
                            .userAgent(USER_AGENT).method(Connection.Method.POST).requestBody(obj.toJSONString())
                            .maxBodySize(1_000_000 * 30).timeout(3000).execute();

                    if(execute.statusCode() == 201) {
                        total++;
                        System.out.println("Inserted comic " + total + " : " + i.title);
                    } else {
                        System.out.println("ERROR");
                    }
                    System.gc();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initClient() {
        client = new WebClient(BrowserVersion.FIREFOX_78);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
    }
}
