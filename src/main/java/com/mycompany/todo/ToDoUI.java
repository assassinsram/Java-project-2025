/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.todo;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import com.toedter.calendar.JDateChooser; // importing a ready assets.
import javax.sound.sampled.*; // for sound.
import java.io.File;
import java.io.*;
import java.util.Collections;
import java.util.logging.*;



public class ToDoUI extends javax.swing.JFrame {

    private DefaultListModel<DynamicList> ListofLists = new DefaultListModel<>(); // Holds the list objects.
    private JList<DynamicList> listJList = new JList<>(ListofLists);; // Displays the list names in the UI.
    private JPanel taskPanel = new JPanel();; // Panel to show tasks related to selected list.

    public ToDoUI() {
        loadData();
        // Main Window options:-
        setTitle("To-Do or not To-Do"); // Title of the window.
        setSize(900, 600); // Size of the window.
        setLayout(new BorderLayout()); // Use border layout for main window.        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // a pop-up message when closing the application.
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveData(); // to save data before closing the application.
                JOptionPane.showMessageDialog(rootPane, "Thank you for using To-Do or not To-Do!\n                      Goodbye!");
                System.exit(0);
            }});

        // Main Welcome Message.
        JOptionPane.showMessageDialog(rootPane, """
            Welcome To our Smart To-Do app 
                    *To-Do or not To-Do*
                - Ramzi Younis
                - Asem Jalaileh
                - Abd-Alqader Alliftawi""", "Welcome message", 1);

        
//-------------------------------- Left Panel (Lists) ----------------------------------------------------------------------------------------------\\
        JScrollPane listScrollPane = new JScrollPane(listJList); // Add scroll functionality.
        listJList.setFont(new Font("Arial", Font.BOLD, 24)); // font options for list names.

        // ----------- Add list button ----------- \\
        JButton addListButton = new JButton("+"); // Button to add new list.
        addListButton.setFont(new Font("Arial", Font.BOLD, 20)); // font options for add list button.
        addListButton.addActionListener(e -> { // Define button action.
            String listName = JOptionPane.showInputDialog(this, "Enter List Name:"); // Asks for list name.
            if (listName != null && !listName.trim().isEmpty()) { // If not empty.
                DynamicList newList = new DynamicList(listName); // Create new List object.
                ListofLists.addElement(newList);} // Add to list model.
        });
        
        // ----------- Remove list button ----------- \\
        JButton removeListButton = new JButton("-"); // Button to remove a list.
        removeListButton.setFont(new Font("Arial", Font.BOLD, 20)); // font options for remove list button.
        removeListButton.addActionListener(e -> {
            List selectedList = listJList.getSelectedValue();
            if (selectedList != null) {
                int confirm = JOptionPane.showConfirmDialog(this, // delete list confirmation pop-up.
                    "Are you sure you want to remove this list?", 
                    "Confirm Remove", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ListofLists.removeElement(selectedList); // remove list.
                    taskPanel.removeAll(); // remove all tasks.
                    taskPanel.revalidate(); // refresh.
                    taskPanel.repaint(); // refresh.
                }
            }
            else JOptionPane.showMessageDialog(this, "Please select a list to remove."); // if there is no list selected.
        });
        
        // ----------- Edit list button ----------- \\
        JButton EditListButton = new JButton("Edit");
        EditListButton.setFont(new Font("Arial", Font.BOLD, 20)); // font options for Edit list button.
        EditListButton.addActionListener(E -> {
            DynamicList selectedList = listJList.getSelectedValue();
            if (selectedList != null) {
                String newName = JOptionPane.showInputDialog(this, "Edit List Name:", selectedList.getname());
                if (newName != null && !newName.trim().isEmpty()) {
                    selectedList.setname(newName);
                    listJList.repaint(); // Refresh list display
                }
            }
            else JOptionPane.showMessageDialog(this, "Please select a list to edit.");
        });

        listJList.addListSelectionListener(e -> { // When list selection changes.
            if (!e.getValueIsAdjusting()) { showTasksForSelectedList();}}); // after selection is done, update task panel.
        
        // getting the list header ready:-
        JPanel listHeader = new JPanel(); // Header panel for list.
        JLabel listLabel = new JLabel(" New List"); // Label text.
        listLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style.
        listHeader.add(addListButton); // add new list button.
        listHeader.add(removeListButton); // add remove list button.
        listHeader.add(EditListButton); // add Edit list button.
        listHeader.add(listLabel); // Add "New List" label.

        JPanel listPanel = new JPanel(new BorderLayout()); // Full left panel.
        listPanel.add(listHeader, BorderLayout.NORTH); // Add header to top.
        listPanel.add(listScrollPane, BorderLayout.CENTER); // Add list below.

        
//-------------------------------- Right Panel (Tasks) ----------------------------------------------------------------------------------------------\\
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS)); // Vertical layout.
        JScrollPane taskScrollPane = new JScrollPane(taskPanel); // Scrollable panel for tasks.

        JButton addTaskButton = new JButton("+"); // Button to add new task.
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 24)); // Set font style.
        addTaskButton.addActionListener(e -> { // Define task button action.
            DynamicList selectedList = listJList.getSelectedValue(); // Get selected list.
            if (selectedList == null) { JOptionPane.showMessageDialog(this, "Please select a list first.");return;} // if no selected list.

        String taskName = JOptionPane.showInputDialog(this, "Enter Task Name:"); // Ask for task name.
        if (taskName == null || taskName.trim().isEmpty()) return; // Ignore if task name is blank.

        // Create a date chooser component
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        // Show it inside a dialog.
        int option = JOptionPane.showConfirmDialog(this, dateChooser, "Select Due Date", JOptionPane.OK_CANCEL_OPTION);

        LocalDate dueDate = null;  // default to no date
        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) return; // cancel the date choosing operation.

        if (option == JOptionPane.OK_OPTION) { // if the user pressed OK.
            java.util.Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) { 
                dueDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(); // turning the selected date type into a local date.
                if (dueDate.isBefore(LocalDate.now())) {  JOptionPane.showMessageDialog(this, "Invalid Date");return;  } // if the date is before today, then ignore it.
            }
        }
        Task newTask = new Task(taskName, dueDate);
        selectedList.append(newTask);
        showTasksForSelectedList();
        });

        JPanel taskHeader = new JPanel(new BorderLayout()); // Header for task section.
        taskHeader.add(addTaskButton, BorderLayout.EAST); // Add task button right.

        JPanel taskContainer = new JPanel(new BorderLayout()); // Container for tasks.
        taskContainer.add(taskHeader, BorderLayout.NORTH); // Add header top.
        taskContainer.add(taskScrollPane, BorderLayout.CENTER); // Add tasks below.

        
//-------------------------------- Split Pane Layout ----------------------------------------------------------------------------------------------\\
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, taskContainer); // Splits the screen.
        splitPane.setDividerLocation(290); // Set divider width.
        add(splitPane, BorderLayout.CENTER); // Add to main frame.
        
        loadData(); // to load data after starting the program.
    }

    // a function to display tasks of the selected list.
    private void showTasksForSelectedList() {
        taskPanel.removeAll(); // Clear previous tasks.
        DynamicList selectedList = listJList.getSelectedValue(); // Get selected list.
        
        if (selectedList != null) {
            
            // ----------- Notifications ----------- \\
            for (Task t : selectedList.getTaskList()) { // After each list selection the reminder will work for the tasks that due today or tomorrow.
                if (t != null && !t.getstatus() && t.getDueDate() != null &&
                    (t.getDueDate().isEqual(LocalDate.now()) || t.getDueDate().isEqual(LocalDate.now().plusDays(1)))) {
                    JOptionPane.showMessageDialog(this, "Reminder: \"" + t.getname() + "\" due on " + t.getDueDate());
                }
            }
            
            for (int i = 0; i < selectedList.getsize(); i++) { // For each task.
                Task t = selectedList.getTaskList()[i]; // Get task.
                if (t == null) continue; // Skip if the task is null.
                JPanel taskItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,6,0)); // Create a panel to hold each task item.
                
                
                // ----------- Create the checkbox ----------- \\
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(t.getstatus()); // link it with the task status.
                checkBox.addActionListener(e -> { 
                    t.setstatus(); // update status when checkbox is clicked.
                    if (t.getstatus()){ // if the task is completed, play the audio
                        try {
                            File audio = new File("src\\main\\java\\com\\mycompany\\todo\\complete.wav"); // play audio.
                            PlayAudio(audio);
                        }
                        catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) { // if any of these errors happen to the audio, ignore it.
                            Logger.getLogger(ToDoUI.class.getName()).log(Level.SEVERE, null, ex);
                        }}});

                
                // ----------- Create the label for task name and date ----------- \\
                JLabel taskLabel = new JLabel(t.getname() + "     " + (t.getDueDate() != null ? t.getDueDate() : "")); // if the DueDate is null, don't show it.
                taskLabel.setFont(new Font("Arial", Font.PLAIN, 24));
                
                
                // ----------- Edit button ----------- \\
                JButton editButton = new JButton("Edit");
                editButton.setFont(new Font("Arial", Font.BOLD, 16));
                editButton.addActionListener(e -> {
                    
                    // Rename: 
                    String newName = JOptionPane.showInputDialog(this, "Edit Task Name:", t.getname()); // name input window.
                    if (newName != null && !newName.trim().isEmpty()) {t.setname(newName);} // if the new name is not null or not empty, accept it.
                    
                    // Rechoose the date: 
                    JDateChooser dateChooser = new JDateChooser();
                    dateChooser.setDateFormatString("yyyy-MM-dd");

                    int option = JOptionPane.showConfirmDialog(this, dateChooser, "Edit Due Date", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) return; // cancel the date choosing operation.

                    if (option == JOptionPane.OK_OPTION) {
                        java.util.Date selectedDate = dateChooser.getDate();
                        if (selectedDate != null) {
                            LocalDate newDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                            if ( newDate.isBefore(LocalDate.now()) ){ JOptionPane.showMessageDialog(this, "Inavlid Date");return; } // if the selected date is before today, ignore it.
                            t.setDueDate(newDate);}
                        else t.setDueDate(null); // editing the duedate to null if no date is choosen (removing duedate).
                    }
                // Refresh the display to show updates
                showTasksForSelectedList();});
                
                
                // ----------- Remove button ----------- \\
                JButton removeButton = new JButton("X");
                removeButton.setFont(new Font("Arial", Font.BOLD, 16));
                removeButton.addActionListener(e2 -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Find the correct index by searching for the task
                        for (int j = 0; j < selectedList.getsize(); j++) {
                            if (selectedList.getTaskList()[j] == t) { // compare by object reference
                                selectedList.remove(j);
                                break;} // after finding the selected task, exit the loop.
                        }
                    }
                    showTasksForSelectedList(); // refresh the display
                });
                taskItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // a few space between tasks.

                // Add the task labels and controls.
                taskItemPanel.add(checkBox);
                taskItemPanel.add(taskLabel);
                taskItemPanel.add(editButton);
                taskItemPanel.add(removeButton);
                taskPanel.add(taskItemPanel);// Add the task panel to the main taskPanel(area).
            }
        }
        taskPanel.revalidate(); // Refreshs the layout.
        taskPanel.repaint(); // Redraw panel. 
   }
    
    // a function to play the audio.
    private void PlayAudio(File f) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(f);
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        clip.start();
    }

    private void saveData() {
        try (ObjectOutputStream outfile = new ObjectOutputStream(new FileOutputStream("todolists.ser"))) { // it opens a file for writing.
            java.util.List<DynamicList> lists = Collections.list(ListofLists.elements()); // convert list of lists to the java list.
            outfile.writeObject(lists); // write the list format in the file.
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage()); // happens when there is a problem in writing.
        }
    }

    @SuppressWarnings("unchecked") // to make java ignores the warning of reading a file.
    private void loadData() {
        File file = new File("todolists.ser");
        if (!file.exists()) return; // if the saving file does not exist, then we exit.

        try (ObjectInputStream infile = new ObjectInputStream(new FileInputStream(file))) { // it opens a file for reading.
            java.util.List<DynamicList> SavedLists = (java.util.List<DynamicList>) infile.readObject(); // deserializing the file and taking the saved lists.
            ListofLists.clear(); // clearing the ListofLists.
            for (DynamicList d : SavedLists) {
                ListofLists.addElement(d); // puting the saved lists in the ListofLists.
            }
        } catch (IOException | ClassNotFoundException e) { // happens when there is a problem in reading, and when the class is not found.
            System.out.println("Error loading: " + e.getMessage());
        }
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