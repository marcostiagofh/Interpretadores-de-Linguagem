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
public class Clause extends BoolValue{
    private boolean neg;
    private BoolValue cmp;

    public Clause(boolean neg, BoolValue cmp, int line) {
        super(line);
        this.neg = neg;
        this.cmp = cmp;
    }
    
    public Boolean getValue(){
        return null;
    }
}
