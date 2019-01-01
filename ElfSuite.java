import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class ElfSuite extends JFrame {
  static String VERSION_ID = "1.0";
  static final int N_REGISTERS = 6;
  
  JMenuBar mb = new JMenuBar();
  JMenu mFile = new JMenu("File");
  JMenuItem mNew = new JMenuItem("New");
  JMenuItem mOpen = new JMenuItem("Open");
  JMenuItem mSave = new JMenuItem("Save");
  JMenuItem mSaveAs = new JMenuItem("Save As");
  JMenuItem mExit = new JMenuItem("Exit");
  
  JEditorPane jtaMain = new JEditorPane() {
    public void setBounds(int x, int y, int width, int height) {
      Dimension size = this.getPreferredSize();
      super.setBounds(x,  y,  Math.max(size.width,  width),  height);
    }
  };
  JEditorPane jtaLineNos = new JEditorPane();
  
  JScrollPane jspMain = new JScrollPane(jtaMain);
  JScrollPane jspLines = new JScrollPane(jtaLineNos);
  
  JTextField[] jtRegs = new JTextField[N_REGISTERS];
  EventHandler eh = new EventHandler();
  JFileChooser jfc = new JFileChooser();
  JScrollBar jspMain_vert = jspMain.getVerticalScrollBar();
  
  String current_fname = null;
  ElfHTMLParser EHP = new ElfHTMLParser();
  boolean changes_unsaved = false;
  
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
      jpRegs.add(jp);
    }
    jtaLineNos.setContentType("text/html");
    jtaMain.setContentType("text/html");
        
    jspMain.setPreferredSize(new Dimension(300,300));
    jspLines.setPreferredSize(new Dimension(50,300));
    jtaLineNos.setBackground(ElfSuite.this.getBackground());
    jspMain.getVerticalScrollBar().addAdjustmentListener(eh);
    
    JPanel jpMain = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpMain.add(jspLines);
    jspLines.setBorder(new EtchedBorder());
    jspLines.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    jspLines.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jspMain.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jspMain.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    jpMain.add(jspLines);
    jpMain.add(jspMain);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jpRegs,  BorderLayout.NORTH);
    
    jtaMain.setFont(new Font("Courier New", Font.BOLD, 20));
    jtaLineNos.setFont(new Font("Courier New", Font.BOLD, 20));
    getContentPane().add(jpMain, BorderLayout.CENTER);
    jtaMain.getDocument().addDocumentListener(eh);
    
    jfc.setCurrentDirectory(new File("."));
    try {
      ElfFiles.openFile("./wes-input.txt", this);
    } catch (Exception e) {}
    pack();
    setVisible(true);
    
  }
   
  class EventHandler implements ActionListener, AdjustmentListener, DocumentListener {
    public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == mNew) {
        if ((!changes_unsaved) || (JOptionPane.showConfirmDialog(ElfSuite.this, "There are unsaved changes - continue losing them?", 
            "Elf Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
          current_fname = null;
          ElfFiles.newFile(ElfSuite.this);
        }  
        
      } else if (src == mExit) {
        if ((!changes_unsaved) || (JOptionPane.showConfirmDialog(ElfSuite.this, "There are unsaved changes. Ok to exit?", 
            "Elf Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
          setVisible(false);
          System.exit(0);  
        }  
        
      } else if (src == mOpen) {
        int result = jfc.showOpenDialog(ElfSuite.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            ElfFiles.openFile(jfc.getSelectedFile().getAbsolutePath(), ElfSuite.this);
          } catch(Exception ex) { ex.printStackTrace(); }
        }
      
      } else if ((src == mSave) || (src == mSaveAs)) {
        if ((src == mSaveAs) || (current_fname == null)) {
          if (current_fname != null) jfc.setSelectedFile(new File(current_fname));
          int result = jfc.showSaveDialog(ElfSuite.this);
          if (result == JFileChooser.APPROVE_OPTION) {
            String new_fname = jfc.getSelectedFile().getAbsolutePath();
            if ((!new_fname.equals(current_fname)) && (new File(new_fname).exists())) {
              int result2 = JOptionPane.showConfirmDialog(ElfSuite.this, "This file already exists - is it ok to overwrite?", 
                  "Elf Warning", JOptionPane.YES_NO_OPTION);
              if (result2 == JOptionPane.NO_OPTION) new_fname = null;
            }
            if (new_fname!=null) {
              try {
                ElfFiles.saveFile(new_fname, ElfSuite.this);
              } catch (Exception ex) { ex.printStackTrace(); }
            }
          }   
        }
      }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
      if (e.getSource() == jspMain_vert) {
        jspLines.getVerticalScrollBar().setValue(jspMain.getVerticalScrollBar().getValue());
      }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
      if (!changes_unsaved) {
        changes_unsaved = true;
        setTitle(getTitle()+" *");
      }
      
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      if (!changes_unsaved) {
        changes_unsaved = true;
        setTitle(getTitle()+" *");
      }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {      
    }
  }
  
  public static void main(String[] args) throws Exception {
    SwingUtilities.invokeLater(new Runnable() { 
      public void run() {
        new ElfSuite(); 
      }
    });
  }
}
