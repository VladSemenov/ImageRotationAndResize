package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

/**
 * Created by Vladislav on 26.04.16.
 */
public class MainFrame extends JFrame {
    private JLabel imageLabel;
    private JButton plusButton;
    private JButton minusButton;
    private JTextField angleTextField;
    private JButton rotateButton;

    public MainFrame() {
        super.setTitle("Image rotation and resize");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

//        JMenuBar menuBar = new JMenuBar();
//        setJMenuBar(menuBar);
//
//        JMenu fileMenu = new JMenu("File");
//        menuBar.add(fileMenu);

        JToolBar toolBar = new JToolBar();

        this.plusButton = new JButton("+");
        toolBar.add(this.plusButton);

        this.minusButton = new JButton("-");
        toolBar.add(this.minusButton);

        this.angleTextField = new JTextField(3);
        toolBar.add(this.angleTextField);

        this.rotateButton = new JButton("Rotate");
        toolBar.add(this.rotateButton);

        this.add(toolBar, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();

        this.imageLabel = new JLabel();
        this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.imageLabel.setVerticalAlignment(JLabel.CENTER);

        scrollPane.getViewport().add(this.imageLabel);

        this.add(scrollPane);
//        add(this.imageLabel, BorderLayout.CENTER);
    }
    public void showFrame() {
        setVisible(true);
    }
    public void setImage(BufferedImage image) {
        ImageIcon imageIcon = new ImageIcon(image);
        this.imageLabel.setIcon(imageIcon);
        this.imageLabel.setSize(image.getWidth(), image.getHeight());
        this.imageLabel.repaint();
    }
    public void addMouseListner(MouseAdapter mouseAdapter) {
        this.imageLabel.addMouseListener(mouseAdapter);
    }
    public void addPlusButtonActionListener(ActionListener listener) {
        this.plusButton.addActionListener(listener);
    }
    public void addMinusButtonActionListener(ActionListener listener) {
        this.minusButton.addActionListener(listener);
    }
    public void addRotateButtonActionListener(ActionListener listener) {
        this.rotateButton.addActionListener(listener);
    }
    public int getAngle() {
        String angleString = this.angleTextField.getText();
        int angle = 0;

        try {
            angle = Integer.parseInt(angleString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return angle;
    }
}
