
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.media.Player;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Time;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BeatBox 
{
	private JFrame theFrame;
	private JFrame logIn;
	private JFrame BeatsStudio;
	private JPanel mainPanel;
	private JPanel Panel;
	private JButton btnLogIn;
	@SuppressWarnings("rawtypes")
	private JList incomingList;
	private JTextField userMessage;
	private JLabel lblUserName;
	private JTextField txtUserName;
	private JTextField txtFilePath;
	TargetDataLine line;
	private JTextField txtMp3Name;
	private ArrayList<JCheckBox> checkBoxList;
	private int nextNum;
	private Vector<String> listVector = new Vector<String>();
	private String userName;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private HashMap<String, boolean[]> otherSeqMap = new HashMap<String, boolean[]>();

	private Sequencer sequencer;
	private Sequence sequence;
	private Sequence mySequence = null;
	private Track track;
	private JTextField fileField;
	private JButton StartRecording;
	private JButton StopRec;
	private JButton openFile;
	private JButton convert;
	private JButton convertToAudio;
	private JButton PlayRecording;
	private JButton StopRecording;
	private JButton PauseRecording;
	private JPanel panel;
	private JLabel to;
	private JLabel LLVMout;
	private JLabel fileArea;
	@SuppressWarnings("rawtypes")
	private JComboBox Languages;
	private Socket conn;
	private OutputStream output;
	private InputStream input;
	private JTextArea LLVMOutput;
	private JTextArea txtFile;

	private static Player player=null;

	// record duration, in milliseconds
	static final long RECORD_TIME = 600;  // 1 minute

	// path of the wav file
	private File wavFile = new File("Recorded.AU");

	// format of audio file
	private AudioFileFormat.Type fileType = AudioFileFormat.Type.AU;

	private String mp3Path = null;

	private String[] Lang = {"English","Spanish", "French", "German", "Russian"}; 

	private String[] instrumentNames = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare", "Crash Cymbal",
			"Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistel", "Low Conga", "Cowbell",
			"Vibraslap", "Low-midi Tom", "High Agogo", "Open High-Conga"};

	private int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

	public static void main(String[] args) 
	{
		new BeatBox().start();
	}

	public void start()
	{
		logIn = new JFrame("Log In User Name");
		lblUserName = new JLabel("User Name");
		txtUserName = new JTextField(30);
		btnLogIn = new JButton("Log In");
		fileField = new JTextField(35);

		btnLogIn.addActionListener(new btnLogInClass());

		Panel = new JPanel();
		Panel.add(lblUserName);
		Panel.add(txtUserName);
		Panel.add(btnLogIn);

		logIn.add(Panel);
		logIn.setSize(400, 150);
		logIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logIn.setResizable(false);
		logIn.setLocationRelativeTo(null);	
		logIn.setVisible(true);
	}

	public class btnLogInClass implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			userName = txtUserName.getText();

			logIn.setVisible(false);

			startUp();
		}
	}

	public void startUp()
	{
		try
		{
			@SuppressWarnings("resource")
			Socket sock = new Socket("127.0.0.1", 4422);
			System.out.println("Trying to connect to server......");
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			Thread remote = new Thread(new RemoteReader());
			System.out.println("Connected to server");
			remote.start();
		}
		catch(Exception x)
		{
			System.out.println("There were problems connecting to server");
			System.out.println(x.getMessage());
		}

		setUpMidi();
		buildGui();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildGui()
	{
		theFrame = new JFrame("Cyber Beatbox");
		//Layout manager for the main panel
		FlowLayout LayoutManager = new FlowLayout();
		LayoutManager.setHgap(2);
		LayoutManager.setVgap(2);
		//Main window panel
		JPanel mainLayout = new JPanel(LayoutManager);

		//Sharing the first panel in half using a grid layout 
		JPanel GridPanel = new JPanel(new GridLayout(2, 1));
		GridPanel.setAutoscrolls(true);
		//Adding the first panel to the main panel

		//Making a layout for the beats area of the beat box
		BorderLayout layout = new BorderLayout();
		JPanel backGround = new JPanel(layout);

		//Making a new panel for the second half of the first panel
		GridLayout layout1 = new GridLayout(2, 1);
		JPanel backGround1 = new JPanel(layout1);

		//Adding the two panels for the first main panel
		GridPanel.add(backGround);
		GridPanel.add(backGround1);
		//Set the border of components on the panel
		backGround.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


		BeatsStudio = new JFrame("Recording Studio");
		checkBoxList = new ArrayList<JCheckBox>();

		//Creating a text area for the output of the llvm
		LLVMOutput = new JTextArea();
		//Making sure that it's not editable
		LLVMOutput.setEditable(false);
		//LLVMOutput.setPreferredSize(new Dimension(100,100));
		LLVMOutput.setVisible(true);
		//Adding a scroll bar to the LLVM's text area
		JScrollPane outPutScroll = new JScrollPane(LLVMOutput);
		//Setting the properties of the the scroll bar
		outPutScroll.setAutoscrolls(true);
		outPutScroll.setPreferredSize(new Dimension(100, 100));
		outPutScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		outPutScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//Add text area and scroll bar to the panel
		backGround1.add(outPutScroll);

		//Creating a new text area for file to be loaded in
		txtFile = new JTextArea();
		//txtFile.setSize(arg0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//txtFile.setPreferredSize(new Dimension(550, 350));
		txtFile.setVisible(true);
		//Adding a scroll bar to file text area
		JScrollPane fileScroll = new JScrollPane(txtFile);
		//Setting the properties of the file text area scroll bar
		fileScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fileScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		fileScroll.setAutoscrolls(true);
		//fileScroll.setSize(new Dimension(550,350));
		fileScroll.setPreferredSize(new Dimension(550, 380));
		//Creating a new Panel for the text area
		JPanel fileTextAreaPanel = new JPanel(new GridLayout(2,1));
		fileTextAreaPanel.setAutoscrolls(true);

		JPanel fileMan = new JPanel(new BorderLayout());
		fileMan.add(BorderLayout.CENTER, fileScroll);

		fileArea = new JLabel("File");
		fileMan.add(BorderLayout.NORTH, fileArea);

		JLabel RecordingAreaPanel = new JLabel("Control Panel");
		fileMan.add(BorderLayout.SOUTH, RecordingAreaPanel);

		fileTextAreaPanel.add(fileMan);

		JLabel BeatMixer = new JLabel("Beat Mixing Area");
		BeatMixer.setVisible(true);

		JPanel MixerLabelPanel = new JPanel(new FlowLayout());
		MixerLabelPanel.add(BeatMixer);

		JPanel RecordingArea = new JPanel(new FlowLayout());

		openFile = new JButton("Open File");
		openFile.addActionListener(new openFileListener());
		RecordingArea.add(openFile);

		convert = new JButton("Convert Text");
		convert.addActionListener(new convertListener());
		RecordingArea.add(convert);

		to = new JLabel(" to: ");
		RecordingArea.add(to);

		Languages = new JComboBox(Lang);
		Languages.setSelectedIndex(0);
		RecordingArea.add(Languages);

		convertToAudio = new JButton("Convert Text to Audio");
		convertToAudio.addActionListener(new filechoose());
		RecordingArea.add(convertToAudio);
		panel = new JPanel(new BorderLayout());

		PlayRecording = new JButton("Play");
		PlayRecording.addActionListener(new runPlay());
		StopRecording = new JButton("Stop");
		PauseRecording = new JButton("Pause");
		StartRecording = new JButton("Start Recording");
		StartRecording.addActionListener(new StartRecord());
		StopRec = new JButton("Stop Recording");
		StopRec.addActionListener(new StopRecord());

		JPanel musicPanel = new JPanel(new FlowLayout());
		musicPanel.add(PlayRecording);
		musicPanel.add(PauseRecording);
		musicPanel.add(StopRecording);

		musicPanel.add(StartRecording);
		musicPanel.add(StopRec);

		JPanel gridMusic = new JPanel(new GridLayout(2,1));

		gridMusic.add(musicPanel);
		//gridMusic.add(RecPanel);

		panel.add(BorderLayout.CENTER, gridMusic);
		panel.add(BorderLayout.NORTH, RecordingArea);
		fileTextAreaPanel.add(panel);

		mainLayout.add(GridPanel);
		mainLayout.add(fileTextAreaPanel);
		JPanel boxPanel = new JPanel(new BorderLayout());
		JPanel buttonBox = new JPanel(new BorderLayout());
		JButton start = new JButton("Start Beat");
		start.addActionListener(new MyStartListener());
		boxPanel.add(BorderLayout.NORTH, start);

		JButton stop = new JButton("Stop Beat");
		stop.addActionListener(new MyStopListener());
		boxPanel.add(BorderLayout.CENTER, stop);

		JButton upTempo = new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());
		boxPanel.add(BorderLayout.EAST, upTempo);
		JPanel MessagePanel = new JPanel(new BorderLayout());
		MessagePanel.add(BorderLayout.CENTER, buttonBox);
		MessagePanel.add(BorderLayout.NORTH, boxPanel);
		JButton downTempo = new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());
		boxPanel.add(BorderLayout.WEST, downTempo);
		boxPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		userMessage = new JTextField();
		userMessage.setToolTipText("Type Message and click \n send to broadcast a message");
		userMessage.setSize(new Dimension(15, 30));
		JPanel sendButton = new JPanel(new FlowLayout());
		boxPanel.add(BorderLayout.SOUTH, userMessage);

		JButton sendIt = new JButton("Send");
		sendIt.addActionListener(new MySendListener());

		//theList.setSize(new Dimension(20, 10));

		sendButton.add(sendIt);
		buttonBox.add(BorderLayout.NORTH, sendButton);

		incomingList = new JList();
		incomingList.addListSelectionListener(new MyListSelectionListener());
		incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList = new JScrollPane(incomingList);
		buttonBox.add(BorderLayout.CENTER, theList);
		incomingList.setListData(listVector);

		Box nameBox = new Box(BoxLayout.Y_AXIS);

		for(int i = 0; i < 16; i++)
		{
			nameBox.add(new Label(instrumentNames[i]));
		}
		backGround.add(BorderLayout.NORTH, MixerLabelPanel);
		backGround.add(BorderLayout.EAST, MessagePanel);
		backGround.add(BorderLayout.WEST, nameBox);

		LLVMout = new JLabel("LLVM's Output: ");
		backGround.add(BorderLayout.SOUTH, LLVMout);
		GridLayout grid = new GridLayout(16,16);
		grid.setVgap(0);
		grid.setHgap(0);
		mainPanel = new JPanel(grid);

		backGround.add(BorderLayout.CENTER, mainPanel);

		for (int i = 0; i < 256; i++)
		{
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkBoxList.add(c);
			mainPanel.add(c);
		}

		int height = screenSize.height - 40; 
		int width = screenSize.width - 20; 

		//Add main panel to the window
		theFrame.add(mainLayout);
		// set the main JFrame height and width
		theFrame.setPreferredSize(new Dimension(width, height));
		theFrame.setBounds(10,1,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}

	public void setUpMidi()
	{
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}
		catch(Exception x)
		{

		}
	}

	public class recordingStudio implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			BeatsStudio.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

			BorderLayout layout = new BorderLayout();
			JPanel backGround = new JPanel(layout);
			backGround.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			BeatsStudio.add(backGround);

			Box buttonBox = new Box(BoxLayout.Y_AXIS);

			JButton SelectFolder = new JButton("Choose Song Folder");
			SelectFolder.addActionListener(new FileChooser());
			buttonBox.add(SelectFolder);

			//JButton Record = new JButton("Record");
			//Record.addActionListener(new Record());
			//buttonBox.add(Record);

			JButton StopRecording = new JButton("Stop");
			StopRecording.addActionListener(new StopRecordingClass());
			buttonBox.add(StopRecording);

			JButton Close = new JButton("Close Studio");
			Close.addActionListener(new closeBeatsStudio());
			buttonBox.add(Close);

			backGround.add(BorderLayout.CENTER, buttonBox);

			Box txtBoxArea = new Box(BoxLayout.Y_AXIS);

			txtFilePath = new JTextField(30);	
			txtBoxArea.add(txtFilePath);

			txtMp3Name = new JTextField(30);	
			txtBoxArea.add(txtMp3Name);

			backGround.add(BorderLayout.EAST, txtBoxArea);

			BeatsStudio.setBounds(50,50,300,300);
			BeatsStudio.pack();
			BeatsStudio.setVisible(true);
		}	
	}

	public class StopRecordingClass implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			StopRecording();
		}	

		public void StopRecording()
		{
			line.stop();
			line.close();
			System.out.println("Recording Stopped");
		}
	}

	public class filechoose implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			new Thread()
			{
				public void run()
				{
					new filechoose().runLLVM();
				}
			}.start();
		}

		private void runLLVM()
		{
			LLVMOutput.setText("Processing......");
			PlayRecording.setEnabled(false);

			try 
			{
				conn = new Socket(InetAddress.getLocalHost(), 9090);
			} 
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

			try
			{
				output = conn.getOutputStream();
				input = conn.getInputStream();

				String text = txtFile.getText();
				int read = 0;
				double bytes = text.length();
				System.out.println("bytes : " + bytes);
				String filesize=Double.toString(bytes);
				output.write(filesize.getBytes());
				output.flush();

				output.write(text.getBytes());

				//Retrieve from Java starts
				byte buffer1[] = new byte[1024];
				read = input.read(buffer1);
				System.out.println("okay   "+new String(buffer1, 0, read));
				int newfilesize1 = Integer.parseInt(new String(buffer1, 0, read));
				System.out.println(newfilesize1);
				int recieve1=0,total1=0;
				buffer1 = new byte[(1024*1024)*25];
				FileOutputStream out1 = new FileOutputStream("llvmresults.txt");

				while(total1<newfilesize1)
				{
					recieve1 = input.read(buffer1,0,1024*8);
					if(recieve1 != -1)
					{
						fileField.setText(buffer1.toString());
						out1.write(buffer1, 0, recieve1);
						System.out.println(recieve1);
						total1+=recieve1;
					}
				}

				out1.close();

				//Retrieve from Java ends
				FileReader reader = null;
				try 
				{
					reader = new FileReader("llvmresults.txt");
				} 
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}

				try 
				{
					LLVMOutput.read(reader, "llvmresults.txt");
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				} 

				byte buffer2[] = new byte[1024];
				read = input.read(buffer2);
				int newfilesize = 0; 
				System.out.println("okay   "+new String(buffer1, 0, read));
				newfilesize = Integer.parseInt(new String(buffer2, 0, read));


				recieve1 = total1 = 0;
				buffer2 = new byte[(1024*1024)*25];
				FileOutputStream out2 = new FileOutputStream("ttstemp.mp3");

				while(total1<newfilesize)
				{
					recieve1 = input.read(buffer1,0,1024*8);
					if(recieve1 != -1)
					{
						fileField.setText(buffer1.toString());
						out2.write(buffer1, 0, recieve1);
						System.out.println(recieve1);
						total1+=recieve1;
					}
				}
				out2.close();
			} 
			catch (IOException x) 
			{
				System.err.format("IOException: %s%n", x);
			}
			PlayRecording.setEnabled(true);

			try 
			{
				
				URL url = new File("C:\\Users\\Randy\\Desktop\\Eclipse\\DPLClient\\ttstemp.mp3").toURL();
				player = Manager.createRealizedPlayer(url);
			} 
			catch (NoPlayerException | CannotRealizeException | IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public class getMP3 
	{
		void collectMp3FromServer()
		{
			while(true)
			{

			}
		}
	}

	public class FileChooser implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser PChooser = new JFileChooser();
			PChooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);

			int fileValue = PChooser.showOpenDialog(PChooser);

			if(fileValue == JFileChooser.APPROVE_OPTION)
			{
				mp3Path = PChooser.getSelectedFile().getAbsolutePath();
				txtFilePath.setText(mp3Path);
			}
		}
	}

	public class StartRecord implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{

			new Thread()
			{
				public void run()
				{
					Recorder();
				}
			}.start();
		}

		void start() 
		{
			StartRecording.setEnabled(false);
			try 
			{
				AudioFormat format = getAudioFormat();
				DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

				// checks if system supports the data line
				if (!AudioSystem.isLineSupported(info)) 
				{
					System.out.println("Line not supported");
					System.exit(0);
				}
				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format);
				line.start();   // start capturing

				System.out.println("Start capturing...");

				AudioInputStream ais = new AudioInputStream(line);

				System.out.println("Start recording...");

				// start recording
				AudioSystem.write(ais, fileType, wavFile);
			} 
			catch (LineUnavailableException ex) 
			{
				ex.printStackTrace();
			} 
			catch (IOException ioe) 
			{
				ioe.printStackTrace();
			}

		}

		@SuppressWarnings("unused")
		void Recorder()
		{
			//final JavaSoundRecorder recorder = new JavaSoundRecorder();

			// creates a new thread that waits for a specified
			// of time before stopping
			Thread stopper = new Thread(new Runnable() 
			{
				public void run() 
				{
					try 
					{
						Thread.sleep(RECORD_TIME);
					}
					catch (InterruptedException ex) 
					{
						ex.printStackTrace();
					}
					finish();
				}
			});
			start();
			// start recording
			start();
		}

		void finish()
		{
			line.stop();
			line.close();
			StartRecording.setEnabled(true);
			System.out.println("Finished");
		}

		/**
		 * Defines an audio format
		 */
		AudioFormat getAudioFormat() 
		{
			float sampleRate = 16000;
			int sampleSizeInBits = 8;
			int channels = 2;
			boolean signed = true;
			boolean bigEndian = true;
			AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
			return format;
		}
	}

	public class StopRecord implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{

			new Thread()
			{
				public void run()
				{
					finish();
				}
			}.start();
		}

		void finish()
		{
			line.stop();
			line.close();
			StartRecording.setEnabled(true);
		}
	}

	public class closeBeatsStudio implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			BeatsStudio.setVisible(false);
		}
	}

	@SuppressWarnings("static-access")
	public void buildTrackAndStart()
	{
		ArrayList<Integer> trackList = null;
		sequence.deleteTrack(track);
		track = sequence.createTrack();

		for(int i = 0; i < 16; i++)
		{
			trackList = new ArrayList<Integer>();

			for(int j = 0; j < 16; j++)
			{
				JCheckBox jc = (JCheckBox) checkBoxList.get(j + (16 * i));

				if(jc.isSelected())
				{
					int key = instruments[i];
					trackList.add(new Integer(key));
				}
				else
				{
					trackList.add(null);
				}
			}
			makeTracks(trackList);
		}
		track.add(makeEvent(192, 9, 1, 0, 15));

		try
		{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}
		catch(Exception x)
		{

		}
	}

	public class convertListener implements ActionListener
	{
		String s[] = null;
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			s = txtFile.getText().split("\\r?\\n");
			txtFile.setText("Processing translation");
			new Thread(new Runnable() 
			{
				public void run() 
				{
					translate();
				}
			}).start();
		}
		
		public void translate()
		{
		
			if (! s[0].isEmpty())
			{
				PythonProcess proc = new PythonProcess();
				String convertedText = "";
				txtFile.setText(convertedText);
				for (int i = 0, j = s.length; i < j; i++)
				{
					switch(Languages.getSelectedIndex())
					{
					case 0: 
						convertedText += proc.process(s[i], "en");
						convertedText += "\n";
						break;
					case 1: 
						convertedText += proc.process(s[i], "es");
						convertedText += "\n";
						break;
					case 2: 
						convertedText += proc.process(s[i], "fr");
						convertedText += "\n";
						break;
					case 3:
						convertedText += proc.process(s[i], "ge");
						convertedText += "\n";
						break;
					default:
						convertedText += proc.process(s[i], "ru");
						convertedText += "\n";
						break;
					}
				}
				txtFile.setText(convertedText);
			}
		}
	}

	public class convertToAudioListener 
	{
		public void PerformedAction() 
		{
			int read = 0;

			try 
			{
				conn = new Socket(InetAddress.getLocalHost(), 9091);
			} 
			catch (UnknownHostException x)
			{
				x.printStackTrace();
			} 
			catch (IOException ex)
			{
				ex.printStackTrace();
			} 

			//Retrieve from Java ends
			byte buffer2[] = new byte[1024];
			try 
			{
				read = input.read(buffer2);
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}

			int newfilesize = 0; 

			newfilesize = Integer.parseInt(new String(buffer2, 0, read));

			System.out.println(newfilesize);
			int recieve = 0, total = 0;

			buffer2 = new byte[(1024*1024)*25];
			FileOutputStream out = null;
			try
			{
				out = new FileOutputStream("ttstemp.mp3");
			} 
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}

			while(total<newfilesize)
			{
				try 
				{
					recieve=input.read(buffer2,0,1024*8);
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				if(recieve!=-1)
				{
					try 
					{
						out.write(buffer2,0,recieve);
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(recieve);
					total+=recieve;
				}
			}

			try 
			{
				out.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public class openFileListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setCurrentDirectory(new File("/User/alvinreyes"));
			int result = jFileChooser.showOpenDialog(new JFrame());

			if (result == JFileChooser.APPROVE_OPTION) 
			{
				File selectedFile = jFileChooser.getSelectedFile();

				FileReader reader = null;
				try 
				{
					reader = new FileReader(selectedFile.getAbsolutePath());
				} 
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try 
				{
					txtFile.read(reader, selectedFile.getAbsolutePath());
				}
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}

	public class MyStartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			buildTrackAndStart();
		}	
	}

	public class MyStopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			sequencer.stop();
		}
	}

	public class MyUpTempoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * 1.03));
		}
	}

	public class MyDownTempoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * .97));
		}
	}

	public class MySendListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			boolean[] checkBoxState = new boolean[256];

			for (int i = 0; i < 256; i++)
			{
				JCheckBox check = (JCheckBox) checkBoxList.get(i);

				if(check.isSelected())
				{
					checkBoxState[i] = true;
				}
			}

			@SuppressWarnings("unused")
			String messageToSend = null;

			try
			{
				out.writeObject(userName + nextNum++ + ":" + userMessage.getText());
				out.writeObject(checkBoxState);
			}
			catch(Exception x)
			{

			}

			userMessage.setText("");
		}
	}

	public class MyListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent i)
		{
			if(!i.getValueIsAdjusting())
			{
				String select = (String) incomingList.getSelectedValue();
				if (select != null)
				{
					boolean[] selectedState = (boolean[]) otherSeqMap.get(select);
					changeSequence(selectedState);
					sequencer.stop();
					buildTrackAndStart();
				}
			}
		}
	}

	public class RemoteReader implements Runnable
	{
		boolean[] checkBoxState = null;
		String nameToShow = null;
		Object obj = null;

		@SuppressWarnings("unchecked")
		public void run()
		{
			try
			{
				while((obj = in.readObject()) != null)
				{
					String nameToShow = (String) obj;
					checkBoxState = (boolean[]) in.readObject();
					otherSeqMap.put(nameToShow, checkBoxState);
					listVector.add(nameToShow);
					incomingList.setListData(listVector);
				}
			}
			catch(Exception x)
			{
				System.out.println(x.getMessage());
			}
		}
	}

	public class MyPlayMineListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(mySequence != null)
			{
				sequence = mySequence;
			}
		}
	}

	public void changeSequence(boolean[] checkBoxState)
	{
		for(int i = 0; i < 256; i++)
		{
			JCheckBox check = (JCheckBox) checkBoxList.get(i);

			if(checkBoxState[i])
			{
				check.setSelected(true);
			}
			else
			{
				check.setSelected(false);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void makeTracks(ArrayList list)
	{
		Iterator it = list.iterator();

		for (int i = 0; i < 16; i++)
		{
			Integer num = (Integer) it.next();

			if (num != null)
			{
				int numKey = num.intValue();

				track.add(makeEvent(144, 9, numKey, 100, i));
				track.add(makeEvent(128, 9, numKey, 100, i));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick)
	{
		MidiEvent event = null;

		try
		{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		}
		catch(Exception x)
		{

		}
		return event;
	}

	public class runPlay implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			new Thread(new Runnable() 
			{
				public void run() 
				{
					play();
				}
			}).start();
		}

		public void play()
		{
			try
			{
				player.start();
			}
			catch(NullPointerException nl)
			{
				JOptionPane.showMessageDialog(BeatsStudio, "No Audio File has been loaded as yet");
			}
			catch (Exception x)
			{
				x.printStackTrace();
			}
		}
	}

	public class runStop implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			new Thread(new Runnable() 
			{
				public void run() 
				{
					stop();
				}
			}).start();
		}

		public void stop()
		{
			try
			{
				player.stop(); 
				player.setMediaTime(new Time(0.0));
			}
			catch(NullPointerException nl)
			{
				JOptionPane.showMessageDialog(BeatsStudio, "No Audio File has been loaded as yet");
			}
		}
	}

	public class runPause implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			new Thread(new Runnable() 
			{
				public void run() 
				{
					pause();
				}
			}).start();
		}

		public void pause()
		{
			try
			{
				player.stop();
			}
			catch(NullPointerException nl)
			{
				JOptionPane.showMessageDialog(BeatsStudio, "No Audio File has been loaded as yet");
			}

		}
	}
}
