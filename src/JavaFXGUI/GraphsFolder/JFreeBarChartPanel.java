package src.JavaFXGUI.GraphsFolder;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import src.OOPBackEnd.*;

public class JFreeBarChartPanel extends JPanel {

    private JFreeBarChartPanel() {
        setLayout(new BorderLayout());
    }

    
    public static JFreeBarChartPanel fromRobotTeams(
            List<RobotTeam> data, String chartTitle) {

        JFreeBarChartPanel panel = new JFreeBarChartPanel();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (data != null) {
            for (RobotTeam rt : data) {
                if (rt == null) continue;

                String key = rt.getTeamNumber() + " - " + rt.getTeamName();
                int points = rt.getTotalCoralPoints() + rt.getTotalAlgaePoints();
                dataset.addValue(points, "Points", key);
            }
        }

        panel.buildChart(dataset, chartTitle,
                "Robot Team",
                "Most Points Scored (Coral + Algae)");

        return panel;
    }

    public static JFreeBarChartPanel fromMatches(
            List<Matches> matches, String chartTitle, RobotTeam team) {

        JFreeBarChartPanel panel = new JFreeBarChartPanel();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // build dataset from matches here

        

        panel.buildChart(dataset, chartTitle,
                "Match",
                "Points Scored By Team");

        return panel;
    }

    private void buildChart(
            DefaultCategoryDataset dataset,
            String title,
            String xLabel,
            String yLabel) {

        JFreeChart chart = ChartFactory.createBarChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 600));
        add(chartPanel, BorderLayout.CENTER);
    }
}
