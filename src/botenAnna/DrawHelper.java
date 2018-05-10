package botenAnna;

import java.awt.*;

public class DrawHelper {

    /** Draws the text and splits it at upper case letters
     * @param g2d a graphical element.
     * @param text the text to be drawn. */
    public static void drawText(Graphics g2d, int x, int centerY, String text, int maxCharsOnLine){

        //Can the string be on one line
        if(text.length() <= maxCharsOnLine)
            g2d.drawString(text, x, centerY);
        else{

            //Find the last upperCase / start of a word
            int i = maxCharsOnLine;
            while(i >= 0 && !Character.isUpperCase(text.charAt(i))){i--;}

            //Cut the line into two
            String firstLine = text.substring(0, i);
            String secondLine = text.substring(i, text.length());

            g2d.drawString(firstLine, x, centerY - 5);
            g2d.drawString(secondLine, x, centerY + 8);
        }
    }
}
