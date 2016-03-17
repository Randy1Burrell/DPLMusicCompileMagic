/*********************************************************************** 
Syntax Analysis - Interface for the SyntaxAnalysis class.
**********************************************************************/
#if !defined(_SA_)
#define _SA_


#include <iostream>
#include <string>
#include "LexicalAnalysis.h"

using namespace std;
/*
	grammar rules:
	
	EXPR#1 ::= LEFT_BRACKET BACK_SLASH INDENTIFIER RIGHT_BRACKET
	EXPR#2 ::= LEFT_BRACKET INDENTIFIER RIGHT_BRACKET
	EXPR#3 ::= EXPR#1 TERM EXPR#2

	...
	TERM ::= INDENTIFIER | INTEGER | SYMBOL
OR
	EXPR#1 ::= [INDENTIFIER]
	EXPR#2 ::= [/INDENTIFIER]
	EXPR#3 ::= [INDENTIFIER] INDENTIFIER {+ INDENTIFIER | INTEGER | SYMBOL} SYMBOL [/INDENTIFIER]
	
  */

class SyntaxAnalysis  
{
public:
	void syntax();
	void setNewInput(string in);
	void tagSyntax();
	SyntaxAnalysis(string in);
	virtual ~SyntaxAnalysis();
	void body();

private:
	void term();
	void expr();
	void sterm(); //start of tag
	void eterm(); //end of tag


private:
	LexicalAnalysis LA;
	int nextToken;
};


#endif