import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;



public class mp3driver extends JFrame
{
	JButton pauseButton, stopButton, playButton;

	JPanel playerPanel;

	// the player actually doing all the work
	private static Player player=null;

	private InputStream inputStream;
	private AudioDevice audioDevice;



	private final static int NOTSTARTED = 0;
	private final static int PLAYING = 1;
	private final static int PAUSED = 2;
	private final static int FINISHED = 3;


	public mp3driver (final InputStream inputStream) throws JavaLayerException 
	{
		this.inputStream = inputStream;
		this.initializeComponents();
		this.addComponentsToPanels();
		this.addPanelstoWindow();
		this.setWindowProperties();
		//this.registerListeners();
		mp3driver.player = new Player(this.inputStream);
	}

	public mp3driver (final InputStream inputStream, final AudioDevice audioDevice) throws JavaLayerException 
	{
		this.inputStream = inputStream;
		this.audioDevice = audioDevice;
		mp3driver.player = new Player(this.inputStream, this.audioDevice);
	}

	//	private void registerListeners() 
	//	{
	//		pauseButton.addActionListener(this);
	//		stopButton.addActionListener(this);
	//		playButton.addActionListener(this);
	//	}



	private void setWindowProperties() 
	{
		this.setLayout(new FlowLayout());
		this.setSize(550,150);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

	}



	private void addPanelstoWindow() 
	{
		this.add(playerPanel);

	}



	private void addComponentsToPanels() {

		playerPanel.add(pauseButton);playerPanel.add(stopButton);playerPanel.add(playButton);
	}



	private void initializeComponents() 
	{
		pauseButton= new JButton("Pause");
		pauseButton.addActionListener(new runPause());
		stopButton= new JButton("Stop"); 
		stopButton.addActionListener(new runStop());
		playButton= new JButton("Play");
		playButton.addActionListener(new runPlay());
		playerPanel=new JPanel();

	}

	// locking object used to communicate with player thread
	private final Object playerLock = new Object();

	// status variable what player thread is doing/supposed to do
	private int playerStatus = NOTSTARTED;


	public boolean pause() {
		synchronized (playerLock) {
			if (playerStatus == PLAYING) {
				playerStatus = PAUSED;
			}
			return playerStatus == PAUSED;
		}
	}

	/**
	 * Resumes playback. Returns true if the new state is PLAYING.
	 */
	public boolean resume() {
		synchronized (playerLock) {
			if (playerStatus == PAUSED) {
				playerStatus = PLAYING;
				playerLock.notifyAll();
			}
			return playerStatus == PLAYING;
		}
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	/* public static void main(String[] argv) 
    {
    	try {
    		 // the player actually doing all the work
    		FileInputStream input = new FileInputStream("C:/Users/Chad/Documents/Visual Studio 2013/Projects/DPL project1/DPL project1/Popcaan.mp3");

    		mp3driver player = new mp3driver(input);

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

    }*/

	public class runPlay implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			try {
				play();
			} catch (JavaLayerException e1) {
				e1.printStackTrace();
			}
		}

		public void play() throws JavaLayerException {

			synchronized (playerLock) {
				switch (playerStatus) {
				case NOTSTARTED:
					new Thread(){
						public void run(){
							playInternal();
						}
					}.start();
					playerStatus = PLAYING;
					break;
				case PAUSED:
					resume();
					break;
				default:
					break;
				}
			}
		}

		private void playInternal() 
		{
			while (playerStatus != FINISHED) 
			{
				try 
				{
					if (!player.play(1))
					{
						break;
					}
				} 
				catch (final JavaLayerException e) 
				{
					break;
				}
				// check if paused or terminated
				synchronized (playerLock) 
				{
					while (playerStatus == PAUSED) 
					{
						try 
						{
							playerLock.wait();
						} 
						catch (final InterruptedException e) 
						{
							// terminate player
							break;
						}
					}
				}
			}
			close();
		}

		public void close() 
		{
			synchronized (playerLock) 
			{
				playerStatus = FINISHED;
			}
			try 
			{
				player.close();
			} 
			catch (final Exception e) 
			{
				// ignore, we are terminating anyway
			}
		}
	}

	public class runPause implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			new Thread()
			{
				public void run()
				{
					pause();
				}
			}.start();

		}
	}

	public class runStop implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			stop();
			try 
			{
				mp3driver.player = new Player(inputStream);
			} catch (JavaLayerException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void stop()
		{
			synchronized (playerLock) 
			{
				playerStatus = FINISHED;
				playerLock.notifyAll();
			}
		}
	}
}




