#include "tree.h"
#include "SyntaxAnalysis.h"


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

void tree::error_handler(node * error_node, ostream &result)
{
	if(error_node != NULL)
	{
		error_handler(error_node->getLeft(), result);
		result << error_node->getWord() << endl;
		error_handler(error_node->getRight(), result);
	}
	else
	{
		return;
	}
}

node * tree:: buildTree(node * addNode, string word)
{
	if (word.empty())
	{
		return NULL;
	}
	else
	{

	}
}

void tree:: insertNode(node * root, node * leftNode)
{
	node * temp = root;

	if(this->root == NULL)
	{
		this->root = leftNode;
	}
	else if (temp->getLeft() != NULL)
	{
		temp = temp->getLeft();
		insertNode(temp, leftNode);
		return;
	}
	else
	{
		temp->setLeft(leftNode);
	}
}

string tree:: Dictionary(string x)
{
	if (x == "[/Genre]")
	{
		return "[Genre]";
	}
	else if ( x == "[/Sentence]")
	{
		return "[Sentence]";
	}
	else if( x == "[/Title]")
	{
		return "[Title]";
	}
	else if(x == "[/Chorus]" )
	{
		return "[Chorus]";
	}
	else if ( x == "[/Verse]")
	{
		return "[Verse]";
	}
	else if ( x == "[/Bridge]" )
	{
		return "[Bridge]";
	}
	else if ( x == "[/Description]")
	{
		return "[Description]";
	}
	else
	{
		return "";
	}
}

void tree::insertList(node * root, node * insertNode)
{
	//if(root->getLeft() == NULL)
	//{
	SyntaxAnalysis wordCheck;
	string n = this->Dictionary(insertNode->getWord());
	if(root->getRight() == NULL)
	{
		if (root->getWord() == n)
		{
			root->setRight(insertNode);
		}
		else
		{
			node * temp = root->getLeft();
			this->insertList(temp, insertNode);
			return;
		}
	}
	else
	{
		node * temp = root->getLeft();
		this->insertList(temp, insertNode);
		return;
	}
	//}
	//else
	//{
	/*node * temp = root->getLeft();
	this->insertList(temp, insertNode);
	}*/
	return;
}

void tree::parseTree(node * root)
{
	tree n;

	if(root == NULL)
	{
		cerr << "No tags in file" <<endl;
		return;
	}

	node * temp;
	string j = "";

	if (root->getRight() != NULL)
	{
		temp = root->getRight();
		j = n.Dictionary(temp->getWord());
	}
	
	if (root->getWord() != j || root->getRight() == NULL)
	{
		cerr << "Error missin closing tag for " << root->getWord() << endl;
		return;
	}
	else if(root->getLeft() != NULL)
	{
		temp = root->getLeft();
		parseTree(temp);
	}

	return;
}
