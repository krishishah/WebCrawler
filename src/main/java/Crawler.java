import java.util.*;

public class Crawler {

    private final String sourceUrl;
    private Map<String, Set<String>> graph;
    private Queue<String> linksToVisit;
    private List<String> visitedLinks;

    Crawler(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        this.graph = new HashMap<>();
        this.visitedLinks = new LinkedList<>();
        this.linksToVisit = new LinkedList<>();
        this.linksToVisit.add(sourceUrl);
    }

    Map<String, Set<String>> crawl() {

        while(!linksToVisit.isEmpty()) {
            String link = linksToVisit.poll();

            if(visitedLinks.contains(link)) {
                continue;
            }

            visitedLinks.add(link);

            PageParser parser = new PageParser(link, sourceUrl);

            if(parser.isValidHtmlPage()) {
                Set<String> extractedLinks = parser.extractLinks();
                graph.put(parser.getUrl(), extractedLinks);

                Set<String> unvisitedLinks = new HashSet<>(extractedLinks);
                unvisitedLinks.remove(visitedLinks);

                linksToVisit.addAll(unvisitedLinks);
            }
        }
        return graph;
    }

}
