#include "TTS.h"

using namespace std;

TTS::TTS(std::string in) : text(in), pVoice(NULL), wtext(in), {}
TTS::~TTS(){}

void TTS::TTSRun(char values){

	char text[] = "something, is happening with this shit";
	//text[] = values;
	wchar_t wtext[20];
	
	mbstowcs(wtext, text, strlen(text)+1);//Plus null
	LPWSTR ptr = wtext;

	if (FAILED(::CoInitialize(NULL))) exit(1);

    HRESULT hr = CoCreateInstance(CLSID_SpVoice, NULL, CLSCTX_ALL, IID_ISpVoice, (void **)&pVoice);    
	if(SUCCEEDED(hr)){		
		
		// hr = pVoice->Speak(lp, 0, NULL);
		// Change pitch
        hr = pVoice->Speak(ptr, SPF_IS_XML, NULL );
        pVoice->Release();
        pVoice = NULL;
	}
    ::CoUninitialize();

	//return EXIT_SUCCESS;

}
