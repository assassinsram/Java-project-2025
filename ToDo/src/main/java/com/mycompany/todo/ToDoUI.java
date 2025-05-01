/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoUI extends javax.swing.JFrame {
    
    private DefaultListModel<List> ListofLists = new DefaultListModel<>(); // Holds the list objects.
    private JList<List> listJList = new JList<>(ListofLists);; // Displays the list names in the UI.
    private JPanel taskPanel = new JPanel();; // Panel to show tasks related to selected list.

    public ToDoUI() {
        // Main Window options:-
        setTitle("To-Do or not To-Do"); // Title of the window.
        setSize(800, 600); // Size of the window.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the program when window closes.
        setLayout(new BorderLayout()); // Use border layout for main window.

         // Main Welcome Message.
        JOptionPane.showMessageDialog(rootPane,"Welcome To our Smart To-Do app \n        *To-Do or not To-Do*", "Welcome message", 1);

        // --- Left Panel (Lists) --- \\
        JScrollPane listScrollPane = new JScrollPane(listJList); // Add scroll functionality.
        listJList.setFont(new Font("Arial", Font.BOLD, 24)); // font options for list names.

        JButton addListButton = new JButton("+"); // Button to add new list.
        addListButton.setFont(new Font("Arial", Font.BOLD, 24)); // font options for add list button.
        addListButton.addActionListener(e -> { // Define button action.
            String listName = JOptionPane.showInputDialog(this, "Enter List Name:"); // Asks for list name.
            if (listName != null && !listName.trim().isEmpty()) { // If not empty.
                List newList = new List(listName); // Create new List object.
                ListofLists.addElement(newList); // Add to list model.
            }
        });

        listJList.addListSelectionListener(e -> { // When list selection changes.
            if (!e.getValueIsAdjusting()) { // Only act after selection is done.
                showTasksForSelectedList(); // Update task panel.
            }
        });

        // getting the list header ready:-
        JPanel listHeader = new JPanel(new BorderLayout()); // Header panel for list.
        JLabel listLabel = new JLabel(" New List"); // Label text.
        listLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style.
        listHeader.add(addListButton, BorderLayout.WEST); // Add button left.
        listHeader.add(listLabel, BorderLayout.CENTER); // Add label center.

        JPanel listPanel = new JPanel(new BorderLayout()); // Full left panel.
        listPanel.add(listHeader, BorderLayout.NORTH); // Add header to top.
        listPanel.add(listScrollPane, BorderLayout.CENTER); // Add list below.

        // --- Right Panel (Tasks) --- \\
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS)); // Vertical layout.
        JScrollPane taskScrollPane = new JScrollPane(taskPanel); // Scrollable panel for tasks.

        JButton addTaskButton = new JButton("+"); // Button to add new task.
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 24));
        addTaskButton.addActionListener(e -> { // Define task button action.
            List selectedList = listJList.getSelectedValue(); // Get selected list.
            if (selectedList == null) { // If nothing selected.
                JOptionPane.showMessageDialog(this, "Please select a list first.");
                return;
            }

            String taskName = JOptionPane.showInputDialog(this, "Enter Task Name:"); // Ask for task name.
            if (taskName == null || taskName.trim().isEmpty()) return; // Ignore if blank.

            String dateInput = JOptionPane.showInputDialog(this, "Enter Due Date (YYYY-MM-DD):"); // Ask for date.
            LocalDate dueDate;
            try {
                dueDate = LocalDate.parse(dateInput); // Convert to LocalDate.
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format."); // Show error.
                return;
            }

            Task newTask = new Task(taskName, dueDate); // Create new Task object.
            System.out.println(newTask.toString());
            selectedList.append(newTask); // Add task to list.
            System.out.println(selectedList.toString());
            showTasksForSelectedList(); // Refresh task panel.
        });

        JPanel taskHeader = new JPanel(new BorderLayout()); // Header for task section.
        taskHeader.add(addTaskButton, BorderLayout.EAST); // Add task button right.

        JPanel taskContainer = new JPanel(new BorderLayout()); // Container for tasks.
        taskContainer.add(taskHeader, BorderLayout.NORTH); // Add header top.
        taskContainer.add(taskScrollPane, BorderLayout.CENTER); // Add tasks below.

        // --- Split Pane Layout --- \\
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, taskContainer); // Splits the screen.
        splitPane.setDividerLocation(200); // Set divider width.
        add(splitPane, BorderLayout.CENTER); // Add to main frame.
    }

    // a function to display tasks of the selected list.
    private void showTasksForSelectedList() {
        taskPanel.removeAll(); // Clear previous tasks.
        List selectedList = listJList.getSelectedValue(); // Get selected list.
        if (selectedList != null) {
            for (int i = 0; i < selectedList.getsize(); i++) { // For each task.
                Task t = selectedList.getTaskList()[i]; // Get task.
                if (t == null) continue; // Skip if null.
                JLabel label = new JLabel(" • " + t.getname() + "         " + t.getDueDate()); // Create label for task.
                label.setFont(new Font("Arial", Font.PLAIN, 24)); // font options for task label.
                taskPanel.add(label); // Add to panel.
            }
        }
        taskPanel.revalidate(); // Refreshs the layout.
        taskPanel.repaint(); // Redraw panel.
    }
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("To-Do or not To-Do");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 1920, 1080));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setSize(new java.awt.Dimension(1800, 900));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1086, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
