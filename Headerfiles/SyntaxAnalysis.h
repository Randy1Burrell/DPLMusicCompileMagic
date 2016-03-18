#if !defined(_SA_)
#define _SA_


#include <iostream>
#include <string>
#include "LexicalAnalysis.h"

using namespace std;
/*
	STTAG ::= LEFT_Bracket IDENTIFIER RIGHT_Bracket
	ENTAG ::= LEFT_Bracket BACK_SLASH IDENTIFIER RIGHT_Bracket
	EXPR ::= (STTAG IDENTIFIER {+ IDENTIFIER} ENTAG )| (STTAG SENT {+ SENT} ENTAG)
	
	SENT ::= IDENTIFIER {+ SENT} (PERIOD | QUESTION_MARK | EXCLAMATION)
  */

class SyntaxAnalysis  
{
public:
	void syntax();
	void setNewInput(string in);
	SyntaxAnalysis(string in);
	virtual ~SyntaxAnalysis();

private:
	void exp();
	void strtag(); //START TAG
	void endtag(); //END TAG
	void sent();	//SENTENCE
	


private:
	LexicalAnalysis LA;
	int nextToken;
};


#endif