
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.sql.*;
import java.io.*;
import java.applet.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Atmcardno extends JFrame implements ActionListener
 {
    JFrame jf;
    Font f,f1;
	JButton b1,b2,b3;
	JLabel l1,l2,l3,l4;
	ImageIcon img1;
    JTextField t1;
   	JPasswordField pwd;
   	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
    File wavFile = new File("clicksound.au");
    AudioClip sound;
   // Timer t;
	int atno,acno,pno;
	String curdate;
    Date date;
    GregorianCalendar calendar;


    public Atmcardno()
     {
     	date= new Date();
     	calendar=new GregorianCalendar();
	    calendar.setTime(date);
	    curdate =calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
	    System.out.println(curdate);

     	jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

		l4=new JLabel("ATM Card Number");
	    l4.setFont(new Font("Times New ROman",Font.BOLD,30));
	    l4.setForeground(Color.BLUE);
		l4.setBounds(250,250,300,30);
		jf.add(l4);

    	l2=new JLabel("Enter ATM card no:");
		l2.setFont(f1);
		l2.setForeground(Color.WHITE);
		l2.setBounds(120,380,250,30);
		jf.add(l2);

		t1 = new JTextField(20);
		t1.setBounds(370,380,200,30);
		jf.add(t1);

		l3=new JLabel("Enter PIN no:");
		l3.setFont(f1);
		l3.setForeground(Color.WHITE);
		l3.setBounds(120,430,250,30);
		jf.add(l3);

		pwd=new JPasswordField(10);
		pwd.setFont(f1);
		pwd.setBounds(370,430,200,30);
		jf.add(pwd);

		b1=new JButton("Enter",new ImageIcon("ok.png"));
		b1.setFont(f);
		b1.setBounds(120,550,130,40);jf.add(b1);
		b1.addActionListener(this);

		b2=new JButton("Clear",new ImageIcon("clear.png"));
		b2.setFont(f);
		b2.setBounds(280,550,130,40);jf.add(b2);
		b2.addActionListener(this);

		b3=new JButton("Cancel",new ImageIcon("cancel.png"));
		b3.setFont(f);
		b3.setBounds(440,550,130,40);jf.add(b3);
		b3.addActionListener(this);

        img1=new ImageIcon("bank.jpg");
		l1=new JLabel(img1);
		l1.setBounds(1,1,800,700);
        jf.add(l1);

         jf.setTitle("ATM CARD NO");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);

	   //t =new Timer(20000,this);
      // t.start();

    }

     public void actionPerformed(ActionEvent ae)
     {
      	//JOptionPane.showMessageDialog(null,"Your time is out for doing transaction");
     //	 new Welcome();
     	// jf.setVisible(false);
     	 //t.stop();

		if(ae.getSource()==b1)
		{
		//fetch
           	sound.play();
	    try
	     {
	   		if(((t1.getText()).equals(""))&&((pwd.getText()).equals("")))
	        {
		    JOptionPane.showMessageDialog(this,"Please enter ATM card no and PIN no!","Warning",JOptionPane.WARNING_MESSAGE);
		    sound.play();
	        }
	        else
	        {
	        int foundrec = 0;
	        Class.forName("com.mysql.jdbc.Driver");
		    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		    System.out.println("Connected to database.");
            ps=con.prepareStatement("select * from accountdetail where atmno='"+t1.getText()+"' and pinno='"+pwd.getText()+"'");
	        rs=ps.executeQuery();
		    while(rs.next())
	        {

	        atno=rs.getInt(1);   System.out.println(atno);
	        acno=rs.getInt(2);   System.out.println(acno);
	        pno=rs.getInt(3);     System.out.println(pno);
	        String cardname=rs.getString(5);  System.out.println(cardname);
	        String expdate=rs.getString(7);

	        try
        	{

    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		Date date1 = sdf.parse(expdate);
        	//Date date1 = sdf.parse("2009-12-31");
        	Date date2 = sdf.parse(curdate);

        	System.out.println("expiry date of atm card:"+sdf.format(date1));
        	System.out.println("Today date:"+sdf.format(date2));

        	if(date1.compareTo(date2)>=0)
        	{
        		System.out.println("Expiry date of atm:"+sdf.format(date1)+"is after todat date:"+sdf.format(date2));
        		 JOptionPane.showMessageDialog(null,"Hello "+cardname);
	             sound.play();
	             new AccountType(atno,acno,pno);
	             jf.setVisible(false);
        	}
        	else if(date1.compareTo(date2)<0)
        	{
        		System.out.println("Date1 is before Date2");
        JOptionPane.showMessageDialog(this,"Your atm card is out of expiry date.Please take new ATM card from your home bank.","Warning",JOptionPane.WARNING_MESSAGE);
		    sound.play();
	             new Welcome();
	             jf.setVisible(false);

        	}
        	}
        	catch(ParseException ex)
        	{
        	System.out.println("Exception in date format"+ex);
    		ex.printStackTrace();
    	    }

	        foundrec = 1;

	       }
	       if (foundrec == 0)
             {
              JOptionPane.showMessageDialog(null,"Invalid ATM card no or PIN no.","Warning",JOptionPane.WARNING_MESSAGE);
              sound.play();
              t1.setText("");
              pwd.setText("");
             }
  	      } con.close();
        }
        catch(SQLException se)
		{
		System.out.println(se);
	    // JOptionPane.showMessageDialog(null,"SQL Error:"+se);
        }
	    catch(Exception e)
	     {
	     System.out.println(e);
		 //JOptionPane.showMessageDialog(null,"Error:"+e);
	     }
       }
		else if(ae.getSource()==b2)
		{
			sound.play();
			t1.setText("");
			pwd.setText("");

		}
		else if(ae.getSource()==b3)
		{
			sound.play();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
           	new Welcome();
           	jf.setVisible(false);
		}
     }
    /* public static void main(String args[])
	  {
		new Atmcardno();
	 }*/
}