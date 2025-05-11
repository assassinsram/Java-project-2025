package com.mycompany.todo;

public class DynamicList extends List {
    
    public DynamicList(){ // a constructor.
        super();
    }
    
    public DynamicList(String firstname){ // a constructor.
        super(firstname);
    }
    
    public void resize(int newcapacity){ // an algorithm to make the list limitless.
        Task[] newlist = new Task[newcapacity];
        System.arraycopy(this.TaskList, 0, newlist, 0, this.size);
        this.TaskList=newlist;
        this.capacity=newcapacity;
    }
    
    public void append(Task Tobj){ // a method for adding tasks to the list.
        if (this.size == this.capacity) {  resize(2*this.capacity);  }
        TaskList[size] = Tobj;
        this.size++;
    }
}