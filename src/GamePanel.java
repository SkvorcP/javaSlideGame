import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class GamePanel extends JPanel implements MouseListener {

    public void mouseEntered(MouseEvent s) {}
    public void mouseExited(MouseEvent s) {}
    public void mousePressed(MouseEvent s) {}
    public void mouseReleased(MouseEvent s) {}
    private Frame frame;
    private int n;
    private int squareSide, xStartingPoint, yStartingPoint;
    private static final Color BACKGROUND_COLOR = new Color (208, 224, 240);
    private static final Color NUMBER_COLOR = new Color (0, 66, 128);
    private static final Color FULL_SQUARE_COLOR = new Color (102, 181, 255);
    private static final Color EMPTY_SQUARE_COLOR = new Color (204, 230, 255);
    private boolean gameFinished;
    private int[][] numbers;
    private int counter = 0;

    public GamePanel(Frame frame, int n) {
        this.frame = frame;
        setBackground(EMPTY_SQUARE_COLOR);
        addMouseListener(this);
        reset(n);
    }

    public void reset(int n) {
        this.n = n;
        int[] serialNumbers = new int[n * n];
        int[] randomNumbers = new int[n * n];
        int b = 0;
        for(int i = 0; i < n * n; i++)
            serialNumbers[i] = i;
        for(int i = 0; i < n * n; i++) {
            int a = (int)(Math.random() * (n * n - i));
            randomNumbers[i] = serialNumbers[a];
            serialNumbers[a] = serialNumbers[serialNumbers.length - 1 - i];
        }
        numbers = new int[n + 2][n + 2];
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(i == 0 || j == 0 || i == numbers.length - 1 || j == numbers[i].length - 1) {
                    numbers[i][j] = 1;
                } else {
                    numbers[i][j] = randomNumbers[b];
                    b++;
                }
            }
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (n != 0)
            drawCurrentState(g);
    }

    private void drawCurrentState(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke normal = g2.getStroke();
        int width = getWidth();
        int height = getHeight();
        if (width / n < height / n)
            squareSide = (int)Math.ceil((width) / n);
        else
            squareSide = (int)Math.ceil((height) / n);
        xStartingPoint = (width - n * squareSide) / 2;
        yStartingPoint = (height - n * squareSide) / 2;
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(xStartingPoint, yStartingPoint, n * squareSide, n * squareSide);
        g.setColor(Color.WHITE);
        g2.setStroke(normal);
        // net
        for (int j = 0; j < n + 1; j++)
            g.drawLine(xStartingPoint, yStartingPoint + j * squareSide, xStartingPoint + n * squareSide, yStartingPoint + j * squareSide);
        for (int i = 0; i < n + 1; i++)
            g.drawLine(xStartingPoint + i * squareSide, yStartingPoint, xStartingPoint + i * squareSide, yStartingPoint + n * squareSide);
        // double frame line around gaming panel
        Stroke fatStroke = new BasicStroke(2.0f);
        g2.setStroke(fatStroke);
        Shape frame2 = new Rectangle2D.Float(xStartingPoint, yStartingPoint, n * squareSide, n * squareSide);
        g2.draw(frame2);
        // font settings
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        int ascent = fm.getAscent();
        // font size
        int fontSIze = (int)(0.8 * 14 * squareSide / ascent);
        g.setFont(new Font("SansSerif", Font.BOLD, fontSIze));
        fm = g.getFontMetrics();
        ascent = fm.getAscent();
        // draw gaming panel
        int x = xStartingPoint;
        for (int i = 1; i < numbers.length - 1; i++) {
            int y = yStartingPoint;
            for (int j = 1; j < numbers[i].length - 1; j++) {
                int digit = numbers[i][j];
                String text = Integer.toString(digit);
                int txtSirina = fm.stringWidth(text);
                if (digit != 0) {
                    g.setColor(FULL_SQUARE_COLOR);
                    g.fillRect(x + 2, y + 2, squareSide - 3, squareSide - 3);
                    g.setColor(NUMBER_COLOR);
                    g.drawString(text, x + (squareSide - txtSirina) / 2, y + (squareSide + ascent - 14) / 2);
                } else if (digit == 0) {
                    g.setColor(EMPTY_SQUARE_COLOR);
                    g.fillRect(x + 2, y + 2, squareSide - 3, squareSide - 3);
                }
                y = y + squareSide;
            }
            x = x + squareSide;
        }
    }

    public void checkEnd() {
        int a = 0;
        int[] serialNumbers = new int[n * n];
        for(int i = 1; i < n * n; i++) {
            serialNumbers[i - 1] = i;
            serialNumbers[(n * n) - 1] = 0;
        }
        boolean game = true;
        for (int i = 1; i < numbers.length - 1; i++) {
            for (int j = 1; j < numbers[i].length - 1; j++) {
                // System.out.printf("%3d ", numbers[j][i]);
                if (numbers[j][i] != serialNumbers[a])
                    game = false;
                a++;
            }
        }
        gameFinished = game;
    }

    public void resetPanel() {
        this.gameFinished = false;
    }

    public void setCounter() {
        this.counter = 0;
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        int tx = (p.x - xStartingPoint) / squareSide;
        int ty = (p.y - yStartingPoint) / squareSide;
        int digit = numbers[tx + 1][ty + 1 ];
        if ((p.x > xStartingPoint) && (p.y > yStartingPoint) && (tx < n) && (ty < n) && (e.getButton() == MouseEvent.BUTTON1)) {
            // left
            if (digit != 0 && numbers[tx + 2][ty + 1] == 0) {
                numbers[tx + 2][ty + 1] = digit;
                numbers[tx + 1][ty + 1] = 0;
                repaint();
                counter++;
                frame.writeMove(counter);
                checkEnd();
            }
            // right
            else if (digit != 0 && numbers[tx][ty + 1] == 0) {
                numbers[tx][ty + 1] = digit;
                numbers[tx + 1][ty + 1] = 0;
                repaint();
                counter++;
                frame.writeMove(counter);
                checkEnd();
            }
            // up
            else if (digit != 0 && numbers[tx + 1][ty + 2] == 0) {
                numbers[tx + 1][ty + 2] = digit;
                numbers[tx + 1][ty + 1] = 0;
                repaint();
                counter++;
                frame.writeMove(counter);
                checkEnd();
            }
            // down
            else if (digit != 0 && numbers[tx + 1][ty] == 0) {
                numbers[tx + 1][ty] = digit;
                numbers[tx + 1][ty + 1] = 0;
                repaint();
                counter++;
                frame.writeMove(counter);
                checkEnd();
            }
        }
        if (gameFinished) {
            JOptionPane.showMessageDialog(null, "Congratulation! \nYou Win!", "Great", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
}	