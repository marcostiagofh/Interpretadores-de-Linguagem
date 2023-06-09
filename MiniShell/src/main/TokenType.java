package main;

public enum TokenType {
    // special tokens
    INVALID_TOKEN,
    UNEXPECTED_EOF,
    END_OF_FILE,

    // keywords
    EXIT,     // exit
    READ,     // read
    PRINT,    // print
    PRINTLN,  // println
    SET,      // set
    EXEC,     // exec
    IF,       // if
    THEN,     // then
    ELIF,     // elif
    ELSE,     // else
    FI,       // fi
    WHILE,    // while
    DO,       // do
    DONE,     // done
    FOR,      // for
    IN,       // in

    // operators
    CONCAT,          // .
    PLUS,            // +
    MINUS,           // -
    MUL,             // *
    DIV,             // /
    MOD,             // %
    POWER,           // **
    AND,             // &&
    OR,              // ||
    NOT,             // !
    EMPTY,           // --z
    NOT_EMPTY,       // --n
    EQUAL,           // --eq
    NOT_EQUAL,       // --ne
    LOWER_THAN,      // --lt
    LOWER_EQUAL,     // --le
    GREATER_EQUAL,   // --ge
    GREATER_THAN,    // --gt

    // symbols
    DOT_COMMA,     // ;
    ASSIGN,        // =
    OPEN_ARITH,    // $((
    CLOSE_ARITH,   // ))
    OPEN_PARAM,    // ${
    CLOSE_PARAM,   // }
    OPEN_CMD,      // $(
    OPEN_PAREN,    // (
    CLOSE_PAREN,   // )
    OPEN_BRACKET,  // [
    CLOSE_BRACKET, // ]

    // others
    VAR,          // variable
    STRING,       // string
    NUMBER,       // number
}
