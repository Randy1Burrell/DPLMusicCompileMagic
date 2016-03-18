#if !defined(_MAIN_)
#define _MAIN_

#include <iostream>
#include <string>
#include <fstream>

#include "LexicalAnalysis.h"
#include "SyntaxAnalysis.h"

using namespace std;



int main ()
{
	/********************************************LOCAL VARIABLES DECLARATION********************************************/
	int token;
	string str;
	string file_contents;
	int count;

	/********************************************LOCAL VARIABLES INITIALIZATION********************************************/
	count = 0;
	str = " ";
	file_contents = "";
	token = WHITE_SPACE;
	ifstream file("song.txt");
	
	cout<<"========== START LEXICAL ANALYZER =========="<<endl;
	//Below procedure will traverse the whole text file and just concatenate every retrieved lines to file_contents.
	while (std::getline(file, str)){  file_contents += str;/*file_contents.push_back('\n');*/}
	
	/**************************************************************************************************
	The first phase of scanner works as a text scanner. 
	This phase scans the source code as a stream of characters and converts it into meaningful lexemes. 
	Lexical analyzer represents these lexemes in the form of tokens as:
	<token-name, attribute-value>
	***************************************************************************************************/
	
	LexicalAnalysis LA(file_contents);
	while (token!=STOP) //parse tree
	{
		token = LA.lex();
		//std::cout<<"<"<<LA.lexeme<<", "<<convertToken(token)<<">"<<std::endl;
		std::cerr<<"<TOKEN-NAME: \""<<LA.lexeme<<"\", ATTRIBUTE-VALUE:"<<LA.convertToken(token)<<">"<<std::endl;
	}
	
	//cout<<"========== START SYNTAX ANALYZER =========="<<endl;
	//SyntaxAnalysis SA("I have no fear cause I know who's in control.");
	//SA.syntax();
		
	return 0;
} //END MAIN LOGICAL CODE BLOCK
#endif
