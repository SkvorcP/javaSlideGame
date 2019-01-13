import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener {

    private Frame frame;
    private JButton button;
    private JTextField nSquares, nmbMoves;
    private static final Color BACKGROUND_COLOR = new Color (204, 230, 255);
    private static final Color TEXT_FIELD_COLOR = new Color (242, 242, 242);

    public ControlPanel(Frame frame, int n) {
        this.frame = frame;
        JLabel nLabel = new JLabel("n = ");
        nSquares = new JTextField(Integer.toString(n), 6);
        nSquares.setHorizontalAlignment(JTextField.CENTER);
        nSquares.requestFocus();
        nSquares.setBackground(TEXT_FIELD_COLOR);
        nSquares.addActionListener(this);
        button = new JButton("New Game");
        button.addActionListener(this);
        JLabel movesLabel = new JLabel("Moves: ");
        nmbMoves = new JTextField(Integer.toString(0), 6);
        nmbMoves.setHorizontalAlignment(JTextField.CENTER);
        nmbMoves.setEditable(false);
        nmbMoves.setBackground(TEXT_FIELD_COLOR);
        add(new Box.Filler(new Dimension(5, 50), new Dimension(5, 60), new Dimension(Short.MAX_VALUE, 100)));
        add(nLabel);
        add(nSquares);
        add(button);
        add(movesLabel);
        add(nmbMoves);
        setBackground(BACKGROUND_COLOR);
    }

    public void moves(int counter) {
        nmbMoves.setText(Integer.toString(counter));
    }

    public void actionPerformed (ActionEvent e) {
        int n;
        try {
            n = Integer.parseInt(nSquares.getText());
            if (n < 3) throw new NumberFormatException();
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(null, "Incorrect input format.\nPlease pick a number that is greater than 2.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frame.newGame(n);
        frame.resetGame();
        frame.resetCounter();
        nmbMoves.setText(Integer.toString(0));
    }
}