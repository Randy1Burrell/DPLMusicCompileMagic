import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer.Info;

public class Mixer {

	/**
	 * @param args
	 */
	
	public static Mixer mixer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Info[] mixinfo = AudioSystem.getMixerInfo();
		
		for(Info info: mixinfo)
		{
			System.out.println(info.getName() + "<=======>" + info.getDescription());
		}
		
	}

}
