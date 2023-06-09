/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

/**
 *
 * @author Marcos
 */
public class SingleIntExpr extends IntValue{
    private final IntValue value;

    public SingleIntExpr(IntValue value, int line) {
        super(line);
        this.value = value;
    }
    
    @Override
    public Integer value(){
        return value.value();
    }
}
