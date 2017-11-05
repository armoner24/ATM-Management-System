
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.sql.*;
import java.io.*;
import java.applet.*;
import javax.swing.Timer;

public class TransactionMenu extends JFrame implements ActionListener
{

	JFrame jf;
    Font f,f1;
	JButton b1,b2,b3,b4,b5,b6,b61,b7,b8,b9,b51;
	JLabel l1,l2,l3;
	ImageIcon img1;
	Connection con;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;
    int atno,acno,pno;
	String actype;

    public TransactionMenu(int atno1,int acno1,int pno1,String actype1)
    {
    	atno=atno1;
     	acno=acno1;
     	pno=pno1;
     	actype=actype1;

    	jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

		l2=new JLabel("Select Any One Option From The Following");
		l2.setFont(f1);
		l2.setForeground(Color.BLUE);
		l2.setBounds(130,250,600,30);
		jf.add(l2);

        b1=new JButton("Cash Withdrawal");
		b1.setFont(f);
		b1.setBounds(120,300,200,40);jf.add(b1);
		b1.addActionListener(this);

		b2=new JButton("Balance Enquiry");
		b2.setFont(f);
		b2.setBounds(120,350,200,40);jf.add(b2);
		b2.addActionListener(this);

		b3=new JButton("Mini Statement");
		b3.setFont(f);
		b3.setBounds(120,400,200,40);jf.add(b3);
		b3.addActionListener(this);

		b8=new JButton("Electricity Bill Pay");
		b8.setFont(f);
		b8.setBounds(120,450,200,40);jf.add(b8);
		b8.addActionListener(this);

		b4=new JButton("cash Deposit");
		b4.setFont(f);
		b4.setBounds(450,300,200,40);jf.add(b4);
		b4.addActionListener(this);

		b51=new JButton("Pin change");
		b51.setFont(f);
		b51.setBounds(450,350,200,40);jf.add(b51);
		b51.addActionListener(this);

		b5=new JButton("Loan Information");
		b5.setFont(f);
		b5.setBounds(450,400,200,40);jf.add(b5);
		b5.addActionListener(this);

		b6=new JButton("Help");
		b6.setFont(f);
		b6.setBounds(450,450,200,40);jf.add(b6);//320,500,190,40 cash dep
		b6.addActionListener(this);

		b7=new JButton("Cancel",new ImageIcon("cancel.png"));
		b7.setFont(f);
		b7.setBounds(300,580,150,40);jf.add(b7);
		b7.addActionListener(this);

		img1=new ImageIcon("bank.jpg");
		l1=new JLabel(img1);
		l1.setBounds(1,1,800,700);
        jf.add(l1);

     	 jf.setTitle("Transaction Menu");
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
          	new CashWithdrawal(atno,acno,pno,actype);
          	jf.setVisible(false);
		}

		else if(ae.getSource()==b2)
		{
       	   	sound.play();
       	   	t.stop();
          	new BalanceEnquiry(atno,acno,pno,actype);
          	jf.setVisible(false);
		}
		else if(ae.getSource()==b3)
		{
			 sound.play();
			 t.stop();
			 new MiniStatement(atno,acno,pno,actype);
			 jf.setVisible(false);

		}
		else if(ae.getSource()==b4)
		{
          	sound.play();
          	t.stop();
          	new CashDeposit(atno,acno,pno,actype);
          	jf.setVisible(false);
		}
		else if(ae.getSource()==b51)
		{
            sound.play();
            t.stop();
          	new PinChange(atno,acno,pno,actype);
          	jf.setVisible(false);
		}

		else if(ae.getSource()==b5)
		{
          	sound.play();
          	t.stop();
          	new LoanInformation();
          	jf.setVisible(false);
		}
		else if(ae.getSource()==b6)
		{
          	sound.play();
          	t.stop();
          	new Help();
          	jf.setVisible(false);
		}

		else if(ae.getSource()==b7)
		{
           	sound.play();
           	t.stop();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
           	new Welcome();
           	jf.setVisible(false);
		}
		else if(ae.getSource()==b8)
		{
          	sound.play();
          	t.stop();
          	new ElectricityBillPay(atno,acno,pno,actype);
          	jf.setVisible(false);
		}

    }
    /* public static void main(String args[])
	  {
		new TransactionMenu(ano,acno,pno);
	 }*/
}