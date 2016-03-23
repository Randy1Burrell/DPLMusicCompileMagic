

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
		void error_handler(node * error_node, ostream &results);
		
		node * buildTree(node * addNode, string word);

		void insertNode(node * root, node * leftNode);

		void insertList(node * root, node * insertNode);
		
		string Dictionary(string x);

		void parseTree(node * root);
};

#endif