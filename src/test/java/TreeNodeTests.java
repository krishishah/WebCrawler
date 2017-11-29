import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class TreeNodeTests {

    @Test
    public void getParentShouldReturnParentNodeFedIntoConstructor() throws Exception {
        //Arrange
        URI expectedParent = new URI("http://example.com");
        TreeNode<URI> root = new TreeNode<>(new URI("http://example.com"), null);

        //Act
        URI parent = root.getParent();

        //Assert
        Assert.assertEquals(parent, expectedParent);
    }

    @Test
    public void getChildrenShouldGetChildrenFedIntoConstructor() throws Exception {
        //Arrange
        Set<TreeNode<URI>> treeNodeSet = new HashSet<>();
        TreeNode<URI> child1 = new TreeNode<>(new URI("http://example1.com"), new HashSet<>());
        TreeNode<URI> child2 = new TreeNode<>(new URI("http://example2.com"), new HashSet<>());

        treeNodeSet.add(child1);
        treeNodeSet.add(child2);

        TreeNode<URI> root = new TreeNode<>(new URI("http://example.com"), treeNodeSet);

        //Act
        Set<TreeNode<URI>> children = root.getChildren();

        //Assert
        Assert.assertEquals(treeNodeSet, children);
    }

    @Test
    public void printShouldPrintCorrectNodeTree() throws Exception {
        //Arrange
        String possibleExpectedOutput = null;
        String possibleExpectedOutput2 = null;

        try(BufferedReader br = new BufferedReader(new FileReader("src/test/resources/SampleNodeTreeOutput.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            possibleExpectedOutput = sb.toString();
        }

        try(BufferedReader br = new BufferedReader(new FileReader("src/test/resources/SampleNodeTreeOutput2.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            possibleExpectedOutput2 = sb.toString();
        }

        Set<TreeNode<URI>> childTreeNodeSet = new HashSet<>();
        TreeNode<URI> child1 = new TreeNode<>(new URI("http://example1.com"), new HashSet<>());
        TreeNode<URI> child2 = new TreeNode<>(new URI("http://example2.com"), new HashSet<>());

        childTreeNodeSet.add(child1);
        childTreeNodeSet.add(child2);

        TreeNode<URI> root = new TreeNode<>(new URI("http://example.com"), childTreeNodeSet);

        child1.getChildren().add(new TreeNode<>(new URI("http://childOfExample1.com"), new HashSet<>()));

        child2.getChildren().add(new TreeNode<>(new URI("http://childOfExample2.com"), new HashSet<>()));

        Writer writer = new StringWriter();

        //Act
        root.print(writer);

        //Assert
        Assert.assertTrue(writer.toString().equals(possibleExpectedOutput) || writer.toString().equals(possibleExpectedOutput2) );
    }

}