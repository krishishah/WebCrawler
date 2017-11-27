import java.util.*;
import java.net.URI;

public class Crawler {

    private final URI sourceUri;
    private Map<URI, Set<URI>> graph;
    private Queue<URI> linksToVisit;
    private List<URI> visitedLinks;

    Crawler(URI sourceUri) {
        this.sourceUri = sourceUri;
        this.graph = new HashMap<>();
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

            PageParser parser = new PageParser(link, sourceUri);

            if(parser.isValidHtmlPage()) {
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
