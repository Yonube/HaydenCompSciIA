package src.JavaFXGUI.GraphsFolder;
import javax.swing.*;

import org.jfree.data.general.PieDataset;

public class JFreePieChartPanel extends JPanel {
    private org.jfree.data.general.DefaultPieDataset dataset;
    private org.jfree.chart.JFreeChart chart;
    private org.jfree.chart.ChartPanel chartPanel;

    public JFreePieChartPanel() {
        this(null, "Pie Chart");
    }

    public JFreePieChartPanel(java.util.Map<String, ? extends Number> data, String title) {
        setLayout(new java.awt.BorderLayout());
         dataset = new org.jfree.data.general.DefaultPieDataset();
        if (data != null) {
            for (java.util.Map.Entry<String, ? extends Number> e : data.entrySet()) {
                dataset.setValue(e.getKey(), e.getValue());
            }
        }
        chart = org.jfree.chart.ChartFactory.createPieChart(title, dataset, true, true, false);
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        add(chartPanel, java.awt.BorderLayout.CENTER);
    }

    /** Replace current dataset with new values (clears previous values). */
    public void updateData(java.util.Map<String, ? extends Number> data) {
        dataset.clear();
        if (data != null) {
            for (java.util.Map.Entry<String, ? extends Number> e : data.entrySet()) {
                dataset.setValue(e.getKey(), e.getValue());
            }
        }
        chart.fireChartChanged();
    }

    public org.jfree.chart.JFreeChart getChart() {
        return chart;
    }

    public org.jfree.data.general.DefaultPieDataset getDataset() {
        return dataset;
    }
}
