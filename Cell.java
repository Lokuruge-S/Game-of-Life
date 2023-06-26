import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class Cell extends JComponent{
    private int width;
    private int height;
    private int x;
    private int y;

    public Cell(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D obj = (Graphics2D) g;
        Color cellColour = new Color(6,145, 27);
        obj.setColor(cellColour);
        obj.drawRect(this.x, this.y, this.width, this.height);
    }
}
