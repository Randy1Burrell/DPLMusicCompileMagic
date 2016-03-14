#include <iostream>
#include <string>
#include <ctime>
#include <sapi.h>

//type something in to have it spoken!
//enter quit to exit
int main()
{
	 ISpVoice * pVoice = NULL;

    if (FAILED(::CoInitialize(NULL)))
        return EXIT_FAILURE;

    HRESULT hr = CoCreateInstance(CLSID_SpVoice, NULL, CLSCTX_ALL, IID_ISpVoice, (void **)&pVoice);    
	if(SUCCEEDED(hr))
	{		
		 hr = pVoice->Speak(L"Hello world", 0, NULL);

        // Change pitch
        hr = pVoice->Speak(L"This sounds normal <pitch middle = '-10'/> but the pitch drops half way through", SPF_IS_XML, NULL );
        pVoice->Release();
        pVoice = NULL;
	}
    ::CoUninitialize();

	return EXIT_SUCCESS;
}