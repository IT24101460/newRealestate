package RealEstateProperty;

public class BSTNode {
    private Property property;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(Property property) {
        this.property = property;
        this.left = null;
        this.right = null;
    }

    // Getters and Setters
    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public BSTNode getLeft() { return left; }
    public void setLeft(BSTNode left) { this.left = left; }

    public BSTNode getRight() { return right; }
    public void setRight(BSTNode right) { this.right = right; }
}