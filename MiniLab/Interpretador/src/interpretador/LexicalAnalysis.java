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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable {
    private int line;
    private PushbackInputStream input;
    private final SymbolTable st ;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            this.input = new PushbackInputStream(new FileInputStream(filename));
        } catch (Exception e) {
            throw new LexicalException("Unable to open file");
        }

        this.line = 1;
        this.st = new SymbolTable();
    }

    @Override
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

        // HINT: read a char.
        // int c = input.read();

        // HINT: unread a char.
        // if (c != -1)
        //     input.unread(c);
         
        int estado = 1;
		int c;
		while(estado!= 8) {
			c = input.read();
                        switch(estado) {
				case 1:
                                {
                                    	if (c == -1) {
                                                return lex;
                                        } else if (c == '\n'){
                                                this.line++;
                                        } else if (c == ' ' | c == '\r' | c == '\t'){
                                        } else if (c == '#'){
						estado = 2;     
                                        } else if (Character.isDigit(c)) {
						lex.token += (char) c;
						estado = 3;					                                   
                                        } else if (c == '!'){
						lex.token += (char) c;
						estado = 4;                                                        
                                        } else if (c == '=' | c =='<' | c == '>'){
						lex.token += (char) c;
						estado = 5;
					} else if (Character.isLetter(c)) {
                                                if(c == 'ÿ')
                                                    return lex;
						lex.token += (char) c;
						estado = 6;		
                                        } else if (c == '"'){
                                                estado = 7;                                        
                                        } else if (c == ';' | c == '.' | c == ',' | c == '(' | c == ')' | c == '[' | c == ']' | c == '&' | c == '|' | c == '+' | c == '-' | c == '*' | c == '/' | c == '%'){
 						lex.token += (char) c;
						estado = 8;                      
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
                                case 2:
                                {
                                        while(c != '\n'){
                                            c = input.read();
                                            if (c == -1){ 
                                                input.unread(c);
                                                break;
                                            }
                                        }
                                        this.line++;
                                        estado = 1;
                                }
                                break;       
				case 3:
					if (Character.isDigit(c)) {
						lex.token += (char) c;
					} else {
						//if (c == -1) 
                                                    input.unread(c);
						lex.type = TokenType.NUMBER;
                                                System.out.println("Token: "+lex.token+" "+lex.type);
                                                return lex;
					}
				break;
				case 4:
					if (c == '=') {
						lex.token += (char) c;
						estado = 8;
					} else {
						if (c == -1) {
                                                        input.unread(c);
                                                        lex.type = TokenType.UNEXPECTED_EOF;
                                                        System.out.println("Token: "+lex.token+" "+lex.type);
                                                        System.out.println(this.line+": Fim de arquivo inesperado");						
                                                        System.exit(1);
                                                        return null;	
						} else {
							lex.token += (char) c;
                                                        lex.type = TokenType.INVALID_TOKEN;
                                                        System.out.println("Token: "+lex.token+" "+lex.type);
                                                        System.out.println(this.line+": Lexema inválido ["+lex.token+"]");						
                                                        System.exit(1);
                                                        return null;		
						}					
					}
				break;
				case 5:
					if (c == '=') {
                                            lex.token += (char) c;
                                            estado = 8;		
					} else {
                                            if (c == -1) input.unread(c);
                                            input.unread(c);
                                            estado = 8;						
					}
				break;
                                case 6:
                                        if (Character.isLetter(c) | Character.isDigit(c)) {
                                            lex.token += (char) c;
                                        } else {                                            
                                            input.unread(c);
                                            lex.type = TokenType.VAR;
                                            estado = 8;
                                        }
                                break;
                                case 7:
                                        if (c != '"'){
                                            lex.token += (char) c;
                                            if(c == -1){
                                                lex.type = TokenType.UNEXPECTED_EOF;
                                                System.out.println("Token: "+lex.token+" "+lex.type);
                                                System.out.println(this.line+": Fim de arquivo inesperado");						
                                                System.exit(1);
                                                return null;
                                            }
                                        } else {
                                            lex.type = TokenType.STRING;
                                            System.out.println("Token: "+lex.token+" "+lex.type);
                                            estado = 8;
                                        }
				default:
				break;
			}
		}
                
		if (st.contains(lex.token)){
                        lex.type = st.get(lex.token);
                        System.out.println("Token: "+lex.token+" "+lex.type);			
		} else {
                        if(lex.type != TokenType.STRING){
                            lex.type = TokenType.VAR;
                            System.out.println("Token: "+lex.token+" "+lex.type);
                        }
		}
                
        return lex;
    }

}