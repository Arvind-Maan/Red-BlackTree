import java.awt.*;

public class RedBlackTree {
    //external node
    public static final RedBlackNode EXTERNAL_NODE = new RedBlackNode(null, null, null, Color.black, Integer.MAX_VALUE);
    private RedBlackNode root;
    public RedBlackTree(){
        root = EXTERNAL_NODE;
    }

    public void insert(RedBlackNode newNode){
    }

    public boolean isEmpty(){
        return root.key == Integer.MAX_VALUE;
    }


}
