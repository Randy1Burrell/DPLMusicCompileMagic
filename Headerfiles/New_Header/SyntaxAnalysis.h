#if !defined(_SA_)
#define _SA_

#include <iostream>
#include <fstream>
#include <string>
#include "LexicalAnalysis.h"
#include "tree.h"
#include "node.h"

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
	SyntaxAnalysis();
	string Dictionary(string x);
	node * buildTree(string  fileName, ostream &results);
	bool parseTree(node *, ostream &SyntaxResults, int counter);
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