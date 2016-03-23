/*********************************************************************** 
Lexical Analysis - Interface for the Lexical Analysis class
**********************************************************************/
#if !defined(_LA_)
#define _LA_


#include <iostream>
#include <string>
#include <vector>
#include <fstream>

using namespace std;

enum 
{
	DIGIT, 
	LETTER, 
	IDENTENTIFIER, 
	INT_LIT, //integer literal
	ERROR_R, 
	WHITE_SPACE, 
	STOP, 
	PLUS, 
	PERIOD, 
	COMMA,
	COLON,
	LEFT_BRACKET, 
	RIGHT_BRACKET, 
	BACK_SLASH, 
	QUESTION_MARK, 
	EXCLAMATION_MARK,
	APOSTRPPHE
};

class LexicalAnalysis
{
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
		vector<string> loadLeximes(string fileName);
		string convertToken(int t);

	private:
		void addChar();
		void getChar();
};

#endif