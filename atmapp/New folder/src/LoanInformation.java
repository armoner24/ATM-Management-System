
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.applet.*;

public class LoanInformation extends JFrame implements ActionListener
{
	JFrame jf;
    Font f,f1;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
	JButton cancel;
	ImageIcon img;
	File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;

		LoanInformation()
		{
	     	jf=new JFrame();
	     	f = new Font("Times New Roman",Font.BOLD,20);//button
	   		f1 = new Font("Times New Roman",Font.BOLD,25);//label
			jf.setLayout(null);
			try{sound = Applet.newAudioClip(wavFile.toURL());}
           catch(Exception e){e.printStackTrace();}

			l2=new JLabel("Loan Information");
		    l2.setFont(new Font("Times New ROman",Font.BOLD,30));
		    l2.setForeground(Color.BLUE);
			l2.setBounds(280,250,250,20);
			jf.add(l2);

			l3=new JLabel("Home Loan @ only 9%.");
			l3.setFont(f1);
			l3.setForeground(Color.WHITE);
			l3.setBounds(40,300,680,25);
			jf.add(l3);

			l4=new JLabel("Personal Loan @ only 10%.");
			l4.setFont(f1);
			l4.setForeground(Color.WHITE);
			l4.setBounds(40,350,680,25);
			jf.add(l4);

			l5=new JLabel("Car Loan @ only 12%.");
			l5.setFont(f1);
			l5.setForeground(Color.WHITE);
			l5.setBounds(40,400,680,25);
			jf.add(l5);

			l6=new JLabel("Student Loan @ only 5%.");
			l6.setFont(f1);
			l6.setForeground(Color.WHITE);
			l6.setBounds(40,450,680,25);
	    	jf.add(l6);

			l7=new JLabel("Computer or Laptop Loan @ only 8%.");
		    l7.setFont(f1);
			l7.setForeground(Color.WHITE);
			l7.setBounds(40,500,680,25);
			jf.add(l7);

			l8=new JLabel("For more information Visit our nearest branch of our Bank.");
			l8.setFont(f1);
			l8.setForeground(Color.WHITE);
			l8.setBounds(40,550,680,25);
			jf.add(l8);

			l9=new JLabel("Visit www.icicibank.com or call Toll free no 1800 2000 1911 .");
			l9.setFont(f1);
			l9.setForeground(Color.RED);
			l9.setBounds(60,580,680,25);
			jf.add(l9);

			cancel=new JButton("Cancel",new ImageIcon("cancel.png"));
			cancel.setFont(f);
			cancel.setBounds(280,620,130,40);
			cancel.addActionListener(this);
			jf.add(cancel);

			img=new ImageIcon("bank.jpg");
	    	l1=new JLabel(img);
	    	l1.setBounds(1,1,800,700);
	    	jf.add(l1);

		 	jf.setTitle("Loan Information");
	        jf.setSize(800,700);
			jf.setLocation(220,20);
		 	jf.setResizable(false);
	   		jf.setVisible(true);

	   	    t =new Timer(30000,this);// 30 minisecond
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
	   else	if(ae.getSource()==cancel)
		{
			sound.play();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
	        t.stop();
           	new Welcome();
           	jf.setVisible(false);
		}
	}
	 public static void main(String args[])
	  {
	    	new LoanInformation();
	  }
}
