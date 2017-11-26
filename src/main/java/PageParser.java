import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class PageParser {

    private final String url;

    PageParser(String url) {
        this.url = url;
    }

    String getUrl() {
        return url;
    }

    List<String> extractLinks() {
        Connection connection = Jsoup.connect("http://www.google.com");
        Document htmlDocument = null;
        try {
            htmlDocument = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements linksOnPage = htmlDocument.select("a[href]");

        return null;
    }
}
