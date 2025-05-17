/*
Welcome to our application.
This application allows users to manage their daily tasks using a simple and user-friendly interface.
The system will enable users to add, edit, check, delete tasks, or set due dates, and
get notification for upcoming deadlines. The aim is to help users stay organized and improve their productivity.
It will maintain task data using arrays. A focus will also be placed on clean code and usability.
*/

package com.mycompany.todo;

import java.time.LocalDate;

public class ToDo {

    public static void main(String[] args) {
        // Task Example // 
        Task t1 = new Task("University",LocalDate.of(2025, 5, 4));
        System.out.println(t1);
        t1.setDueDate(LocalDate.of(2025, 5, 8));
        t1.setname("GYM");
        t1.setstatus();
        System.out.println(t1);

        
        // List Example // 
        DynamicList exams = new DynamicList("exams");
        Task T1 = new Task("Java",LocalDate.of(2025, 5, 4));
        Task T2 = new Task("web1",LocalDate.of(2025, 5, 8));
        exams.append(T1);
        exams.append(T2);
        exams.append(T2);
        exams.remove(1);
        System.out.println(exams.getname());
        exams.setname("University exams");
        System.out.println(exams.print());
        
        
        // Swing  (UI Frame)
        new ToDoUI().setVisible(true);
    }
}