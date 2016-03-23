#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include <iostream>
#include "node.h"

class LinkedList
{
private:
	node * top;
	node * next;

public:
	LinkedList();
	void insert(node * head, node * insertNode);
	//void Delete(string * value);
	void insertNext(node * head, node * insert);

};
#endif //!LINKEDLIST_H