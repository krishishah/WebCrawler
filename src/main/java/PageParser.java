import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class PageParser {

    private final String sourceUrl;
    private final String url;
    private Set<String> links;
    private static final int HTTP_OK = 200;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    PageParser(String url, String sourceUrl) {
        this.sourceUrl = sourceUrl;
        this.url = url;
        this.links = new HashSet<>();
    }

    String getUrl() {
        return url;
    }

    Set<String> extractLinks() {
        Connection connection = Jsoup.connect(url);

        Document htmlDocument;

        try {
            htmlDocument = connection.get();
        } catch (Exception e) {
            e.printStackTrace();
            return links;
        }

        Elements linksOnPage = htmlDocument.select("a[href]");
        System.out.println("Visiting " + url);
        System.out.println("Found (" + linksOnPage.size() + ") links");

        for(Element link : linksOnPage)
        {
            String l = link.absUrl("href");

            if(l.contains(sourceUrl)) {
                System.out.println("Extracted " + l);
                links.add(l);
            }
        }

        return links;
    }

    boolean isValidHtmlPage() {
        Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);

        try {
            connection.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection.response().statusCode() != HTTP_OK) {
            System.out.println("Failure: Can not establish connection to " + url);
            return false;
        }

        if (!connection.response().contentType().contains("text/html")) {
            System.out.println("Failure: Retrieved something other than HTML from " + url);
            return false;
        }

        return true;
    }
}
