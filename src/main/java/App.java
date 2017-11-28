
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        logger.info("Starting Application");

        if (args.length != 1) {
            logger.error("Failure: Exactly 1 URL parameter required");
            throw new IllegalArgumentException("Failure: Exactly 1 URL parameter required");
        }

        Writer writer = null;

        try {
            URI sourceUri = new URI(args[0]);
            Crawler c = new Crawler(sourceUri);
            Map<URI, Set<URI>> res = c.crawl();
            TreeNode<URI> tree = buildTreeNode(sourceUri, res);
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("sitemap.txt"), "utf-8"));
            tree.print(writer);

        } catch (URISyntaxException e) {
            logger.error(e);
            throw new IllegalArgumentException("Failure: Invalid URL provided");

        } catch (IOException e) {
            logger.error(e);
            throw new IOException("Unable to write sitemap to file");
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException ignore) { }
        }

        logger.info("Exiting application");
    }

    public static TreeNode<URI> buildTreeNode(URI sourceUri, Map<URI, Set<URI>> pageLinks) {

        Set<URI> visitedNodes = new HashSet<>();

        Map<URI, TreeNode<URI>> treeNodeMap = new LinkedHashMap<>();

        pageLinks.forEach((key, value) -> {
            Set<TreeNode<URI>> children = new LinkedHashSet<>();
            TreeNode<URI> root = new TreeNode<>(key, children);
            treeNodeMap.put(key, root);
        });

        treeNodeMap.forEach((URI key, TreeNode<URI> value) -> {
            Set<TreeNode<URI>> children = value.getChildren();
            Set<URI> c = pageLinks.get(key);
            c.forEach((URI node) -> {
                if(!(visitedNodes.contains(node) && node.toString().equals(key.toString()))) {
                    children.add(treeNodeMap.get(node));
                    visitedNodes.add(node);
                }
            });
            visitedNodes.add(key);
        });

        return treeNodeMap.get(sourceUri);
    }

}
