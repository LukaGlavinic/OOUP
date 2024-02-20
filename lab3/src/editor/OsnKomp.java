package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;

public class OsnKomp extends JFrame {

	@Serial
    private static final long serialVersionUID = 1L;

    public OsnKomp() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
		setSize(600, 600);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dispose();
                }
            }
        });
        initGUI();
    }

    private void initGUI() {
        Platno platno = new Platno();
        getContentPane().add(platno);
    }

    private static class Platno extends JComponent {
        
		@Serial
        private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);

            Font font = new Font("Monospaced", Font.PLAIN, 12);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.BLACK);
            g.drawString("This is another test string", 0, fm.getAscent());
            g.drawString("This is another new test string", 0, 2 * fm.getAscent());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OsnKomp().setVisible(true));
    }
}