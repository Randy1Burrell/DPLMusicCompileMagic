#include "LLVM.h"
//using namespace std;

//Default Constructor;
LLVM::LLVM()
{
	this->SemanticError = false;
	this->SyntaxError = false;
}

//Primary Constructor
LLVM::LLVM(bool SemanticError, bool SyntaxError)
{
	this->SemanticError = SemanticError;
	this->SyntaxError = SyntaxError;
}

//Accesor
bool LLVM::getSemanticError()
{
	return this->SemanticError;
}

bool LLVM::getSyntaxError()
{
	return this->SyntaxError;
}

//Mutator
void LLVM::setSemanticError(bool SemanticError)
{
	this->SemanticError = SemanticError;
}

void LLVM::setSyntaxError(bool SyntaxError)
{
	this->SyntaxError = SyntaxError;
}

//LLVM Run
void LLVM::runLLVM()
{
	LexicalAnalysis * lexer = new LexicalAnalysis();
	//	lexer->convertToken();

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
	if (file == NULL)
	{
		return;
	}

	ofstream results("llvmresults.txt");
	if (results == NULL)
	{
		return;
	}

	results<<"========== STARTING LEXICAL ANALYZER =========="<<endl;

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
		results<<"<TOKEN-NAME: \""<<LA.lexeme<<"\", ATTRIBUTE-VALUE:"<<LA.convertToken(token)<<">"<<std::endl;
	}

	results<<"========== STARTING SYNTAX ANALYZER =========="<<endl;
	SyntaxAnalysis * Syntax = new SyntaxAnalysis();
	tree * tres = new tree();
	node * n = Syntax->buildTree("song.txt", results);
	tres->setRoot(n);
	
	this->SyntaxError = Syntax->parseTree(tres->getRoot(), results, 0);

	results<<"========== STARTING SEMANTIC ANALYZER =========="<<endl;
	SemanticAnalysis * Semantics = new SemanticAnalysis();

	this->SemanticError = Semantics->Analysis(tres->getRoot(), "", results);

	if(this->SemanticError == false)
	{
		results << "No Semantic Error Found" << endl;
	}

	if (this->SyntaxError == true || this->SemanticError == true){return;}

	results<<"========== STARTING CODE OPTIMISATION =========="<<endl;
	
	CodeOptimisation * optimiser = new CodeOptimisation();
	optimiser->setVec(LA.loadLeximes("song.txt"));
	optimiser->optimize();
	results << optimiser->getOString() << endl;
	//char * x = "Yeah Baby";
	//char ** f = &x;

	//Python py(0, f); 
	//py.load("Translate");

	//string ret;
	//Arg_map args; 

	//args[optimiser->getOString()] = Py_string;
	//args["es"] = Py_string;

	//// make the call
 //     py.call("Translator", args, ret);

 //     // will print 200
 //     std::cout << ret << '\n';
	results<<"========== STARTING INTERMEDIATE CODE GENERATION =========="<<endl<<endl;
	IntermediateCodeGeneration * genhex = new IntermediateCodeGeneration(optimiser->getOString());
	results << genhex->getHex();
	results<<"========== Generating MP3 =========="<<endl;
	TTS * text = new TTS(optimiser->getOString());
	text->ConverTextToSpeech(optimiser->getOString());
}