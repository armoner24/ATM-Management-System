
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;
import java.io.*;
import java.applet.*;

public class ElectricityBillPaid extends JFrame implements ActionListener
 {
 	JFrame jf;
    Font f,f1;
	JButton b1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l31,l41,l51,l61,l55,l551,ll5,ll51;
	ImageIcon img1;
	Connection con;
	PreparedStatement ps,ps1;
	Statement stmt;
	ResultSet rs;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;
    int atno,acno,pno,billno;
    String actype,strdate,strtime,edate;
    float givam,amt,abal,amtmin,sumbal;
    Date date;
    GregorianCalendar calendar;

    public ElectricityBillPaid(int atno1,int acno1,int pno1,String actype1,int billno1,float givam1,String edate1)
    {
    	atno=atno1;
     	acno=acno1;
     	pno=pno1;
     	actype=actype1;
     	billno=billno1;
     	givam=givam1;
     	edate=edate1;

     	date= new Date();
     	calendar=new GregorianCalendar();
	    calendar.setTime(date);
        strdate =calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
        strtime=date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        System.out.println(strdate);
        System.out.println(strtime);

     	jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

		l2=new JLabel("Electricity Bill Receipt");
	    l2.setFont(new Font("Times New ROman",Font.BOLD,30));
	    l2.setForeground(Color.BLUE);
		l2.setBounds(250,250,300,30);
		jf.add(l2);

		l3=new JLabel("DATE");
		l3.setFont(f1);
		l3.setForeground(Color.WHITE);
		l3.setBounds(100,300,70,30);jf.add(l3);

		l31=new JLabel(strdate);
		l31.setFont(f1);
		l31.setForeground(Color.WHITE);
		l31.setBounds(80,340,150,30);jf.add(l31);
		l31.setText(strdate);

		l4=new JLabel("TIME");
		l4.setFont(f1);
		l4.setForeground(Color.WHITE);
		l4.setBounds(250,300,70,30);jf.add(l4);

		l41=new JLabel(strtime);
		l41.setFont(f1);
		l41.setForeground(Color.WHITE);
		l41.setBounds(250,340,150,30);jf.add(l41);

		l5=new JLabel("ATM NO");
		l5.setFont(f1);
		l5.setForeground(Color.WHITE);
		l5.setBounds(380,300,110,30);jf.add(l5);

		l51=new JLabel();
		l51.setFont(f1);
		l51.setForeground(Color.WHITE);
		l51.setBounds(380,340,300,30);jf.add(l51);

	    l55=new JLabel("Bill no");
		l55.setFont(f1);
		l55.setForeground(Color.WHITE);
		l55.setBounds(510,300,110,30);jf.add(l55);

		l551=new JLabel();
		l551.setFont(f1);
		l551.setForeground(Color.WHITE);
		l551.setBounds(510,340,300,30);jf.add(l551);

		ll5=new JLabel("Bill amount");
		ll5.setFont(f1);
		ll5.setForeground(Color.WHITE);
		ll5.setBounds(620,300,150,30);jf.add(ll5);

		ll51=new JLabel();
		ll51.setFont(f1);
		ll51.setForeground(Color.WHITE);
		ll51.setBounds(620,340,300,30);jf.add(ll51);

		l6=new JLabel("Available Balance in RS:");
		l6.setFont(f1);
		l6.setForeground(Color.WHITE);
		l6.setBounds(100,400,270,30);jf.add(l6);

		l61=new JLabel();
		l61.setFont(f1);
		l61.setForeground(Color.WHITE);
		l61.setBounds(380,400,270,30);jf.add(l61);

		l7=new JLabel("Thank You For Banking With ICICI BANK.For more information,");
		l7.setFont(f);
		l7.setForeground(Color.RED);
		l7.setBounds(50,450,650,20);jf.add(l7);

		l8=new JLabel("Please call Toll Free No:1800 2000 1911  or  Visit www.icicibank.com ");
		l8.setFont(f);
		l8.setForeground(Color.RED);
		l8.setBounds(50,480,650,30);jf.add(l8);

		b1=new JButton("Cancel",new ImageIcon("cancel.png"));
		b1.setFont(f);
		b1.setBounds(280,600,130,40);jf.add(b1);
		b1.addActionListener(this);

		img1=new ImageIcon("bank.jpg");
		l1=new JLabel(img1);
		l1.setBounds(1,1,800,700);
        jf.add(l1);

         jf.setTitle("Electricity Bill Receipt");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);


        try
		 {
		   Class.forName("com.mysql.jdbc.Driver");
		   con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		   System.out.println("Connected to database.");
		   ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"'");
	       rs=ps.executeQuery();
		   while(rs.next())
	        {
	         String atno2=rs.getString(1);
	         System.out.println("ATM NO is:"+atno2);
	         l51.setText(atno2);
	         String curbal=rs.getString(6);
	         System.out.println("Available balance are:"+curbal);
	         l61.setText(curbal);
	    	}
 ps1=con.prepareStatement("select * from electricitybill where atmno='"+atno+"' and accno='"+acno+"' and ebillno='"+billno+"' and edate='"+edate1+"' ");
   	 rs=ps1.executeQuery();
		   while(rs.next())
	        {
	         String ebillno1=rs.getString(4);
	         System.out.println("Bill NO is:"+ebillno1);
	         l551.setText(ebillno1);

	         String eamount=rs.getString(5);
	         System.out.println("Bill amount:"+eamount);
	         ll51.setText(eamount);
	    	}

	    	con.close();
		 }
         catch(SQLException se)
		{
		System.out.println(se);
	    //JOptionPane.showMessageDialog(null,"SQL Error:"+se);
        }
	    catch(Exception e)
	     {
	     System.out.println(e);
		 //JOptionPane.showMessageDialog(null,"Error:"+e);
	     }

	     t =new Timer(20000,this);// 20 minisecond
         t.start();

    }

     public void actionPerformed(ActionEvent ae)
      {
         if(ae.getSource()==t)
     	{
     			t.stop();
 int reply=JOptionPane.showConfirmDialog(null,"Do you want continue?","ATM Time Warning",JOptionPane.YES_NO_OPTION);

	             if (reply == JOptionPane.YES_OPTION)
	   			{
	   				sound.play();
	   				t.start();
	   		    }
	   		  else if (reply == JOptionPane.NO_OPTION)
	   		    {
	   		    	sound.play();
	   		    	t.stop();
                   new Welcome();
         	       jf.setVisible(false);
		        }
     	}
		 else if(ae.getSource()==b1)
		{	sound.play();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
	        t.stop();
           	new Welcome();
           	jf.setVisible(false);
		}
    }
       /* public static void main(String args[])
	  {
		new ElectricityBillPaid();
	  }*/
 }
