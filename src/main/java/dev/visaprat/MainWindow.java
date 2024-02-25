package dev.visaprat;

import lombok.Cleanup;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {

    public static final String SECRET_TXT = "secret.txt";
    private final JTextField textField;
    private final JButton button;

    public MainWindow() {
        setTitle("swingOTP");
        setBounds(300, 400, 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(null);

        textField = new JTextField();
        textField.setSize(200, 30);
        textField.setLocation(100, 150);

        button = new JButton();
        button.setSize(80, 20);
        button.setLocation(160, 200);

        File f = new File(SECRET_TXT);

        if (!f.exists()) {
            button.setText("Add");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(button)) {
                        String secret = textField.getText();
                        if (secret != null && !secret.isBlank()) {
                            File f = new File(SECRET_TXT);
                            try {
                                boolean ignored = f.createNewFile();
                                @Cleanup FileWriter writer = new FileWriter(f, false);
                                writer.write(secret);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    displayOTP();
                    mainContainer.revalidate();
                    mainContainer.repaint();
                }
            });
        } else {
            displayOTP();

        }
        mainContainer.add(textField);
        mainContainer.add(button);
        setVisible(true);
    }

    private void displayOTP() {
        textField.setText("659837");
        textField.setEditable(false);
        button.setText("Copy");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(button)) {
                    Toolkit.getDefaultToolkit().getSystemClipboard()
                            .setContents(new StringSelection(textField.getText()), null);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {
            String secret = textField.getText();
            if (secret != null && !secret.isBlank()) {
                File f = new File("secret.txt");
                try {
                    boolean ignored = f.createNewFile();
                    @Cleanup FileWriter writer = new FileWriter(f, false);
                    writer.write(secret);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
