import java.awt.*;

public class RedBlackTree {
    //external node
    public static final RedBlackNode EXTERNAL_NODE = new RedBlackNode(null, null, null, "BLACK", Integer.MAX_VALUE);
    private RedBlackNode root;

    public RedBlackTree() {
        root = EXTERNAL_NODE;
    }

    public void insert(RedBlackNode newNode) {
        if (isEmpty()) {
            root = newNode;
        } else {
            //if it is not empty we must perform two steps:
            //Step 1: Ordinary BST insert to put the node in the correct spot.
            bstInsert(newNode);
            //Step 2: fixUp
            fixUp(newNode);
        }
    }

    public void bstInsert(RedBlackNode newNode) {
        RedBlackNode curr = root;
        RedBlackNode prev = EXTERNAL_NODE;
        int key = newNode.key;
        while (curr != EXTERNAL_NODE) {
            prev = curr;
            if (key < curr.key)
                curr = curr.left;
            else
                curr = curr.right;
        }
        newNode.parent = prev;
        if (prev == EXTERNAL_NODE) {
            prev = newNode;
            newNode.parent = null;
        } else if (key < prev.key)
            prev.left = newNode;
        else
            prev.right = newNode;
    }

    public void fixUp(RedBlackNode curr) {
        RedBlackNode currParent = curr.parent;
        while (curr != root && !currParent.color.equals("BLACK")) {
            //is currParent the left child of its parent?
            if (currParent == currParent.parent.left) {
                RedBlackNode currUncle = currParent.parent.right;
                //case 1: currUncle is red
                if (currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currParent.parent.color = "RED";
                    //push curr up to it's grandparent.
                    curr = currParent.parent;
                    currParent = curr.parent;
                } else {
                    //case 2: we are our parent's right child
                    if (curr == currParent.right) {
                        curr = currParent;
                        leftRotate(curr);
                        currParent = curr.parent;
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currParent.parent.color = "RED";
                    rightRotate(currParent.parent);
                } //end of if-else chain
            } else {
                //currParent is the right child
                RedBlackNode currUncle = currParent.parent.left;
                //case 1: currUncle is red
                if (currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currParent.parent.color = "RED";
                    //push curr up to it's grandparent.
                    curr = currParent.parent;
                    currParent = curr.parent;
                } else {
                    //case 2: we are our parent's right child
                    if (curr == currParent.left) {
                        curr = currParent;
                        rightRotate(curr);
                        currParent = curr.parent;
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currParent.parent.color = "RED";
                    leftRotate(currParent.parent);
                } //end of else
            } //end of if-else
        } //end of while
        root.color = "BLACK";
    }//end of fixUp

    public void leftRotate(RedBlackNode curr){
        //set up the variables
        RedBlackNode currParent = curr.parent;
        RedBlackNode currRight = curr.right;
        RedBlackNode currRightLC = currRight.left;
        //transfer the right node of curr into curr's position
        if(currParent.left == curr)
            currParent.left = currRight;
        else
            currParent.right = currRight;
        //fix new curr's children.
        currRight.left = curr;
        curr.right = currRightLC;
        //reassign parent pointers.
        curr.parent = currRight;
        currRight.parent = currParent;
        currRightLC.parent = curr;
    } //end of left rotate

    public void rightRotate(RedBlackNode curr){
        //set up the variables
        RedBlackNode currParent = curr.parent;
        RedBlackNode currLeft = curr.left;
        RedBlackNode currLeftRC = currLeft.right;
        //transfer the right node of curr into curr's position
        if(currParent.left == curr)
            currParent.left = currLeft;
        else
            currParent.right = currLeft;
        //fix new curr's children.
        currLeft.right = curr;
        curr.left = currLeftRC;
        //reassign parent pointers.
        curr.parent = currLeft;
        currLeft.parent = currParent;
        currLeftRC.parent = curr;
    } //end of right rotate

    public boolean isEmpty() {
        return root.key == Integer.MAX_VALUE;
    } //end of isEmpty


}
