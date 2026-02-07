package src.JavaFXGUI.GraphsFolder;

import javax.swing.*;

import org.jfree.chart.plot.PiePlot;

import src.JavaFXGUI.*;

import java.awt.BasicStroke;
import java.util.List;
import src.OOPBackEnd.RobotTeam;

public class JFreePieChartPanel extends JPanel {
    public org.jfree.data.general.DefaultPieDataset<String> dataset;
    public org.jfree.chart.JFreeChart chart;
    public org.jfree.chart.ChartPanel chartPanel;

    public JFreePieChartPanel(List<RobotTeam> data, String title) {
        setLayout(new java.awt.BorderLayout());
        dataset = new org.jfree.data.general.DefaultPieDataset<String>();
        if (data != null) {
            for (RobotTeam rt : data) {

                if (rt == null) {
                    continue;
                }
                String key = "Name: " + rt.getTeamNumber() + " Number: " + rt.getTeamName() + "  Total Points: "
                        + rt.getTotalPoints();
                int points = rt.getTotalCoralPoints() + rt.getTotalAlgaePoints();
                dataset.setValue(key, points);
            }
        }
        chart = org.jfree.chart.ChartFactory.createPieChart(title, dataset, true, true, true);
        chart.getTitle().setPaint(Mainapp.TEXT);
        chart.setBackgroundPaint(Mainapp.BACKGROUND);
        PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
        plot.setBackgroundPaint(Mainapp.BACKGROUND);
        plot.setOutlineVisible(false);
        plot.setLabelLinkPaint(Mainapp.TEXT);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlinePaint(Mainapp.TEXT); // optional: outline around labels
        plot.setLabelPaint(Mainapp.BACKGROUND); // label text color

        chart.getLegend().setBackgroundPaint(Mainapp.BACKGROUND);
        chart.getLegend().setItemPaint(Mainapp.TEXT);
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        add(chartPanel, java.awt.BorderLayout.CENTER);
    }
}