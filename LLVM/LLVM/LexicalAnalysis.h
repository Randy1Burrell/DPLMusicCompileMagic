/*********************************************************************** 
Lexical Analysis - Interface for the Lexical Analysis class
**********************************************************************/

#if !defined(_LA_)
#define _LA_

#include <iostream>
#include <string>

using namespace std;

enum {DIGIT, LETTER, IDENTENTIFIER, INT_LIT, ERROR, WHITE_SPACE, STOP, PLUS, PERIOD, COMMA};

class LexicalAnalysis{

	public:
		char nextChar;
		int charClass;
		string lexeme;

	private:
		string input;

	public:
		int lex();
		void setNewInput(string in);
		LexicalAnalysis(string in="");
		virtual ~LexicalAnalysis();

private:
	void addChar();
	void getChar();
};
#endif