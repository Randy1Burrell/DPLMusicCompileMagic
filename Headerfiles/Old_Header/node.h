#ifndef NODE_H
#define NODE_H

#include <iostream>

class node;

using namespace std;

class node
{
	private:
		string word;
		int pitch;
		node * leftNode;
		node * rightNode;

	public:
		//Default Constructor
		node();
		
		//Primary Constructor
		node(const string word, int pitch);
		
		//Accessors
		string getWord();
				
		int getPitch();
		
		node * getLeft();

		node * getRight();


		//Mutators
		void setWord(string word);
				
		void setPitch(int pitch);
		
		void setLeft(node * leftNode);

		void setRight(node * rightNode);

};
#endif
