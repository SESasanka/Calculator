package hardcode;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
    
    JTextField display;
    JButton[] numberButtons = new JButton[10];
    JButton addButton, subButton, mulButton, divButton, PerButton;
    JButton decButton, equButton, delButton, clrButton;
    
    JPanel panel;
    
    double num1 = 0, num2 = 0, result = 0;
    char operator;
    
    public Calculator() {
        setTitle("Java Swing Calculator");
        setSize(420, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Display field
        display = new JTextField();
        display.setBounds(50, 25, 300, 50);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display);

        // Create buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        PerButton = new JButton("%");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("C");

        // Store function buttons
        JButton[] functionButtons = {
            addButton, subButton, mulButton, divButton, PerButton,
            decButton, equButton, delButton, clrButton
        };

        // Button styling and event handling
        for (JButton btn : functionButtons) {
            btn.addActionListener(this);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusable(false);
        }

        // Number buttons 0-9
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numberButtons[i].setFocusable(false);
        }

        // Panel for number and operator buttons
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        // Add buttons to panel
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(equButton);
        panel.add(divButton);
        
        add(panel);

        // Add delete and clear buttons
        delButton.setBounds(50, 420, 145, 50);
        clrButton.setBounds(205, 420, 145, 50);
        add(delButton);
        add(clrButton);

        // Make frame visible
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                display.setText(display.getText().concat(String.valueOf(i)));
            }
        }

        // Handle decimal point
        if (e.getSource() == decButton) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText().concat("."));
            }
        }

        // Handle operators
        if (e.getSource() == addButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '+';
            display.setText("");
        }
        if (e.getSource() == subButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '-';
            display.setText("");
        }
        if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '*';
            display.setText("");
        }
        if (e.getSource() == divButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '/';
            display.setText("");
        }

        // Handle equals button
        if (e.getSource() == equButton) {
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        result = (num2 != 0) ? num1 / num2 : 0;
                        break;
                }
                display.setText(String.valueOf(result));
                num1 = result;
            } catch (Exception ex) {
                display.setText("Error");
            }
        }

        // Handle clear
        if (e.getSource() == clrButton) {
            display.setText("");
        }

        // Handle delete
        if (e.getSource() == delButton) {
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        }
    }
    
    public static void main(String[] args) {
        
        FlatMacDarkLaf.setup();
        
        new Calculator();
    }
}
