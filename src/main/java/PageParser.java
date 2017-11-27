import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class PageParser {

    private final URI hostUri;
    private final URI uri;
    private Set<URI> links;
    private static final int HTTP_OK = 200;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    PageParser(URI uri, URI hostUri) {
        this.hostUri = hostUri;
        this.uri = uri;
        this.links = new HashSet<>();
    }

    URI getUri() {
        return uri;
    }

    Set<URI> extractLinks() {
        Connection connection = Jsoup.connect(uri.toString());

        Document htmlDocument;

        try {
            htmlDocument = connection.get();
        } catch (Exception e) {
            e.printStackTrace();
            return links;
        }

        Elements linksOnPage = htmlDocument.select("a[href]");
        System.out.println("Visiting " + uri.toString());
        System.out.println("Found (" + linksOnPage.size() + ") links");

        for(Element link : linksOnPage)
        {
            String extractedLink = link.absUrl("href");

            if(isValidLink(extractedLink)) {
                System.out.println("Extracted " + extractedLink.toString());
                links.add(URI.create(extractedLink));
            }
        }

        return links;
    }

    boolean isValidLink(String url) {

        if(url.isEmpty()) {
            return false;
        }

        try {
            URI link = new URI(url);

            if(!getDomainName(link).equals(getDomainName(hostUri))) {
                return false;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    boolean isValidHtmlPage() {
        Connection connection = Jsoup.connect(uri.toString()).userAgent(USER_AGENT);

        try {
            connection.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection.response().statusCode() != HTTP_OK) {
            System.out.println("Failure: Can not establish connection to " + uri.toString());
            return false;
        }

        if (!connection.response().contentType().contains("text/html")) {
            System.out.println("Failure: Retrieved something other than HTML from " + uri.toString());
            return false;
        }

        return true;
    }

    public static String getDomainName(URI uri) throws URISyntaxException {
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
