package src.JavaFXGUI.GraphsFolder;
import javax.swing.*;

import java.awt.event.MouseListener;
import java.util.List;
import src.OOPBackEnd.RobotTeam;
import org.jfree.data.general.PieDataset;

public class JFreePieChartPanel extends JPanel {
    private org.jfree.data.general.DefaultPieDataset dataset;
    private org.jfree.chart.JFreeChart chart;
    private org.jfree.chart.ChartPanel chartPanel;

    public JFreePieChartPanel(List<RobotTeam> data, String title) {
        setLayout(new java.awt.BorderLayout());
        dataset = new org.jfree.data.general.DefaultPieDataset();
        if (data != null) {
            for (RobotTeam rt : data) {
                
                if (rt == null) {
                    continue;
                }
                String key = "Name: " + rt.getTeamNumber() + " Number: " + rt.getTeamName() + "  Total Points: " + rt.getTotalPoints();
                int points = rt.getTotalCoralPoints() + rt.getTotalAlgaePoints();
                dataset.setValue(key, points);
            }
        }
        chart = org.jfree.chart.ChartFactory.createPieChart(title, dataset, true, true, false);
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        add(chartPanel, java.awt.BorderLayout.CENTER);
    }
}