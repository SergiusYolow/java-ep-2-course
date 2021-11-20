/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class EventListener extends java.util.EventObject {
    
    public static final int MESS1 = 0;
    protected int id;                            

    public EventListener(Object source, int id) {
        super(source);
        this.id = id;
    }

    public int getID() {
        return id;
    }     
}
