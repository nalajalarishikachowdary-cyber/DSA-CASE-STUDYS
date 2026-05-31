class Node {
    long timestamp;
    Node left, right;

    Node(long timestamp) {
        this.timestamp = timestamp;
    }
}

public class ReceiptBST {

    Node root;

    // Insert timestamp
    Node insert(Node node, long timestamp) {

        if (node == null)
            return new Node(timestamp);

        if (timestamp < node.timestamp)
            node.left = insert(node.left, timestamp);
        else
            node.right = insert(node.right, timestamp);

        return node;
    }

    // Find oldest pending receipt (minimum timestamp)
    Node findOldest(Node node) {

        if (node == null)
            return null;

        int hops = 0;

        while (node.left != null) {
            node = node.left;
            hops++;
        }

        System.out.println("Pointer hops = " + hops);
        return node;
    }

    // Height of tree
    int height(Node node) {

        if (node == null)
            return 0;

        return 1 + Math.max(
                height(node.left),
                height(node.right));
    }

    public static void main(String[] args) {

        ReceiptBST bst = new ReceiptBST();

        // Monotonically increasing timestamps
        for (int i = 1; i <= 20; i++) {
            bst.root = bst.insert(bst.root, i);
        }

        System.out.println(
                "Tree Height = " +
                bst.height(bst.root));

        Node oldest = bst.findOldest(bst.root);

        System.out.println(
                "Oldest Receipt Timestamp = "
                + oldest.timestamp);
    }
}