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
public class StringConcat extends StringValue{
    
    private Value<?> left;
    private Value<?> right;

    public StringConcat(Value<?> left, Value<?> right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String value(){
        Value<?> v1 = (this.left instanceof Variable ? ((Variable)this.left).value() : this.left);
        Value<?> v2 = (this.right instanceof Variable ? ((Variable)this.right).value() : this.right);
        
        String ret = "";

        if (v1 instanceof IntValue) {
            ret += Integer.toString(((IntValue) v1).value());
        } else if (v1 instanceof StringValue) {
            ret += ((StringValue) v1).value();
        } else {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }

        if (v2 instanceof IntValue) {
            ret += Integer.toString(((IntValue) v2).value());
        } else if (v2 instanceof StringValue) {
            ret += ((StringValue) v2).value();
        } else {
            System.out.println(this.line()+": Tipos inválidos");
            System.exit(1);
            return null;
        }

        return ret;
    }
}

