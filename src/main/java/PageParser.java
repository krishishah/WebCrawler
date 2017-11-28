import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

class PageParser {

    static final Logger logger = LogManager.getLogger(PageParser.class.getName());

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
            return links;
        }

        Elements linksOnPage = htmlDocument.select("a[href]");

        logger.debug("Visiting " + uri.toString());
        logger.debug("Found (" + linksOnPage.size() + ") links");

        for (Element link : linksOnPage) {
            String extractedLink = link.absUrl("href");

            if (isValidLink(extractedLink)) {
                logger.debug("Extracted " + extractedLink.toString());
                links.add(URI.create(extractedLink));
            }
        }

        return links;
    }

    private boolean isValidLink(String url) {

        if (url.isEmpty()) {
            return false;
        }

        if (!url.contains("http")) {
            return false;
        }

        try {
            URI link = new URI(url);

            if (!getDomainName(link).equals(getDomainName(hostUri))) {
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
            logger.warn(e);
            return false;
        }

        if (connection.response().statusCode() != HTTP_OK) {
            logger.warn("Can not establish connection to " + uri.toString());
            return false;
        }

        if (!connection.response().contentType().contains("text/html")) {
            logger.warn("Retrieved something other than HTML from " + uri.toString());
            return false;
        }

        return true;
    }

    private static String getDomainName(URI uri) throws URISyntaxException {
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
