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
public class SingleBoolExpr extends BoolValue{
    private BoolValue value;

    public SingleBoolExpr(BoolValue value, int line) {
        super(line);
        this.value = value;
    }

    public Boolean getValue(){
        return null;
    }
}
