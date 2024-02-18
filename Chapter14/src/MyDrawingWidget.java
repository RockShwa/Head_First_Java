import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MyDrawingWidget extends JPanel{

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(20, 50, 100, 100);

        // you can also display a JPEG
        Image image = new ImageIcon("catzilla.jpg").getImage();
        g.drawImage(image, 3, 4, this); // x-y coords relative to the widget, not the whole frame

        // Paint a randomly colored circle on a black background
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        Random random = new Random(); // This has a nextInt method that takes a max value and returns a number between 0 (inclusive) and max value (not inclusive)
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        Color randomColor = new Color(red, green, blue);
        g.setColor(randomColor);
        g.fillOval(70, 70, 100, 100); // start at 70 pixels from the left, 70 from the top, and make it 100 wide & tall

        // Graphics2D cool stuff :)
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(70, 70, Color.blue, 150, 150, Color.green);
        // (x1 (starting x), y1 (starting y), starting Color, x2 (ending x), y2 (ending y), ending Color)
        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 100, 100);



    }
    
}
