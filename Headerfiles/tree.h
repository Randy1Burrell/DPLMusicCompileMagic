

#ifndef TREE_H
#define TREE_H

#include "node.h"

using namespace std;

class tree
{
	private:
		node * root;
	public:
		//Default Constructor
		tree();
		
		//Primary Constructor
		tree(node * root);
		
		//Accessor
		node * getRoot();
		
		//Mutator
		void setRoot(node * root);
		
		//Print error
		void error_handler(node * error_node);
		
		
		
};

#endif
