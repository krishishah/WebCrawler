
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public class App {

    public static void main(String[] args) throws IOException {

        // Error handling for incorrect arguments
        try {
            URI sourceUri = new URI(args[0]);

            Crawler c = new Crawler(sourceUri);

            Map<URI, Set<URI>> graph = c.crawl();

            System.out.println("RESULT \n" + graph);
        } catch (URISyntaxException e) {
            System.out.println("Failure: Invalid URL provided");
        }

    }
}
