
#ifndef LLVM_H
#define LLVM_H

#include "CodeOptimisation.h"
#include "LexicalAnalysis.h"
#include "IntermediateCodeGeneration.h"
#include "SemanticAnalysis.h"
#include "SyntaxAnalysis.h"
#include "TTS.h"
//#include "pyembed.h"
#include <fstream>
#include <iostream>
#include <string>
#include <stdio.h> 

//using namespace pyembed;
using namespace std;

class LLVM
{
private:
	bool SemanticError;
	bool SyntaxError;

public:
	//Default Constructor;
	LLVM();

	//Primary Constructor
	LLVM(bool SemanticError, bool SyntaxError);

	//Accesor
	bool getSemanticError();
	bool getSyntaxError();

	//Mutator
	void setSemanticError(bool SemanticError);
	void setSyntaxError(bool SyntaxError);

	//LLVM Run
	void runLLVM();
};

#endif //!LLVM_H