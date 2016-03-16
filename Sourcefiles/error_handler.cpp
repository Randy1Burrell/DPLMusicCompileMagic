#include <stdio.h>
#include <iostream>
#include "../Headerfiles/tree.h"

using namespace std;

int main(void)
{
	node * q = new node("Best", 2);
	node * n = new node("the", 2);
	node * g = new node("is", 2);
	node * y = new node("Randy", 2);
	tree * a = new tree(n);
	n->setLeft(y);
	y->setRight(g);
	g->setLeft(q);
	//printf("hello, world!!\n%d\n", a->getRoot());
	
	a->error_handler(a->getRoot());
}


