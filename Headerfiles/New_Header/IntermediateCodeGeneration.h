#ifndef INTERMEDITECOGEGENERATION_H
#define INTERMEDITECOGEGENERATION_H

#include <string>
#include <iostream>
#include<iostream>
#include<fstream>
#include<cstdlib>

using namespace std;

class IntermediateCodeGeneration
{
private:
	string hex;
	string orig;
public:
	//Default Constructor
	IntermediateCodeGeneration();

	//Constructors Primary
	IntermediateCodeGeneration(string orig);
	IntermediateCodeGeneration(string hex, string orig);

	//Accessors
	string getHex();
	string getOrig();

	//Mutator
	void setHex(string hex);
	void setOrig(string orig);

	//Processor Intermediate Code Generator
	string generateIntermediateCode();
	void stream2hex(const string str, string& hexstr);
};

#endif //!INTERMEDITECOGEGENERATION_H