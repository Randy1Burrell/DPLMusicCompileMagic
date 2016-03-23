/*********************************************************************** 
Syntax Anal - Interface for the SyntaxAnalysis class.
**********************************************************************/
#if !defined(_TTS_)
#define _TTS_

#include <iostream>
#include <string>
#include <ctime>
#include <sapi.h>
#include <atlconv.h>

class TTS
{
	public:
		void TTSRun(char val);
		TTS(std::string in);
		virtual ~TTS();
	
	private:
		void setText();

	public:
			char textt;
			ISpVoice * pVoice;
			wchar_t wtextt;  
			std::wstring wtext;
			std::string text;
};


#endif