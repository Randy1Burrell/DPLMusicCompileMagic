#include "SemanticAnalysis.h"

//Default Constructor
SemanticAnalysis::SemanticAnalysis()
{
	root = NULL;
}

//Primary Constructor
SemanticAnalysis::SemanticAnalysis(node * root)
{
	this->root = root;
}

//Accessor
node * SemanticAnalysis::getRoot()
{
	return this->root;
}

//Mutator
void SemanticAnalysis::setRoot(node * root)
{
	this->root = root;
}

//Analysis
bool SemanticAnalysis::Analysis(node * root, string identifier, ostream &results)
{
	this->root = root;

	if(this->root == NULL)
	{
		results << "Error please check file for error." << endl;
		return true;
	}
	else
	{
		if (this->root->getLeft() != NULL)
		{
			node * temp = root->getLeft();
			if (this->root->getWord() == "[Verse]" || this->root->getWord() == "[Chorus]")
			{
				if(this->root->getWord() == identifier && identifier == "[Verse]")
				{
					results << "Two verse cannot occur one behind the other" << endl;
					return true;
				}
				
				if (Analysis(temp, this->root->getWord(), results) == true)
				{
					return true;
				}
				else
				{
					Analysis(temp, this->root->getWord(), results);
					return false;
				}
			}
		}
	}
}