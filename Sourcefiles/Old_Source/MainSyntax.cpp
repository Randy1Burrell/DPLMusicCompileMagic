#if !defined(_TEST_)
#define _TEST_

#include <iostream>
#include <string>
#include <fstream>
#include "LexicalAnalysis.h"
#include "SyntaxAnalysis.h"


using namespace std;


//
//int main ()
//{
//	
//	int token = WHITE_SPACE;
//	
//	string str = " ";
//	std::string file_contents = "";
//	int count = 0;
//
//	ifstream file("song.txt");
//	
//	while (std::getline(file, str)){  file_contents += str;}
//	
//	
//	SyntaxAnalysis SA(file_contents); 
//	SA.syntax();
//	
//	system("pause");
//	return 0;
//}

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

//===============================================================================================================


using namespace std;
//using namespace System;



int main()
{


		string text = "<emph><pitch middle ='0'/>usted es muy bonito</emph>!";
	USES_CONVERSION_EX;
	LPWSTR output = A2W_EX(text.c_str(), text.length());
	

	CComPtr <ISpVoice>		cpVoice;
	CComPtr <ISpStream>		cpStream;
	CSpStreamFormat			cAudioFmt;
	HRESULT hr = CoCreateInstance(CLSID_SpVoice, NULL, CLSCTX_ALL, IID_ISpVoice, (void **)&cpVoice);

	if (FAILED(::CoInitialize(NULL)))
		return FALSE;
	//Create a SAPI Voice
	hr = cpVoice.CoCreateInstance(CLSID_SpVoice);
	
	//Set the audio format
	if (SUCCEEDED(hr))
	{
		hr = cAudioFmt.AssignFormat(SPSF_22kHz16BitMono);
	}


//===========================================================================================
	if (SUCCEEDED(hr))
	{
		//cfile.Open("C:\\temp\\hellosample.wav", spFileMode, false);
		hr = SPBindToFile(L"C:\\Users\\Randy\\Desktop\\ttstemp.mp3", SPFM_CREATE_ALWAYS,
			&cpStream, &cAudioFmt.FormatId(), cAudioFmt.WaveFormatExPtr());
	}

//==========================================================================================





	//set the output to cpStream so that the output audio data will be stored in cpStream
	if (SUCCEEDED(hr))
	{
		hr = cpVoice->SetOutput(cpStream, TRUE);
	}
	if (SUCCEEDED(hr))
	{
		SpeechVoiceSpeakFlags my_Spflag = SpeechVoiceSpeakFlags::SVSFlagsAsync; // declaring and initializing Speech Voice Flags
		//hr = cpVoice->Speak(L"Hello World", my_Spflag, NULL);
		      hr = cpVoice->Speak(output, SPF_IS_XML, NULL );
		cpVoice->WaitUntilDone(-1);
	}
	//close the stream


		//Call SPBindToFile, a SAPI helper method,  to bind the audio stream to the file



	if (SUCCEEDED(hr))
	{
		hr = cpStream->Close();
	}
	//Release the stream and voice object
	cpStream.Release();
	cpVoice.Release();
	::CoUninitialize();
	return TRUE;
}
#endif
