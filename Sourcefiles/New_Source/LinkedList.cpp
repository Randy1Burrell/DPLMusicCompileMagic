#include "LinkedList.h"

void LinkedList::insertNext(node * head, node * insert)
{
	node * temp;
	if(head == NULL)
	{
		head = insert; 
	}
	else
	{
		temp = head->getRight();
		insertNext(temp, insert);
		return;
	}
}

void LinkedList::insert(node * head, node * insertNode)
{
	node * temp;
	if(head == NULL)
	{
		head = insertNode; 
	}
	else
	{
		temp = head->getLeft();
		insert(temp, insertNode);
		return;
	}
}

LinkedList::LinkedList()
{
	this->next = NULL;
	this ->top = NULL;
}