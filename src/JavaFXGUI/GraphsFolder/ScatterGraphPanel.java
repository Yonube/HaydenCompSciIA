package src.JavaFXGUI.GraphsFolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A fixed-size scatter graph panel where:
 * - X-axis = match number (dynamic quantity)
 * - Y-axis = points scored
 * - Points are connected with lines
 */
public class ScatterGraphPanel extends JPanel {

    private final List<Integer> pointsPerMatch;
    private final int padding = 50;
    private final int pointRadius = 5;
    private final Color backgroundColor = Color.WHITE;
    private final Color axisColor = Color.BLACK;
    private final Color lineColor = Color.BLUE;
    private final Color pointColor = Color.RED;

    public ScatterGraphPanel(List<Integer> pointsPerMatch) {
        this.pointsPerMatch = pointsPerMatch;
        setPreferredSize(new Dimension(300, 200)); // constant size
        setBackground(backgroundColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw axes
        g2.setColor(axisColor);
        g2.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g2.drawLine(padding, padding, padding, height - padding); // Y-axis

        if (pointsPerMatch.isEmpty())
            return;

        int maxPoints = pointsPerMatch.stream().max(Integer::compare).orElse(1);
        int matchCount = (int) pointsPerMatch.stream()
                .filter(point -> point != null && point != 0)
                .count();

        // Scale factors
        double xScale = (double) (width - 2 * padding) / (matchCount - 1);
        double yScale = (double) (height - 2 * padding) / maxPoints;

        // Draw points and connecting lines
        g2.setColor(lineColor);

        int prevX = -1, prevY = -1;

        for (int i = 0; i < matchCount; i++) {
            int x = (int) (padding + i * xScale);
            int y = (int) (height - padding - pointsPerMatch.get(i) * yScale);

            // Draw line to previous point
            if (i > 0) {
                g2.drawLine(prevX, prevY, x, y);
            }

            // Draw point
            g2.fillOval(x - pointRadius, y - pointRadius,
                    pointRadius * 2, pointRadius * 2);

            prevX = x;
            prevY = y;
        }

        // Axis labels
        g2.setColor(axisColor);
        g2.drawString("Match Number", width / 2 - 40, height - 10);
        g2.drawString("Points Scored", 5, padding - 10);
    }
}
