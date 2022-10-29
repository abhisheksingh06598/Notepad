

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.*;

class Notepad extends WindowAdapter implements ActionListener,KeyListener
{
    Frame f;
    FileDialog fd;
    Dialog d,d1,d2,d3,d4,df,df1;
    MenuBar mb;
    Menu m1,m2;
    MenuItem nw,opn,sve,sveas,ext,find,find_replace;
    TextArea t;
    Button b1,b2,b3,b11,b22,b33,b6,b7,b8,b9,b10,bb11,b_1,b_2,b_3,bf,bf1;
    Panel p,p1,p2,p3,p4,p5,p6,pf,pf1;
    static int x,y,con,count,i;
    static String fn,dir,path,source,dir1;
    static char c[];
    FileOutputStream fos;
    File fi,cur_f,fo;
    TextField t2,t3,t4;
    String sf1,sf11,sf2,sf22,curr="untitled",sr1,sr2,sr3;
    Pattern pa,pa1;
    Matcher m,ma1;
    static int g;
    int strt[]=new int[10000];
    int ed[]=new int[10000];
    static boolean change=false;
    static int fpos,fpos1,fpost,fpost1,fpp,fpp1,k=-1,k1=-1;

    public Notepad()
    {
        f=new Frame();
        f.setSize(600,500);
        t=new TextArea();
        fpos=t.getCaretPosition();
        t.addKeyListener(this);
        mb=new MenuBar();
        m1=new Menu("File");
        m2=new Menu("Edit");
        nw=new MenuItem("New");
        opn=new MenuItem("Open");
        sve=new MenuItem("Save");
        sveas=new MenuItem("Save as");
        ext=new MenuItem("Exit");
        find=new MenuItem("Find");
        find_replace=new MenuItem("Find & Replace");

        nw.addActionListener(this);
        opn.addActionListener(this);
        sve.addActionListener(this);
        sveas.addActionListener(this);
        ext.addActionListener(this);
        find.addActionListener(this);
        find_replace.addActionListener(this);

        m1.add(nw);
        m1.add(opn);
        m1.add(sve);
        m1.add(sveas);
        m1.addSeparator();
        m1.add(ext);

        m2.add(find);
        m2.add(find_replace);

        mb.add(m1);
        mb.add(m2);

        d = new Dialog(f , "Notepad", true);
        //d.setLocation(100,100);
        d.setLayout( new BorderLayout() );
        Label l=new Label("Do you want to save changes to untitled?");
        b1 = new Button ("Save");
        b2 = new Button ("Don't Save");
        b3 = new Button ("Cancel");
        d.add(l);
        p=new Panel(new FlowLayout(FlowLayout.RIGHT));
        p.add(b1);
        p.add(b2);
        p.add(b3);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        d.add(p,"South");
        d.setSize(300,120);
        d.addWindowListener(this);

        d1 = new Dialog(f , "Notepad");
        //d.setLocation(100,100);
        d1.setLayout( new BorderLayout() );
        Label l1=new Label("Do you want to save changes to untitled?");
        b11 = new Button ("Save");
        b22 = new Button ("Don't Save");
        b33 = new Button ("Cancel");
        d1.add(l1);
        p1=new Panel(new FlowLayout(FlowLayout.RIGHT));
        p1.add(b11);
        p1.add(b22);
        p1.add(b33);
        b11.addActionListener(this);
        b22.addActionListener(this);
        b33.addActionListener(this);
        d1.add(p1,"South");
        d1.setSize(300,120);
        d1.addWindowListener(this);

        d2 = new Dialog(f , "Notepad");
        //d.setLocation(100,100);
        d2.setLayout( new BorderLayout() );
        Label l3=new Label("Find what: ");
        b6 = new Button ("Find Next");
        b7 = new Button ("Cancel");
        t2=new TextField(20);
        p3=new Panel(new FlowLayout(FlowLayout.LEFT));
        p3.add(l3);
        p3.add(t2);
        d2.add(p3);
        p2=new Panel(new FlowLayout(FlowLayout.LEFT,20,5));
        p2.add(b6);
        p2.add(b7);
        b6.addActionListener(this);
        b7.addActionListener(this);
        d2.add(p2,"South");
        d2.setSize(300,120);
        d2.addWindowListener(this);

        d3 = new Dialog(f , "Replace");
        //d.setLocation(100,100);
        d3.setLayout( new BorderLayout() );
        Label l4=new Label("Find what:       ");
        Label l5=new Label("Replace with :");
        b8 = new Button ("Find Next");
        b9 = new Button ("Replace");
        b10 = new Button ("Replace All");
        bb11 = new Button ("Cancel");
        t3=new TextField(20);
        t4=new TextField(20);
        p4=new Panel(new FlowLayout(FlowLayout.LEFT));
        p4.add(l4);
        p4.add(t3);
        p4.add(l5);
        p4.add(t4);
        d3.add(p4);
        p5=new Panel(new FlowLayout(FlowLayout.LEFT));
        p5.add(b8);
        p5.add(b9);
        p5.add(b10);
        p5.add(bb11);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b10.addActionListener(this);
        bb11.addActionListener(this);
        d3.add(p5,"South");
        d3.setSize(340,150);
        d3.addWindowListener(this);

        d4 = new Dialog(f , "Notepad");
        //d.setLocation(100,100);
        d4.setLayout( new BorderLayout() );
        Label l6=new Label("Do you want to save changes to untitled?");
        b_1 = new Button ("Save");
        b_2 = new Button ("Don't Save");
        b_3 = new Button ("Cancel");
        d4.add(l6);
        p6=new Panel(new FlowLayout(FlowLayout.RIGHT));
        p6.add(b_1);
        p6.add(b_2);
        p6.add(b_3);
        b_1.addActionListener(this);
        b_2.addActionListener(this);
        b_3.addActionListener(this);
        d4.add(p6,"South");
        d4.setSize(300,120);
        d4.addWindowListener(this);

        f.setMenuBar(mb);
        f.add(t);
        f.setVisible(true);
        f.addWindowListener(this);

    }

    public void actionPerformed(ActionEvent e)
    {
        String str=e.getActionCommand();
        if(e.getSource()==ext)
        {
            if(t.getText().equals(""))
                System.exit(1);
            else
            {
                x=f.getWidth();
                y=f.getHeight();
                d.setLocation(x/3,y/2);
                d.setVisible(true);
            }
        }

        else if(e.getSource()==find)
        {
            x=f.getWidth();
            y=f.getHeight();
            d2.setLocation(x/3,y/2);
            d2.setVisible(true);
        }

        else if(e.getSource()==find_replace)
        {
            x=f.getWidth();
            y=f.getHeight();
            d3.setLocation(x/3,y/2);
            d3.setVisible(true);
        }

        else if(e.getSource()==b6)
        {
            if(fpp==0)
            {	fpos=t.getCaretPosition();
                fpost=t.getCaretPosition();
            }
            fpp=1;
            if(fpost==t.getCaretPosition() || fpost!=t.getCaretPosition())
            {
                if(k!=t.getCaretPosition()){
                    fpos=t.getCaretPosition();
                    fpost=t.getCaretPosition();}
            }
            System.out.println("fpos = "+fpos+" fpost = "+fpost+" caret = " + t.getCaretPosition());
            sf1=t.getText();
            //System.out.println("here");
            sf2=t2.getText();

            pa=Pattern.compile("\r\n");
            m=pa.matcher(sf1);

            sf1=m.replaceAll("\n");
            t.setText(sf1);

            pa=Pattern.compile(sf2);
            m=pa.matcher(sf1);
            //System.out.println("no here");

            if(m.find(fpos))
            {
                t.select(m.start(),m.end());
                fpos=m.end();
                k=m.start();
                f.toFront();
                t.requestFocus();
                System.out.println(k);
            }
            else
            {
                df=new Dialog(d2,"Notepad");
                df.setLayout( new BorderLayout() );
                df.setSize(200,120);
                Label lf=new Label("Can't find symbol "+ sf2);
                bf = new Button ("OK");
                pf=new Panel(new FlowLayout(FlowLayout.CENTER));
                bf.addActionListener(this);
                pf.add(lf);
                df.add(pf);
                df.add(bf,"South");
                df.addWindowListener(this);

                x=f.getWidth();
                y=f.getHeight();

                df.setLocation(x/2,y/2);
                df.setVisible(true);

            }



        }

        else if(e.getSource()==bf)
        {
            char cbf[]=t.getText().toCharArray();
            t.setCaretPosition(cbf.length-1);
            fpp=0;
            df.setVisible(false);
            df.dispose();
        }

        else if(e.getSource()==bf1)
        {
            char cbf1[]=t.getText().toCharArray();
            t.setCaretPosition(cbf1.length-1);

            fpp1=0;
            df1.setVisible(false);
            df1.dispose();
        }

        else if(e.getSource()==b7)
        {
            fpp=0;
            d2.setVisible(false);
            d2.dispose();
        }

        else if(e.getSource()==b8)
        {
            if(fpp1==0)
            {	fpos1=t.getCaretPosition();
                fpost1=t.getCaretPosition();
            }
            fpp1=1;
            if(fpost1==t.getCaretPosition() || fpost1!=t.getCaretPosition())
            {
                if(k1!=t.getCaretPosition()){
                    fpos1=t.getCaretPosition();
                    fpost1=t.getCaretPosition();}
            }
            System.out.println(fpos1+" "+fpost1);

            sf11=t.getText();

            pa1=Pattern.compile("\r\n");
            ma1=pa1.matcher(sf11);

            sf11=ma1.replaceAll("\n");
            t.setText(sf11);

            sf22=t3.getText();
            pa1=Pattern.compile(sf22);
            ma1=pa1.matcher(sf11);


            if(ma1.find(fpos1))
            {

                t.select(ma1.start(),ma1.end());
                fpos1=ma1.end();
                k1=ma1.start();
                System.out.println(fpos1);
                //t.setCaretPosition(fpos1+1);
                f.toFront();
                //t.requestFocus();
                System.out.println(ma1.start()+" "+ma1.end());
            }
            else
            {
                df1=new Dialog(d3,"Notepad");
                df1.setLayout( new BorderLayout() );
                df1.setSize(200,120);
                Label lf1=new Label("Can't find symbol "+ sf2);
                bf1 = new Button ("OK");
                pf1=new Panel(new FlowLayout(FlowLayout.CENTER));
                bf1.addActionListener(this);
                pf1.add(lf1);
                df1.add(pf1);
                df1.add(bf1,"South");
                df1.addWindowListener(this);

                x=f.getWidth();
                y=f.getHeight();

                df1.setLocation(x/2,y/2);
                df1.setVisible(true);

            }



        }
        else if(e.getSource()==b9)
        {
            if(t.getSelectedText().equals(""))
            {

            }

            else
            {
                String sss=t4.getText();
                int ss=t.getSelectionStart();
                int se=t.getSelectionEnd();
                t.replaceText(sss,ss,se);
                System.out.println("p");
            }
        }

        else if(e.getSource()==b10)
        {
            sr1=t.getText();
            sr2=t3.getText();
            sr3=t4.getText();
            pa1=Pattern.compile(sr2);
            ma1=pa1.matcher(sr1);
            t.setText(ma1.replaceAll(sr3));

        }


        else if(e.getSource()==nw)
        {

            if(!t.getText().equals(" "))
            {

                System.out.println("here i come "+curr);

                System.out.println("1");
                System.out.println(change);

                if(change==true)
                {
                    x=f.getWidth();
                    y=f.getHeight();
                    d1.setLocation(x/3,y/2);
                    d1.setVisible(true);
                }

                else
                {
                    curr="untitled";
                    t.setText(" ");
                    change=false;
                }



            }

            else
            {
                curr="untitled";
                t.setText(" ");
                change=false;
            }

					/*	System.out.println("g = "+g);

					if(g==0)
					{
						System.out.println("I passed this");
						System.out.println("con = "+con);
						if(fn==null)
							con=0;
						if(con==0 && curr=="untitled")
						{
							System.out.println("I passed this also");

							x=f.getWidth();
							y=f.getHeight();
							d1.setLocation(x/3,y/2);
							d1.setVisible(true);

						}

						else
						{
							curr="untitled";
							t.setText("");
						}
					}

						con=0;
				}*/


        }

        else if(e.getSource()==opn)
        {

            if(change==false)
            {
                fd=new FileDialog(f,"Open",FileDialog.LOAD);
                fd.setVisible(true);
                dir=fd.getDirectory();
                fn=fd.getFile();
                if(fn!=null)
                {
                    curr=dir+fn;

                    fo=new File(curr);
                    try
                    {
                        FileInputStream ff=new FileInputStream(fo);
                        int cv;
                        char cw;
                        String stw="",sw;
                        t.setText(" ");
                        while((cv=ff.read())!=-1)
                        {
                            cw=(char)cv;
                            sw=cw+stw;
                            t.appendText(sw);
                        }
                        ff.close();

                        change=false;
                    }

                    catch(Exception eo)
                    {
                        System.out.println(eo.getMessage());
                    }
                }
            }

            else
            {
                System.out.println("open");
                x=f.getWidth();
                y=f.getHeight();
                d4.setLocation(x/3,y/2);
                d4.setVisible(true);
            }


        }

        else if(e.getSource()==sve)
        {
            g=0;
            fd=new FileDialog(f,"Save",FileDialog.SAVE);
            if(change==true && curr.equals("untitled"))
            {
                //con=1;
                fd.setVisible(true);
                fn=fd.getFile();
                dir=fd.getDirectory();

            }
            System.out.println(fd.getFile());
            if(fn!=null){
                curr=dir+fn;
                change=false;
                cur_f=new File(curr);
                System.out.println("current file in save : "+curr);
                path=dir+fn;
                source=t.getText();
                c=source.toCharArray();

                try
                {
                    fos=new FileOutputStream(path);
                    File fi=new File(path);
                    int i=0;
                    while(c[i]!='\0')
                    {
                        fos.write(c[i]);
                        i++;
                    }
                    fos.close();


                }

                catch(Exception ee)
                {
                    System.out.println(ee.getMessage());
                }

            }
        }

        else if(e.getSource()==sveas)
        {
            fd=new FileDialog(f,"Save",FileDialog.SAVE);
            fd.setVisible(true);
            fn=fd.getFile();
            dir=fd.getDirectory();
            cur_f=new File(dir,curr);
            if(fn!=null)
            {
                curr=dir+fn;
                change=false;

                //path=dir+fn;
                source=t.getText();
                c=source.toCharArray();

                try
                {
                    fos=new FileOutputStream(curr);
                    File fi=new File(curr);
                    int i=0;
                    while(c[i]!='\0')
                    {
                        fos.write(c[i]);
                        i++;
                    }
                    fos.close();

                }

                catch(Exception ee)
                {
                    System.out.println(ee.getMessage());
                }

                con=0;
                System.out.println(change);
            }


        }

        else if(e.getSource()==b1)
        {
            System.out.println("b1");

            if(change==true && curr.equals("untitled"))
            {
                //con=1;
                fd=new FileDialog(f,"Save",FileDialog.SAVE);
                fd.setVisible(true);
                fn=fd.getFile();
                dir=fd.getDirectory();

            }
            if(fn!=null){
                curr=dir+fn;
                source=t.getText();
                c=source.toCharArray();
                change=false;
                try
                {
                    fos=new FileOutputStream(curr);
                    File fi=new File(curr);
                    int i=0;
                    while(c[i]!='\0')
                    {
                        fos.write(c[i]);
                        i++;
                    }
                    fos.close();

                }

                catch(Exception ee)
                {
                    System.out.println(ee.getMessage());
                }

                f.setVisible(false);
                f.dispose();
            }

            else
            {
                d.setVisible(false);
                d.dispose();
            }

        }

        else if(e.getSource()==b7)
        {
            d2.setVisible(false);
            d2.dispose();
        }


        else if(e.getSource()==bb11)
        {
            fpp1=0;
            d3.setVisible(false);
            d3.dispose();
        }

        else if(e.getSource()==b11)
        {
            fd=new FileDialog(f,"Save",FileDialog.SAVE);
            System.out.println("hi"+change);
            if(change==true && curr.equals("untitled"))
            {
                //con=1;
                fd.setVisible(true);
                fn=fd.getFile();
                dir=fd.getDirectory();

            }
            if(fn!=null){
                fn=curr;
                curr=dir+fn;

                source=t.getText();
                c=source.toCharArray();
                change=false;
                try
                {
                    fos=new FileOutputStream(curr);
                    File fi=new File(curr);
                    int i=0;
                    while(c[i]!='\0')
                    {
                        fos.write(c[i]);
                        i++;
                    }
                    fos.close();

                }

                catch(Exception ee)
                {
                    System.out.println(ee.getMessage());
                }

                d1.setVisible(false);
                d1.dispose();
                t.setText("");
            }
            else
            {
                d1.setVisible(false);
                d1.dispose();
            }

        }

        else if(e.getSource()==b_1)
        {
            System.out.println("b_1");
            fd=new FileDialog(f,"Save",FileDialog.SAVE);
            fd.setVisible(true);
            fn=fd.getFile();
            dir=fd.getDirectory();
            if(fn==null)
            {
                d4.setVisible(false);
                d4.dispose();
            }
            else
            {
                curr=dir+fn;
                //path=dir+fn;

                source=t.getText();
                c=source.toCharArray();
                change=false;
                try
                {
                    fos=new FileOutputStream(curr);
                    File fi=new File(curr);
                    int i=0;
                    while(c[i]!='\0')
                    {
                        fos.write(c[i]);
                        i++;
                    }
                    fos.close();

                }

                catch(Exception ee)
                {
                    System.out.println(ee.getMessage());
                }
                System.out.println("save done");

                fd=new FileDialog(f,"Open",FileDialog.LOAD);
                fd.setVisible(true);
                fn=fd.getFile();
                dir=fd.getDirectory();
                if(fn==null)
                {
                    d4.setVisible(false);
                    d4.dispose();
                }
                else
                {
                    curr=dir+fn;
                    fo=new File(curr);
                    try
                    {
                        FileInputStream ff=new FileInputStream(fo);
                        int cv;
                        char cw;
                        String stw="",sw;
                        t.setText(" ");
                        while((cv=ff.read())!=-1)
                        {
                            cw=(char)cv;
                            sw=cw+stw;
                            t.appendText(sw);
                        }
                        ff.close();
                        d4.setVisible(false);
                        d4.dispose();
                    }

                    catch(Exception eo)
                    {
                        System.out.println(eo.getMessage());
                    }
                }

                change=false;
            }
        }

        else if(e.getSource()==b22)
        {
            d1.setVisible(false);
            d1.dispose();
            t.setText("");
            change=false;
        }
        else if(e.getSource()==b2)
        {
            f.setVisible(false);
            f.dispose();
            System.exit(1);
        }

        else if(e.getSource()==b22)
        {
            d1.setVisible(false);
            d1.dispose();
            t.setText("");
        }

        else if(e.getSource()==b_2)
        {
            d4.setVisible(false);
            d4.dispose();

            fd=new FileDialog(f,"Open",FileDialog.LOAD);
            fd.setVisible(true);
            fn=fd.getFile();
            if(fn!=null)
            {
                curr=fn;
                dir=fd.getDirectory();
                fo=new File(dir,curr);
                try
                {
                    FileInputStream ff=new FileInputStream(fo);
                    int cv;
                    char cw;
                    String stw="",sw;
                    t.setText(" ");
                    while((cv=ff.read())!=-1)
                    {
                        cw=(char)cv;
                        sw=cw+stw;
                        t.appendText(sw);
                    }
                    ff.close();
                    change=false;
                }

                catch(Exception eo)
                {
                    System.out.println(eo.getMessage());
                }
            }


        }

        else if(e.getSource()==b33)
        {
            d1.setVisible(false);
            d1.dispose();
        }

        else if(e.getSource()==b_3)
        {
            d4.setVisible(false);
            d4.dispose();
        }

        else if(e.getSource()==b3)
        {
            d.setVisible(false);
            d.dispose();
        }

    }

    public void keyTyped(KeyEvent ete)
    {

        System.out.println("t changed");
        if(ete.getKeyChar()!=KeyEvent.VK_LEFT && ete.getKeyChar()!=KeyEvent.VK_RIGHT && ete.getKeyChar()!=KeyEvent.VK_UP && ete.getKeyChar()!=KeyEvent.VK_DOWN)
            change = true;

    }

    public void keyReleased(KeyEvent ete1)
    {}

    public void keyPressed(KeyEvent ete2)
    {}


    public void windowClosing(WindowEvent e)
    {

        Window w=e.getWindow();
        if(w==f){
            if(t.getText().equals("") || con==1)
            {
                w.setVisible(false);
                w.dispose();
                System.exit(1);
            }
            else
            {
                x=f.getWidth();
                y=f.getHeight();
                System.out.println(x+" "+y+" "+x/2+" "+y/2);
                d.setLocation(x/3,y/2);
                d.setVisible(true);
            }
        }
        else if(w==d)
        {
            d.setVisible(false);
            d.dispose();
        }

        else if(w==d1)
        {
            d1.setVisible(false);
            d1.dispose();
        }

        else if(w==d2)
        {
            d2.setVisible(false);
            d2.dispose();
        }

        else if(w==d3)
        {
            d3.setVisible(false);
            d3.dispose();
        }

        else if(w==d4)
        {
            d4.setVisible(false);
            d4.dispose();
        }

        else if(w==df)
        {
            char cbf[]=t.getText().toCharArray();
            t.setCaretPosition(cbf.length-1);
            fpp=0;
            df.setVisible(false);
            df.dispose();
        }

        else if(w==df1)
        {
            char cbf1[]=t.getText().toCharArray();
            t.setCaretPosition(cbf1.length-1);
            fpp1=0;
            df1.setVisible(false);
            df1.dispose();
        }
    }

    public static void main(String args[])
    {
        Notepad n=new Notepad();
    }
}
