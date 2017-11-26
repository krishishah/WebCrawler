
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws IOException {

        String sourceUrl = args[0];

        Crawler c = new Crawler(sourceUrl);

        Map<String, List<String>> graph = c.crawl();
    }
}
