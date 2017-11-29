import java.net.URI;
import java.util.*;

public class TreeNodeUtils {

    public static TreeNode<URI> buildUriTreeNode(URI sourceUri, LinkedHashMap<URI, Set<URI>> pageLinks) {

        Set<URI> visitedNodes = new HashSet<>();

        LinkedHashMap<URI, TreeNode<URI>> treeNodeMap = new LinkedHashMap<>();

        pageLinks.forEach((key, value) -> {
            Set<TreeNode<URI>> children = new LinkedHashSet<>();
            TreeNode<URI> root = new TreeNode<>(key, children);
            treeNodeMap.put(key, root);
        });

        treeNodeMap.forEach((URI key, TreeNode<URI> value) -> {
            Set<TreeNode<URI>> children = value.getChildren();
            Set<URI> c = pageLinks.get(key);
            for (URI node : c) {
                if (node.toString().equals(key.toString()) || visitedNodes.contains(node)) {
                    children.add(new TreeNode<>(node, new HashSet<>()));
                } else if (!(visitedNodes.contains(node))) {
                    children.add(treeNodeMap.get(node));
                    visitedNodes.add(node);
                }
            }
            visitedNodes.add(key);
        });

        return treeNodeMap.get(sourceUri);
    }

}
