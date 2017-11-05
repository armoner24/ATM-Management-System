import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.applet.*;

public class Welcome extends JFrame implements ActionListener
{
    JFrame jf;
    Font f,f1;
	JButton b,b1;
	JLabel l1,l2,l3,l4,l5,l6;
	ImageIcon img1;
	JTextField t1;
	String strdate,strtime;
	File wavFile = new File("clicksound.au");
    AudioClip sound;

	public Welcome()
	{
		jf=new JFrame();
		f = new Font("Times New Roman",Font.BOLD,20);//button
		f1 = new Font("Times New Roman",Font.BOLD,25);//label
		jf.setLayout(null);

		try{sound = Applet.newAudioClip(wavFile.toURL());}
        catch(Exception e){e.printStackTrace();}

	    l2=new JLabel("Welcome To ICICI Bank");
		l2.setFont(new Font("Times New Roman",Font.BOLD,40));
		l2.setForeground(Color.BLUE);
		l2.setBounds(180,230,500,30);
		jf.add(l2);

		l3=new JLabel("ATM Services");
		l3.setFont(new Font("Times New Roman",Font.BOLD,30));
		l3.setForeground(Color.BLUE);
		l3.setBounds(300,300,300,30);
		jf.add(l3);

		l4=new JLabel("Press OK To Use ATM Service or Press Exit to quite");
		l4.setFont(f1);
		l4.setForeground(Color.BLUE);
		l4.setBounds(120,370,580,30);
		jf.add(l4);

		b=new JButton("Ok",new ImageIcon("ok.png"));
		b.setFont(f);
	 	b.setBounds(200,550,130,40);
		jf.add(b);
		b.addActionListener(this);

		b1=new JButton("Exit",new ImageIcon("cancel.png"));
		b1.setFont(f);
		b1.setBounds(500,550,130,40);
		jf.add(b1);
		b1.addActionListener(this);

        img1=new ImageIcon("bank.jpg");
		l1=new JLabel(img1);
		l1.setBounds(1,1,800,700);
        jf.add(l1);

		 jf.setTitle("WELCOME TO ICICI BANK ATM");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);

	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b)
		{
			sound.play();
	       	new Atmcardno();
            jf.setVisible(false);
		}
		else if(ae.getSource()==b1)
		{
			sound.play();
			System.exit(0);
		}
    }
	public static void main(String args[])
	{
		new Welcome();
	}

}