#if !defined(_TEST_)
#define _TEST_

#include <iostream>
#include <string>
#include <fstream>
#include "LexicalAnalysis.h"


using namespace std;

string convertToken(int t);

int main ()
{
	
	//std::ifstream file;
	int token = WHITE_SPACE;
	
	string str = " ";
	std::string file_contents = "";
	int count = 0;

	ifstream file("song.txt");//file.open ("song.txt", std::ifstream::in);
	//val = ifs.get();
	//std::cin>>val;

	cout<<"========== BUILD LEXICAL ANALYZER =========="<<endl;
	
	/***
		Below procedure will traverse the whole text file and just concatenate every retrieved lines to file_contents.
	***/
	while (std::getline(file, str)){  file_contents += str;/*file_contents.push_back('\n');*/}  
	/*******************************************
	The first phase of scanner works as a text scanner. 
	This phase scans the source code as a stream of characters and converts it into meaningful lexemes. 
	Lexical analyzer represents these lexemes in the form of tokens as:
	
	<token-name, attribute-value>
	********************************************/
	LexicalAnalysis LA(file_contents);
	while (token!=STOP) {
		token = LA.lex();
		//std::cout<<"<"<<LA.lexeme<<", "<<convertToken(token)<<">"<<std::endl;
		std::cerr<<"<TOKEN-NAME: \""<<LA.lexeme<<"\", ATTRIBUTE-VALUE:"<<convertToken(token)<<">"<<std::endl;
	}
	return 0;
}
string convertToken(int t) {
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
    default:  return "UNKNOWN";
  }
}
#endif