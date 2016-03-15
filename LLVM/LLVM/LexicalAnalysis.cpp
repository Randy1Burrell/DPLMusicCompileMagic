/*********************************************************************** 
Lexical Analysis - Implementation of the Lexical Analysis class.
**********************************************************************/
#include "LexicalAnalysis.h"

// Construction & Destruction
LexicalAnalysis::LexicalAnalysis(string in) : input(in), charClass(ERROR), nextChar(' '), lexeme(""){ getChar(); } 
LexicalAnalysis::~LexicalAnalysis(){ /**NOTE: KILL ifstream process**/ }

void LexicalAnalysis::setNewInput(string in){
	input=in;
	getChar();
}

void LexicalAnalysis::getChar(){
	if (input.size()>0){
	  nextChar = input[0];
	  input.erase(0,1);
	}else 
		nextChar = '$';
		charClass = ERROR;
	
	//get char/letters base on ascii table values
	if ( (nextChar > 64 && nextChar <91) || (nextChar > 96 && nextChar <123) )
		charClass = LETTER;

	if ( nextChar > 47 && nextChar <58 )
		charClass = DIGIT;

	if (nextChar == ' ') charClass = WHITE_SPACE;
	if (nextChar == '$') charClass = STOP;
	if (nextChar == '+') charClass = PLUS;
	if (nextChar == '.') charClass = PERIOD;
	if (nextChar == ';') charClass = COMMA;
}

void LexicalAnalysis::addChar(){ lexeme+=nextChar; }
int LexicalAnalysis::lex(){
	  
	lexeme="";
	while (charClass == WHITE_SPACE) getChar();

	if (charClass == ERROR) {addChar(); getChar(); return ERROR;}
	if (charClass == STOP) { return STOP;}

	switch (charClass) {
		
		case LETTER:
			addChar();
			getChar();
			while (charClass == LETTER || charClass == DIGIT){
				addChar();
				getChar();
			}
			return IDENTENTIFIER;
			break;
		
		case DIGIT: 
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			  }
			  return INT_LIT;
			  break;
		
		case PLUS:
		  addChar();
		  getChar();
		  return PLUS;
		  break;
		
		case COMMA:
		  addChar();
		  getChar();
		  return COMMA;
		  break;
		
		case PERIOD:
		  addChar();
		  getChar();
		  return PERIOD;
		  break;
	  }//end switch code block

}
