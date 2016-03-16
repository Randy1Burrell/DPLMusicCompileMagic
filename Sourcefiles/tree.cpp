#include "../Headerfiles/tree.h"



//Default Constructor
tree::tree()
{
	this->root = NULL;
}

//Primary Constructor
tree::tree(node * root)
{
	this->root = root;
}

//Accessor
node * tree::getRoot()
{
	return root;
}

//Mutator
void tree::setRoot(node * root)
{
	this->root = root;
}

void tree::error_handler(node * error_node)
{
	if(error_node != NULL)
    {
		error_handler(error_node->getLeft());
		cerr << error_node->getWord() << endl;
		error_handler(error_node->getRight());
    }
}
