#include "../Headerfiles/node.h"
#include<string>

using namespace std;

//Default Constructor
node::node()
{
	this->word = "";
	this->pitch = 0;
}

//Primary Constructor
node:: node(const string word, int pitch)
{
	this->word = word;
	this->pitch = pitch;
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
	return this->leftNode;
}

node * node::getRight()
{
	return this->rightNode;
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
