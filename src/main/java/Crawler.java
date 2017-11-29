import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.net.URI;

class Crawler {

    static final Logger logger = LogManager.getLogger(Crawler.class.getName());

    private final URI sourceUri;
    private Map<URI, Set<URI>> graph;
    private Queue<URI> linksToVisit;
    private List<URI> visitedLinks;

    Crawler(URI sourceUri) {
        this.sourceUri = sourceUri;
        this.graph = new LinkedHashMap<>();
        this.visitedLinks = new LinkedList<>();
        this.linksToVisit = new LinkedList<>();
        this.linksToVisit.add(sourceUri);
    }

    Map<URI, Set<URI>> crawl() {
        while(!linksToVisit.isEmpty()) {
            URI link = linksToVisit.poll();

            if(visitedLinks.contains(link)) {
                continue;
            }

            visitedLinks.add(link);

            if(PageParser.isCrawlableHtmlPage(link)) {
                PageParser parser = new PageParser(link, sourceUri);

                Set<URI> extractedLinks = parser.extractLinks();
                graph.put(parser.getUri(), extractedLinks);

                Set<URI> unvisitedLinks = new HashSet<>(extractedLinks);
                unvisitedLinks.remove(visitedLinks);

                linksToVisit.addAll(unvisitedLinks);
            }
        }
        return graph;
    }

}
