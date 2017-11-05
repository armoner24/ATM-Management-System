
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

public class CashWithdrawal extends JFrame implements ActionListener
{
    JFrame jf;
    Font f,f1;
	JButton b1,b2,b3;
	JLabel l1,l2,l3,l4;
	ImageIcon img1;
	JTextField t1;
	Connection con;
	PreparedStatement ps;
	Statement stmt,stmt1;
	ResultSet rs;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    //Timer t;
    int atno,acno,pno;
    String actype,strdate;
    float givam,amt,abal,amtmin,sumbal;
    Date date1;
    GregorianCalendar calendar;

    public CashWithdrawal(int atno1,int acno1,int pno1,String actype1)
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

		l4=new JLabel("Cash Withdrawl");
	    l4.setFont(new Font("Times New ROman",Font.BOLD,30));
	    l4.setForeground(Color.BLUE);
		l4.setBounds(250,250,250,30);
		jf.add(l4);

		l3=new JLabel("Enter the amount to be withdrawal:");
		l3.setFont(f1);
		l3.setForeground(Color.WHITE);
		l3.setBounds(100,380,400,30);jf.add(l3);

		t1=new JTextField(40);
		t1.setBounds(500,380,200,30);jf.add(t1);

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

         jf.setTitle("Cash Withdrawal");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);

	       //Timer t=new Timer(20000,this);
        // t.start();
    }

    public void actionPerformed(ActionEvent ae)
	{
         //	JOptionPane.showMessageDialog(null,"Your time is out for doing transaction");
     	// jf.setVisible(false);
     	 //new Welcome();
     	 //t.stop();
		if(ae.getSource()==b1)
		{    	sound.play();
           	if(((t1.getText()).equals("")))
	        {
		    JOptionPane.showMessageDialog(this,"Please enter withdrawal amount!","Warning",JOptionPane.WARNING_MESSAGE);
		    sound.play();
	        }
            givam=0;
            amt=0;
	   	    givam=Float.parseFloat(t1.getText());
		   System.out.println("You enter amount are:"+givam);//////////

		  try
		   {

		   	Class.forName("com.mysql.jdbc.Driver");
		     con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		      System.out.println("Connected to database.");
 ps=con.prepareStatement("select sum(withamt) from transaction where tdate='"+strdate+"' and atmno='"+atno+"' and accno='"+acno+"'");
	            rs=ps.executeQuery();
		       while(rs.next())//check today withdrawal sum
	             {
	                sumbal=0;
	                sumbal=sumbal+rs.getFloat(1);
	                System.out.println("You Today previous wihthdraw Rs:"+sumbal);
	             }
	     if(sumbal>1.0)  ////if record found on current date then
	     {
	       if(actype=="saving")//saving account checking previous amt on current date and enter amt on current date
	     	{
	       if((sumbal+givam)<=25000)
           {
		   if(givam<100)
    		{
           JOptionPane.showMessageDialog(this,"You can not withdraw amount less than 100 RS!","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
            t1.setText("");
			}
			else if(givam>10000)
			{
     	JOptionPane.showMessageDialog(this,"You can not withdraw amount greater than 10000 RS at the same time!","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
             t1.setText("");
		    }
			else if(givam>100 && (!(givam % 100 ==0)))
			{
			JOptionPane.showMessageDialog(this,"Amount should be multiple of 100","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
             t1.setText("");
			}
			else
			{

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
                             System.out.println("You withdraw rs:"+t1.getText());
		                    stmt1=con.createStatement();
stmt1.executeUpdate("insert into transaction (atmno,accno,depositamt,withamt,avbalance,tdate)values('"+atno+"','"+acno+"',0,'"+givam+"','"+amtmin+"','"+strdate+"') ");//
int reply=JOptionPane.showConfirmDialog(null,"Your cash withdrawl is in processing take money from machine.Do you have to take receipt?","Cash withdrawl Message",JOptionPane.YES_NO_OPTION);

	             if (reply == JOptionPane.YES_OPTION)
	   			{
	   				sound.play();
	   				new BalanceEnquiry(atno,acno,pno,actype);
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
	          new Welcome();
	          jf.setVisible(false);
		           }
		         }
			   }///
			   else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less to withdraw amount","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	          }
	           else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less,You should keep minimum balance 1000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	         }
    	   }
	      }
	      else
	      {
	JOptionPane.showMessageDialog(this,"Your can not withdraw per day greater than 25000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	       sound.play();
	       t1.setText("");
	      }//
	     }
	     else
	     {
	     	if(actype=="current")//current account checking previous amt on current date and enter amt on current date
	     	{
	       if((sumbal+givam)<=50000)
           {
		   if(givam<100)
    		{
           JOptionPane.showMessageDialog(this,"You can not withdraw amount less than 100 RS!","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
            t1.setText("");
			}
			else if(givam>10000)
			{
     	JOptionPane.showMessageDialog(this,"You can not withdraw amount greater than 10000 RS at the same time!","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
             t1.setText("");
		    }
			else if(givam>100 && (!(givam % 100 ==0)))
			{
			JOptionPane.showMessageDialog(this,"Amount should be multiple of 100","Warning",JOptionPane.WARNING_MESSAGE);
            sound.play();
             t1.setText("");
			}
			else
			{

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
                            System.out.println("You withdraw rs:"+t1.getText());
		                    stmt1=con.createStatement();
stmt1.executeUpdate("insert into transaction (atmno,accno,depositamt,withamt,avbalance,tdate)values('"+atno+"','"+acno+"',0,'"+givam+"','"+amtmin+"','"+strdate+"') ");//
int reply=JOptionPane.showConfirmDialog(null,"Your cash withdrawl is in processing take money from machine.Do you have to take receipt?","Cash withdrawl Message",JOptionPane.YES_NO_OPTION);

	             if (reply == JOptionPane.YES_OPTION)
	   			{
	   				sound.play();
	   				new BalanceEnquiry(atno,acno,pno,actype);
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
	          new Welcome();
	          jf.setVisible(false);
		           }
		         }
			   }///
			   else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less to withdraw amount","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	          }
	           else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less,You should keep minimum balance 1000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	         }
    	   }
	      }
	      else
	      {
	 JOptionPane.showMessageDialog(this,"Your can not withdraw per day greater than 50000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	       sound.play();
	       t1.setText("");//
	      }
	      }
	     }
	     } //if record not found on current date then else
	     else
	     {
	       if(actype=="saving")
	       {  System.out.println(actype);

	     	if(givam<=25000)
            {
		   if(givam<100)
    		{
   JOptionPane.showMessageDialog(this,"You can not withdraw amount less than 100 RS!","Warning",JOptionPane.WARNING_MESSAGE);
                sound.play();
                t1.setText("");
			}
			else if(givam>10000)
			{
	JOptionPane.showMessageDialog(this,"You can not withdraw amount greater than 10000 RS at the same time!","Warning",JOptionPane.WARNING_MESSAGE);
             sound.play();
             t1.setText("");
		    }
			else if(givam>100 && (!(givam % 100 ==0)))
			{
			JOptionPane.showMessageDialog(this,"Amount should be multiple of 100","Warning",JOptionPane.WARNING_MESSAGE);
             sound.play();
             t1.setText("");
			}
			else
			{
  ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and pinno='"+pno+"' and acctype='"+actype+"'");
	       rs=ps.executeQuery();
		         while(rs.next())
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
 							System.out.println("You withdraw rs:"+t1.getText());
		                    stmt1=con.createStatement();
     stmt1.executeUpdate("insert into transaction (atmno,accno,depositamt,withamt,avbalance,tdate)values('"+atno+"','"+acno+"',0,'"+givam+"','"+amtmin+"','"+strdate+"') ");

int reply=JOptionPane.showConfirmDialog(null,"Your cash withdrawl is in processing take money from machine.Do you have to take receipt?","Cash withdrawl Message",JOptionPane.YES_NO_OPTION);

	             if (reply == JOptionPane.YES_OPTION)//go to balance diaplay
	   			{
	   				 sound.play();
	   				 new BalanceEnquiry(atno,acno,pno,actype);
	   				jf.setVisible(false);
	   		    }
	   		  else if (reply == JOptionPane.NO_OPTION)//only display the balance on dialog box
	   		   {
	           ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"'");
	            rs=ps.executeQuery();
		         while(rs.next())
	              {
	             int curbal=rs.getInt(6);
 JOptionPane.showMessageDialog(null,"Your available balance are: '"+curbal+"'","Available balance",JOptionPane.INFORMATION_MESSAGE);
	               sound.play();
	               new Welcome();
	               jf.setVisible(false);
		          }
		         }
			    }///
			   else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less to withdraw amount","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	          }
	           else
	           {
 JOptionPane.showMessageDialog(this,"Your balance is less,You should keep minimum balance 1000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	            sound.play();
	            t1.setText("");
	           }
	          }
    	    }
	      }
	      else
	      {
	     JOptionPane.showMessageDialog(this,"Your can not withdraw per day greater than 25000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	     sound.play();
	      t1.setText("");
	      }
	     }//
	     else if(actype=="current")//current acc type if record are not found on current date
	     {
	     	if(givam<=50000)
            {
		   if(givam<100)
    		{
   JOptionPane.showMessageDialog(this,"You can not withdraw amount less than 100 RS!","Warning",JOptionPane.WARNING_MESSAGE);
                sound.play();
                t1.setText("");
			}
			else if(givam>10000)
			{
	JOptionPane.showMessageDialog(this,"You can not withdraw amount greater than 10000 RS at the same time!","Warning",JOptionPane.WARNING_MESSAGE);
             sound.play();
             t1.setText("");
		    }
			else if(givam>100 && (!(givam % 100 ==0)))
			{
			JOptionPane.showMessageDialog(this,"Amount should be multiple of 100","Warning",JOptionPane.WARNING_MESSAGE);
             sound.play();
             t1.setText("");
			}
			else
			{
  ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and pinno='"+pno+"' and acctype='"+actype+"'");
	       rs=ps.executeQuery();
		         while(rs.next())
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
                            System.out.println("You withdraw rs:"+t1.getText());
		                    stmt1=con.createStatement();
     stmt1.executeUpdate("insert into transaction (atmno,accno,depositamt,withamt,avbalance,tdate)values('"+atno+"','"+acno+"',0,'"+givam+"','"+amtmin+"','"+strdate+"') ");

int reply=JOptionPane.showConfirmDialog(null,"Your cash withdrawl is in processing take money from machine.Do you have to take receipt?","Cash withdrawl Message",JOptionPane.YES_NO_OPTION);

	        if (reply == JOptionPane.YES_OPTION)//go to balance diaplay
	   		{
	   		 sound.play();
	   		 new BalanceEnquiry(atno,acno,pno,actype);
	   	   	jf.setVisible(false);
	   		 }
	   		  else if (reply == JOptionPane.NO_OPTION)//only display the balance on dialog box
	   		   {
	           ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"'");
	            rs=ps.executeQuery();
		         while(rs.next())
	              {
	             int curbal=rs.getInt(6);
 JOptionPane.showMessageDialog(null,"Your available balance are: '"+curbal+"'","Available balance",JOptionPane.INFORMATION_MESSAGE);
	               sound.play();
	               new Welcome();
	               jf.setVisible(false);
		          }
		         }
			    }///
			   else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less to withdraw amount","Warning",JOptionPane.WARNING_MESSAGE);
	           sound.play();
	           t1.setText("");
	           }
	          }
	           else
	           {
JOptionPane.showMessageDialog(this,"Your balance is less,You should keep minimum balance 1000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	            sound.play();
	            t1.setText("");
	           }
	          }
    	    }
	      }
	         //}//
	      else
	      {
JOptionPane.showMessageDialog(this,"Your can not withdraw per day greater than 50000 RS","Warning",JOptionPane.WARNING_MESSAGE);
	     sound.play();
	      t1.setText("");
	      }
	     }
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

	    }
		else if(ae.getSource()==b2)
		{
			sound.play();
			t1.setText("");
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
		new CashWithdrawal();
	 }*/
}
