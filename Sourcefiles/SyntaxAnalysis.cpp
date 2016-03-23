#include "SyntaxAnalysis.h"
#include <iostream>
#include <string>




SyntaxAnalysis::SyntaxAnalysis()
{
}

SyntaxAnalysis::~SyntaxAnalysis()
{
}

node * SyntaxAnalysis::buildTree(string  fileName, ostream &results)
{
    LexicalAnalysis fileValues;
    vector<string> v = fileValues.loadLeximes(fileName);
    tree * m = new tree();
    string startTag;
    SyntaxAnalysis * o = new SyntaxAnalysis;
    for(int i = 0, n = v.size(); i < n ; i ++)
    {
        string b = o->Dictionary((string)v[i]);
        if (b.empty())
        {
            
        }
        else if (b == v[i])
        {
            node * temp = new node();
            string a = (string)v[i];
            temp->setWord(a);
            m->insertList(m->getRoot(), temp);
            if((i + 1) < n)
            {
                string h = this->Dictionary(v[i + 1]);
                if (h.compare("") == 0)
                {
                    results << "Error word without identifier at word " << i <<endl;
                    results << "After tag >> " << v[i] << " <<" <<endl;
                }
            }
        }
        else if (!b.empty())
        {
            startTag = (string)v[i];
            node * temp = new node();
            temp->setWord((string)v[i]);
            m->insertNode(m->getRoot(), temp);
        }
    }
    
    node * kool = m->getRoot();
    return kool;
}


bool SyntaxAnalysis::parseTree(node * root, ostream &SyntaxResults, int counter)
{
    tree n;
    
    if(root == NULL)
    {
        SyntaxResults << "No tags in file" <<endl;
        return true;
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
        SyntaxResults << "Error missin closing tag for " << root->getWord() << " at line " << counter << endl;
        tree * tres = new tree();
        tres->error_handler(root, SyntaxResults);
        return true;
    }
    else if(root->getLeft() != NULL)
    {
        temp = root->getLeft();
        parseTree(temp, SyntaxResults, counter + 1);
        SyntaxResults << "Line " << counter<< " parsed and syntax is fine" << endl;
    }
    return false;
}


//Dictionary of the Tags
string SyntaxAnalysis:: Dictionary(string x)
{
    if (x == "[Genre]" || x == "[/Genre]")
    {
        return "[/Genre]";
    }
    else if (x == "[Sentence]" || x == "[/Sentence]")
    {
        return "[/Sentence]";
    }
    else if(x == "[Title]" || x == "[/Title]")
    {
        return "[/Title]";
    }
    else if(x == "[Chorus]" || x == "[/Chorus]" )
    {
        return "[/Chorus]";
    }
    else if (x == "[Verse]" || x == "[/Verse]")
    {
        return "[/Verse]";
    }
    else if (x == "[Bridge]" || x == "[/Bridge]" )
    {
        return "[/Bridge]";
    }
    else if (x == "[Description]" || x == "[/Description]")
    {
        return "[/Description]";
    }
    else
    {
        return "";
    }
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
	while(nextToken==IDENTENTIFIER)
		sent();
	endtag();
}


void SyntaxAnalysis::sent()
{
	while(nextToken==IDENTENTIFIER || nextToken==COMMA || nextToken==APOSTRPPHE)
	{
		nextToken = LA.lex();
	}

	if(nextToken == PERIOD || nextToken == QUESTION_MARK || nextToken == EXCLAMATION_MARK)
	{
		cout<<"End of sentence\n";
		nextToken = LA.lex();
	}
}


void SyntaxAnalysis::strtag() //checks for strtag
{
	if(nextToken==LEFT_BRACKET)
	{
		nextToken= LA.lex();
		if(nextToken=IDENTENTIFIER)
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


void SyntaxAnalysis::endtag() // Checks for end tag
{
	if(nextToken==LEFT_BRACKET)
	{
		nextToken= LA.lex();
		if(nextToken==BACK_SLASH)
		{
			nextToken=LA.lex();
			if(nextToken=IDENTENTIFIER)
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


void SyntaxAnalysis::setNewInput(string in)
{
    LA.setNewInput(in);
    cout<<"input:"<<in<<endl;
    nextToken = LA.lex();
}

SyntaxAnalysis::SyntaxAnalysis(string in)
{
    LA.setNewInput(in);
    cout<<"input:"<<in<<endl;
    nextToken = LA.lex();
}



