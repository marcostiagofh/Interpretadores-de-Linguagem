package main;

import main.Lexeme;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class Lexical implements AutoCloseable {

    private int line;
    private PushbackInputStream input;
    private final SymbolTable st ;
    
    public Lexical(String filename) throws LexicalException {
        try {
            this.input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        this.line = 1;
        this.st = new SymbolTable();
    }

    public void close() {
        try {
            input.close();
        } catch (Exception e) {
            // ignore
        }
    }

    public int line() {
        return this.line;
    }

    public Lexeme nextToken() throws IOException {
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);

        // TODO: implement me.

        // HINT: read a char
        // int c = input.read();

        // HINT: unread a char
        // if (c != -1)
        //     input.unread(c);

        int estado = 1;
        int c;
        while(estado!= 14) {
                c = input.read();
                switch(estado) {
                        case 1:
                        {
                            if(c == ' ' || c == '\b' || c == '\t' || c == '\r'){

                            } else if(c == '\n'){
                                this.line++;
                            } else if (c == '#'){ 
                                estado = 2;
                            }else if (c == '-'){
                                lex.token += (char) c;
                                estado = 3;
                            } else if (c == '*'){
                                lex.token += (char) c;
                                estado = 5;
                            }else if (c == ')'){
                                lex.token += (char) c;
                                estado = 6;
                            }else if (c == '&'){
                                lex.token += (char) c;
                                estado = 7;
                            }else if (c == '|'){
                                lex.token += (char) c;
                                estado = 8;
                            }else if (c == '$'){
                                lex.token += (char) c;
                                estado = 9;
                            }else if (Character.isDigit(c)){
                                lex.token += (char) c;
                                estado = 11;
                            }else if (Character.isLetter(c)){
                                lex.token += (char) c;
                                estado = 12;
                            }else if (c == '"'){
                                estado = 13;
                            }else if (c == ';' || c == '=' || c == '(' || c == '[' || c == ']' || c == '}' || c == '.' || c == '+' || c == '/' || c == '%' || c == '!'){
                                lex.token += (char) c;
                                estado = 14;
                            }else{
                                lex.token += (char) c;
                                lex.type = TokenType.INVALID_TOKEN;
                                System.out.println("Token: "+lex.token+" "+lex.type);
                                System.out.println(this.line+": Lexema inválido ["+lex.token+"]");						
                                System.exit(1);
                                return null;
                            }
                            break;
                        }
                        case 2:
                        {
                            if(c != '\n'){

                            } else {
                                this.line++;
                                estado = 1;
                            }
                            break;
                        }
                        case 3:
                        {
                            if(c != '-'){
                                input.unread(c);
                                estado = 14;
                            } else {
                                lex.token += (char) c;
                                estado = 4;
                            }
                            break;
                        }
                        case 4:
                        {
                            if(Character.isLetter(c)){
                                lex.token += (char) c;
                            } else {
                                input.unread(c);
                                estado = 14;
                            }
                            break;
                        }
                        case 5:
                        {
                            if(c != '*')
                                input.unread(c);
                            else
                                lex.token += (char) c;
                            estado = 14;
                            break;
                        }
                        case 6:
                        {
                            if(c != ')')
                                input.unread(c);
                            else
                                lex.token += (char) c;
                            estado = 14;
                            break;
                        }
                        case 7:
                        {
                            if(c == '&'){
                                lex.token += (char) c;
                                estado = 14;
                            } else {
                                lex.token += (char) c;
                                lex.type = TokenType.INVALID_TOKEN;
                                System.out.println("Token: "+lex.token+" "+lex.type);
                                System.out.println(this.line+": Lexema inválido ["+lex.token+"]");						
                                System.exit(1);
                                return null;
                            }
                            break;
                        }
                        case 8:
                        {

                            if(c == '|'){
                                lex.token += (char) c;
                                estado = 14;
                            }else {
                                lex.token += (char) c;
                                lex.type = TokenType.INVALID_TOKEN;
                                System.out.println("Token: "+lex.token+" "+lex.type);
                                System.out.println(this.line+": Lexema inválido ["+lex.token+"]");						
                                System.exit(1);
                                return null;
                            }
                            break;
                        }
                        case 9:
                        {
                            if(c == '('){
                                lex.token += (char) c;
                                estado = 10;
                            }else if(c == '{'){
                                lex.token += (char) c;
                                estado = 14;
                            }else {
                                lex.token += (char) c;
                                lex.type = TokenType.INVALID_TOKEN;
                                System.out.println("Token: "+lex.token+" "+lex.type);
                                System.out.println(this.line+": Lexema inválido ["+lex.token+"]");						
                                System.exit(1);
                                return null;
                            }
                            break;
                        }
                        case 10:
                        {
                            if(c != '(')
                                input.unread(c);
                            else
                                lex.token += (char) c;
                            estado = 14;                                    
                            break;
                        }
                        case 11:
                        {
                            if(Character.isDigit(c)){
                                lex.token += (char) c;
                            } else {
                                input.unread(c);
                                lex.type = TokenType.NUMBER;
                                estado = 14;
                            }
                            break;
                        }
                        case 12:
                        {                                    
                            if(Character.isLetter(c)){
                                lex.token += (char) c;
                            } else {
                                input.unread(c);
                                lex.type = TokenType.VAR;
                                estado = 14;
                            }
                            break;
                        }
                        case 13:
                        {
                            if(c != '"'){
                                lex.token += (char) c;
                            } else {
                                lex.type = TokenType.STRING;
                                estado = 14;
                            }
                            break;
                        }
                        default: break;
                }
        }
        if(st.contains(lex.token))
            lex.type = st.get(lex.token);		

        System.out.println("Token: "+lex.token+" "+lex.type);	

        return lex;
    }
}
