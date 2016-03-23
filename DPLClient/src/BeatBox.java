
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.*;


import java.util.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.event.*;

import javazoom.jl.decoder.JavaLayerException;


import java.io.*;

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
		private JButton button;
		private JPanel panel;
		private JLabel label;
		private JComboBox Languages;
		private JFileChooser filechooser;
		private Socket conn;
		private OutputStream output;
		private InputStream input;
		private JTextArea LLVMOutput;
		
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
			fileField= new JTextField(35);
			
			btnLogIn.addActionListener(new btnLogInClass());
			Panel = new JPanel();
			
			Panel.add(lblUserName);
			Panel.add(txtUserName);
			Panel.add(btnLogIn);
			
			logIn.add(Panel);
			logIn.setSize(400, 500);
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
				
				JButton Record = new JButton("Record");
				Record.addActionListener(new Record());
				buttonBox.add(Record);
				
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
					 JFileChooser jFileChooser = new JFileChooser();
						
						jFileChooser.setCurrentDirectory(new File("/User/alvinreyes"));
						
						int result = jFileChooser.showOpenDialog(new JFrame());
						
						StringBuilder sb= new StringBuilder();
						
						try 
						{
							conn = new Socket(InetAddress.getLocalHost(), 9090);
						} 
						catch (UnknownHostException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						    
						if (result == JFileChooser.APPROVE_OPTION) 
						{
						
						     File selectedFile = jFileChooser.getSelectedFile();
						
						     System.out.println("Selected file: " + selectedFile.getAbsolutePath());
						     
						     fileField.setText(selectedFile.getAbsolutePath());
						     
						     //java.io.File file = filechooser.getSelectedFile();
						     
						     try
						     {
						    	 FileReader fileReader = new FileReader(selectedFile.getAbsolutePath());
						    	 
						    	 BufferedReader reader = new BufferedReader(fileReader);
						    	 
						    	 FileInputStream io = new FileInputStream(selectedFile.getAbsolutePath());
						    	 
						    	    String line = null;
						    	    int read = 0;
						    	    
						    	
										
								
						    	  //get file send to translator
										PythonProcess proc = new PythonProcess();
										
										 BufferedWriter output1 = null;
									        try {
									            File file = new File("translate.txt");
									            output1 = new BufferedWriter(new FileWriter(file));
		
									        } catch ( IOException e ) {
									            e.printStackTrace();
									        } finally {
									            if ( output != null ) output.close();
									        }
									        String line2 = "", lines = "";
									        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
									            
									            while ((line2 = br.readLine()) != null) 
									            {
									            	
									            	switch(Languages.getSelectedIndex())
													  {
														  case 0: 
															  lines += proc.process(line2, "en");
															  break;
														  case 1: 
															  lines += proc.process(line2, "es");
															  break;
														  case 2: 
															  lines += proc.process(line2, "fr");
															  break;
														  case 3:
															  lines += proc.process(line2, "ge");
															  break;
														  default:
															  lines += proc.process(line2, "ru");
															  break;
													  }
									               // process the line.
									            }
									        }
									        File file = new File("Translate.txt");

											// if file doesnt exists, then create it
											if (!file.exists()) {
												file.createNewFile();
											}

											FileWriter fw = new FileWriter(file.getAbsoluteFile());
											BufferedWriter bw = new BufferedWriter(fw);
											bw.write(lines);
											bw.close();
										
										
					    			//frtgyhuikop
					    			//File file =new File(selectedFile.getAbsolutePath());
					    			
											output = conn.getOutputStream();
											
											
											input = conn.getInputStream();
					    			
					    			
					    			if(file.exists()){
					    				
					    				double bytes = file.length();
					    				
					    				 System.out.println("bytes : " + bytes);
					
					    				 String filesize=Double.toString(bytes);
					    				
					    				 output.write(filesize.getBytes());
					    				 
					    				 output.flush();
					    				 
					    				 byte buffer[] = new byte[1024];
					    				 
										while ((read = io.read(buffer, 0, 1023)) != -1)
										{
											System.out.println("file size "+read);
										    System.out.println(buffer);
										    output.write(buffer, 0, read);//String.valueOf(usernamefield.getText()));
										}
										
										
										//Retrieve from Java starts
										
										byte buffer1[] = new byte[1024];
										
										read = input.read(buffer1);
										
										System.out.println("okay   "+new String(buffer1, 0, read));
										
										int newfilesize1=Integer.parseInt(new String(buffer1, 0, read));
										
										System.out.println(newfilesize1);
										
										int recieve1=0,total1=0;
										
										buffer1 = new byte[(1024*1024)*25];
										
										FileOutputStream out1 = new FileOutputStream("llvmresults.txt");
										
										String answer="";
										
										while(total1<newfilesize1)
										{
											recieve1=input.read(buffer1,0,1024*8);
											if(recieve1!=-1){
										
												fileField.setText(buffer1.toString());
												out1.write(buffer1,0,recieve1);
												System.out.println(recieve1);
												total1+=recieve1;
											}
										}
										
										out1.close();
										
										
										
										//Retrieve from Java ends
										
										byte buffer2[] =new byte[1024];
										
										buffer2 = new byte[1024];
									
										read = input.read(buffer2);
										//System.out.println("okay   "+new String(buffer, 0, read));
										
										int newfilesize=Integer.parseInt(new String(buffer2, 0, read));
										
										System.out.println(newfilesize);
										int recieve=0,total=0;
										
										buffer2 = new byte[(1024*1024)*25];
										
										FileOutputStream out = new FileOutputStream("ttstemp.mp3");
										
										while(total<newfilesize)
										{
											recieve=input.read(buffer2,0,1024*8);
											if(recieve!=-1){
												out.write(buffer2,0,recieve);
												System.out.println(recieve);
												total+=recieve;
											}
										}
										
										
										out.close();
										
										
										BufferedReader br;
								        try {
								            br = new BufferedReader(new FileReader("llvmresults.txt"));
								            try {
								                String x;
								                while ( (x = br.readLine()) != null ) {
								                    // printing out each line in the file
								                    LLVMOutput.setText(x);
								                    
								                } 
								            } catch (IOException e) {
								                e.printStackTrace();
								            }
								        } catch (FileNotFoundException e) {
								            System.out.println(e);
								            e.printStackTrace();
								        }

					    			}
					    			else
					    			{
					    				 System.out.println("File does not exists!");
					    			}
						    	    
						    } 
						    catch (IOException x) 
						     {
						    	    System.err.format("IOException: %s%n", x);
						     }
						     
						}
					
					
						try {
				    		 // the player actually doing all the work
				    		FileInputStream input = new FileInputStream("ttstemp.mp3");
				    	    
				    		mp3driver player = new mp3driver(input);
				         
				        } 
						catch (final Exception e) 
						{
				            throw new RuntimeException(e);
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
		
		public class Record implements ActionListener
		{
			// record duration, in milliseconds
		    static final long RECORD_TIME = 600;  // 1 minute
		 
		    // path of the wav file
		    File wavFile = new File("ww");
		 
		    // format of audio file
		    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		 
		    // the line from which audio data is captured

		    
			public void actionPerformed(ActionEvent e) 
			{
				new Thread(){
					public void run()
					{
						Recorder();
					}
				}.start();
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
		    /**
		     * Captures the sound and record into a WAV file
		     */
		    void start() 
		    {
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
		    
		    void finish()
		    {
		        line.stop();
		        line.close();
		        System.out.println("Finished");
		    }
		    
		    void Recorder()
		    {
		    	if(txtMp3Name.getText() == "")
		    	{
		    		System.out.println("You need to suply a file name");
					return;
		    	}
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
		}
		
		public class closeBeatsStudio implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				BeatsStudio.setVisible(false);
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
			theFrame.setSize(800, 800);
			BorderLayout layout = new BorderLayout();
			JPanel backGround = new JPanel(layout);

			backGround.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			BeatsStudio = new JFrame("Recording Studio");
			checkBoxList = new ArrayList<JCheckBox>();
			button = new JButton("Upload File");
			button.addActionListener(new filechoose());
			
			LLVMOutput = new JTextArea();
			LLVMOutput.setEditable(false);
			LLVMOutput.setSize(200, 200);
			Box textArea = new Box(BoxLayout.LINE_AXIS);
			textArea.add(LLVMOutput);
			textArea.setSize(200, 200);
			Box buttonBox = new Box(BoxLayout.Y_AXIS);
		
			//BorderLayout layoutText = new BorderLayout();
			//JPanel txtOutputArea = new JPanel(layoutText);
			//txtOutputArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			JButton start = new JButton("Start Beat");
			start.addActionListener(new MyStartListener());
			buttonBox.add(start);
		
			JButton stop = new JButton("Stop Beat");
			stop.addActionListener(new MyStopListener());
			buttonBox.add(stop);
		
			JButton upTempo = new JButton("Tempo Up");
			upTempo.setSize(40, 10);
			upTempo.addActionListener(new MyUpTempoListener());
			buttonBox.add(upTempo);
		
			
			JButton downTempo = new JButton("Tempo Down");
			downTempo.addActionListener(new MyDownTempoListener());
			buttonBox.add(downTempo);
		
			userMessage = new JTextField();
			userMessage.setToolTipText("Type Message and click \n send to broadcast a message");
			buttonBox.add(userMessage);
		
			JButton sendIt = new JButton("Send Message");
			sendIt.addActionListener(new MySendListener());
			buttonBox.add(sendIt);
			
			JButton RecordingStudio = new JButton("Recording Studio");
			RecordingStudio.addActionListener(new recordingStudio());
			buttonBox.add(RecordingStudio);
			
			Languages = new JComboBox(Lang);
			Languages.setSelectedIndex(0);
			buttonBox.add(Languages);
			
			buttonBox.add(button);
			incomingList = new JList();
			incomingList.addListSelectionListener(new MyListSelectionListener());
			incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane theList = new JScrollPane(incomingList);
			buttonBox.add(theList);
			incomingList.setListData(listVector);
		
			Box nameBox = new Box(BoxLayout.Y_AXIS);
		
			for(int i = 0; i < 16; i++)
			{
				nameBox.add(new Label(instrumentNames[i]));
			}
			//txtOutputArea.add(textArea);
			backGround.add(BorderLayout.EAST, buttonBox);
			backGround.add(BorderLayout.WEST, nameBox);
			backGround.add(BorderLayout.SOUTH, textArea);
			theFrame.getContentPane().add(backGround);
			//theFrame.getContentPane().add(txtOutputArea);
			GridLayout grid = new GridLayout(16,16);
			grid.setVgap(1);
			grid.setHgap(2);
			mainPanel = new JPanel(grid);
		
			backGround.add(BorderLayout.CENTER, mainPanel);
		
			for (int i = 0; i < 256; i++)
			{
				JCheckBox c = new JCheckBox();
				c.setSelected(false);
				checkBoxList.add(c);
				mainPanel.add(c);
			}
		
			theFrame.setBounds(50,50,300,300);
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
}