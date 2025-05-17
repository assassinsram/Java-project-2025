package com.mycompany.todo;

import java.io.Serializable; /* Serialization means converting an object to a format which can be saved in a file.
                                           Deserialization means the reverse of the Serialization process.*/

public class DynamicList extends List implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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