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

public class Lexeme {

    public String token;
    public TokenType type;

    public Lexeme(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public String token() {
        //System.out.println("11 - Entrou na funcao lex.token()");
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType type() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
    

}
