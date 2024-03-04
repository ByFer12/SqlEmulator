
/* codigo de usuario */
package inicio.flexycup;

import java_cup.runtime.*;import java.util.LinkedList;

%% //separador de area

%{
    public static LinkedList<Errors> erroresLexicos=new LinkedList<>();
%}

/* opciones y declaraciones de jflex */
%public
%class SqlLexer
%cup
%line
%column

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator} | [ \t\f]

/* integer literals */
entero = [0-9]
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

	"SELECCIONAR" { return symbol(sym.SELEC,yytext()); }
    "INSERTAR" { return symbol(sym.INSERT,yytext()); }
    "VALORES" { return symbol(sym.VALORES,yytext()); }

	"EN"	{ return symbol(sym.EN, yytext());}

	"FILTRAR"		{ return symbol(sym.FILT, yytext());}
     "AND"     {return symbol(sym.AND,yytext());}
      "OR"     {return symbol(sym.OR,yytext());}

    {letters}({letters}|{entero})* {return symbol(sym.ID,yytext());}

	"="		{ return symbol(sym.IGUAL,yytext());}

    ","		{ return symbol(sym.COMA,yytext());}

    ";"     {return symbol(sym.PCOMA,yytext());}

    "*"		{ return symbol(sym.AST, yytext());}
    "."     {return symbol(sym.PUNTO,yytext());}
    "<="     {return symbol(sym.MENIGUAL,yytext());}
    ">="     {return symbol(sym.MAYIGUAL,yytext());}
    ">"     {return symbol(sym.MAY,yytext());}
    "<"     {return symbol(sym.MEN,yytext());}
    "("     {return symbol(sym.PARENA,yytext());}
    ")"     {return symbol(sym.PARENC,yytext());}



    {entero}+ {return symbol(sym.NUM, yytext());}
    \"[^\"]*\"  {return symbol(sym.STRING,yytext());}



	{WhiteSpace} 	{/* ignoramos */}

    [^] {System.out.println("Error lexico: ");
      Errors e=new Errors(yytext(),"Error Lexico", "Este token no es reconocido",yyline,yycolumn);
      erroresLexicos.add(e);
      }


      }