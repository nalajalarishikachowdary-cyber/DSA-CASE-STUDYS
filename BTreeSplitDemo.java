import java.util.*;

class BTreeNode {

    int t;
    int n;
    boolean leaf;

    String[] keys;
    BTreeNode[] children;

    // Constructor
    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;

        keys = new String[2 * t - 1];
        children = new BTreeNode[2 * t];

        n = 0;
    }
}

public class BTreeSplitDemo {

    private BTreeNode root;
    private int t;

    public BTreeSplitDemo(int t) {
        this.t = t;
        root = new BTreeNode(t, true);
    }

    private void splitChild(BTreeNode parent,
                             int index,
                             BTreeNode fullChild) {

        BTreeNode newNode =
                new BTreeNode(t, fullChild.leaf);

        newNode.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            newNode.keys[j] = fullChild.keys[j + t];
        }

        if (!fullChild.leaf) {
            for (int j = 0; j < t; j++) {
                newNode.children[j] =
                        fullChild.children[j + t];
            }
        }

        fullChild.n = t - 1;

        for (int j = parent.n; j >= index + 1; j--) {
            parent.children[j + 1] =
                    parent.children[j];
        }

        parent.children[index + 1] = newNode;

        for (int j = parent.n - 1; j >= index; j--) {
            parent.keys[j + 1] =
                    parent.keys[j];
        }

        parent.keys[index] =
                fullChild.keys[t - 1];

        parent.n++;

        System.out.println(
                "Page Split Occurred. Promoted Key = "
                        + parent.keys[index]);
    }

    private void insertNonFull(BTreeNode node,
                               String key) {

        int i = node.n - 1;

        if (node.leaf) {

            while (i >= 0 &&
                    node.keys[i] != null &&
                    key.compareTo(node.keys[i]) < 0) {

                node.keys[i + 1] = node.keys[i];
                i--;
            }

            node.keys[i + 1] = key;
            node.n++;
        }
        else {

            while (i >= 0 &&
                    key.compareTo(node.keys[i]) < 0) {
                i--;
            }

            i++;

            if (node.children[i].n ==
                    (2 * t - 1)) {

                splitChild(node,
                           i,
                           node.children[i]);

                if (key.compareTo(node.keys[i]) > 0) {
                    i++;
                }
            }

            insertNonFull(node.children[i], key);
        }
    }

    public void insert(String key) {

        BTreeNode r = root;

        if (r.n == (2 * t - 1)) {

            BTreeNode s =
                    new BTreeNode(t, false);

            root = s;

            s.children[0] = r;

            splitChild(s, 0, r);

            insertNonFull(s, key);

            System.out.println(
                    "Root Split Occurred");
        }
        else {
            insertNonFull(r, key);
        }
    }

    public static void main(String[] args) {

        BTreeSplitDemo tree =
                new BTreeSplitDemo(3);

        tree.insert("10");
        tree.insert("20");
        tree.insert("30");
        tree.insert("40");
        tree.insert("50");
        tree.insert("60");
        tree.insert("70");

        System.out.println(
                "Insertion completed successfully.");
    }
}