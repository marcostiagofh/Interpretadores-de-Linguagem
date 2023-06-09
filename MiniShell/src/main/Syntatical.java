/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import Command.Command;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Command.CommandBlock;
import Command.SetCommand;
import Value.ConstString;
import Value.StringConcat;
import Value.StringValue;
import Value.Value;
import Value.Variable;

/**
 *
 * @author marcos
 */
public class Syntatical {
        private Lexical lex;
	private Lexeme current;
        public Map<String,Variable> variables = new HashMap<>();
	public Syntatical(Lexical lex) throws IOException{
                this.lex = lex;
		this.current = lex.nextToken();
	}
        public CommandBlock start() throws IOException{
            CommandBlock cmdblock = procCommands();
            matchToken(TokenType.END_OF_FILE);
            return cmdblock;            
        }
	
        public Lexeme getCurrent() {
            return current;
        }

        public void setCurrent(Lexeme current) {
            this.current = current;
        }

	public void matchToken(TokenType type)throws IOException {
		if (type == this.current.type){
			this.current = this.lex.nextToken();
                }else {
                    if(this.current.type == TokenType.END_OF_FILE){
                        System.out.println(this.lex.line()+": Fim de arquivo inesperado");
                        System.exit(1); 
                                     
                    } else {
                        System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                        System.exit(1); 
                    }
		}
	}
        
        //<commands> ::= <command> { <command> }
        public CommandBlock procCommands() throws IOException{
            CommandBlock cmdblock = new CommandBlock(this.lex.line());
            Command c = procCommand();
            cmdblock.addCommand(c);
            while(  current.type == TokenType.EXIT ||
                    current.type == TokenType.READ ||
                    current.type == TokenType.PRINT ||
                    current.type == TokenType.PRINTLN ||
                    current.type == TokenType.SET ||
                    current.type == TokenType.EXEC ||
                    current.type == TokenType.IF ||
                    current.type == TokenType.WHILE ||
                    current.type == TokenType.FOR){
                c = procCommand();
                cmdblock.addCommand(c);
            }
            return cmdblock;
        }
        
        //<command> ::= <exit> | <read> | <print> | <set> | <exec> | <if> | <while> | <for>
        public Command procCommand() throws IOException{
            if(current.type == TokenType.EXIT)
                procExit();
            else if(current.type == TokenType.READ)
                procRead();
            else if(current.type == TokenType.PRINT ||
                    current.type == TokenType.PRINTLN){
                procPrint();
            } else if(current.type == TokenType.SET){
                SetCommand sc = procSet();
                return sc;
            } else if(current.type == TokenType.EXEC)
                procExec();
            else if(current.type == TokenType.IF)
                procIf();
            else if(current.type == TokenType.WHILE)
                procWhile();
            else if(current.type == TokenType.FOR)
                procFor();
            else{
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }
        
        //<exit> ::= exit ';'
        public void procExit() throws IOException{
            matchToken(TokenType.EXIT);
            matchToken(TokenType.DOT_COMMA);
        }
        
        //<read> ::= read <var> ';'
        public void procRead() throws IOException{
            matchToken(TokenType.READ);
            procVar();
            matchToken(TokenType.DOT_COMMA);
        }
        
        //<print> ::= (print | println) <expr> ';'
        public void procPrint() throws IOException{
            if(current.type == TokenType.PRINT){
                matchToken(TokenType.PRINT);
                procExpr();
                matchToken(TokenType.DOT_COMMA);
            }else if(current.type == TokenType.PRINTLN){
                matchToken(TokenType.PRINTLN);
                procExpr();
                matchToken(TokenType.DOT_COMMA);
            }else{
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }
        
        //<set> ::= set <var> '=' <expr> ';'
        public SetCommand procSet() throws IOException{
            matchToken(TokenType.SET);
            Variable var = procVar();
            matchToken(TokenType.ASSIGN);
            Value<?> value = procExpr();
            matchToken(TokenType.DOT_COMMA);
            SetCommand sc = new SetCommand(var,value,this.lex.line());
            return sc;
        }
        
        //<exec> ::= exec <expr> ';'
        public void procExec() throws IOException{
            matchToken(TokenType.EXEC);
            procExpr();
            matchToken(TokenType.DOT_COMMA);
        }
        
        //<if> ::= if <bool-expr> ';' then <commands> { elif <bool-expr> ';' then <commands> } [ else <commands> ] fi
        public void procIf() throws IOException{
            matchToken(TokenType.IF);
            procBoolExpr();
            matchToken(TokenType.DOT_COMMA);
            matchToken(TokenType.THEN);
            procCommands();
            while(current.type == TokenType.ELIF){
                matchToken(TokenType.ELIF);
                procBoolExpr();
                matchToken(TokenType.DOT_COMMA);
                matchToken(TokenType.THEN);
                procCommands();
            }
            if(current.type == TokenType.ELSE){
                matchToken(TokenType.ELSE);
                procCommands();
            }
            matchToken(TokenType.FI);
        }
        
        //<while> ::= while <bool-expr> ';' do <commands> done
        public void procWhile() throws IOException{
            matchToken(TokenType.WHILE);
            procBoolExpr();
            matchToken(TokenType.DOT_COMMA);
            matchToken(TokenType.DO);
            procCommands();
            matchToken(TokenType.DONE);
        }
        
        //<for> ::= for <var> in <expr> ';' do <commands> done
        public void procFor() throws IOException{
            matchToken(TokenType.FOR);
            procVar();
            matchToken(TokenType.IN);
            procExpr();
            matchToken(TokenType.DOT_COMMA);
            matchToken(TokenType.DO);
            procCommands();
            matchToken(TokenType.DONE);
        }
        
        //<expr> ::= <string-expr> { '.' <string-expr> }
        public Value<?> procExpr() throws IOException{
            StringValue sv1 = procStringExpr();
            StringValue sv2;
            String st = ((StringValue)sv1).getValue();                    
            ConstString cs = new ConstString(st,this.lex.line());
            StringConcat sc;
            if(current.type == TokenType.CONCAT){
                while(current.type == TokenType.CONCAT){
                    matchToken(TokenType.CONCAT);
                    sv2 = procStringExpr();
                    cs = new StringConcat(cs,sv2,this.lex.line());
                }
            } else {
                String st;
                if(sv1 instanceof StringValue){
                    
                    return cs;
                } else {
                    System.out.println(this.lex.line()+": Tipos inválidos");
                    System.exit(1);
                    return null;
                }
            }
            
        }
        
        //<string-expr> ::= <string> | <expansion>
        public void procStringExpr() throws IOException{
            if(current.type == TokenType.STRING)
                procString();
            else if(current.type == TokenType.OPEN_ARITH ||
                    current.type == TokenType.OPEN_PARAM ||
                    current.type == TokenType.OPEN_CMD){
                procExpansion();
            }else{
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }        
        
        //<expansion> ::= <arith-exp> | <param-exp> | <cmd-exp>
        public void procExpansion() throws IOException{
            if(current.type == TokenType.OPEN_ARITH)
                procArithExp();
            else if(current.type == TokenType.OPEN_PARAM)
                procParamExp();
            else if(current.type == TokenType.OPEN_CMD)
                procCmdExp();
            else {
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }
        //<arith-exp> ::= '$((' <int-expr> '))'
        public void procArithExp() throws IOException{
            matchToken(TokenType.OPEN_ARITH);
            procIntExpr();
            matchToken(TokenType.CLOSE_ARITH);
        }
        
        //<param-exp> ::= '${' <var> '}'
        public void procParamExp() throws IOException{
            matchToken(TokenType.OPEN_PARAM);
            procVar();
            matchToken(TokenType.CLOSE_PARAM);
        }
        
        //<cmd-exp> ::= '$(' <expr> ')'
        public void procCmdExp() throws IOException{
            matchToken(TokenType.OPEN_CMD);
            procExpr();
            matchToken(TokenType.CLOSE_PAREN);
        }
        
        //<int-expr> ::= <term> [ ('+' | '-') <term> ]
        public void procIntExpr() throws IOException{
            procTerm();
            while(  current.type == TokenType.PLUS ||
                    current.type == TokenType.MINUS ){
                
                if(current.type == TokenType.PLUS){
                    matchToken(TokenType.PLUS);
                    procTerm();
                } else {
                    matchToken(TokenType.MINUS);
                    procTerm();
                }
                
            }
        }
        
        //<term> ::= <power> [ ('*' | '/' | '%') <power> ]
        public void procTerm() throws IOException{
            procPower();
            while(  current.type == TokenType.MUL ||
                    current.type == TokenType.DIV || 
                    current.type == TokenType.MOD){
                
                if(current.type == TokenType.MUL){
                    matchToken(TokenType.MUL);
                    procPower();
                } else if(current.type == TokenType.DIV){
                    matchToken(TokenType.DIV);
                    procPower();
                } else {
                    matchToken(TokenType.MOD);
                    procPower();
                }
            
            }
        }
        
        //<power> ::= <factor> [ '**' <power> ]
        public void procPower() throws IOException{
            procFactor();
            while(current.type == TokenType.POWER){
                matchToken(TokenType.POWER);
                procPower();
            }
        }
        
        //<factor> ::= <var> | <number> | '(' <int-expr> ')'
        public void procFactor() throws IOException{
            if(current.type == TokenType.VAR){
                procVar();
            } else if(current.type == TokenType.NUMBER) {
                procNumber();
            } else if(current.type == TokenType.OPEN_PAREN){
                matchToken(TokenType.OPEN_PAREN);
                procIntExpr();
                matchToken(TokenType.CLOSE_PAREN);                
            } else {
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
            
        }
        
        //<bool-expr> ::= <clause> { ('&&' | '||') <clause> }
        public void procBoolExpr() throws IOException{
            procClause();
                while(  current.type == TokenType.AND ||
                        current.type == TokenType.OR ){
                
                if(current.type == TokenType.AND){
                    matchToken(TokenType.AND);
                    procClause();
                } else {
                    matchToken(TokenType.OR);
                    procClause();
                }
                
            }
        }
        
        //<clause> ::= '[' [ '!' ] <cmp> ']'
        public void procClause() throws IOException{
            matchToken(TokenType.OPEN_BRACKET);
            while(current.type == TokenType.NOT){
                matchToken(TokenType.NOT);
            }
            procCmp();
            matchToken(TokenType.CLOSE_BRACKET);            
        }
        
        //<cmp> ::= <unaryop> <expr> | <expr> <relop> <expr>
        public void procCmp() throws IOException{
            if( current.type == TokenType.EMPTY ||
                current.type == TokenType.NOT_EMPTY ){
                procUnaryOp();
                procExpr();
            } else if(  current.type == TokenType.STRING ||
                        current.type == TokenType.OPEN_ARITH ||
                        current.type == TokenType.OPEN_PARAM ||
                        current.type == TokenType.OPEN_CMD) {
                procExpr();
                procRelOp();
                procExpr();
            } else {    
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }
        
        //<unaryop> ::= --z | --n
        public void procUnaryOp() throws IOException{
            if( current.type == TokenType.EMPTY){
                matchToken(TokenType.EMPTY);
            } else if (current.type == TokenType.NOT_EMPTY){
                matchToken(TokenType.NOT_EMPTY);
            } else {
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }
        //<relop> ::= --eq | --ne | --lt | --le | --ge | --gt
        public void procRelOp() throws IOException{
            if( current.type == TokenType.EQUAL){
                matchToken(TokenType.EQUAL);
            } else if (current.type == TokenType.NOT_EQUAL){
                matchToken(TokenType.NOT_EQUAL);
            } else if (current.type == TokenType.LOWER_THAN){
                matchToken(TokenType.LOWER_THAN);
            } else if (current.type == TokenType.LOWER_EQUAL){
                matchToken(TokenType.LOWER_EQUAL);
            } else if (current.type == TokenType.GREATER_EQUAL){
                matchToken(TokenType.GREATER_EQUAL);
            } else if (current.type == TokenType.GREATER_THAN){
                matchToken(TokenType.GREATER_THAN);
            } else {
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
            }
        }

        //<string> ::= string
        public void procString() throws IOException{
            matchToken(TokenType.STRING);
        }

        //<var> ::= var
        public Variable procVar() throws IOException {
            matchToken(TokenType.VAR);
            Variable var = null;
            return var;
        }

        //<number> ::= number
        public void procNumber() throws IOException {
            matchToken(TokenType.NUMBER);
        }
        
}
