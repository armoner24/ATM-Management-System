
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

public class ElectricityBillPay extends JFrame implements ActionListener
{
    JFrame jf;
    Font f,f1;
	JButton b1,b2,b3;
	JLabel l1,l2,l3,l4;
	ImageIcon img1;
	JTextField t1,t2;
	Connection con;
	PreparedStatement ps;
	Statement stmt,stmt1,stmt2;
	ResultSet rs;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;
    int atno,acno,pno,billno;
    String actype,strdate;
    float givam,amt,abal,amtmin,sumbal;
    Date date1;
    GregorianCalendar calendar;

    public ElectricityBillPay(int atno1,int acno1,int pno1,String actype1)
     {
     	atno=atno1;
     	acno=acno1;
     	pno=pno1;
     	actype=actype1;

     	date1= new Date();
     	calendar=new GregorianCalendar();
	    calendar.setTime(date1);
        strdate =calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
        System.out.println(strdate);
        System.out.println(actype);

     	jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

		l4=new JLabel("Electricity Bill Pay");
	    l4.setFont(new Font("Times New ROman",Font.BOLD,30));
	    l4.setForeground(Color.BLUE);
		l4.setBounds(250,250,250,30);
		jf.add(l4);

		l3=new JLabel("Enter the electricity bill no:");
		l3.setFont(f1);
		l3.setForeground(Color.WHITE);
		l3.setBounds(100,380,400,30);jf.add(l3);

		t1=new JTextField(40);
		t1.setBounds(500,380,200,30);jf.add(t1);

		l2=new JLabel("Enter the electricity bill amount:");
		l2.setFont(f1);
		l2.setForeground(Color.WHITE);
		l2.setBounds(100,430,400,30);jf.add(l2);

		t2=new JTextField(40);
		t2.setBounds(500,430,200,30);jf.add(t2);

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

         jf.setTitle("Electricity Bill Pay");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);

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
		{    	sound.play();
		        t.stop();
           	if(((t1.getText()).equals(""))&& ((t2.getText()).equals("")))
	        {
		    JOptionPane.showMessageDialog(this,"Please enter electricity bill no & amount!","Warning",JOptionPane.WARNING_MESSAGE);
		    sound.play();
	        }

            givam=0;
            amt=0;
            billno=Integer.parseInt(t1.getText());
	   	    givam=Float.parseFloat(t2.getText());
		   System.out.println("You enter bill amount are:"+givam);//////////

		  try
		  {
		   	Class.forName("com.mysql.jdbc.Driver");
		     con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		      System.out.println("Connected to database.");

 ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and pinno='"+pno+"' and acctype='"+actype+"'");
	       rs=ps.executeQuery();
		         if(rs.next())
	             {
	             	abal=Float.parseFloat(rs.getString(6));

	              	if(abal>1000)
	                {
	                  if(givam<=(abal-1000))
					   {
							amt=((abal-1000)-givam);
							amtmin=(amt+1000);
							stmt=con.createStatement();
							stmt.executeUpdate("update accountdetail set balance="+amtmin+" where atmno='"+atno+"'");
		                    stmt1=con.createStatement();
		                    stmt2=con.createStatement();
stmt1.executeUpdate("insert into transaction (atmno,accno,depositamt,withamt,avbalance,tdate)values('"+atno+"','"+acno+"',0,'"+givam+"','"+amtmin+"','"+strdate+"') ");//
stmt2.executeUpdate("insert into electricitybill (atmno,accno,ebillno,ebillamount,edate)values('"+atno+"','"+acno+"','"+billno+"','"+givam+"','"+strdate+"') ");

 System.out.println("You paid bill rs:"+t2.getText());
int reply=JOptionPane.showConfirmDialog(null,"Your bill paid.Do you have to take receipt?","Bill Pay Message",JOptionPane.YES_NO_OPTION);

	             if (reply == JOptionPane.YES_OPTION)
	   			{
	   				sound.play();
	   				t.stop();
	   				new ElectricityBillPaid(atno,acno,pno,actype,billno,givam,strdate);
	   		       jf.setVisible(false);
	   		    }
	   		  else if (reply == JOptionPane.NO_OPTION)
	   		    {
	           ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"'");
	            rs=ps.executeQuery();
		         while(rs.next())
	               {
	             float curbal=rs.getFloat(6);
 JOptionPane.showMessageDialog(null,"Your available balance are: '"+curbal+"'","Available balance",JOptionPane.INFORMATION_MESSAGE);
	          sound.play();
	          t.stop();
	          new Welcome();
	          jf.setVisible(false);
		           }
		         }
			   }///
			   else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less to pay electricity bill","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t2.setText("");
	           }
	          }
	           else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less,You should keep minimum balance 1000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t2.setText("");
	           }
	         }
             con.close();
         }/////////////////////////////
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

	    }
		else if(ae.getSource()==b2)
		{
			sound.play();
			t1.setText("");
			t2.setText("");

		}
		else if(ae.getSource()==b3)
		{
			sound.play();
			JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
	        t.stop();
			new Welcome();
			jf.setVisible(false);
		}
	}
   /* public static void main(String args[])
	  {
		new ElectricityBillPay();
	 }*/
}
