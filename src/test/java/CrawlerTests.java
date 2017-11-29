import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CrawlerTests {

    @Test
    public void crawlShouldReturnAppropriateUriMap() throws Exception {
        //Arrange
        URI sourceUri = new URI("https://www.hashhack.it");
        Map<URI, Set<URI>> expectedOutput = new HashMap<>();

        Set<URI> sourceSet = new HashSet<>();
        sourceSet.add(new URI("https://www.hashhack.it/categories/blockchain"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/source-r-ethereum"));
        sourceSet.add(new URI("https://www.hashhack.it/categories/scalability"));
        sourceSet.add(new URI("https://www.hashhack.it/categories/ethereum"));
        sourceSet.add(new URI("https://www.hashhack.it/about"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/the-synergies-gained-from-building-on-ethereums-decentralized-app-ecosystem"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/blockchains-dont-scale-not-today-at-least-but-theres-hope"));
        sourceSet.add(new URI("https://www.hashhack.it/all-posts"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/coffee-variety-macchiato-as-organic-ut-variety-caffeine-americano"));
        sourceSet.add(new URI("https://www.hashhack.it/contact"));
        sourceSet.add(new URI("https://www.hashhack.it/"));

        URI aboutUri = new URI("https://www.hashhack.it/about");
        URI contactUri = new URI("https://www.hashhack.it/contact");
        Set<URI> aboutAndContactSet = new HashSet<>();
        aboutAndContactSet.add(new URI("https://www.hashhack.it/"));
        aboutAndContactSet.add(new URI("https://www.hashhack.it/contact"));
        aboutAndContactSet.add(new URI("https://www.hashhack.it/about"));

        Crawler c = new Crawler(sourceUri);

        //Act
        Map<URI, Set<URI>> output = c.crawl();

        //Assert
        Assert.assertEquals(output.get(sourceUri), sourceSet);
        Assert.assertEquals(output.get(aboutUri), aboutAndContactSet);
        Assert.assertEquals(output.get(contactUri), aboutAndContactSet);

        sourceSet.add(new URI("https://www.hashhack.it"));

        Assert.assertEquals(output.keySet(), sourceSet);
    }

}