import java.awt.*;

class RedBlackNode {
    RedBlackNode left;
    RedBlackNode right;
    RedBlackNode parent;
    Color color;
    int key;
    RedBlackNode(RedBlackNode P, RedBlackNode L, RedBlackNode R, Color C, int K) {
        parent = P;
        left = L;
        right = R;
        color = C;
        key = K;
    }
}
