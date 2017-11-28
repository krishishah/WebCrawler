
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeNode<T> {

    private T parent;
    Set<TreeNode<T>> children;

    public TreeNode(T parent, Set<TreeNode<T>> children) {
        this.parent = parent;
        this.children = children;
    }

    public T getParent() {
        return parent;
    }

    public Set<TreeNode<T>> getChildren() {
        return children;
    }

    public void print(Writer writer) throws IOException {
        print("", true, new HashSet<>(), writer);
    }

    private void print(String prefix, boolean isTail, Set<TreeNode<T>> visited, Writer writer) throws IOException {
        writer.write(prefix + (isTail ? "└── " : "├── ") + parent.toString() + "\n");

        Iterator<TreeNode<T>> nodeIterator = children.iterator();

        TreeNode<T> tailNode = null;

        while (nodeIterator.hasNext()) {
            TreeNode<T> node = nodeIterator.next();
            if(!visited.contains(node)) {
                tailNode = node;
                break;
            }
        }

        while(nodeIterator.hasNext()) {
            TreeNode<T> node = nodeIterator.next();
            if(node == null) {
                continue;
            }
            if(!visited.contains(node)) {
                visited.add(node);
                node.print(prefix + (isTail ? "    " : "│   "), false, visited, writer);
            }
        }

        if (tailNode != null) {
            visited.add(tailNode);
            tailNode.print(prefix + (isTail ?"    " : "│   "), true, visited, writer);
        }
    }


}