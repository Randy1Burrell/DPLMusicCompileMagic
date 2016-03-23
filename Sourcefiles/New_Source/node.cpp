#include "node.h"
#include<string>

using namespace std;

//Default Constructor
node::node()
{
	this->word = "";
	this->pitch = 0;
	this->leftNode = NULL;
	this->rightNode = NULL;
}

//Primary Constructor
node:: node(const string word, int pitch)
{
	this->word = word;
	this->pitch = pitch;
	this->leftNode = NULL;
	this->rightNode = NULL;
}

//Accessors
string node::getWord()
{
	return this->word;
}

int node::getPitch()
{
	return this->pitch;
}

node * node::getLeft()
{
	return leftNode;
}

node * node::getRight()
{
	return rightNode;
}

//Mutators
void node::setWord(string word)
{
	this->word = word;
}

void node::setPitch(int pitch)
{
	this->pitch = pitch;
}

void node::setLeft(node * leftNode)
{
	this->leftNode = leftNode;
}

void node::setRight(node * rightNode)
{
	this->rightNode = rightNode;
}