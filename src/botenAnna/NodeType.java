package botenAnna;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public enum NodeType {
    /* Declare node pictures and their respective colors */
    SELECTOR("Selector.png", Constants.COMPOSITE_COLOR),
    SEQUENCER("Sequencer.png", Constants.COMPOSITE_COLOR),
    INVERTER("Inverter.png", Constants.DECORATOR_COLOR),
    GUARD("Guard.png", Constants.GUARD_COLOR),
    TASK("Task.png", Constants.TASK_COLOR),
    ALWAYS_FAILURE("AlwaysFailure.png", Constants.DECORATOR_COLOR),
    ALWAYS_SUCCESS("AlwaysSuccess.png", Constants.DECORATOR_COLOR),
    IF_THEN_ELSE("IfThenElse.png", Constants.ABSOLUTE_COLOR),
    SUBTREE("Subtree.png", Constants.SUBTREE);

    private Image image;
    private final Color color;

    NodeType(String fileName, Color color){
        try {
            this.image = ImageIO.read(new File("out/production/Boten-Anna-Visualizer/botenAnna/images/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public Color getColor() {
        return color;
    }

    /* Declare colors for node types */
    private static class Constants {
        private static final Color COMPOSITE_COLOR = Color.decode("#B38867");
        private static final Color DECORATOR_COLOR = Color.decode("#FD974F");
        private static final Color ABSOLUTE_COLOR = Color.decode("#128277");
        private static final Color GUARD_COLOR = Color.decode("#c6d166");
        private static final Color TASK_COLOR = Color.decode("#B2473E");
        private static final Color SUBTREE = Color.decode("#ff69b4");
    }
}
