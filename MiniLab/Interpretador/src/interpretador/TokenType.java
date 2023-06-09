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

public enum TokenType {
        UNEXPECTED_EOF ,// -2,
	INVALID_TOKEN ,//-1,
	END_OF_FILE ,//	0,
	VAR ,// 	1,
	ASSIGN ,// 	2,  =
	INPUT ,// 	3,  input
	PAR_OPEN ,// 	4,  (
	PAR_CLOSE ,// 	5,  )
	STRING ,// 	6,
	DOT_COMMA ,// 	7,  ;
	BRA_OPEN ,// 	8,  [
	BRA_CLOSE ,// 	9,  ]
	DOT ,// 	10, .
	RAND ,// 	11, rand
	COMMA ,// 	12, ,
	SHOW ,// 	13, show
	NUMBER ,// 	14,
	FOR ,// 	15, for
	SEQ ,// 	16, seq
	MINUS ,// 	17, -
	PLUS ,// 	18, +
	TIMES ,// 	19, *
	DIV ,// 	20, /
	MOD ,// 	21, %
	VALUE  ,//	22, value
	END ,// 	23, end
	ISEQ ,// 	24, iseq
	EQUAL ,// 	25, ==
	DIFF ,//	26, !=
	LOWER ,// 	27, <	
	LOWER_EQUAL ,//	28, <=
	GREATER ,//	29, >
	GREATER_EQUAL ,//30, >=
	AND ,//		31, &
	OR ,// 		32, |
	IF ,// 		33, if
	ELSE ,// 	34, else
	WHILE ,//	35, while
	OPPOSED ,// 	36, opposed
	TRANSPOSED ,// 	37, transposed
	SUM ,//		38, sum
	MUL ,//		39, mul
	NULL ,// 	40, null
	FILL ,// 	41, fill
	ID ,//		42, id
	SIZE ,// 	43, size
	ROWS ,// 	44, rows
	COLS ;// 	45, cols
}

