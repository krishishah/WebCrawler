
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {

        logger.info("Starting Application");

        // Error handling for incorrect arguments
        try {
            URI sourceUri = new URI(args[0]);

            Crawler c = new Crawler(sourceUri);

            Map<URI, Set<URI>> graph = c.crawl();

            logger.debug("RESULT \n" + graph);
        } catch (URISyntaxException e) {
            logger.error("Failure: Invalid URL provided \n " + e);
        }

        logger.info("Exiting application");

    }
}
