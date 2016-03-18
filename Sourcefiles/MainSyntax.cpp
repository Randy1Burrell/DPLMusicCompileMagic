#if !defined(_TEST_)
#define _TEST_

#include <iostream>
#include <string>
#include <fstream>
#include "LexicalAnalysis.h"
#include "SyntaxAnalysis.h"


using namespace std;



int main ()
{
	
	int token = WHITE_SPACE;
	
	string str = " ";
	std::string file_contents = "";
	int count = 0;

	ifstream file("song.txt");
	
	while (std::getline(file, str)){  file_contents += str;}
	
	
	SyntaxAnalysis SA(file_contents); SA.syntax();
	
	system("pause");
	return 0;
}

#endif