/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Value;

/**
 *
 * @author marcos
 */
public class ConstInt extends IntValue{
    private int value;

    public ConstInt(int value, int line) {
        super(line);
        this.value = value;
    }
    
    public Integer getValue(){
        return null;
    }
}
