package botenAnna;

import java.util.ArrayList;

public class GraphicalNode extends Node{

    final private int width = 150;
    final private int height = 60;
    private String name;
    private int x = 0;
    private int y = 0;
    final private int verticalSpace = 10;
    final private int horizontalSpace = 30;

    public GraphicalNode(String name) {
        super();
        this.name = name;
    }

    public boolean isCoordinatesSet(){
        return x != 0 && y != 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGraphicalWidth() {
        return width;
    }

    public int getGraphicalHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }
}
