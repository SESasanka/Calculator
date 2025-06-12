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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalculatorUI extends JFrame implements ActionListener {

    JTextField display;
    JLabel label;
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
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Standard Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.LEADING);
        topPanel.add(titleLabel);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 35));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        topPanel.add(display);
        
        add(topPanel, BorderLayout.NORTH);

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
