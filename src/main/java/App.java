
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class App {

    public static void main(String[] args) throws IOException {

        String sourceUrl = args[0];

        Crawler c = new Crawler(sourceUrl);

        Map<String, Set<String>> graph = c.crawl();

        System.out.println("RESULT \n" + graph);
    }
}
