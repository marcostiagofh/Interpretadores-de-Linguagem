/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpretador;

import commands.AssignCommand;
import commands.Command;
import commands.CommandBlock;
import commands.ForCommand;
import commands.IfCommand;
import commands.ShowCommand;
import commands.WhileCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import op.RelOp;
import op.BoolOp;
import op.IntOp;
import values.CompareBoolValue;
import values.ConstIntValue;
import values.ConstStringValue;
import values.DualBoolExpr;
import values.BoolValue;
import values.ColsIntMatrixValue;
import values.DualIntExpr;
import values.FillMatrixValue;
import values.IdMatrixValue;
import values.InputIntValue;
import values.MatrixValue;
import values.MulMatrixValue;
import values.NullMatrixValue;
import values.OpposedMatrixValue;
import values.RandMatrixValue;
import values.RowsIntMatrixValue;
import values.SeqMatrixValue;
import values.SizeIntMatrixValue;
import values.StringConcat;
import values.SumMatrixValue;
import values.TransposedMatrixValue;
import values.Value;
import values.ValueIntMatrixValue;
import values.Variable;


/**
 *
 * @author Marcos
 */

public class SyntaticalAnalysis {

	private LexicalAnalysis lex;
	private Lexeme current;
        public Map<String,Variable> variables = new HashMap<>();
	public SyntaticalAnalysis(LexicalAnalysis lex) throws IOException{
                this.lex = lex;
		this.current = lex.nextToken();
	}
        
        public CommandBlock start() throws IOException{
            CommandBlock cmdblock = procStatements();
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

        private Variable procVar() throws IOException{
                String varName = this.current.token();
                matchToken(TokenType.VAR);
                Variable var = this.variables.get(varName);
                if(var == null) {
                    var = new Variable(varName, this.lex.line());
                    this.variables.put(varName,var);
                }
                return var;
	}

	private ConstIntValue procNumber() throws IOException{
                String tmp = this.current.token();
                matchToken(TokenType.NUMBER);
                int n = Integer.parseInt(tmp);
                ConstIntValue c = new ConstIntValue(n,this.lex.line());
                return c;
        }

	private ConstStringValue procString() throws IOException{
                String varName = this.current.token();
                matchToken(TokenType.STRING);
                ConstStringValue c = new ConstStringValue(varName,this.lex.line());
                return c;
	}

	// <input> ::= input '(' <text> ')'
	private InputIntValue procInput() throws IOException{
                matchToken(TokenType.INPUT);
                matchToken(TokenType.PAR_OPEN);
		Value<?> value = procText();
                matchToken(TokenType.PAR_CLOSE);
                InputIntValue iiv = new InputIntValue(value, this.lex.line());
                return iiv;
        }

        // <if> ::= if <boolexpr> <statements> [ else <statements> ] end
	private IfCommand procIf() throws IOException{
                matchToken(TokenType.IF);
		BoolValue expr = procBooleanExpr();
		CommandBlock then = procStatements();
		if(this.current.type == TokenType.ELSE){
			matchToken(TokenType.ELSE);
			CommandBlock elsecmd = procStatements();
                        IfCommand ic = new IfCommand(then,elsecmd,expr,lex.line());
                        return ic;
                }
		matchToken(TokenType.END);
                IfCommand ic = new IfCommand(then,expr,lex.line());
                return ic;
	}

	// <statements> ::= <statement> { < statement > }
	private CommandBlock procStatements() throws IOException{
                Command cmd = procStatement();
                List<Command> commands = new ArrayList<>();
                commands.add(cmd);

		while ( current.type == TokenType.SHOW || 
                        current.type == TokenType.VAR ||
                        current.type == TokenType.IF || 
                        current.type == TokenType.WHILE || 
                        current.type == TokenType.FOR){
                                      
                        cmd = procStatement();
                        commands.add(cmd);
		}
		CommandBlock cmdblock = new CommandBlock(commands,lex.line());
                return cmdblock;
	}

	// <statement> ::= <show> | <assign> | <if> | <while> | <for>
	private Command procStatement() throws IOException{
                switch (current.type) {
                case SHOW:
                    ShowCommand sc = procShow();
                    return sc;
                case VAR:
                    AssignCommand ac = procAssign();
                    return ac;
                case IF:
                    IfCommand ic = procIf();
                    return ic;
                case WHILE:
                    WhileCommand wc = procWhile();
                    return wc;
                case FOR:
                    ForCommand fc = procFor();
                    return fc;
                default:
                    System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                    System.exit(1);
                    return null;
            }

	}

	// <show> ::= show '(' <text> ')' ';'
	private ShowCommand procShow() throws IOException{
            matchToken(TokenType.SHOW);
            matchToken(TokenType.PAR_OPEN);
            Value<?> value = procText();
            matchToken(TokenType.PAR_CLOSE);
            matchToken(TokenType.DOT_COMMA);
            ShowCommand sc = new ShowCommand(value, lex.line());
            return sc;
	}

	// <assign> ::= <var> '=' <expr> ';'
	private AssignCommand procAssign() throws IOException{
                Variable var = procVar();            
                matchToken(TokenType.ASSIGN);
                Value<?> value = procExpr();
                matchToken(TokenType.DOT_COMMA);

                AssignCommand ac = new AssignCommand(var, value, lex.line());
                return ac;
	}

	// <while> ::= while <boolexpr> <statements> end
	private WhileCommand procWhile() throws IOException {
            matchToken(TokenType.WHILE);
            BoolValue expr = procBooleanExpr();
            CommandBlock cmdblock = procStatements();
            matchToken(TokenType.END);
            
            WhileCommand wc = new WhileCommand(expr,cmdblock,lex.line());
            return wc;
        }

	// <for> ::= for <var> '=' <value> <statements> end
	private ForCommand procFor() throws IOException {
            matchToken(TokenType.FOR);
            Variable var = procVar();
            matchToken(TokenType.ASSIGN);
            Value<?> value = procValue();           
            CommandBlock cmdblock = procStatements();
            matchToken(TokenType.END);
            
            ForCommand fc = new ForCommand(var,value,cmdblock,lex.line());
            return fc;
	}

	//<text> ::= { <expr> | <string> }
	private Value<?> procText() throws IOException {
            Value<?> ret = null;
            while(  current.type == TokenType.STRING ||
                    current.type == TokenType.NUMBER ||
                    current.type == TokenType.INPUT ||
                    current.type == TokenType.VAR ||
                    current.type == TokenType.BRA_OPEN ||
                    current.type == TokenType.PAR_OPEN) {
                @SuppressWarnings("UnusedAssignment")
                Value<?> next = null;
                if (current.type == TokenType.STRING) {
                    next = procString();
                } else {
                    next = procExpr();
                }
                
                if (ret == null)
                    ret = next;
                else {
                    ret = new StringConcat(ret, next, lex.line());
                }
            }
            return ret;
	}

	// <boolexpr> ::= <expr> <boolop> <expr> { ('&' | '|') <boolexpr> }
	private BoolValue procBooleanExpr() throws IOException {
                Value<?> left = procExpr();
                RelOp rp = procBooleanOperation();
                Value<?> right = procExpr();

                CompareBoolValue bv = new CompareBoolValue(left, right, rp, lex.line());
               
                BoolValue tmp;
                BoolOp bo;
                
                    switch (current.type) {
                case AND:
                {
                    matchToken(TokenType.AND);
                    bo = BoolOp.And;
                    tmp = procBooleanExpr();
                    
                    DualBoolExpr dbe = new DualBoolExpr(bv, tmp, bo, lex.line());
                    
                    while(  current.type == TokenType.AND ||
                            current.type == TokenType.OR ){
                        if(current.type == TokenType.AND){
                            matchToken(TokenType.AND);
                            bo = BoolOp.And;
                        } else {
                            matchToken(TokenType.OR);
                            bo = BoolOp.Or;
                        }
                        
                        tmp = procBooleanExpr();
                        
                        dbe = new DualBoolExpr(dbe, tmp, bo, lex.line());
                    }
                    return dbe;
                    
                }
                case OR:
                {
                    matchToken(TokenType.OR);
                    bo = BoolOp.Or;
                    tmp = procBooleanExpr();
                    
                    DualBoolExpr dbe = new DualBoolExpr(bv, tmp, bo, lex.line());
                    
                    while(  current.type == TokenType.AND ||
                            current.type == TokenType.OR ){
                        if(current.type == TokenType.AND){
                            matchToken(TokenType.AND);
                            bo = BoolOp.And;
                        } else {
                            matchToken(TokenType.OR);
                            bo = BoolOp.Or;
                        }
                        
                        tmp = procBooleanExpr();
                        
                        dbe = new DualBoolExpr(dbe, tmp, bo, lex.line());
                    }
                    return dbe;
                }
                default:
                    return bv;
            }
	}

	// <boolop> ::= '==' | '!=' | '<' | '>' | '<=' | '>='
	private RelOp procBooleanOperation() throws IOException {
            switch (current.type) {
                case EQUAL:
                    matchToken(TokenType.EQUAL);
                    return RelOp.EQUAL;
                case DIFF:
                    matchToken(TokenType.DIFF);
                    return RelOp.DIFF;
                case LOWER:
                    matchToken(TokenType.LOWER);
                    return RelOp.LOWER;
                case GREATER:
                    matchToken(TokenType.GREATER);
                    return RelOp.GREATER;
                case LOWER_EQUAL:
                    matchToken(TokenType.LOWER_EQUAL);
                    return RelOp.LOWER_EQUAL;
                case GREATER_EQUAL:
                    matchToken(TokenType.GREATER_EQUAL);
                    return RelOp.GREATER_EQUAL;
                default:
                    System.out.println(this.lex.line()+": Lexema não esperado ["+current.token+"]");
                    System.exit(1);
                    return null;
            }
	}

        // <term> ::= <factor> [ ('*' | '/' | '%') <factor> ]
	private Value<?> procTerm() throws IOException {
            Value<?> value = procFactor();
                    
	    switch (current.type) {
                case TIMES:
                {
                    matchToken(TokenType.TIMES);
                    Value<?> value2 = procFactor();                    
                    DualIntExpr die = new DualIntExpr(value,value2,IntOp.Mul,lex.line());
                    return die;
                }
                case DIV:
                {
                    matchToken(TokenType.DIV);
                    Value<?> value2 = procFactor();                    
                    DualIntExpr die = new DualIntExpr(value,value2,IntOp.Div,lex.line());
                    return die;
                }
                case MOD:
                {
                    matchToken(TokenType.MOD);
                    Value<?> value2 = procFactor();                    
                    DualIntExpr die = new DualIntExpr(value,value2,IntOp.Mod,lex.line());
                    return die;
                }
                default:
                    return value;
            }
	}
        
        // <factor> ::= (<number> | <input> | <value>) | '(' <expr> ')'
	private Value<?> procFactor() throws IOException {
                switch (current.type) {
                case NUMBER:
                    ConstIntValue civ = procNumber();
                    return civ;
                case INPUT:
                    InputIntValue iiv = procInput();
                    return iiv;
                case VAR:
                case BRA_OPEN:
                {
                    Value<?> value = procValue();
                    return value;
                }
                default:
                {
                    matchToken(TokenType.PAR_OPEN);
                    Value<?> value = procExpr();
                    matchToken(TokenType.PAR_CLOSE);
                    return value;
                }
            }
	}

	// <opposed> ::= opposed '(' ')'
	private OpposedMatrixValue procOpposed(Value<?> mv) throws IOException {
               matchToken(TokenType.OPPOSED);
               matchToken(TokenType.PAR_OPEN);
               matchToken(TokenType.PAR_CLOSE);
               
               OpposedMatrixValue omv = new OpposedMatrixValue(mv,lex.line());
               return omv;
        }

	// <transposed> ::= transposed '(' ')'
	private TransposedMatrixValue procTransposed(Value<?> mv) throws IOException {
               matchToken(TokenType.TRANSPOSED);
               matchToken(TokenType.PAR_OPEN);
               matchToken(TokenType.PAR_CLOSE);
               
               TransposedMatrixValue tmv = new TransposedMatrixValue(mv,lex.line());
               return tmv;
	}

	// <sum> ::= sum '('<expr>')'
	private SumMatrixValue procSum(Value<?> m1) throws IOException{
                matchToken(TokenType.SUM);
                matchToken(TokenType.PAR_OPEN);
                Value<?> m2;
                
                switch (current.type) { 
                case VAR:
                    m2 = procVar();
                    break;
                case BRA_OPEN:
                    m2 = procGen();
                    break;
                default:
                    System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                    System.exit(1);
                    return null;
            }
                matchToken(TokenType.PAR_CLOSE);

                SumMatrixValue smv = new SumMatrixValue(m1, m2, lex.line());
                return smv;
	}

	// <mul> ::= mul '(' <value> | <expr>')'
	private MulMatrixValue procMul(Value<?> m) throws IOException {
                matchToken(TokenType.MUL);
                matchToken(TokenType.PAR_OPEN);

                @SuppressWarnings("UnusedAssignment")
                MulMatrixValue mmv = null;
                Value<?> value;
                switch (current.type) {
                case NUMBER:
                    value = procNumber();
                    mmv = new MulMatrixValue(m, value, lex.line());
                    break;
                case INPUT:
                    value = procInput();
                    mmv = new MulMatrixValue(m, value, lex.line());
                    break;
                case VAR:
                    value = procVar();
                    mmv = new MulMatrixValue(m, value, lex.line());
                    break;
                case BRA_OPEN:
                    value = procGen();
                    mmv = new MulMatrixValue(m, value, lex.line());
                    break;
                default:
                    System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                    System.exit(1);
                    return null;
                }
                matchToken(TokenType.PAR_CLOSE);
                return mmv;
            }

	// <gen> ::= '[' ']' '.' (<null> | <fill> | <rand> | <id> | <seq> | <iseq>)
	private MatrixValue procGen() throws IOException {
            matchToken(TokenType.BRA_OPEN);
            matchToken(TokenType.BRA_CLOSE);
            matchToken(TokenType.DOT);
            switch (current.type) {
                case NULL:
                    NullMatrixValue nmv = procNull();
                    return nmv;
                case FILL:
                    FillMatrixValue fmv = procFill();
                    return fmv;
                case RAND:
                    RandMatrixValue rmv = procRand();
                    return rmv;
                case ID:
                    IdMatrixValue imv = procId();
                    return imv;
                case SEQ:
                {
                    SeqMatrixValue smv = procSeq();
                    return smv;
                }
                case ISEQ:
                {
                    SeqMatrixValue smv = procIseq();
                    return smv;
                }
                default:
                    System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                    System.exit(1);
                    return null;
            }
	}

	// <null> ::= null '(' <expr> ',' <expr> ')'
	private NullMatrixValue procNull() throws IOException{
            matchToken(TokenType.NULL);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            
            NullMatrixValue nmv = new NullMatrixValue(r,c,lex.line());
            return nmv;
	}

	// <fill> ::= fill '(' <expr> ',' <expr> ',' <expr> ')'
	private FillMatrixValue procFill() throws IOException {
            matchToken(TokenType.FILL);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> v = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            
            FillMatrixValue fmv = new FillMatrixValue(r, c, v, lex.line());
            return fmv;
	}

	// <rand> ::= rand '(' <expr> ',' <expr> ')'
	private RandMatrixValue procRand() throws IOException{
            matchToken(TokenType.RAND);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            
            RandMatrixValue rmv = new RandMatrixValue(r, c, lex.line());
            return rmv;
	}

	// <id> ::= id '(' <expr> ',' <expr> ')'
	private IdMatrixValue procId() throws IOException {
            matchToken(TokenType.ID);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            
            IdMatrixValue imv = new IdMatrixValue(r,c,lex.line());
            return imv;
	}        

	// <seq> ::= seq '(' <expr> ',' <expr> ')'
	private SeqMatrixValue procSeq() throws IOException {
            matchToken(TokenType.SEQ);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            SeqMatrixValue smv = new SeqMatrixValue(false,r,c,lex.line());
            return smv;
	}

	// <iseq> ::= iseq '(' <expr> ',' <expr> ')'
	private SeqMatrixValue procIseq() throws IOException {
            matchToken(TokenType.ISEQ);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();            
            matchToken(TokenType.PAR_CLOSE);
            SeqMatrixValue smv = new SeqMatrixValue(true,r,c,lex.line());
            return smv;           
	}

        // <value> ::= ( <gen> | <var> ) { '.' (<opposed> | <transposed> | <sum> | <mul>) } [ '.' (<size> | <rows> | <cols> | <val>) ]
        private Value<?> procValue() throws IOException {
            @SuppressWarnings("UnusedAssignment")
            Value<?> mv = null;
            if (current.type == TokenType.BRA_OPEN)
                mv = procGen();
            else if (current.type == TokenType.VAR)
                mv = procVar();
            else {
                System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                System.exit(1);
                return null;
            }
            
            while (current.type == TokenType.DOT) {
                matchToken(TokenType.DOT);

                if (current.type == TokenType.OPPOSED) {
                    mv = procOpposed(mv);
                } else if (current.type == TokenType.TRANSPOSED) {
                    mv = procTransposed(mv);
                } else if (current.type == TokenType.SUM) {
                    mv = procSum(mv);
                } else if (current.type == TokenType.MUL) {
                    mv = procMul(mv);
                } else if (current.type == TokenType.ROWS) {
                    mv = procRows(mv);
                    break;
                } else if (current.type == TokenType.SIZE) {
                    mv = procSize(mv);
                    break;
                } else if (current.type == TokenType.COLS){
                    mv = procCols(mv);
                    break;
                } else if (current.type == TokenType.VALUE){
                    mv = procVal(mv);
                    break;
                } else {
                    System.out.println(this.lex.line()+": Lexema não esperado ["+this.current.token+"]");
                    System.exit(1);
                    return null;
                }
            }
            return mv;
        }
        
        // <expr> ::= <term> [ ('+' | '-') <term> ]
        private Value<?> procExpr() throws IOException{
            Value<?> value = procTerm();
            
            switch (current.type) {
                case PLUS:
                {
                    matchToken(TokenType.PLUS);
                    Value<?> value2 = procTerm();
                    DualIntExpr die = new DualIntExpr(value,value2,IntOp.Add,lex.line());
                    return die;
                }
                case MINUS:
                {
                    matchToken(TokenType.MINUS);
                    Value<?> value2 = procTerm();
                    DualIntExpr die = new DualIntExpr(value,value2,IntOp.Sub,lex.line());
                    return die;
                }
                default:
                    return value;
            }
        }

        
        public void showError(){
            if(current.type == TokenType.UNEXPECTED_EOF){
                    //imprimir erro 1
            } else {
                    //imprimir erro 2
            }
            System.exit(1);
        }
        
        // <size> ::= size '(' ')'
        private SizeIntMatrixValue procSize(Value<?> mv) throws IOException {
           matchToken(TokenType.SIZE);
           matchToken(TokenType.PAR_OPEN);
           matchToken(TokenType.PAR_CLOSE);
           
           SizeIntMatrixValue simv = new SizeIntMatrixValue(mv,lex.line());
           return simv;
        }

        // <cols> ::= cols '(' ')'
        private ColsIntMatrixValue procCols(Value<?> mv) throws IOException {
           matchToken(TokenType.COLS);
           matchToken(TokenType.PAR_OPEN);
           matchToken(TokenType.PAR_CLOSE);
           
           ColsIntMatrixValue cimv = new ColsIntMatrixValue(mv,lex.line());
           return cimv;
        }

        // <rows> ::= rows '(' ')'
        private RowsIntMatrixValue procRows(Value<?> mv) throws IOException {
           matchToken(TokenType.ROWS);
           matchToken(TokenType.PAR_OPEN);
           matchToken(TokenType.PAR_CLOSE);
           
           RowsIntMatrixValue rimv = new RowsIntMatrixValue(mv,lex.line());
           return rimv;
        }
        
        // <val> ::= value '(' <expr> ',' <expr> ')'
        private ValueIntMatrixValue procVal(Value<?> mv) throws IOException{
            matchToken(TokenType.VALUE);
            matchToken(TokenType.PAR_OPEN);
            Value<?> r = procExpr();
            matchToken(TokenType.COMMA);
            Value<?> c = procExpr();
            matchToken(TokenType.PAR_CLOSE);
            
            ValueIntMatrixValue vimv = new ValueIntMatrixValue(mv,r,c,lex.line());
            return vimv;
        }
}

