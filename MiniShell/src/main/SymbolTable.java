package main;

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<String, TokenType>();

        // special tokens
        st.put("", TokenType.INVALID_TOKEN);
        st.put("", TokenType.UNEXPECTED_EOF);
        st.put("", TokenType.END_OF_FILE);

        // keywords        
        st.put("exit", TokenType.EXIT);
        st.put("read", TokenType.READ);
        st.put("print", TokenType.PRINT);
        st.put("println", TokenType.PRINTLN);
        st.put("set", TokenType.SET);
        st.put("exec", TokenType.EXEC);
        st.put("if", TokenType.IF);
        st.put("then", TokenType.THEN);
        st.put("elif", TokenType.ELIF);
        st.put("else", TokenType.ELSE);
        st.put("fi", TokenType.FI);
        st.put("while", TokenType.WHILE);
        st.put("do", TokenType.DO);
        st.put("done", TokenType.DONE);
        st.put("for", TokenType.FOR);
        st.put("in", TokenType.IN);
        
        // operators
        st.put(".", TokenType.CONCAT);
        st.put("+", TokenType.PLUS);
        st.put("-", TokenType.MINUS);
        st.put("*", TokenType.MUL);
        st.put("/", TokenType.DIV);
        st.put("%", TokenType.MOD);
        st.put("**", TokenType.POWER);
        st.put("&&", TokenType.AND);
        st.put("||", TokenType.OR);
        st.put("!", TokenType.NOT);
        st.put("--z", TokenType.EMPTY);
        st.put("--n", TokenType.NOT_EMPTY);
        st.put("--eq", TokenType.EQUAL);
        st.put("--ne", TokenType.NOT_EQUAL);
        st.put("--lt", TokenType.LOWER_THAN);
        st.put("--le", TokenType.LOWER_EQUAL);
        st.put("--ge", TokenType.GREATER_EQUAL);
        st.put("--gt", TokenType.GREATER_THAN);
        st.put("/", TokenType.DIV);

        // symbols
        st.put(";", TokenType.DOT_COMMA);
        st.put("=", TokenType.ASSIGN);
        st.put("$((", TokenType.OPEN_ARITH);
        st.put("))", TokenType.CLOSE_ARITH);
        st.put("${", TokenType.OPEN_PARAM);
        st.put("}", TokenType.CLOSE_PARAM);
        st.put("$(", TokenType.OPEN_CMD);
        st.put("(", TokenType.OPEN_PAREN);
        st.put(")", TokenType.CLOSE_PAREN);
        st.put("[", TokenType.OPEN_BRACKET);
        st.put("]", TokenType.CLOSE_BRACKET);
        
        // others
        st.put("", TokenType.VAR);
        st.put("", TokenType.STRING);
        st.put("", TokenType.NUMBER);
   
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType get(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }
}
