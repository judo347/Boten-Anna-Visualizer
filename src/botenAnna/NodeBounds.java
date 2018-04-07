package botenAnna;

/** A NodeBounds defines a rectangular area and allows abstraction of coordinates. Its origin is the top center of
 * the rectangle, so changing width will affect the x-coordinates of right and left side. Changing the height
 * will affect the bottom, but not the top. */
public class NodeBounds {

    private int topX, topY;
    private int width, height;

    /** A NodeBounds defines a rectangular area and allows abstraction of coordinates. Its origin is the top center of
     * the rectangle, so changing width will affect the x-coordinates of right and left side. Changing the height
     * will affect the bottom, but not the top. */
    public NodeBounds(int topX, int topY, int width, int height) {
        this.topX = topX;
        this.topY = topY;
        this.width = width;
        this.height = height;
    }

    public int getTopX() {
        return topX;
    }

    public void setTopX(int topX) {
        this.topX = topX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTopRightX() {
        return topX + width / 2;
    }

    public int getTopLeftX() {
        return topX - width / 2;
    }

    public int getBottomX() {
        return topX;
    }

    public int getBottomY() {
        return topY + height;
    }

    public int getBottomRightX() {
        return getBottomX() + width / 2;
    }

    public int getBottomLeftX() {
        return getTopLeftX() - width / 2;
    }

    public int getCenterX() {
        return topX;
    }

    public int getCenterY() {
        return topY + height / 2;
    }
}
