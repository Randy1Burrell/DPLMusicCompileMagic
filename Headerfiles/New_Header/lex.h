#ifndef LEX_H
#define LEX_H

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <sstream>
#include <regex>
#include "node.h"

using namespace std;

class lex
{
private:
	string fileName;
	vector<string> v;
public:
	lex()
	{
		fileName = "";
	}

	lex(string fileName)
	{
		this->fileName = fileName;
	}

	vector<string> * loadLeximes(string fileName)
	{
		ifstream file;
		file.open(this->fileName, ios::in);
		string j;
		//vector<string> v;

		if(!file.is_open())
		{
			return NULL;
		}
		else
		{
			while (!file.eof())
			{
				file >> j;

				v.push_back(j);
			}

			

			for (int i = 0, n= v.size(); i < n; i++)
			{
				cout << v[i] << endl;
			}
			file.close();
			v.pop_back();
		}
		return &v;
	}

	bool checkLex()
	{
		if (v.empty())
		{
			return false;
		}

		string j;

		vector<string> n;

		for (int i = 0, l = v.size(); i < l; i++)
		{
			j =  (string) v[i];
			if (j.find("[") == 0 && j.find("]"))
			{
				n.push_back(j);
			}
		}

		if(n.size() % 2 != 0)
		{
			cout << "Error" << n.size() << endl;
		}

		//regex e("^[[A-Z]\/w+](\/w(.)*)*");

		/*if ( regex_match(j, e))
		{
			cout << "true" << endl;
		}*/

		cout << j << endl;
		return true;
	}
};
#endif // !LEX_H
