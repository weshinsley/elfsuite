import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Tools {
  public static void addMenuItem(JMenu parent, JMenuItem kid, int mnemonic, ActionListener e) {
    parent.add(kid);
    kid.setMnemonic(mnemonic);
    kid.addActionListener(e);
  }
  
  public static void addMenuItem(JPopupMenu parent, JMenuItem kid, int mnemonic, ActionListener e) {
    parent.add(kid);
    kid.setMnemonic(mnemonic);
    kid.addActionListener(e);
  }
  
  public static ArrayList<String> readLines(String f) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(f));
    ArrayList<String> res = new ArrayList<String>();
    String s = br.readLine();
    while (s!=null) {
      res.add(s);
      s = br.readLine();
    }
    br.close();
    return res; 
  }
  
}
