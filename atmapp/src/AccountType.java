
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.sql.*;
import java.io.*;
import java.applet.*;

public class AccountType extends JFrame implements ActionListener
 {
    JFrame jf;
    Font f,f1;
	JButton b1,bs1,bc1;
	JLabel l1,l2,l4;
	ImageIcon img1;
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;
    int atno,acno,pno;
	String actype,actypegive;

    public AccountType(int atno1,int acno1,int pno1)
     {
     	atno=atno1;
     	acno=acno1;
     	pno=pno1;

     	jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

		l4=new JLabel("Account Type");
	    l4.setFont(new Font("Times New ROman",Font.BOLD,30));
	    l4.setForeground(Color.BLUE);
		l4.setBounds(250,250,300,30);
		jf.add(l4);

    	l2=new JLabel("Seclect Account Type:");
		l2.setFont(f1);
		l2.setForeground(Color.WHITE);
		l2.setBounds(120,400,250,30);
		jf.add(l2);

		bs1=new JButton("Saving");
		bs1.setFont(f);
		bs1.setBounds(390,360,130,40);jf.add(bs1);
		bs1.addActionListener(this);

		bc1=new JButton("Current");
		bc1.setFont(f);
		bc1.setBounds(390,440,130,40);jf.add(bc1);
		bc1.addActionListener(this);

		b1=new JButton("Cancel",new ImageIcon("cancel.png"));
		b1.setFont(f);
		b1.setBounds(280,550,130,40);jf.add(b1);
		b1.addActionListener(this);

        img1=new ImageIcon("bank.jpg");
		l1=new JLabel(img1);
		l1.setBounds(1,1,800,700);
        jf.add(l1);

		 jf.setTitle("Account Type");
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
		{
			sound.play();
            t.stop();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
           	new Welcome();
           	jf.setVisible(false);
		}
		else if(ae.getSource()==bs1)//saving account matching
		{
			sound.play();
			t.stop();

			 try
	         {

	        int foundrec = 0;
	        Class.forName("com.mysql.jdbc.Driver");
		    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		    System.out.println("Connected to database.");
            ps=con.prepareStatement("select acctype from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and pinno='"+pno+"' ");
	        rs=ps.executeQuery();
		    while(rs.next())
	        {
              actypegive=rs.getString(1);
              System.out.println(actypegive);
	          foundrec = 1;
	        }
	       if (foundrec == 1)
             {
             	if(actypegive.equals("saving"))
             	{
                   actype="saving";
             	  System.out.println(actypegive);
                  new TransactionMenu(atno,acno,pno,actype);
		       	 jf.setVisible(false);
             	}
               else
              {
         JOptionPane.showMessageDialog(null,"Your account type is not match with given account type.","Warning",JOptionPane.WARNING_MESSAGE);
              sound.play();
              new Welcome();
               jf.setVisible(false);
              }
            }
	       con.close();
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
		else if(ae.getSource()==bc1)//current account matching
		{
		    sound.play();
		    	t.stop();
			 try
	         {

	        int foundrec1 = 0;
	        Class.forName("com.mysql.jdbc.Driver");
		    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		    System.out.println("Connected to database.");
            ps=con.prepareStatement("select acctype from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and pinno='"+pno+"' ");
	        rs=ps.executeQuery();
		    while(rs.next())
	        {
              actypegive=rs.getString(1);
              System.out.println(actypegive);
	          foundrec1 = 1;
	        }
	       if (foundrec1 == 1)
             {
             	if(actypegive.equals("current"))
             	{
                     actype="current";
             	  System.out.println(actype);
                  new TransactionMenu(atno,acno,pno,actype);
		       	 jf.setVisible(false);
             	}
               else
              {
         JOptionPane.showMessageDialog(null,"Your account type is not match with given account type.","Warning",JOptionPane.WARNING_MESSAGE);
              sound.play();
              new Welcome();
               jf.setVisible(false);
              }
            }
	       con.close();
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
   }
    /*public static void main(String args[])
	  {
		new AccountType();
	 }*/
}






