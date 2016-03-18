#include "SyntaxAnalysis.h"
#include <iostream>
#include <string>


SyntaxAnalysis::SyntaxAnalysis(string in)
{
	LA.setNewInput(in);
	cout<<"input:"<<in<<endl;
	nextToken = LA.lex();
}

SyntaxAnalysis::~SyntaxAnalysis()
{
}

void SyntaxAnalysis::setNewInput(string in)
{
	LA.setNewInput(in);
	cout<<"input:"<<in<<endl;
	nextToken = LA.lex();

}

void SyntaxAnalysis::syntax()
{
	exp();
	if (nextToken==STOP)
	{ 
		cout<<"Syntax 3 is correct\n";
	}
	else 
	{ 
		cout<<"Error\n";
		exit(1); 
	}
	
}

void SyntaxAnalysis::exp()
{
	strtag(); 
	sent();
	while(nextToken==IDENTIFIER)
		sent();
	endtag();
}


void SyntaxAnalysis::sent()
{
	while(nextToken==IDENTIFIER || nextToken==COMMA || nextToken==APOSTRPPHE)
	{
		nextToken = LA.lex();
	}
	
	if(nextToken == PERIOD || nextToken == QUESTION_MARK || nextToken == EXCLAMATION_MARK)
	{
		cout<<"End of sentence\n";
		nextToken = LA.lex();
	}
}

void SyntaxAnalysis::strtag()
{
	if(nextToken==LEFT_BRACKET)
	{
		nextToken= LA.lex();
		if(nextToken=IDENTIFIER)
		{
			nextToken=LA.lex();
			if(nextToken==RIGHT_BRACKET)
			{
				nextToken=LA.lex();
				cout<<"Syntax 1 is Correct\n";
			}
			else
			{
				cout<<"Invalid 1 Tag"<<LA.convertToken(nextToken)<<"  error\n";
			}
		}
		else
		{
			cout<<"Invalid 2 Tag"<<LA.convertToken(nextToken)<<"  error\n";
		}
	}
	else
	{
		cout<<"Invalid 3 Tag"<<LA.convertToken(nextToken)<<"  error\n";
	}
}

void SyntaxAnalysis::endtag()
{
	if(nextToken==LEFT_BRACKET)
	{
		nextToken= LA.lex();
		if(nextToken==BACK_SLASH)
		{
			nextToken=LA.lex();
			if(nextToken=IDENTIFIER)
			{
				nextToken=LA.lex();
				if(nextToken==RIGHT_BRACKET)
				{
					nextToken=LA.lex();
					if(nextToken==STOP)
					{
						cout<<"Syntax 2 is Correct\n";
					}
					else
					{
						cout<<"Invalid 4 Tag"<<LA.convertToken(nextToken)<<"  error\n";
					}
				}
				else
				{
					cout<<"Invalid 5 Tag"<<LA.convertToken(nextToken)<<"  error\n";
				}
			}
			else
			{
				cout<<"Invalid 6 Tag"<<LA.convertToken(nextToken)<<"  error\n";
			}
		}
		else
		{
			cout<<"Invalid 7 Tag"<<LA.convertToken(nextToken)<<"  error\n";
		}
	}
	else
	{
		cout<<"Invalid 8 Tag"<<LA.convertToken(nextToken)<<"  error\n";
	}
}
