import org.junit.Assert;
import org.junit.Test;
import java.net.URI;
import java.util.*;

public class TreeNodeUtilsTests {

    @Test
    public void buildUriTreeNodeGeneratesAppropriateTreeNode() throws Exception {
        //Arrange
        URI sourceUri = new URI("https://www.hashhack.it");
        LinkedHashMap<URI, Set<URI>> crawlerOutput = new LinkedHashMap<>();

        Set<URI> sourceSet = new HashSet<>();
        sourceSet.add(new URI("https://www.hashhack.it/categories/blockchain"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/source-r-ethereum"));
        sourceSet.add(new URI("https://www.hashhack.it/categories/scalability"));
        sourceSet.add(new URI("https://www.hashhack.it/categories/ethereum"));
        URI aboutUri = new URI("https://www.hashhack.it/about");
        sourceSet.add(aboutUri);
        sourceSet.add(new URI("https://www.hashhack.it/posts/blahblah"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/blockchains-stuff"));
        sourceSet.add(new URI("https://www.hashhack.it/all-posts"));
        sourceSet.add(new URI("https://www.hashhack.it/posts/coffee-variety-stuff"));
        URI contactUri = new URI("https://www.hashhack.it/contact");
        sourceSet.add(contactUri);
        URI homeUri = new URI("https://www.hashhack.it/");
        sourceSet.add(homeUri);

        crawlerOutput.put(sourceUri, sourceSet);

        sourceSet.forEach(x -> crawlerOutput.put(x, new HashSet<>()));

        Set<URI> aboutAndContactSet = new HashSet<>();

        aboutAndContactSet.add(homeUri);
        aboutAndContactSet.add(contactUri);
        aboutAndContactSet.add(aboutUri);

        crawlerOutput.put(aboutUri, aboutAndContactSet);
        crawlerOutput.put(contactUri, aboutAndContactSet);

        //Act
        TreeNode<URI> treeNode = TreeNodeUtils.buildUriTreeNode(sourceUri, crawlerOutput);

        //Assert
        Assert.assertTrue(treeNode.getChildren().removeIf(x -> x.getParent().equals(homeUri)));
        Assert.assertTrue(treeNode.getChildren()
                                  .removeIf(
                                          x -> x.getParent().equals(contactUri)
                                            && x.getChildren().removeIf(y->y.getParent().equals(contactUri))
                                            && x.getChildren().removeIf(z->z.getParent().equals(homeUri))
                                            && x.getChildren().removeIf(w->w.getParent().equals(aboutUri))));

        Assert.assertTrue(treeNode.getChildren()
                                  .removeIf(
                                          x -> x.getParent().equals(aboutUri)
                                            && x.getChildren().removeIf(y->y.getParent().equals(contactUri))
                                            && x.getChildren().removeIf(z->z.getParent().equals(homeUri))
                                            && x.getChildren().removeIf(w->w.getParent().equals(aboutUri))));

    }

}