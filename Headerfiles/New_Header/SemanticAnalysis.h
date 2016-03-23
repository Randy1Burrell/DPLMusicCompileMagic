#ifndef SEMANTICANALYSIS_H
#define SEMANTICANALYSIS_H

#include "node.h"

using namespace std;

class SemanticAnalysis
{
private:
	node * root;
	int count;

public:
	//Default Constructor
	SemanticAnalysis();

	//Primary Constructor
	SemanticAnalysis(node * root);

	//Accessor
	node * getRoot();

	//Mutator
	void setRoot(node * root);

	//Analysis
	bool Analysis(node * root, string identifier, ostream &results);
};
#endif //!SEMANTICANALYSIS_H