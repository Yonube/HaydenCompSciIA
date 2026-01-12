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
        chartPanel.setPreferredSize(new Dimension(600, 600));

        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
    public JFreeChartScatterGraphPanel(List<Matches> data, RobotTeam team) {

        XYSeries series = new XYSeries("Data Points");
        for (int i = 0; i < data.size(); i++) {
            Matches match = data.get(i);
            series.add(match.getMatchNumber(), team.getTotalCoralPointsInMatch(match.getMatchNumber())+team.getTotalAlgaePointsInMatch(match.getMatchNumber()));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Scatter Graph",
                "X-Axis",
                "Y-Axis",
                dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setPreferredSize(new Dimension(600, 600));

        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
}