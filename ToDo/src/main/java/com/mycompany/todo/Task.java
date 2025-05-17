package com.mycompany.todo;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // data members.
    private String name;
    private boolean status;
    private LocalDate DueDate;
    
    public Task(String name, LocalDate date) { // a constructor.
        this.name = name;
        this.DueDate = date;
        this.status = false;
    }
    
    public Task(Task obj) { // a copy constructor.
        this.name = obj.name;
        this.DueDate = obj.DueDate;
        this.status = obj.status;
    }
    
    public void setname(String new_name){  this.name = new_name;  } // a method to rename the task.
    
    public void setstatus(){  this.status = !this.status;  } // a method to check the task.
    
    public void setDueDate(LocalDate date) {  this.DueDate = date;  } // a method to set a due date
    
    public String getname(){  return this.name;  } // a method that returns the task name.
    
    public boolean getstatus(){  return this.status;  } // a method that returns the task status if it is checked or not.
    
    public LocalDate getDueDate() {  return this.DueDate;  } // a method that returns the due date of the task.
    
    @Override
    public String toString(){ // a method to represent a string format of the task using override concept.
        return this.name+"\n"+this.DueDate+"\n"+this.status;
    }
}