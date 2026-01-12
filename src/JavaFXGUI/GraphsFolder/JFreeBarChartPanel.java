package src.JavaFXGUI.GraphsFolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

import src.OOPBackEnd.*;

public class JFreeBarChartPanel extends JPanel {

    private org.jfree.data.category.DefaultCategoryDataset dataset;
    private org.jfree.chart.JFreeChart chart;
    private org.jfree.chart.ChartPanel chartPanel;
    
    public JFreeBarChartPanel(List<RobotTeam> data, String chartTitle) {

       dataset = new org.jfree.data.category.DefaultCategoryDataset();
         if (data != null) {
              for (RobotTeam rt : data) {
                
                if (rt == null) {
                     continue;
                }
                String key = rt.getTeamNumber() + " - " + rt.getTeamName();
                int points = rt.getTotalCoralPoints() + rt.getTotalAlgaePoints();
                dataset.addValue(points, "Points", key);
              }
            }
       
        JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Robot Team",            
         "Most Points Scored (Coral + Algae)",            
         dataset,          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
        ChartPanel chartPanel = new ChartPanel( barChart );       
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
}