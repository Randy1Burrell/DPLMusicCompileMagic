import java.io.IOException;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import org.python.core.Py;
import org.python.core.PyInstance;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;


@SuppressWarnings("unused")
public class PythonProcess 
{
	public String process(String text, String to)
	{	
		Translate.setClientId("JavaDevelopmentCon");
		Translate.setClientSecret("LsB7Dqz73y19hpepDTMd5F5Zs8+59Py6nQGrNi9W29k=");
		String translatedText = "";
		String [] text1 = text.split(" ");
		String text2 = "", text3 = "", text4 ="", text5 = "";

		for(int i = 0, n = text1.length; i < n; i++)
		{
			if(i == 0)
			{
				text2 = text1[i];
			}
			else if(i == (n - 1))
			{
				text5 = text1[i];
			}
			else
			{

				text3 += text1[i];
				text3 += " ";
			}

		}

		if(to.equals("fr"))
		{

			try
			{
				text4 = Translate.execute(text3, Language.ENGLISH, Language.FRENCH);
			} 
			catch (Exception e) 
			{
				translatedText = "";
				System.out.println("Couldn't convert text");
			}
		}
		else if (to.equals("es"))
		{
			try
			{
				text4 = Translate.execute(text3, Language.ENGLISH, Language.SPANISH);
			} 
			catch (Exception e) 
			{
				translatedText = "";
				System.out.println("Couldn't convert text");
			}
		}
		else if (to.equals("ge"))
		{
			try
			{
				text4 = Translate.execute(text3, Language.ENGLISH, Language.GERMAN);
			} 
			catch (Exception e) 
			{
				translatedText = "";
				System.out.println("Couldn't convert text");
			}
		}
		else if (to.equals("ru"))
		{
			try
			{
				text4 = Translate.execute(text3, Language.ENGLISH, Language.RUSSIAN);
			} 
			catch (Exception e) 
			{
				translatedText = "";
				System.out.println("Couldn't convert text");
			}
		}
		else
		{
			return text;
		}
		System.out.println(text2 + text4);
		return text = text2 + " " + text4 + " " + text5;

	}

	/*public static void main(String[] args) throws Exception
	{
		// Set your Windows Azure Marketplace client info - See http://msdn.microsoft.com/en-us/library/hh454950.aspx
		//System.out.println(new PythonProcess().process("[Sentence] Yes [/Sentence]", "es"));
	}*/
}
