import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameGraphics {
    public static void createWindow(String title, JPanel panel, boolean specificSize) {
        Window window = new Window(title);
        window.add(panel);
        if (specificSize) {
            window.setWindowSize();
        }
        else {
            window.pack();
            window.alignToCenterDisplay();
        }
        window.setVisible(true);
    }

    private static class Window extends JFrame {
        private int wDisplay;
        private int hDisplay;

        public Window (String title) {
            setTitle(title);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            wDisplay = d.width;
            hDisplay = d.height;
        }

        public void setWindowSize() {
            setSize(3* wDisplay / 4, 3 * hDisplay / 4);
            setLocation(wDisplay / 8, hDisplay / 8);
        }

        public void alignToCenterDisplay() {
            setLocation((wDisplay - getWidth()) / 2, (hDisplay - getHeight()) / 2);
        }
    }
}