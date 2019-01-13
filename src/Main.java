import javax.swing.SwingUtilities;

public class Main {
    public static void main (String[] args) {
        Runnable doShowGUI = new Runnable() {
            public void run() {
                Frame slideGame = new Frame();
                slideGame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(doShowGUI);
    }
}