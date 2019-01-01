import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ElfFiles {
  
  public static void openFile(String fname, ElfSuite E) throws Exception {
    ArrayList<String> s = Tools.readLines(fname);
    StringBuilder sbCode = new StringBuilder();
    StringBuilder sbLines = new StringBuilder();
    if (s.size()==0) {
      JOptionPane.showMessageDialog(E, "Error - file is empty");
      return;
    }
    sbLines.append("&nbsp;<br/>");
    sbCode.append(s.get(0)+"<br/>");
    for (int i=1; i<s.size(); i++) {
      sbLines.append(String.valueOf(i-1)+"<br/>");
      sbCode.append(s.get(i)+"<br/>");
    }
    E.current_fname = fname;
    E.jtaMain.setText("<html><div style='white-space:nowrap; text-overflow:ellipsis;font-family:Courier New;font-size:20pt;font-weight:bold'>"+sbCode.toString()+"</span></div>");
    E.jtaLineNos.setText("<html><div style='text-align:right; color:#000080;font-family:Courier New;font-size:20pt;font-weight:bold'>"+sbLines.toString()+"</div></html>");
    E.setTitle("ElfSuite "+ElfSuite.VERSION_ID+" - "+fname);
    E.changes_unsaved = false;
    E.mSave.setEnabled(true); 
  }
  
  public static void saveFile(String fname, ElfSuite E) throws Exception {
    E.EHP.parse(new StringReader(E.jtaMain.getText()));
    PrintWriter PW = new PrintWriter(new File(fname));
    PW.println(E.EHP.getText());
    PW.close();
    E.current_fname = fname;
    E.setTitle("ElfSuite "+ElfSuite.VERSION_ID+" - "+fname);
    E.changes_unsaved = false;
  }  
  
  public static void newFile(ElfSuite E) {
    E.jtaMain.setText("<html><div style='white-space:nowrap; text-overflow:ellipsis;font-family:Courier New;font-size:20pt;font-weight:bold'>#ip </span></div>");
    E.jtaLineNos.setText("<html><div style='text-align:right; color:#000080;font-family:Courier New;font-size:20pt;font-weight:bold'></div></html>");
    E.setTitle("ElfSuite "+ElfSuite.VERSION_ID);
    E.changes_unsaved = false;
  }
}
