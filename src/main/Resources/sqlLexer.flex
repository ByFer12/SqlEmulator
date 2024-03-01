
/* codigo de usuario */
package inicio.flexycup;

import java_cup.runtime.*;

%% //separador de area

/* opciones y declaraciones de jflex */

%class SqlLexer
%cup
%line
%column
%cupsym Tokens

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator} | [ \t\f]

/* integer literals */
entero = [0-9]*
letters=[a-zA-Z]


%{
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }


  private void error(String message) {
    System.out.println("Error en linea line "+(yyline+1)+", columna "+(yycolumn+1)+" : "+message);
  }
%}

%% // separador de areas

/* reglas lexicas */
<YYINITIAL>{

	"SELECCIONAR"		{ return symbol(sym.P_COMA); }

	"EN"	{ return symbol(sym.ENTERO, new Integer(yytext()));}

	"FILTRAR"		{ return symbol(sym.SUMA);}

	"="		{ return symbol(sym.RESTA);}

    ","		{ return symbol(sym.DIVISION);}

    "*"		{ return symbol(sym.MULTIPLICACION);}

	{WhiteSpace} 	{/* ignoramos */}



/* error fallback */
[^]                              /*{ throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }*/
			{error("Simbolo invalido <"+ yytext()+">");}
<<EOF>>                 { return symbol(sym.EOF); }
      }