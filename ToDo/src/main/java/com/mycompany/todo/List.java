package com.mycompany.todo;

public class List {
    
    // data members
    private String name;
    private int capacity = 1;
    private Task[] TaskList = new Task[capacity];
    private int size = 0;
    
    public List(){ // a constructor.
        this.name = "Untitled List";
    }
    
    public List(String FirstName){ // a constructor.
        this.name = FirstName;
    }
    
    public void append(Task Tobj){ // a method for adding tasks to the list.
        if (this.size == this.capacity) {  resize(2*this.capacity);  }
        TaskList[size] = Tobj;
        this.size++;
    }
    
    public void remove(int index){ // a method to remove an object(Task) using its index from the list.
        for (int i = index; i < this.size-1; i++) {
            TaskList[i] = TaskList[i + 1];}
        this.size--;
        TaskList[this.size]=null;
    }
    
    public void resize(int newcapacity){ // an algorithm to make the list limitless.
        Task[] newlist = new Task[newcapacity];
        System.arraycopy(this.TaskList, 0, newlist, 0, this.size);
        this.TaskList=newlist;
        this.capacity=newcapacity;
    }
    
    public void setname(String newname){  this.name=newname;  } // a method to name and rename the list.
    
    public String getname(){  return this.name;} // a method to return the list name.
    
    public int getsize(){  return this.size;  } // a method to return the list size(length).
    
    @Override
    public String toString(){ return this.name;}
    public String print(){ // a method to represent a string format of the list using override concept.
        String s = "";
        for (int i = 0; i < this.size; i++) {
            s+= this.TaskList[i].toString()+"\n";
        }
        return this.name+"\n"+s;
    }

    public Task[] getTaskList() {
        return this.TaskList;
    }
}