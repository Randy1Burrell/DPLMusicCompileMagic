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

	STTAG ::= LEFT_Bracket SENT RIGHT_Bracket

	ENTAG ::= LEFT_Bracket BACK_SLASH SENT RIGHT_Bracket

	EXPR ::= STTAG SENT ENTAG

	SENT ::= IDENTIFIER {+ SENT}

  */

class SyntaxAnalysis  
{
public:
	SyntaxAnalysis();
    virtual ~SyntaxAnalysis();
	
    string Dictionary(string x);
	node * buildTree(string  fileName, ostream &results);
	bool parseTree(node *, ostream &SyntaxResults, int counter);
	
    void syntax();
    void setNewInput(string in);
    SyntaxAnalysis(string in);

private:
	LexicalAnalysis LA;
	int nextToken;
    
    void exp();
    void strtag(); //START TAG
    void endtag(); //END TAG
    void sent();	//SENTENCE
};

#endif