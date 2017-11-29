import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/*
Design choice - current implementation only builds site map of pages which map the domain name of the user provided url.

Given more time, instead of directly coupling the PageParser directly to Jsoup, I'd create a WebClient class to abstract away
library specific calls. This would help test this test class by mocking the result call to the WebClient with a local
web page

Another option would be hosting a few valid/invalid web pages locally and sending requests to them in order to
abstract away the non determinism/risk associated with making http requests to remote servers e.g. pages being removed
or altered.
*/
public class PageParserTests {
    @Test
    public void getUriShouldReturnCurrentInstanceUriVariable() throws Exception {
        //Arrange
        URI uri = new URI("https://monzo.com/downloads");
        URI hostUri = new URI("https://www.monzo.com");

        //Act
        PageParser p = new PageParser(uri, hostUri);

        //Assert
        Assert.assertEquals(uri, p.getUri());
    }

    @Test
    public void extractLinksShouldExtractCorrectChildLinks() throws Exception {
        //Arrange
        URI hostUri = new URI("https://www.doc.ic.ac.uk/~kks114/");
        PageParser p = new PageParser(hostUri, hostUri);
        Set<URI> expectedChildren = new HashSet<>();
        expectedChildren.add(new URI("http://www.doc.ic.ac.uk/project/2014/163/g1416332/#facebookmsg"));

        //Act
        Set<URI> children = p.extractLinks();
        System.out.println(children);
        //Assert
        Assert.assertTrue(children.equals(expectedChildren));
    }

    @Test
    public void isCrawlableHtmlPageShould() throws Exception {
        //Arrange
        URI crawlableUri = new URI("https://monzo.com/download");
        URI notCrawlableUri = new URI("https://monzo.com/invest/monzo_investment_deck.pdf");

        //Act
        boolean shouldBeCrawlable = PageParser.isCrawlableHtmlPage(crawlableUri);
        boolean shouldNotBeCrawlable = PageParser.isCrawlableHtmlPage(notCrawlableUri);

        //Assert
        Assert.assertTrue(shouldBeCrawlable);
        Assert.assertFalse(shouldNotBeCrawlable);
    }

}