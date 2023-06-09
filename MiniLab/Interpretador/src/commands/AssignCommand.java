/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import values.ConstIntValue;
import values.IntValue;
import values.MatrixValue;
import values.RefMatrixValue;
import values.Value;
import values.Variable;
/**
 *
 * @author Marcos
 */
public class AssignCommand extends Command {
    
    private Variable var;
    private Value<?> value;   
    
    public AssignCommand(Variable var, Value<?> value, int line) {
        super(line);
        
        this.var = var;        
        this.value = value;
    }
    
    /**
     *public void execute() é a função que executa um comando. Ela é específica para cada instância de comando diferente.ariables 
     */
    @Override
    public void execute(){
        Value<?> v = (this.value instanceof Variable ? ((Variable)this.value).value() : this.value);
        
        if(v instanceof IntValue){
            ConstIntValue c = new ConstIntValue(((IntValue)v).value(), -1);
            this.var.setValue(c);
        } else if (v instanceof MatrixValue) {
            RefMatrixValue ref = new RefMatrixValue(((MatrixValue)v).value(), -1);
            this.var.setValue(ref);
        } else {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
        }
    }
    
}
