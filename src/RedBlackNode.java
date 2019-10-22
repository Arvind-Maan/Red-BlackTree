import java.awt.*;

class RedBlackNode {
    RedBlackNode left;
    RedBlackNode right;
    RedBlackNode parent;
    String color;
    int key;

    RedBlackNode(RedBlackNode P, RedBlackNode L, RedBlackNode R, String C, int K) {
        parent = P;
        left = L;
        right = R;
        color = C;
        key = K;
    } //end of RedBlackNode

    /****************************************************
     * getColor
     *
     * return the actual color value of the node.
     ***************************************************/
    Color getColor() {
        Color toRet = new Color(70, 70, 70);
        if (color.equals("RED"))
            toRet = new Color(250, 70, 70);
        return toRet;
    }
}
