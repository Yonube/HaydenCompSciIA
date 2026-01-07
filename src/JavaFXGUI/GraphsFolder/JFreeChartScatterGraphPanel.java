import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartScatterGraphPanel extends JPanel {

    public JFreeChartScatterGraphPanel(List<Integer> data) {

        XYSeries series = new XYSeries("Data Points");
        for (int i = 0; i < data.size(); i++) {
            series.add(i, data.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Graph",
                "X-Axis",
                "Y-Axis",
                dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
}

public class JFreeChartScatterGraphPanel extends JPanel {
    public JFreeChartScatterGraphPanel(List<Integer> data) {
        // Create a dataset
        XYSeries series = new XYSeries("Data Points");
        for (int i = 0; i < data.size(); i++) {
            series.add(i, data.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create the scatter plot
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Scatter Graph", // Title
                "X-Axis", // X-Axis Label
                "Y-Axis", // Y-Axis Label
                dataset // Dataset
        );

        // Create and set up the panel
        ChartPanel chartPanel = new ChartPanel(scatterPlot);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        // Add the chart panel to this panel
        this.setLayout(new java.awt.BorderLayout());
        this.add(chartPanel, java.awt.BorderLayout.CENTER);
    }
}
