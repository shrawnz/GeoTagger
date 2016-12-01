import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class geeyouai 
{
	public static String choice="Select Query";
	 JFrame frame;
	 public static int HEIGHT = 700; 
	 public static int WIDTH = 1000;

     String[] query = {"Select Query","Query 1", "Query 2","Query 3"};
     int searchChoice=0;
     boolean issortbyyear=false,issortbyrelevance=false;

     WrapPane pane;
      JTable table;

    // List<List<String>> arr = new ArrayList<List<String>>();
     
     JTextField titlename1;
     JTextField titlename2;
     JTextField titlename3;
     JTextField titlename4;
     JTextField titlename5;
     JTextField titlename6;
     JTextField titlename;
    public static JTextField titlename7;
     JRadioButton r1,r2,r3,r4;
	 
    public static void main(String[] args) 
    {
        new geeyouai();
    }

    public geeyouai() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                pane = new WrapPane();
                frame = new JFrame("DBLP");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(pane);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setSize(WIDTH,HEIGHT);
            }
        });
    }

    public class WrapPane extends JPanel {

        public WrapPane() {
            setLayout(new BorderLayout());
           

            add(BorderLayout.PAGE_START, new TopPane());     
            
            if(choice.equals("Select Query"))
            	add(BorderLayout.LINE_START, new LeftPane());
            else if(choice.equals("Query 1"))
            	{ add(BorderLayout.LINE_START, new LeftPane_Query1());
                    add(BorderLayout.CENTER, new ContentPane());
                }
            else if(choice.equals("Query 2"))
            	{ add(BorderLayout.LINE_START, new LeftPane_Query2());
                    add(BorderLayout.CENTER, new ContentPane2());
                    validate();
                }
             else if(choice.equals("Query 3"))
                add(BorderLayout.LINE_START, new LeftPane_Query3());

			
        }

        // @Override
        // public Dimension getPreferredSize() {
        //     return new Dimension(200, 200);
        // }

    }

    public class ContentPane extends JPanel {

        
        public ContentPane() {
          
          setLayout(new BorderLayout());

         
         add(BorderLayout.CENTER,new Table());

         /* Adding next button */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton clickmeButton = new JButton("Next");
        buttonPanel.add(clickmeButton);

        
        
        add(BorderLayout.SOUTH,buttonPanel);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(700,600);
        }

       }

       public class ContentPane2 extends JPanel
       {

         public ContentPane2() {
          
          setLayout(new BorderLayout());

         
         add(BorderLayout.CENTER,new Table2());

         /* Adding next button */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton clickmeButton = new JButton("Next");
        buttonPanel.add(clickmeButton);

        
        
        add(BorderLayout.SOUTH,buttonPanel);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(700,600);
        }


       }

    public class TopPane extends JPanel {

        public TopPane() {
           setLayout(new BorderLayout());
           setBackground(Color.RED);
           
            JLabel l = new JLabel("DBLP QUERY ENGINE",JLabel.CENTER);
            l.setFont(new Font("Serif", Font.PLAIN, 50));

            add(BorderLayout.CENTER,l);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000,100);
        }

        

    }

    public class LeftPane extends JPanel {

        public LeftPane() 
        {
            setLayout(null);
            
            JComboBox jbox = new JComboBox(query);
            jbox.setBounds(50,50,160,30);
            jbox.addActionListener(new ActionListener() {
            	
            @Override
                public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                choice = (String) combo.getSelectedItem();

                 frame.dispose();
                   new geeyouai();
               
            }
            });

            add(jbox);       
           
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300,600);
        }


    }

      public class LeftPane_Query1 extends JPanel {

        public LeftPane_Query1() 
        {
           // setBackground(Color.BLUE);
            setLayout(null);

            JComboBox jbox1 = new JComboBox(query);
            jbox1.setBounds(50,50,160,30);
            jbox1.addActionListener(new ActionListener() {
                
            @Override
                public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                choice = (String) combo.getSelectedItem();

                 frame.dispose();
                   new geeyouai();
               
            }
            });


            add(jbox1);
            String[] search = {"Search by","Author Name","Title"};
            JComboBox jbox2 = new JComboBox(search);
			jbox2.setBounds(50,100,150,30);

            SearchBy e1 = new SearchBy();
            jbox2.addItemListener(e1);

          
			JLabel labels= new JLabel("Name/Title Tags",JLabel.CENTER);
            // JTextField f = new JTextField();
            titlename1 = new JTextField(15);
            JLabel labels1= new JLabel("Name/Title Tags",JLabel.CENTER);
            titlename1.setBounds(155,140,100,30);
            labels1.setBounds(30,140,120,30);
            add(titlename1);
            add(labels1);

            titlename2 = new JTextField(15);
            JLabel labels2 = new JLabel("Since Year",JLabel.CENTER);
            titlename2.setBounds(135,185,100,30);
            labels2.setBounds(30,185,100,30);
            add(labels2);
       
			titlename3 = new JTextField(15);
			titlename4 = new JTextField(15);
			JLabel labels3 = new JLabel("Custom Range",JLabel.CENTER);
			titlename3.setBounds(135,230,50,30);
			titlename4.setBounds(195,230,50,30);
			JLabel labels4 = new JLabel("-",JLabel.CENTER);
			labels4.setBounds(185,230,10,30);
			labels3.setBounds(20,230,120,30);

			
            SortByYear e2 = new SortByYear();
            SortByRelevance e3 = new SortByRelevance();

            r1 = new JRadioButton("Sort By year");
			r1.setBounds(40,280,150,30);
            r1.addActionListener(e2);

			r2 = new JRadioButton("Sort By Relevance");
			r2.setBounds(40,320,200,30);
            r2.addActionListener(e3);

			Search e4 = new Search();
            JButton b1 = new JButton("Search");
			b1.setBounds(40,380,100,30);
			b1.setBackground(Color.BLACK);
            b1.addActionListener(e4);

              Reset e5 = new Reset();
            JButton b2 = new JButton("Reset");
            b2.setBounds(150,380,100,30);
            b2.setBackground(Color.RED);
            b2.addActionListener(e5);
            

            add(jbox1);       
            add(jbox2);
            add(titlename2);
            add(labels2);
            add(titlename3);
            add(labels3);
            add(titlename4);
            add(r1);
            add(r2);
            add(b1);
            add(b2);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300,600);
        }


    }

      public class LeftPane_Query2 extends JPanel {

        public LeftPane_Query2() 
        {
            setLayout(null);
            JComboBox jbox3 = new JComboBox(query);
            jbox3.setBounds(50,50,160,30);
            jbox3.addActionListener(new ActionListener() {
                
            @Override
                public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                choice = (String) combo.getSelectedItem();

                 frame.dispose();
                   new geeyouai();
               
            }
            });

            add(jbox3);       
           
            titlename = new JTextField(15);
            JLabel label = new JLabel("No of publications",JLabel.CENTER);
            titlename.setBounds(135,120,100,30);
            label.setBounds(30,120,100,30);
            add(titlename);
            add(label);

            
            Search e4 = new Search();
            JButton b1 = new JButton("Search");
            b1.setBounds(40,380,100,30);
            b1.setBackground(Color.BLACK);
            b1.addActionListener(e4);

              Reset e5 = new Reset();
            JButton b2 = new JButton("Reset");
            b2.setBounds(150,380,100,30);
            b2.setBackground(Color.RED);
            b2.addActionListener(e5);

            add(b1);
            add(b2);


        }

        @Override
        public Dimension getPreferredSize() 
        {
            return new Dimension(300,600);
        }


    }

    public class LeftPane_Query3 extends JPanel {

        public LeftPane_Query3() 
        {
            setLayout(null);
            JComboBox jbox4 = new JComboBox(query);
            jbox4.setBounds(50,50,160,30);
            jbox4.addActionListener(new ActionListener() {
                
            @Override
                public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                choice = (String) combo.getSelectedItem();

                 frame.dispose();
                   new geeyouai();
               
            }
            });

            add(jbox4);       
           
            titlename5 = new JTextField(15);
            JLabel label = new JLabel("Enter filename",JLabel.CENTER);
            titlename5.setBounds(135,110,100,30);
            label.setBounds(30,110,100,30);
            add(titlename5);
            add(label);

            titlename6 = new JTextField(15);
            JLabel label2 = new JLabel("Enter year",JLabel.CENTER);
            titlename6.setBounds(135,160,100,30);
            label2.setBounds(30,160,100,30);
            add(titlename6);
            add(label2);

            titlename7 = new JTextField(15);
            JLabel l3 = new JLabel("Prediction",JLabel.CENTER);
            titlename7.setBounds(135,210,100,30);
            l3.setBounds(30,210,100,30);
            add(titlename7);
            add(l3);


            Search e4 = new Search();
            JButton b1 = new JButton("Predict");
            b1.setBounds(40,380,100,30);
            b1.setBackground(Color.BLACK);
            b1.addActionListener(e4);

              Reset e5 = new Reset();
            JButton b2 = new JButton("Reset");
            b2.setBounds(150,380,100,30);
            b2.setBackground(Color.RED);
            b2.addActionListener(e5);



            add(b1);
            add(b2);


        }

        @Override
        public Dimension getPreferredSize() 
        {
            return new Dimension(300,600);
        }


    }


 
    class SearchBy implements ItemListener
    {
        public void itemStateChanged(ItemEvent itemEvent)
        {
            if(itemEvent.getItem().equals("Author Name"))
            {
                searchChoice=1;
            }
            else if(itemEvent.getItem().equals("Title"))
            {
                searchChoice=2;               
            }
        }
    }
    class SortByYear implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {   
            issortbyyear=true;
        }
    }
    class SortByRelevance implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            issortbyrelevance=true;
        }
    }
    class Search implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Table.model.setRowCount(0);

            if(choice.equals("Query 1"))
            {
                int y,bw1,bw2;

                if(searchChoice==1)
                {
                    if(titlename2.getText().equals(""))
                        y=0;
                    else
                        y=Integer.parseInt(titlename2.getText());

                    if(titlename3.getText().equals(""))
                        bw1=0;
                    else
                        bw1=Integer.parseInt(titlename3.getText());

                    if(titlename4.getText().equals(""))
                        bw2=0;
                    else
                        bw2=Integer.parseInt(titlename4.getText());

                  //  System.out.println(y + " " + bw1 + " " + bw2);

                    String authname = titlename1.getText();
                    Query1a q1 = new Query1a(authname, issortbyyear,y,bw1,bw2);
                }
                else if(searchChoice==2)
                {
                    
                    if(titlename2.getText().equals(""))
                        y=0;
                    else
                        y=Integer.parseInt(titlename2.getText());

                    if(titlename3.getText().equals(""))
                        bw1=0;
                    else
                        bw1=Integer.parseInt(titlename3.getText());

                    if(titlename4.getText().equals(""))
                        bw2=0;
                    else
                        bw2=Integer.parseInt(titlename4.getText());

                    String tag = titlename1.getText();
                    Query1b q2 = new Query1b(tag, issortbyyear, issortbyrelevance,y,bw1,bw2);
                }

            }
            else if(choice.equals("Query 2"))
            {
                new Query2_wrapper(Integer.parseInt(titlename.getText()));
               //new Query2();
            }
            else if(choice.equals("Query 3"))
            {
                new Query3(titlename5.getText(),Double.parseDouble(titlename6.getText()));
            }
        }
    }
    class Reset implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Table.model.setRowCount(0);
            
            choice="Select Query";
            frame.dispose();
            new geeyouai();
            // if(choice.equals("Query 1"))
            // {
            //      titlename1.setText("");
            //      titlename2.setText("");
            //      titlename3.setText("");
            //      titlename4.setText("");
            // }
            // else if(choice.equals("Query 2"))
            //      titlename.setText("");
           
            // else if(choice.equals("Query 3"))
            //     { 
            //         titlename6.setText("");
            //         titlename5.setText("");
            //     }
           
            // choice="Select Query";
            // r1.setSelected(false);
            // r2.setSelected(false);

        }
    }
    
   
}