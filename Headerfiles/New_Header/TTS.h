/*********************************************************************** 
Syntax Anal - Interface for the SyntaxAnalysis class.
**********************************************************************/
#if !defined(_TTS_)
#define _TTS_


//Required Header files
//===============================================================================================================
#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <sapi.h>
#include <atlconv.h>
//#include <atlexcept.h>
#include <stddef.h>
//#include "vcclr.h"
#include "atlbase.h"
#include "atlstr.h"
#include "comutil.h"
#include <sphelper.h>

#include <string>
#include <ctime>
#include <sapi.h>
#include <atlconv.h>

//===============================================================================================================

using namespace std;

class TTS
{
	public:
		void TTSRun(char val);
		TTS(std::string in);
		bool ConverTextToSpeech(string tt);
		virtual ~TTS();

	private:
		void setText();
	public:
			char textt;
			ISpVoice * pVoice;
			wchar_t wtext;  
			//std::wstring wtext;
			std::string text;
};
#endif