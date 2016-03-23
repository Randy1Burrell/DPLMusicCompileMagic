#include "CodeOptimisation.h"

//Default Constructor
CodeOptimisation::CodeOptimisation()
{
    this->oString = "";
}

//Primary Constructor
CodeOptimisation::CodeOptimisation(vector<string> vec)
{
    this->vec = vec;
    this->oString = "";
}

//Accessor
vector<string> CodeOptimisation::getVec()
{
    return this->vec;
}

string CodeOptimisation::getOString()
{
    return this->oString;
}

//Mutator
void CodeOptimisation::setVec(vector<string> vec)
{
    this->vec = vec;
}

void CodeOptimisation::setOString(string oString)
{
    this->oString = oString;
}

//Optimisation
void CodeOptimisation::optimize()
{
    vector<string> temp;
    SyntaxAnalysis * s = new SyntaxAnalysis();
    
    for(int i = 0, n = vec.size(); i < n; i++)
    {
        string j = s->Dictionary(vec[i]);
        if(j.empty())
        {
            temp.push_back(vec[i]);
            this->oString += " ";
            this->oString += vec[i];
        }
        else
        {
            this->oString += "\n";
        }
    }
    //return temp;
}