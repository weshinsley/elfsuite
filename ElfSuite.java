import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class ElfSuite extends JFrame {
  static String VERSION_ID = "1.0";
  static final int N_REGISTERS = 6;
  // GUI Stuff
  JMenuBar mb = new JMenuBar();
  JMenu mFile = new JMenu("File");
  JMenuItem mNew = new JMenuItem("New");
  JMenuItem mOpen = new JMenuItem("Open");
  JMenuItem mSave = new JMenuItem("Save");
  JMenuItem mSaveAs = new JMenuItem("Save As");
  JMenuItem mExit = new JMenuItem("Exit");
  JTextArea jtaMain = new JTextArea();
  JScrollPane jspMain = new JScrollPane(jtaMain);
  JTextField[] jtRegs = new JTextField[N_REGISTERS];
  
  
  EventHandler eh = new EventHandler();
  void openFile(String f) {
    
  }
  
  
  ElfSuite() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800,600);
    setTitle("ElfSuite "+VERSION_ID);
    EventHandler eh = new EventHandler();  
    getContentPane().setLayout(new BorderLayout());
    setJMenuBar(mb);
    mFile.setMnemonic(KeyEvent.VK_F);
    mb.add(mFile);
    Tools.addMenuItem(mFile,mNew,KeyEvent.VK_N,eh);
    Tools.addMenuItem(mFile,mOpen,KeyEvent.VK_O,eh);
    Tools.addMenuItem(mFile,mSave,KeyEvent.VK_S,eh);
    Tools.addMenuItem(mFile,mSaveAs,KeyEvent.VK_A,eh);
    Tools.addMenuItem(mFile,mExit,KeyEvent.VK_X,eh);
    JPanel jpRegs = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpRegs.setBorder(new EtchedBorder());
    for (int i=0; i<jtRegs.length; i++) {
      JPanel jp = new JPanel();
      BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
      jp.setLayout(bl);
      JLabel jl = new JLabel("R"+i, SwingConstants.CENTER);
      jl.setFont(new Font("Calibri", Font.BOLD, 20));
      jl.setPreferredSize(new Dimension(100,25));
      jl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      jtRegs[i] = new JTextField();
      jtRegs[i].setText("0");
      jtRegs[i].setPreferredSize(new Dimension(100, 25));
      jtRegs[i].setHorizontalAlignment(JTextField.CENTER);
      jp.add(jl, BorderLayout.NORTH);
      jp.add(jtRegs[i]);
      jspMain.setBorder(new EtchedBorder());
      jspMain.setPreferredSize(new Dimension(600,300));
      jpRegs.add(jp);
    }
    
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jpRegs,  BorderLayout.NORTH);
    
     jtaMain.setFont(new Font("Courier New", Font.PLAIN, 24));
    getContentPane().add(jspMain, BorderLayout.CENTER);
    pack();
    setVisible(true);
    
  }
    
  
  void run() {
    
    
  }
  
  
  class EventHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == mExit) {
        setVisible(false);
      }
    }
  }
  
  public static void main(String[] args) throws Exception {
    
    ElfSuite E = new ElfSuite();
    SwingUtilities.invokeLater(new Runnable() { 
      public void run() {
        E.run(); 
      }
    });
  }
  
  

}
