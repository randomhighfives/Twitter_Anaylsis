package twitter_app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import twitter4j.TwitterException;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
 
public class PieChart extends JFrame implements ActionListener
{
	private static JFrame mainFrame;
	public static searchTweets obj;
	public static String userinput;
	private static JLabel process = new JLabel("Processing....");

	
    private static JPanel scrollPanel = new JPanel();
    private static ChartPanel graph = new ChartPanel(null);
    private static JPanel start = new JPanel();
	private static JButton backButton = new JButton("Go Back");
	private static JButton back2Search = new JButton("Search Again");
	private static JButton btnSearch = new JButton("Search");

	private static JButton Goodbtn = new JButton("View good tweets");
	private static JButton Badbtn = new JButton("View bad tweets");
	private static JButton Neubtn = new JButton("View neutral tweets");

 	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
 	

	


	
	public static void main( String[ ] args ) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	   {
		  //NLP.init();
	 	 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		  new PieChart();   
	   }
	
   public PieChart() 
   {
	   makeFrame(); 
   }
   
   private void makeFrame(){
	   
	      mainFrame = new JFrame("Twizard");
	      mainFrame.setSize(2000, 1500);
	      mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
	      mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	      mainFrame.getContentPane().add(startPanel());
	      mainFrame.setVisible(true);
   }
   
   private static JPanel startPanel(){
	   JLabel description = new JLabel("Type your keyword");
	   description.setBounds(725, 502, 600, 60);
	   description.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 50));
	   description.setHorizontalAlignment(SwingConstants.CENTER);
	   description.setVerticalAlignment(SwingConstants.CENTER);
	   JTextField search = new JTextField();
	   search.setHorizontalAlignment(SwingConstants.CENTER);
	   search.setBounds(785, 591, 500, 50);
	   search.setColumns(30);
	   start.setLayout(null);
	   start.add(description);
	   start.add(search);
	   JButton btnSearch = new JButton("Search");
	   btnSearch.setBounds(949, 693, 171, 41);
	   start.add(btnSearch);
	   btnSearch.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e){
			   userinput = search.getText();
			   try {
				searchTweets.execute(userinput);
			} catch (TwitterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   
			   start.setVisible(false);
			   createPiePanel();
		   }
	   });
	   
	   return start;
   }
   
   private static PieDataset createDataset( ) 
   {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue( "Positive" , searchTweets.theGood.size());  
      dataset.setValue( "Neutral" , searchTweets.theNeutral.size() );   
      dataset.setValue( "Bad/Inconclusive" , searchTweets.inconclusive.size());    
      return dataset;         
   }
   private static JFreeChart createChart( PieDataset dataset )
   {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Results",  // chart title 
         dataset,        // data    
         true,           // include legend   
         true, 
         false);

      return chart;
   }
   public static void createPiePanel( )
   {
	   
	   JFreeChart chart = createChart(createDataset( ) );  
	   graph = new ChartPanel(chart);
	   graph.setLayout(null);
	   Goodbtn.setPreferredSize(new Dimension(400,400));
	   Goodbtn.setFont(new Font("Arial", Font.PLAIN, 20));
	   Badbtn.setPreferredSize(new Dimension(400,400));
	   Badbtn.setFont(new Font("Arial", Font.PLAIN, 20));
	   Neubtn.setPreferredSize(new Dimension(400,400));
	   Neubtn.setFont(new Font("Arial", Font.PLAIN, 20));
       Goodbtn.setBounds(400, 1300, 220, 70);
       Badbtn.setBounds(1300, 1300, 220, 70);
       Neubtn.setBounds(900, 1300, 220, 70);
       /*back2Search.setPreferredSize(new Dimension(400,400));
	   back2Search.setFont(new Font("Arial", Font.PLAIN, 20));
       back2Search.setBounds(40, 130, 220, 70);
       graph.add(back2Search);
       
       back2Search.addActionListener((new ActionListener() {
		   public void actionPerformed(ActionEvent e){
		      
		   }}));
       
       */
       
	   graph.add(Goodbtn);
	   Goodbtn.addActionListener((new ActionListener() {
		   public void actionPerformed(ActionEvent e){
		       JList<String> displayList = new JList<>(obj.theGood.toArray(new String[0]));
		        JScrollPane scrollPane = new JScrollPane(displayList);
		        scrollPanel.add(scrollPane);
		        scrollPanel.add(backButton);
		        graph.setVisible(false);
		        mainFrame.getContentPane().add(scrollPanel);
		       
		   }}));
	   graph.add(Badbtn);
	   Badbtn.addActionListener((new ActionListener() {
		   public void actionPerformed(ActionEvent e){
		       JList<String> displayList = new JList<>(obj.inconclusive.toArray(new String[0]));
		        JScrollPane scrollPane = new JScrollPane(displayList);
		        scrollPanel.add(scrollPane);
		        scrollPanel.add(backButton);

		        graph.setVisible(false);
		        mainFrame.getContentPane().add(scrollPanel);
		       
		   }}));
	   graph.add(Neubtn);
	   Neubtn.addActionListener((new ActionListener() {
		   public void actionPerformed(ActionEvent e){
		       JList<String> displayList = new JList<>(obj.theNeutral.toArray(new String[0]));
		        JScrollPane scrollPane = new JScrollPane(displayList);
		        scrollPanel.add(scrollPane);
		        scrollPanel.add(backButton);
		        graph.setVisible(false);
		        mainFrame.getContentPane().add(scrollPanel);
		       
		   }}));
	   backButton.addActionListener((new ActionListener(){
       	public void actionPerformed(ActionEvent e){
       		scrollPanel.setVisible(false);
       		graph.setVisible(true);
       		reset();
       	}
       }));
	   mainFrame.getContentPane().add(graph);
   }
   private static void reset(){
	   scrollPanel = new JPanel();
   }
   

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}
}