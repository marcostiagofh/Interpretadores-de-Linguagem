/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package interpretador;

/**
 *
 * @author Marcos
 */

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private final Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<>();
        
        // FIXME: Add the tokens here.
        // st.put("???", TokenType.???);
        
        	st.put("=",TokenType.ASSIGN);
		st.put("input",TokenType.INPUT);
		st.put("(",TokenType.PAR_OPEN);
		st.put(")",TokenType.PAR_CLOSE);
		st.put(";",TokenType.DOT_COMMA);
		st.put("[",TokenType.BRA_OPEN);
		st.put("]",TokenType.BRA_CLOSE);
		st.put(".",TokenType.DOT);
		st.put("rand",TokenType.RAND);
		st.put(",",TokenType.COMMA);
		st.put("show",TokenType.SHOW);
		st.put("for",TokenType.FOR);		
		st.put("seq",TokenType.SEQ);	
		st.put("-",TokenType.MINUS);	
		st.put("+",TokenType.PLUS);	
		st.put("*",TokenType.TIMES);	
		st.put("/",TokenType.DIV);	
		st.put("for",TokenType.FOR);	
		st.put("%",TokenType.MOD);	
		st.put("value",TokenType.VALUE);	
		st.put("end",TokenType.END);	
		st.put("iseq",TokenType.ISEQ);	
		st.put("==",TokenType.EQUAL);	
		st.put("!=",TokenType.DIFF);	
		st.put("<",TokenType.LOWER);	
		st.put("<=",TokenType.LOWER_EQUAL);	
		st.put(">",TokenType.GREATER);	
		st.put(">=",TokenType.GREATER_EQUAL);	
		st.put("&",TokenType.AND);
		st.put("|",TokenType.OR);
		st.put("if",TokenType.IF);
		st.put("else",TokenType.ELSE);		
		st.put("while",TokenType.WHILE);	
		st.put("opposed",TokenType.OPPOSED);		
		st.put("transposed",TokenType.TRANSPOSED);	
		st.put("sum",TokenType.SUM);	
		st.put("mul",TokenType.MUL);	
		st.put("null",TokenType.NULL);		
		st.put("fill",TokenType.FILL);	
		st.put("id",TokenType.ID);	
		st.put("size",TokenType.SIZE);	
		st.put("rows",TokenType.ROWS);
		st.put("cols",TokenType.COLS);
        
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType get(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }

}
