import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class client implements ActionListener
{

    JTextField text; //globally defined because we need text entered in panel a1, also we need to define panel a1 globally
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    client()
    {
        f.setLayout(null);  ///to create own frame // removes default bounds/layout

        //code for panel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);


        //code for label
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 =  i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);               //image
        ImageIcon i3 =  new ImageIcon(i2);                                         // converted above image to icon to be passed in label
        JLabel back = new JLabel(i3);           // only icons can be passed not images that's why converted to icon then created to label
        back.setBounds(5,20,25,25);
        p1.add(back);


        back.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 =  i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);               //image
        ImageIcon i6 =  new ImageIcon(i5);                                         // converted above image to icon to be passed in label
        JLabel profile = new JLabel(i6);           // only icons can be passed not images that's why converted to icon then created to label
        profile.setBounds(40,10,50,50);
        p1.add(profile);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 =  i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);               //image
        ImageIcon i9 =  new ImageIcon(i8);                                         // converted above image to icon to be passed in label
        JLabel video = new JLabel(i9);           // only icons can be passed not images that's why converted to icon then created to label
        video.setBounds(300,20,30,30);
        p1.add(video);


        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 =  i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);               //image
        ImageIcon i12 =  new ImageIcon(i11);                                         // converted above image to icon to be passed in label
        JLabel phone = new JLabel(i12);           // only icons can be passed not images that's why converted to icon then created to label
        phone.setBounds(360,20,30,30);
        p1.add(phone);


        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 =  i13.getImage().getScaledInstance(15,30,Image.SCALE_DEFAULT);               //image
        ImageIcon i15 =  new ImageIcon(i14);                                         // converted above image to icon to be passed in label
        JLabel menu = new JLabel(i15);           // only icons can be passed not images that's why converted to icon then created to label
        menu.setBounds(410,20,15,30);
        p1.add(menu);


        JLabel name = new JLabel("Thor");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);


        JLabel status = new JLabel("Active-now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);


        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);


        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);


        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);


        //code for frame
        f.setSize(450,700);
        f.setLocation(800,30);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);
    }


    public void actionPerformed(ActionEvent ae)
    {

        try
        {
        String out = text.getText();

        JPanel p2 = formatLabel(out);

        a1.setLayout(new BorderLayout());

        JPanel right = new JPanel(new BorderLayout()); //BorderLayout() is an empty or default constructor
        right.add(p2,BorderLayout.LINE_END);

        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));


        a1.add(vertical, BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        new client(); // anonymous object for direct constructor call

        try
        {

        Socket s = new Socket("127.0.0.1",6001);
        DataInputStream din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());

            while(true)
                {
                    a1.setLayout(new BorderLayout());
                    String msg = din.readUTF(); //utf defined in stream


                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);


                    vertical.add(left);


                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);


                    f.validate();
                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}