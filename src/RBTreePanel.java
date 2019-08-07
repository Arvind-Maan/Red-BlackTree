import javax.swing.*;
import java.awt.*;

public class RBTreePanel extends JPanel {
    private static final long serialVersionUID = 1250L;
    private RedBlackTree tree;
    private int radius = 20;
    private int yOffset = 50;
    private Color textCol = new Color(230, 230, 230);

    public RBTreePanel(RedBlackTree tree) {
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        if (!tree.isEmpty()) {
            Graphics2D gfx2d = (Graphics2D) gfx;
            gfx2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gfx2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            paintTree(gfx2d, tree.getRoot(), getWidth() / 2, 30, getGap());
        }
    }

    private void paintTree(Graphics2D g, RedBlackNode root, int x, int y, int xOffset) {
        //update the view if unbound layout
        if (x < 0)
            setPreferredSize(new Dimension(2 * getWidth(), getHeight()));
        drawNode(g, root, x, y);
        if (root.left != null) {
            join(g, x - xOffset, y + yOffset, x, y);
            paintTree(g, root.left, x - xOffset, y + yOffset,
                    xOffset / 2);
        }
        if (root.right != null) {
            join(g, x + xOffset, y + yOffset, x, y);
            paintTree(g, root.right, x + xOffset, y + yOffset,
                    xOffset / 2);
        }
    }

    private void drawNode(Graphics2D g, RedBlackNode node, int x, int y) {
        g.setColor(node.getColor());
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(textCol);

        String text = Integer.toString(node.key);
        drawCentreText(g, text, x, y);
        g.setColor(Color.GRAY);
    }

    private void drawCentreText(Graphics2D g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        double t_width = fm.getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - t_width / 2),
                (int) (y + fm.getMaxAscent() / 2));
    }

    private void join(Graphics2D g, int x1, int y1, int x2, int y2) {
        double hypot = Math.hypot(yOffset, x2 - x1);
        int x11 = (int) (x1 + radius * (x2 - x1) / hypot);
        int y11 = (int) (y1 - radius * yOffset / hypot);
        int x21 = (int) (x2 - radius * (x2 - x1) / hypot);
        int y21 = (int) (y2 + radius * yOffset / hypot);
        g.drawLine(x11, y11, x21, y21);
    }

    private int getGap() {
        int depth = tree.getDepth();
        int multiplier = 30;
        float exponent = (float) 1.4;
        if (depth > 6) {
            multiplier += depth * 3;
            exponent += .1;
        }
        return (int) Math.pow(depth, exponent) * multiplier;
    }

}
