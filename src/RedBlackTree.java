public class RedBlackTree {
    //external node
    private RedBlackNode root;

    public RedBlackTree() {
        root = null;
    }

    /****************************************************
     * insert
     *
     * insert a red node
     ***************************************************/
    public void insert(Integer data) {
        insert(new RedBlackNode(null, null, null, "RED", data.intValue()));
    }

    /****************************************************
     * insert
     *
     * a helper method that inserts the given node
     ***************************************************/
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
        root.color = "BLACK";
    } //end of insert

    /****************************************************
     * bstInsert
     *
     * A regular binary search tree insert.
     * Finds the correct placement off the nodes data and inserts.
     ***************************************************/
    public void bstInsert(RedBlackNode newNode) {
        RedBlackNode curr = root;
        RedBlackNode prev = null;
        int key = newNode.key; //the node value
        //find the correct position of the node
        while (curr != null) {
            prev = curr;
            if (key < curr.key)
                curr = curr.left;
            else
                curr = curr.right;
        } //end of while
        //insert the node in the correct spot
        newNode.parent = prev;
        if (prev == null) {
            prev = newNode;
            newNode.parent = null;
        } else if (key < prev.key)
            prev.left = newNode;
        else
            prev.right = newNode;
    } //end of bstInsert

    /****************************************************
     * fixUp
     *
     * Corrects the insertion to work for the red black tree.
     ***************************************************/
    public void fixUp(RedBlackNode curr) {
        RedBlackNode currParent = curr.parent;
        while (curr != root && !currParent.color.equals("BLACK")) {
            //is currParent the left child of its parent?
            if (currParent == currParent.parent.left) {
                RedBlackNode currUncle = currParent.parent.right;
                //case 1: currUncle is red
                if (currUncle != null && currUncle.color.equals("RED")) {
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
                if (currUncle != null && currUncle.color.equals("RED")) {
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

    /****************************************************
     * leftRotate
     *
     * perform a left rotation on the tree
     ***************************************************/
    public void leftRotate(RedBlackNode curr) {
        //set up the variables
        RedBlackNode currParent = curr.parent;
        RedBlackNode currRight = curr.right;
        RedBlackNode currRightLC = currRight.left;
        RedBlackNode temp = curr;
        //transfer the right node of curr into curr's position
        if (currParent != null && currParent.left != null && currParent.left == curr) {
            currParent.left = currRight;
        } else if (currParent != null && currParent.right != null && currParent.right == curr) {
            currParent.right = currRight;
        } //end of if
        curr = currRight;
        //fix new curr's children.
        curr.left = temp;
        temp.right = currRightLC;
        //reassign parent pointers.
        if (temp.parent == null)
            root = curr;
        temp.parent = curr;
        if (curr != null)
            curr.parent = currParent;
        if (currRightLC != null)
            currRightLC.parent = temp;
    } //end of left rotate

    /****************************************************
     * rightRotate
     *
     * perform a right rotation on the tree
     ***************************************************/
    public void rightRotate(RedBlackNode curr) {
        //set up the variables
        RedBlackNode currParent = curr.parent;
        RedBlackNode currLeft = curr.left;
        RedBlackNode currLeftRC = currLeft.right;
        RedBlackNode temp = curr;
        //transfer the right node of curr into curr's position
        if (currParent != null && currParent.left != null && currParent.left == curr) {
            currParent.left = currLeft;
        } else if (currParent != null && currParent.right != null && currParent.right == curr) {
            currParent.right = currLeft;
        }
        curr = currLeft;
        //fix new curr's children.
        curr.right = temp;
        temp.left = currLeftRC;
        //reassign parent pointers.
        if (temp.parent == null)
            root = curr;
        temp.parent = currLeft;
        if (curr != null)
            curr.parent = currParent;
        if (currLeftRC != null)
            currLeftRC.parent = curr;
    } //end of right rotate

    public boolean isEmpty() {
        return root == null;
    } //end of isEmpty

    public RedBlackNode search(int key) {
        RedBlackNode curr = root;
        RedBlackNode toRet = null;
        while (curr != null && curr.key != key) {
            if (curr.key < key) {
                curr = curr.right;
            } else {
                curr = curr.left;
            } //end of if-else
        } //end of while
        if (curr != null && curr.key == key)
            toRet = curr;
        return toRet;
    } //end of search

    public RedBlackNode getRoot() {
        return root;
    }//end of getRoot

    public void resetTree() {
        root = null;
    } //end of resetTree

    public int getDepth() {
        return this.getDepth(this.root);
    } //end of getDepth

    private int getDepth(RedBlackNode node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.left);
            return left_depth > (right_depth = this.getDepth(node.right)) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    } //end of getDepth

}
