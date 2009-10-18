
import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainServer {
  public static void main(String[] args) {
    Server server=new Server();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frmSize = new Dimension(500,400);
    server.setSize(frmSize);
    server.setVisible(true);
  }

}
