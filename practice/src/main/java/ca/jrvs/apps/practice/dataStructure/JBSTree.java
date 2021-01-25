package ca.jrvs.apps.practice.dataStructure;

import java.util.*;

/**
 * A simplified BST implementation
 *
 * @param <E> type of object to be stored
 */
public class JBSTree<E> implements JTree<E> {

    /**
     * The comparator used to maintain order in this tree map
     * Comparator cannot be null
     */
    private Comparator<E> comparator;
    private Node<E> root;
    private Integer size = 0;


    /**
     * Create a new BST
     *
     * @param comparator the comparator that will be used to order this map.
     * @throws IllegalArgumentException if comparator is null
     */
    public JBSTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Insert an object into the BST.
     * Please review the BST property.
     *
     * @param object item to be inserted
     * @return inserted item
     * @throws IllegalArgumentException if the object already exists
     */
    @Override
    public E insert(E object) {
        if(size == 0){
            this.root = new Node<E>(object,null);
            size+=1;
            return object;
        }

        updateNode( this.root, object,
                (nodeInTree, t) -> {nodeInTree.setLeft(new Node<E>(object, nodeInTree)); return Boolean.TRUE;},
                (nodeInTree, t) -> {throw new IllegalArgumentException("Object exists in Tree"); },
                (nodeInTree,t) -> {nodeInTree.setRight(new Node<E>(object, nodeInTree)); return Boolean.TRUE;});
        size+=1;
        return object;
    }


    /**
     * Search and return an object, return null if not found
     *
     * @param object to be found
     * @return the object if exists or null if not
     */
    @Override
    public E search(E object) {
        if(size == 0){
            return  null;
        }
        return updateNode(this.root, object,
                (nodeInTree, t) -> Boolean.FALSE,
                (nodeInTree, t) -> Boolean.TRUE,
                (nodeInTree,t) -> Boolean.FALSE) ? object: null;
    }

    /**
     * Remove an object from the tree.
     *
     * @param object to be removed
     * @return removed object
     * @throws IllegalArgumentException if the object not exists
     */
    @Override
    public E remove(E object) {
        if(size == 0){
            throw new IllegalArgumentException("Empty Tree");
        }
        updateNode(this.root, object,
                (nodeInTree, t) -> {throw new IllegalArgumentException("Object exists in Tree");},
                (nodeInTree, t) -> {
                    Node<E> parent = nodeInTree.getParent();
                    // modify nodeInTree so that nodeInTree only has left node,
                    // then nodeInTree.parent.left/right = nodeInTree.left
                    if(nodeInTree.getLeft()!=null){
                        // in the subtree with root=nodeInTree, find the most right node
                        Node<E> mostRightNode = nodeInTree.getLeft().findMostRightNodeInSubTree();
                        mostRightNode.setRight(nodeInTree.getRight());
                        nodeInTree.getRight().setParent(mostRightNode);
                    }else{
                        nodeInTree.setLeft(nodeInTree.getRight());
                    }

                    if(parent == null){
                        this.root = nodeInTree.getLeft();
                        this.root.setParent(null);
                        return Boolean.TRUE;
                    }

                    if(comparator.compare(object, parent.getValue()) < 0){
                        // if parent.left = nodeInTree
                        parent.setLeft(nodeInTree.getLeft());
                        nodeInTree.getLeft().setParent(parent);
                    } else{
                        parent.setRight(nodeInTree.getLeft());
                        nodeInTree.getLeft().setParent(parent);
                    }

                    return Boolean.TRUE;
                },
                (nodeInTree,t) -> {throw new IllegalArgumentException("Object exists in Tree"); });
        size-=1;
        return object;
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects in pre-order
     */
    @Override
    public List<E> preOrder() {
        List<E> arr = new ArrayList<E>(size);
        Stack<Node<E>> stack = new Stack<>();
        stack.push(this.root);
        while(!stack.isEmpty()){
            Node<E> current = stack.pop();
            arr.add(current.getValue());
            if(current.getRight()!=null){
                stack.push(current.getRight());
            }
            if(current.getLeft()!=null){
                stack.push(current.getLeft());
            }
        }
        return arr;
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects in-order
     */
    @Override
    public List<E> inOrder() {
        List<E> arr = new ArrayList<E>(size);
        Stack<Node<E>> stack = new Stack<>();
        Node<E> current = this.root;
        while(current != null){
            stack.push(current);
            current = current.getLeft();
        }

        while(!stack.isEmpty()){
            current = stack.pop();
            arr.add(current.getValue());
            current = current.getRight();
            while(current != null){
                stack.push(current);
                current = current.getLeft();
            }
        }
        return arr;
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects pre-order
     */
    @Override
    public List<E> postOrder() {
        List<E> arr = new ArrayList<E>(size);
        Set<E> set = new HashSet<>();
        Stack<Node<E>> stack = new Stack<>();
        stack.add(this.root);

        while(!stack.isEmpty()){
            Node<E> current = stack.pop();
            Node<E> left = current.getLeft();
            Node<E> right= current.getRight();

            if((left==null || set.contains(left.getValue())) &&
                right==null || set.contains(right.getValue())){
                // visited left and right OR left right are null
                arr.add(current.getValue());
                set.add(current.getValue());
            } else {
                stack.add(current);
                if(current.getRight()!=null){
                    stack.add(current.getRight());
                }
                if(current.getLeft()!=null) {
                    stack.add(current.getLeft());
                }
            }
        }
        return arr;
    }

    /**
     * traverse through the tree and find out the tree height
     * @return height
     * @throws NullPointerException if the BST is empty
     */
    @Override
    public int findHeight() {

       return findHeight(this.root);
    }

    private int findHeight(Node<E> root){
        if(size == 0){
            throw new NullPointerException();
        }
        if(root ==null){
            return 0;
        }
        return Math.max(findHeight(root.getLeft()), findHeight(root.getRight()))+1;
    }


    static final class Node<E> {

        E value;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }

        public Node<E> findMostRightNodeInSubTree(){
            Node<E> current = this;
            while(current.right != null){
                current = current.right;
            }
            return current;
        }

        public Node<E> findMostLeftNodeInSubTree(){
            Node<E> current = this;
            while(current.left != null){
                current = current.left;
            }
            return current;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return getValue().equals(node.getValue()) &&
                    Objects.equals(getLeft(), node.getLeft()) &&
                    Objects.equals(getRight(), node.getRight()) &&
                    getParent().equals(node.getParent());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue(), getLeft(), getRight(), getParent());
        }
    }

    /**
     * define a function about how to update Node in a Tree
     * @param <E> given object type E
     */
    interface NodeUpdateFunction<E>{
        /**
         * @param node: Node in Tree,
         * @param target: target object information
         */
        Boolean update(Node<E> node, E target );
    }
    interface NodeLeftUpdateFunction<E> extends NodeUpdateFunction<E>{
    }
    interface NodeRightUpdateFunction<E> extends NodeUpdateFunction<E>{
    }


    private Boolean updateNode(Node<E> current, E target,
                               NodeLeftUpdateFunction<E> updateLeftFunction,
                               NodeUpdateFunction<E> updateFunction,
                               NodeRightUpdateFunction<E> updateRightFunction) {

        Integer compareResult = comparator.compare(target, current.getValue());

        if(compareResult < 0){
            // node < current
            if(current.getLeft() == null){
                return updateLeftFunction.update(current, target);
            } else{
                return updateNode(current.getLeft(), target, updateLeftFunction, updateFunction, updateRightFunction);
            }

        }else if(compareResult == 0){
            return updateFunction.update(current, target);
        } else {
            if(current.getRight() == null){
                return updateRightFunction.update(current, target);
            } else{
                return updateNode(current.getRight(), target, updateLeftFunction, updateFunction, updateRightFunction);
            }
        }
    }

}
