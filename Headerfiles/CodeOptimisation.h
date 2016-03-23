#ifndef CODEOPTIMISATION_H
#define CODEOPTIMISATION_H

#include <iostream>
#include <vector>
#include <string>
//#include "tree.h"
#include "SyntaxAnalysis.h"

using namespace std;
//class CodeOptimisation;

class CodeOptimisation
{
private:
    vector<string> vec;
    string oString;
public:
    //Default Constructor
    CodeOptimisation();
    
    //Primary Constructor
    CodeOptimisation(vector<string> vec);
    
    //Accessor
    vector<string> getVec();
    string getOString();
    
    //Mutator
    void setVec(vector<string> vect);
    void setOString(string oString);
    
    //Optimisation
    void optimize();
};
#endif //!CODEOPTIMISATION_H