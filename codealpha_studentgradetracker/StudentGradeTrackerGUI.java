/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codealpha_studentgradetracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI {
    JFrame frame;
    JTextField nameField, percentField;
    JTable table;
    JLabel avgLabel, maxLabel, minLabel;
    DefaultTableModel model;

    ArrayList<String> studentNames = new ArrayList<>();
    ArrayList<Double> studentPercentages = new ArrayList<>();

    public StudentGradeTrackerGUI() {
        frame = new JFrame("Student Grade Tracker");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(230, 245, 255));
        frame.setLayout(null);

        JLabel title = new JLabel("📘 Student Grade Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(30, 60, 120));
        title.setBounds(450, 20, 600, 40);
        frame.add(title);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setBounds(100, 100, 180, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameField.setBounds(260, 100, 250, 30);
        frame.add(nameField);

        JLabel percentLabel = new JLabel("Percentage (0-100):");
        percentLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        percentLabel.setBounds(540, 100, 220, 30);
        frame.add(percentLabel);

        percentField = new JTextField();
        percentField.setFont(new Font("Arial", Font.PLAIN, 18));
        percentField.setBounds(760, 100, 100, 30);
        frame.add(percentField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(880, 100, 140, 30);
        addButton.setBackground(new Color(0, 150, 136));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(addButton);

        JButton summaryButton = new JButton("Show Summary");
        summaryButton.setBounds(1040, 100, 180, 30);
        summaryButton.setBackground(new Color(33, 150, 243));
        summaryButton.setForeground(Color.WHITE);
        summaryButton.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(summaryButton);

        String[] columns = { "Name", "Percentage", "Grade", "Performance" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setGridColor(Color.GRAY);
        table.setBackground(Color.WHITE);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBounds(100, 170, 1000, 400);
        frame.add(tablePane);

        avgLabel = new JLabel("Average: ");
        avgLabel.setFont(new Font("Arial", Font.BOLD, 20));
        avgLabel.setBounds(100, 600, 300, 30);
        frame.add(avgLabel);

        maxLabel = new JLabel("Highest: ");
        maxLabel.setFont(new Font("Arial", Font.BOLD, 20));
        maxLabel.setBounds(400, 600, 300, 30);
        frame.add(maxLabel);

        minLabel = new JLabel("Lowest: ");
        minLabel.setFont(new Font("Arial", Font.BOLD, 20));
        minLabel.setBounds(700, 600, 300, 30);
        frame.add(minLabel);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String percentText = percentField.getText().trim();
                if (name.isEmpty() || percentText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both name and percentage.");
                    return;
                }
                try {
                    double percent = Double.parseDouble(percentText);
                    if (percent < 0 || percent > 100) {
                        JOptionPane.showMessageDialog(frame, "Percentage must be between 0 and 100.");
                        return;
                    }

                    studentNames.add(name);
                    studentPercentages.add(percent);
                    nameField.setText("");
                    percentField.setText("");
                    JOptionPane.showMessageDialog(frame, "Student added successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Enter a valid number for percentage.");
                }
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                if (studentPercentages.isEmpty()) return;

                double sum = 0, max = studentPercentages.get(0), min = studentPercentages.get(0);
                for (int i = 0; i < studentPercentages.size(); i++) {
                    double percent = studentPercentages.get(i);
                    String name = studentNames.get(i);
                    String grade = getGrade(percent);
                    String performance = getPerformance(percent);

                    model.addRow(new Object[] { name, percent + "%", grade, performance });

                    sum += percent;
                    if (percent > max) max = percent;
                    if (percent < min) min = percent;
                }
                double avg = sum / studentPercentages.size();
                avgLabel.setText("Average: " + String.format("%.2f", avg) + "%");
                maxLabel.setText("Highest: " + String.format("%.2f", max) + "%");
                minLabel.setText("Lowest: " + String.format("%.2f", min) + "%");
            }
        });

        frame.setVisible(true);
    }

    public String getGrade(double percent) {
        if (percent >= 90) return "A";
        else if (percent >= 80) return "B";
        else if (percent >= 70) return "C";
        else if (percent >= 60) return "D";
        else return "F";
    }

    public String getPerformance(double percent) {
        if (percent >= 90) return "Excellent";
        else if (percent >= 80) return "Good";
        else if (percent >= 70) return "Average";
        else if (percent >= 50) return "Poor";
        else return "Very Poor";
    }

}
