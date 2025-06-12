package cal;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorUI extends JFrame implements ActionListener {

    JTextField display;
    JLabel label;
    String operator;
    double num1, num2, result;

    public CalculatorUI() {
        setTitle("My Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        // Display Field
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Buttons Panel
        String[] buttons = {
            "CE", "C", "←", "%",
            "1/x", "x²", "√x", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "="
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5));

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        new CalculatorUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            switch (cmd) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    if (display.getText().equals("0")) {
                        display.setText(cmd);
                    } else {
                        display.setText(display.getText() + cmd);
                    }
                    break;

                case ".":
                    if (!display.getText().contains(".")) {
                        display.setText(display.getText() + ".");
                    }
                    break;

                case "+":
                case "-":
                case "*":
                case "/":
                    num1 = Double.parseDouble(display.getText());
                    operator = cmd;
                    display.setText("");
                    break;

                case "=":
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            result = (num2 != 0) ? num1 / num2 : 0;
                            break;
                    }
                    display.setText(String.valueOf(result));
                    break;

                case "%":
                    double current = Double.parseDouble(display.getText());
                    if (operator != null && !operator.isEmpty()) {
                        current = (num1 * current) / 100;
                        display.setText(String.valueOf(current));
                    } else {
                        display.setText(String.valueOf(current / 100));
                    }
                    break;

                case "C":
                    display.setText("0");
                    num1 = num2 = result = 0;
                    operator = "";
                    break;

                case "CE":
                    display.setText("0");
                    break;

                case "←":
                    String text = display.getText();
                    if (text.length() > 0) {
                        display.setText(text.substring(0, text.length() - 1));
                    }
                    if (display.getText().isEmpty()) {
                        display.setText("0");
                    }
                    break;

                case "±":
                    double val = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(-val));
                    break;

                case "x²":
                    double square = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(square * square));
                    break;

                case "√x":
                    double sqrt = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.sqrt(sqrt)));
                    break;

                case "1/x":
                    double x = Double.parseDouble(display.getText());
                    if (x != 0) {
                        display.setText(String.valueOf(1 / x));
                    } else {
                        display.setText("Error");
                    }
                    break;
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }
}
