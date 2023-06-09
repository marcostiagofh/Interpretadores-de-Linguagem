/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 *
 * @author Marcos
 */
public abstract class Command{
    private int line;

    public Command(int line) {
        this.line = line;
    }
    
    public int line(){
        return this.line;
    }
    
    public abstract void execute();
}
