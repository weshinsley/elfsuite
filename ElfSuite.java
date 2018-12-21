import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class ElfSuite extends JFrame {
  static String VERSION_ID = "1.0";
  
  // GUI Stuff
  JMenuBar mb = new JMenuBar();
  JMenu mFile = new JMenu("File");
  JMenuItem mNew = new JMenuItem("New");
  JMenuItem mOpen = new JMenuItem("Open");
  JMenuItem mSave = new JMenuItem("Save");
  JMenuItem mSaveAs = new JMenuItem("Save As");
  JMenuItem mExit = new JMenuItem("Exit");
  
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
