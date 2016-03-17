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
	if ( (nextChar > 64 && nextChar <91) || (nextChar > 96 && nextChar <123) ) charClass = LETTER;

	if ( nextChar > 47 && nextChar <58 ) charClass = DIGIT;

	if (nextChar == ' ') charClass = WHITE_SPACE;
	if (nextChar == '$') charClass = STOP;
	if (nextChar == '+') charClass = PLUS;
	if (nextChar == '\'') charClass = APOSTRPPHE;
	if (nextChar == '.') charClass = PERIOD;
	if (nextChar == ',') charClass = COMMA;
	if (nextChar == ':') charClass = COLON;
	if (nextChar == '[') charClass = LEFT_BRACKET;
	if (nextChar == ']') charClass = RIGHT_BRACKET;
	if (nextChar == '/') charClass = BACK_SLASH;
	if (nextChar == '?') charClass = QUESTION_MARK;
	if (nextChar == '!') charClass = EXCLAMATION_MARK;
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

		case COLON:
		  addChar();
		  getChar();
		  return COLON;
		  break;

		case LEFT_BRACKET:
		  addChar();
		  getChar();
		  return LEFT_BRACKET;
		  break;

		case RIGHT_BRACKET:
		  addChar();
		  getChar();
		  return RIGHT_BRACKET;
		  break;

		case BACK_SLASH:
		  addChar();
		  getChar();
		  return BACK_SLASH;
		  break;

		case QUESTION_MARK:
		  addChar();
		  getChar();
		  return QUESTION_MARK;
		  break;

		case EXCLAMATION_MARK:
		  addChar();
		  getChar();
		  return EXCLAMATION_MARK;
		  break;
		  case APOSTRPPHE:
		  addChar();
		  getChar();
		  return APOSTRPPHE;
		  break;
	  }//end switch code block

}

string LexicalAnalysis::convertToken(int t) {

  switch (t) {

    case WHITE_SPACE:  return "WHITE SPACE";
    case LETTER: return "LETTER";
    case DIGIT:  return "DIGIT";	  
	case IDENTENTIFIER:  return "IDENTENTIFIER";
    case INT_LIT:return "INT_LIT";
    case ERROR:  return "ERROR";
    case STOP:  return "EOF";
    case PLUS :  return "PLUS";
	case PERIOD: return "PERIOD";
	case COMMA: return "COMMA";

	case COLON: return "COLON";
	case QUESTION_MARK: return "QUESTION MARK";
	case LEFT_BRACKET: return "LEFT BRACKET";
	case RIGHT_BRACKET: return "RIGHT BRACKET";
	case BACK_SLASH: return "BACK SLASH";
	case APOSTRPPHE: return "APOSTRPPHE";
	case EXCLAMATION_MARK: return "EXCLAMATION MARK";

    default:  return "UNKNOWN";
  }
}
