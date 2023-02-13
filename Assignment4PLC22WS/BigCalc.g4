/**
 * @author Lamies Abbas
 * @id 12128050
 */

grammar BigCalcProg;

expressionStatement
        : (statement)+ EOF
        ;

statement
        : (assignment ';' | expression ';' | condition)
        ;

assignment
        : VAR '=' expression                     # assignExp
        | VAR '=' Number                         # assignNum
        ;

condition
        : ('if') ('(') expression (')') statement ('else') statement
        ;

expression
        : expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | expression ('?') expression (':') expression #con
        | Number                                # num
        | VAR                                   # var
        | ('(')+ expression (')')+              # parentheses
        ;


VAR
        : [a-zA-Z] Digit*
        ;

Number
        : Digit* '.' Digit+
        | Digit+
        | Digit* '.' Digit+ [eE] [+-]* Digit+
        ;

Digit
        : [0-9]
        ;

WS      : [ \t\r\n\u000C]+ -> skip
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip
        ;
