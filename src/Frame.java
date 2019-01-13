import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private GamePanel gamingPanel;
    private ControlPanel controlPanel;
    private static final String ICON_PATH = "images/Picture.png";

    public Frame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension ss = kit.getScreenSize();
        int width = ss.width;
        int height = ss.height;
        if (height < width) {
            setLocation((width - ((height * 2) / 3)) / 2, (height - ((height * 2) / 3)) / 2);
            setSize ((height * 2) / 3, (height * 2) / 3);
        }
        else {
            setLocation((height - ((width * 2) / 3)) / 2, (width - ((width  * 2) / 3)) / 2);
            setSize ((width * 2) / 3, (width * 2) / 3);
        }
        String title = "Bonzor's Slide Game";
        setTitle(title);
        Image picture = kit.getImage(ICON_PATH);
        setIconImage(picture);
        addWindowListener (new WindowListener());

        gamingPanel = new GamePanel(this, 4);
        controlPanel = new ControlPanel(this, 4);
        Container contentPane = getContentPane();
        contentPane.add(gamingPanel, BorderLayout.CENTER);
        contentPane.add(controlPanel, BorderLayout.SOUTH);
    }

    public void newGame(int n) {
        gamingPanel.reset(n);
    }

    public void writeMove(int counter) {
        controlPanel.moves(counter);
    }

    public void resetGame() {
        gamingPanel.resetPanel();
    }

    public void resetCounter() {
        gamingPanel.setCounter();
    }
}

class WindowListener extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowIconified (WindowEvent e) {
        System.out.println("[minimized]");
    }
}