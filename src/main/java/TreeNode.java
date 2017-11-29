
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

class TreeNode<T> {

    private T parent;
    Set<TreeNode<T>> children;

    TreeNode(T parent, Set<TreeNode<T>> children) {
        this.parent = parent;
        this.children = children;
    }

    public T getParent() {
        return parent;
    }

    Set<TreeNode<T>> getChildren() {
        return children;
    }

    void print(Writer writer) throws IOException {
        print("", true, writer);
    }

    private void print(String prefix, boolean isTail, Writer writer) throws IOException {
        writer.write(prefix + (isTail ? "└── " : "├── ") + parent.toString() + "\n");

        Iterator<TreeNode<T>> nodeIterator = children.iterator();

        TreeNode<T> tailNode = null;

        if (nodeIterator.hasNext()) {
            TreeNode<T> node = nodeIterator.next();
            tailNode = node;
        }

        while(nodeIterator.hasNext()) {
            TreeNode<T> node = nodeIterator.next();
            if(node == null) {
                continue;
            }
            node.print(prefix + (isTail ? "    " : "│   "), false, writer);
        }

        if (tailNode != null) {
            tailNode.print(prefix + (isTail ?"    " : "│   "), true, writer);
        }
    }
}