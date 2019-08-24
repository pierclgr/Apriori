package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * <p>Title: Apriori</p>
 * <p>Description: Apriori GUI.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
 * <p>Class description: This class is used to handle apriori's graphic user interface.</p>
 * @author Pier
 * @version 1.0
 */
class Apriori extends JFrame{
	private static final long serialVersionUID = 4924789673912687777L;
	private JTextArea rulesAreaTxt;
	private JTextArea msgAreaTxt;
	private JRadioButton db;
	private JRadioButton file;
	private JTextField nameDataTxt;
	private JTextField nameFileTxt;
	private JTextField minSupTxt;
	private JTextField minConfTxt;
	private JLabel dataTxt;
	private JLabel fileTxt;
	private JLabel minSupLabel;
	private JLabel minConfLabel;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket socket;
	/**
	 * This constructor method creates the default GUI shown to the user on program startup.
	 */
	private Apriori(){
		setTitle("Apriori");
		setName("Apriori");
		setPreferredSize(new Dimension(1000,485));
		setMinimumSize(new Dimension(1000,485));
		setMaximumSize(new Dimension(1000,485));
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		Container cp=this.getContentPane();
		cp.setLayout(new FlowLayout());
		cp.setBackground(SystemColor.GRAY);
		JPanel cpParameterSetting=new JPanel();
		cpParameterSetting.setPreferredSize(new Dimension(960,150));
		cpParameterSetting.setLayout(new FlowLayout());
		cpParameterSetting.setBackground(SystemColor.lightGray);
		cpParameterSetting.setBorder(BorderFactory.createTitledBorder("Apriori"));
		JPanel cpAprioriMining=new JPanel();
		cpAprioriMining.setLayout(new FlowLayout());
		cpAprioriMining.setBackground(SystemColor.control);
		cpAprioriMining.setBorder(BorderFactory.createTitledBorder("Selecting Data Source"));
		cpParameterSetting.add(cpAprioriMining);
		cp.add(cpParameterSetting);
		ButtonGroup bg=new ButtonGroup();
		db=new JRadioButton("Learning rules from db");
		file=new JRadioButton("Reading rules from file");
		bg.add(db);
		bg.add(file);
		db.setSelected(true);
		cpAprioriMining.add(db);
		cpAprioriMining.add(file);
		
		JPanel cpAprioriInput=new JPanel();
		cpAprioriInput.setLayout(new FlowLayout());
		cpAprioriInput.setBackground(SystemColor.control);
		cpAprioriInput.setBorder(BorderFactory.createTitledBorder("Input Parameters"));
		cpParameterSetting.add(cpAprioriInput);
		dataTxt=new JLabel("Data");
		fileTxt=new JLabel("File name (leave empty to not save)");
		cpAprioriInput.add(dataTxt);
		nameDataTxt=new JTextField();
		nameFileTxt=new JTextField();
		nameDataTxt.setColumns(6);
		nameFileTxt.setColumns(6);
		cpAprioriInput.add(nameDataTxt);
		cpAprioriInput.add(fileTxt);
		cpAprioriInput.add(nameFileTxt);
		minSupLabel=new JLabel("Min Sup");
		cpAprioriInput.add(minSupLabel);
		minSupTxt=new JTextField();
		minSupTxt.setColumns(6);
		cpAprioriInput.add(minSupTxt);
		minConfLabel=new JLabel("Min Conf");
		cpAprioriInput.add(minConfLabel);
		minConfTxt=new JTextField();
		minConfTxt.setColumns(6);
		cpAprioriInput.add(minConfTxt);
		
		JPanel cpMiningCommand=new JPanel();
		cpMiningCommand.setLayout(new FlowLayout());
		cpMiningCommand.setBackground(SystemColor.lightGray);
		cpMiningCommand.setBorder(BorderFactory.createTitledBorder("Mining Command"));
		cp.add(cpMiningCommand);
		JButton aprioriConstructioBt=new JButton("Apriori");
		cpMiningCommand.setPreferredSize(new Dimension(960,65));
		cpMiningCommand.add(aprioriConstructioBt);
		
		JPanel cpRuleViewer=new JPanel();
		cpRuleViewer.setLayout(new FlowLayout());
		cpRuleViewer.setBackground(SystemColor.lightGray);
		cpRuleViewer.setBorder(BorderFactory.createTitledBorder("Patterns and Rules"));
		cpRuleViewer.setPreferredSize(new Dimension(960,205));
		cp.add(cpRuleViewer);
		rulesAreaTxt=new JTextArea();
		msgAreaTxt=new JTextArea();
		msgAreaTxt.setEditable(false);
		msgAreaTxt.setColumns(84);
		msgAreaTxt.setRows(5);
		rulesAreaTxt.setForeground(SystemColor.infoText);
		rulesAreaTxt.setEditable(false);
		rulesAreaTxt.setColumns(84);
		rulesAreaTxt.setRows(5);
		JScrollPane scrollRules=new JScrollPane(rulesAreaTxt);
		cpRuleViewer.add(scrollRules);
		cpRuleViewer.add(msgAreaTxt);
		
		aprioriConstructioBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startApriori();
			}
		});
		
		db.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileTxt.setVisible(true);
				fileTxt.setText("File name (leave empty to not save)");
				nameFileTxt.setVisible(true);
				dataTxt.setVisible(true);
				nameDataTxt.setVisible(true);
				minSupLabel.setVisible(true);
				minSupTxt.setVisible(true);
				minConfLabel.setVisible(true);
				minConfTxt.setVisible(true);
			}
		});
		
		file.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fileTxt.setText("File name");
				fileTxt.setVisible(true);
				nameFileTxt.setVisible(true);
				dataTxt.setVisible(false);
				nameDataTxt.setVisible(false);
				minSupLabel.setVisible(false);
				minSupTxt.setVisible(false);
				minConfLabel.setVisible(false);
				minConfTxt.setVisible(false);
			}
		});
		
		runConnection();
		setVisible(true);
	}
	/**
	 * This method starts the connection between Apriori Client (this class) and Apriori Server.
	 */
	private void runConnection(){
		try{
			InetAddress addr=InetAddress.getByName("127.0.0.1");
			socket=new Socket(addr,8081);
			out=new ObjectOutputStream(socket.getOutputStream());
			in=new ObjectInputStream(socket.getInputStream());
		}catch(IOException e){
			e.printStackTrace();
			msgAreaTxt.setForeground(SystemColor.RED);
			msgAreaTxt.setText(e.getMessage());
		}
	}
	/**
	 * This method executes commands launched by the user through GUI.
	 */
	private void startApriori(){
		float minSup;
		float minConf;
		try{
			if(db.isSelected()){
				if(!nameDataTxt.getText().equals("")){
					String tableName=nameDataTxt.getText();
					if(minSupTxt.getText().equals("")){
						throw new InvalidInputException("Insert min support value");
					}else{
						minSup=(float)Float.parseFloat(minSupTxt.getText());
						if(minSup<0||minSup>1){
							throw new InvalidInputException("Min support value is not between 0 and 1");
						}else{
							if(minConfTxt.getText().equals("")){
								throw new InvalidInputException("Insert min confidence value");
							}else{
								minConf=(float)Float.parseFloat(minConfTxt.getText());
								if(minConf<0||minConf>1){
									throw new InvalidInputException("Min confidence value is not between 0 and 1");
								}else{
									msgAreaTxt.setText("");
									out.writeObject(1);
									out.writeObject(tableName);
									out.writeObject(minSup);
									out.writeObject(minConf);
									rulesAreaTxt.setText((String)in.readObject());
									if(!nameFileTxt.getText().equals("")){
										out.writeObject(2);
										out.writeObject(nameFileTxt.getText());
									}
								}
							}
						}
					}
				}else{
					throw new InvalidInputException("Insert table name");
				}
			}else if(file.isSelected()){
				if(nameFileTxt.getText().equals("")){
					throw new InvalidInputException("Invalid file name");
				}else{
					out.writeObject(3);
					out.writeObject(nameFileTxt.getText());
					rulesAreaTxt.setText((String)in.readObject());
				}
			}
		}catch(InvalidInputException | IOException | ClassNotFoundException e){
			msgAreaTxt.setForeground(SystemColor.RED);
			msgAreaTxt.setText(e.getMessage());
		}
	}
	/**
	* <p>Title: InvalidInputException</p>
	* <p>Description: Invalid input exception class.</p>
	* <p>Copyright: Copyright (c) 2017</p>
	* <p>Company: Dipartimento di Informatica, Università degli studi di Bari</p>
	* <p>Class description: This exception class represent the case where input parameters are invalid.</p>
	* @author Pier
	* @version 1.0
	*/
	private class InvalidInputException extends Throwable{
		private static final long serialVersionUID = 2427530071845892614L;
		/**
		 * This constructor method sets the exception message shown to the user when exception is thrown.
		 * @param msg Message shown to the user when exception is thrown.
		 */
		private InvalidInputException(String msg){
			super(msg);
		}
	}
	/**
	 * This is the main method of Apriori.
	 * @param args Arguments representing input.
	 */
	public static void main(String[] args){
		new Apriori();
	}
}
