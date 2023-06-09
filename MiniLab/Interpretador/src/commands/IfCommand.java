/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import values.BoolValue;
/**
 *
 * @author Marcos
 */
public class IfCommand extends Command{
    private Command elsecmd;
    private Command then;
    private BoolValue expr;

    public IfCommand(Command then, BoolValue expr, int line) {
        super(line);
        this.then = then;
        this.expr = expr;
    }

    public IfCommand(Command then, Command elsecmd, BoolValue expr, int line) {
        super(line);
        this.then = then;        
        this.elsecmd = elsecmd;
        this.expr = expr;
    }
    
    @Override
    public void execute(){
        if(this.expr.value())
            this.then.execute();
        else
            if(this.elsecmd != null)
                this.elsecmd.execute();
    }
}
