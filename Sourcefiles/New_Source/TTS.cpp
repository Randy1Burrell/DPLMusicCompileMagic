#include "TTS.h"

using namespace std;

TTS::TTS(std::string in) 
{
	text = in;
	pVoice = NULL;
	//const char * n = in.c_str();
	//mbstowcs(wtext, n, strlen(n)+1);
}

TTS::~TTS(){}

void TTS::TTSRun(char values)
{
	char text[] = "something, is happening with this shit";
	//text[] = values;
	wchar_t wtext[20];	
	mbstowcs(wtext, text, strlen(text)+1);//Plus null
	LPWSTR ptr = wtext;
	if (FAILED(::CoInitialize(NULL))) exit(1);
    HRESULT hr = CoCreateInstance(CLSID_SpVoice, NULL, CLSCTX_ALL, IID_ISpVoice, (void **)&pVoice);    

	if(SUCCEEDED(hr))
	{			
		// hr = pVoice->Speak(lp, 0, NULL);
		// Change pitch
        hr = pVoice->Speak(ptr, SPF_IS_XML, NULL );
        pVoice->Release();
        pVoice = NULL;
	}
    ::CoUninitialize();
	//return EXIT_SUCCESS;
}

bool TTS::ConverTextToSpeech (string text)
{


	//string text = "<emph><pitch middle ='0'/>usted es muy bonito</emph>!";
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
		hr = SPBindToFile(L"ttstemp.mp3", SPFM_CREATE_ALWAYS,
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
	return true;
	//return "C:\\Users\\Randy\\Desktop\\ttstemp.mp3";
}