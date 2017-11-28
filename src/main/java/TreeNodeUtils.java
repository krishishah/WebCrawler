import java.net.URI;
import java.util.*;

public class TreeNodeUtils {

    public static TreeNode<URI> buildUriTreeNode(URI sourceUri, Map<URI, Set<URI>> pageLinks) {

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
