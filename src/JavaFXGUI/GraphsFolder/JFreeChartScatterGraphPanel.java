package src.JavaFXGUI.GraphsFolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import src.OOPBackEnd.*;

public class JFreeChartScatterGraphPanel extends JPanel {

    public JFreeChartScatterGraphPanel(List<RobotTeam> data) {

        XYSeries series = new XYSeries("Data Points");
        for (int i = 0; i < data.size(); i++) {
            RobotTeam team = data.get(i);
            series.add(i, team.getTotalCoralPoints());
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