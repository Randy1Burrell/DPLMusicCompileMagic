#include "IntermediateCodeGeneration.h"

//Default Constructor
IntermediateCodeGeneration::IntermediateCodeGeneration()
{
	this->hex = "";
	this->orig = "";
}

//Constructors Primary
IntermediateCodeGeneration::IntermediateCodeGeneration(string orig)
{
	stream2hex(orig, this->hex) ;
}

IntermediateCodeGeneration::IntermediateCodeGeneration(string hex, string orig)
{
	this->hex = hex;
	this->orig = orig;
}

//Accessors
string IntermediateCodeGeneration::getHex()
{
	return this->hex;
}

string IntermediateCodeGeneration::getOrig()
{
	return this->orig;
}

//Mutator
void IntermediateCodeGeneration::setHex(string hex)
{
	this->hex = hex;
}

void IntermediateCodeGeneration::setOrig(string orig)
{
	this->orig = orig;
}

//Processor Intermediate Code Generator
string IntermediateCodeGeneration::generateIntermediateCode()
{
	if(this->orig == "")
	{
		return orig;
	}
	stream2hex(this->orig, this->hex);

	return this->hex;
}

void IntermediateCodeGeneration::stream2hex(string str, string& hexstr)
{
    hexstr = std::string(str.size() << 1, '\0');
    char c;

    for (size_t i = 0, j = 0, k = str.size(); i < k; i++)
    {
        c = (str[i] & 0xF0) >> 4;
        hexstr[j++] = c > 9 ? (c - 9) | 0x40 : c | 0x30;
        c = str[i] & 0xF;
        hexstr[j++] = c > 9 ? (c - 9) | 0x40 : c | 0x30;
    }
}
