package cal;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalculatorUI extends JFrame implements ActionListener {

    JTextField display;
    String operator;
    double num1, num2, result;

    public CalculatorUI() {
        setTitle("My Calculator");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\sasan\\Downloads\\cal.png");
        setIconImage(icon);
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        // Display Field
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Standard Calculator");
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 35));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        topPanel.add(display);
        topPanel.add(titleLabel);

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(display, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

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
        Component last = panel.getComponent(panel.getComponentCount() - 1);
        if (last instanceof JButton button && "=".equals(button.getText())) {
            button.setBackground(new Color(0, 122, 255)); // Blue
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setDisplayValue(double value) {
        if (value > 1e10) {
            display.setText("Value too large");
        } else if (value < -1e10) {
            display.setText("Value too small");
        } else {
            DecimalFormat df = new DecimalFormat("#.###############");
            display.setText(df.format(value));
        }
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        new CalculatorUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String currentText = display.getText();
        String newText = currentText.equals("0") ? cmd : currentText + cmd;

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
                    String digitsOnly = newText.replaceAll("[^0-9]", "");
                    if (digitsOnly.length() <= 10) {
                        display.setText(newText);
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
                    setDisplayValue(result);
                    break;

                case "%":
                    double current = Double.parseDouble(display.getText());
                    setDisplayValue(current / 100);
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
                    setDisplayValue(-val);
                    break;

                case "x²":
                    double square = Double.parseDouble(display.getText());
                    setDisplayValue(square * square);
                    break;

                case "√x":
                    double sqrt = Double.parseDouble(display.getText());
                    setDisplayValue(Math.sqrt(sqrt));
                    break;

                case "1/x":
                    double x = Double.parseDouble(display.getText());
                    if (x != 0) {
                        setDisplayValue(1 / x);
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
