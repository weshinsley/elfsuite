import java.awt.event.ActionListener;

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
}
