
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.sql.*;
import java.io.*;
import java.lang.*;
import java.applet.*;

public class PinChange extends JFrame implements ActionListener
{
    JFrame jf;
    Font f,f1;
	JButton b1,b2,b3;
	JLabel l1,l2,l3,l4,l5;
	ImageIcon img1;
    JPasswordField p1,p2,p3;
   	Connection con;
   	PreparedStatement ps;
	Statement stmt,stmt1;
	ResultSet rs;
    File wavFile = new File("clicksound.au");
    AudioClip sound;
    Timer t;
    int atno,acno,pno;
    String actype;
    int pp1,pp2,pp3;

    public PinChange(int atno1,int acno1,int pno1,String actype1)
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

   	    l5=new JLabel("PIN Change");
		l5.setFont(new Font("Times New ROman",Font.BOLD,30));
		l5.setForeground(Color.BLUE);
		l5.setBounds(300,250,200,30);
		jf.add(l5);

		l2=new JLabel("Enter old PIN no");
		l2.setFont(f1);
		l2.setForeground(Color.WHITE);
		l2.setBounds(120,350,200,30);
		jf.add(l2);

		p1=new JPasswordField(10);
		p1.setBounds(360,350,200,30);
		jf.add(p1);

		l3=new JLabel("Enter new PIN no");
		l3.setFont(f1);
		l3.setForeground(Color.WHITE);
		l3.setBounds(120,400,200,30);
		jf.add(l3);

		p2=new JPasswordField(10);
		p2.setBounds(360,400,200,30);
		jf.add(p2);

		l4=new JLabel("Confirm new PIN no");
		l4.setFont(f1);
		l4.setForeground(Color.WHITE);
		l4.setBounds(120,450,240,30);
		jf.add(l4);

		p3=new JPasswordField(10);
		p3.setBounds(360,450,200,30);
		jf.add(p3);

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

         jf.setTitle("Pin Change");
	     jf.setSize(800,700);
		 jf.setLocation(220,20);
		 jf.setResizable(false);
	     jf.setVisible(true);

	     //Timer t=new Timer(20000,this);
         //t.start();
    }

    public void actionPerformed(ActionEvent ae)
	{

		if(ae.getSource()==b1)
		{
          sound.play();


         if(((p1.getText()).equals(""))&&((p2.getText()).equals(""))&&((p3.getText()).equals("")))
	       {
 JOptionPane.showMessageDialog(this,"Please enter old PIN no 1 times and new PIN no 2 times !","Warning",JOptionPane.WARNING_MESSAGE);
		   sound.play();
	       }
	       else if((Integer.parseInt(p2.getText()))!=(Integer.parseInt(p3.getText())))
	       {
 JOptionPane.showMessageDialog(this,"Please enter New PIN no & confirm PIN no same !","Warning",JOptionPane.WARNING_MESSAGE);
		   sound.play();
		   	p1.setText("");
			p2.setText("");
			p3.setText("");
	       }
	       else
	       	{
	       try
		    {
		   int foundrec=0;
		   int pingiv=Integer.parseInt(p1.getText());
		   	Class.forName("com.mysql.jdbc.Driver");
		     con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","");
		      System.out.println("Connected to database.");
		      	stmt1=con.createStatement();
  ps=con.prepareStatement("select * from accountdetail where atmno='"+atno+"' and accno='"+acno+"' and acctype='"+actype+"' and pinno='"+pingiv+"' ");
	       rs=ps.executeQuery();
		         while(rs.next())
	             {
	             	foundrec=1;
		         }
		         if(foundrec==1)
		         {
		         	 pp1=Integer.parseInt(p1.getText());
	                 pp2=Integer.parseInt(p2.getText());
	                 pp3=Integer.parseInt(p3.getText());

		      stmt=con.createStatement();
 stmt.executeUpdate("update accountdetail set pinno="+pp2+" where atmno='"+atno+"' and accno='"+acno+"' and acctype='"+actype+"' and pinno='"+pp1+"' ");
         JOptionPane.showMessageDialog(null,"You have update PIN no succesfully.");
                 new Welcome();
                 jf.setVisible(false);
		         }
		         else
		         {
		         JOptionPane.showMessageDialog(null,"You enter wrong old PIN no.");
		         	p1.setText("");
		         	p2.setText("");
		        	p3.setText("");
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
	    else if(ae.getSource()==b2)
		{
			sound.play();
			p1.setText("");
			p2.setText("");
			p3.setText("");

		}
		else if(ae.getSource()==b3)
		{
			sound.play();
           	JOptionPane.showMessageDialog(this,"Your last transaction cancel.");
	        sound.play();
           	new Welcome();
           	jf.setVisible(false);
		}

		//JOptionPane.showMessageDialog(null,"Your time is out for doing transaction");
     	// jf.setVisible(false);
     	 //new Welcome();
     	 //t.stop();

 }
    /*public static void main(String args[])
	  {
		new PinChange();
	 }*/
}