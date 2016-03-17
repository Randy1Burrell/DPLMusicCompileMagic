#include <iostream>
#include <string>
#include <ctime>
#include <sapi.h>
#include <atlconv.h>

#include <stddef.h>






int main()
{
ISpVoice * pVoice = NULL;

	
 char text[] = "something, is happening with this shit";
 wchar_t wtext[20];
 mbstowcs(wtext, text, strlen(text)+1);//Plus null
 LPWSTR ptr = wtext;

    if (FAILED(::CoInitialize(NULL)))
        return EXIT_FAILURE;

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

	return EXIT_SUCCESS;
	
}