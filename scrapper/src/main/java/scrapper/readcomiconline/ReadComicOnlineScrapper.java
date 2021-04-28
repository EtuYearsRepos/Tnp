package scrapper.readcomiconline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;

import scrapper.ScrapperApp;

public class ReadComicOnlineScrapper {

    public class Comic {
        public String title;
        public String publisher;
        public String writer;
        public String artist;
        public String publicationDate;
        public String status;
        public String summary;
        public List<String> issues;
    }

    public class ComicLink {
        public String URL;
        public String title;
    }

    public static final String BASE_URL = "https://readcomiconline.to";

    public ReadComicOnlineScrapper() {

    }

    public ArrayList<ComicLink> getAllComicsFromPage(String pageURL) throws IOException, MalformedURLException {

        ArrayList<ComicLink> comics = new ArrayList<ComicLink>();
        HtmlPage page = ScrapperApp.client.getPage(pageURL);

        HtmlTable titles_list = (HtmlTable) page.getByXPath("//table[@class='listing']").get(0);
        List<HtmlTableRow> rows = titles_list.getRows();

        for (int r = 2; r < rows.size(); ++r) {
            HtmlTableRow row = rows.get(r);
            HtmlTableCell cell = row.getCell(0);
            String link = ((HtmlAnchor) cell.getByXPath("a").get(0)).getAttribute("href");

            ComicLink comic = new ComicLink();
            comic.URL = BASE_URL + link;
            comic.title = ((HtmlAnchor) cell.getByXPath("a").get(0)).getTextContent().trim();

            comics.add(comic);
        }
        ScrapperApp.client.close();

        return comics;
    }

    public Comic getAllInfoFromPage(String pageURL) throws IOException, MalformedURLException {

        ArrayList<String> issues = new ArrayList<String>();
        Comic comic = new Comic();

        HtmlPage page = ScrapperApp.client.getPage(pageURL);

        DomNodeList<DomNode> infos = ((HtmlDivision) page.getFirstByXPath("//div[@class='barContent']")).getChildNodes().get(1).getChildNodes();
                
        comic.publisher = infos.get(5).getChildNodes().get(3).getTextContent().trim();
        comic.writer = infos.get(7).getChildNodes().get(3).getTextContent().trim();
        comic.artist = infos.get(9).getChildNodes().get(3).getTextContent().trim();
        comic.publicationDate = infos.get(11).getChildNodes().get(2).getTextContent().trim().replaceAll("[\\n\\t ]", "");;
        comic.status = infos.get(13).getChildNodes().get(2).getTextContent().trim().replaceAll("[\\n\\t ]", "");;
        comic.summary = infos.get(16).getTextContent().trim().replaceAll("[\\n\\t]", "");;
        if (comic.summary.length() >= 255) {
            comic.summary = comic.summary.substring(0, 254);
        }

        HtmlTable titles_list = (HtmlTable) page.getByXPath("//table[@class='listing']").get(0);
        List<HtmlTableRow> rows = titles_list.getRows();
        for (int r = 2; r < rows.size(); ++r) {
            HtmlTableRow row = rows.get(r);
            HtmlTableCell cell = row.getCell(0);
            issues.add(((HtmlAnchor) cell.getByXPath("a").get(0)).getTextContent().trim());
        }
        comic.issues = issues;

        ScrapperApp.client.close();
        return comic;
    }

    public String getPopularPageURL(int pageNumber) {
        return BASE_URL + "/ComicList/MostPopular?page=" + pageNumber;
    }
}
