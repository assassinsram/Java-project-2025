package com.mycompany.todo;

public class List {
    
    // data members
    protected String name;
    protected int capacity = 1;
    protected Task[] TaskList = new Task[capacity];
    protected int size = 0;
    
    public List(){ // a constructor.
        this.name = "Untitled List";
    }
    
    public List(String FirstName){ // a constructor.
        this.name = FirstName;
    }
    
    public void remove(int index){ // a method to remove an object(Task) using its index from the list.
        for (int i = index; i < this.size-1; i++) {
            TaskList[i] = TaskList[i + 1];}
        this.size--;
        TaskList[this.size]=null;
    }
    
    public void setname(String newname){  this.name=newname;  } // a method to name and rename the list.
    
    public String getname(){  return this.name;} // a method to return the list name.
    
    public int getsize(){  return this.size;  } // a method to return the list size(length).
    
    public Task[] getTaskList() {return this.TaskList;} // a method to return the list.
    
    public void append(Task Tobj){ // a method for adding tasks to the list.
        TaskList[size] = Tobj;
        this.size++;
    }
    
    @Override
    public String toString(){ return this.name;} // a method to represent the name of the list using override concept.
    
    public String print(){ // a method to represent a string format of the list.
        String s = "";
        for (int i = 0; i < this.size; i++) {
            s+= this.TaskList[i].toString()+"\n";
        }
        return this.name+"\n"+s;
    }
}