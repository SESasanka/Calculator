package hardcode;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.text.DecimalFormat;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Calculator extends JFrame {

    private JTextField display;
    private double num1 = 0, num2 = 0;
    private String operator = "";

    public Calculator() {
        this.setTitle("My Calculator");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\sasan\\Downloads\\cal.png");
        setIconImage(icon);

        this.setSize(350, 450);

        this.setSize(380, 550);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();

        topPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Standard Calculator");
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        add(topPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel panel = new JPanel(new GridLayout(6, 4, 5, 5));

        // Row 1
        addButton(panel, "CE", new ClearEntryListener());
        addButton(panel, "C", new ClearAllListener());
        addButton(panel, "X", new BackspaceListener());
        addButton(panel, "%", new PercentageListener());

        // Row 2
        addButton(panel, "1 / x", new InverseListener());
        addButton(panel, "x²", new SquareListener());
        addButton(panel, "√x", new SquareRootListener());
        addButton(panel, "/", new OperatorListener("/"));

        // Row 3
        addButton(panel, "7", new DigitListener("7"));
        addButton(panel, "8", new DigitListener("8"));
        addButton(panel, "9", new DigitListener("9"));
        addButton(panel, "*", new OperatorListener("*"));

        // Row 4
        addButton(panel, "4", new DigitListener("4"));
        addButton(panel, "5", new DigitListener("5"));
        addButton(panel, "6", new DigitListener("6"));
        addButton(panel, "-", new OperatorListener("-"));

        // Row 5
        addButton(panel, "1", new DigitListener("1"));
        addButton(panel, "2", new DigitListener("2"));
        addButton(panel, "3", new DigitListener("3"));
        addButton(panel, "+", new OperatorListener("+"));

        // Row 6
        addButton(panel, "±", new SignChangeListener());
        addButton(panel, "0", new DigitListener("0"));
        addButton(panel, ".", new DecimalListener());
        addButton(panel, "=", new EqualListener());

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

    private void addButton(JPanel panel, String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.addActionListener(listener);
        panel.add(button);
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

    // === Inner Classes for ActionListeners ===
    private class DigitListener implements ActionListener {

        private String digit;

        public DigitListener(String digit) {
            this.digit = digit;
        }

        public void actionPerformed(ActionEvent e) {
            String current = display.getText();
            if (current.equals("Value too large") || current.equals("Value too small") || current.equals("Error")) {
                display.setText(digit);
                return;
            }
            int digitCount = current.replace("-", "").replace(".", "").length();
            if (digitCount >= 10) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            if (current.equals("0")) {
                display.setText(digit);
            } else {
                display.setText(current + digit);
            }
        }
    }

    private class OperatorListener implements ActionListener {

        private String op;

        public OperatorListener(String op) {
            this.op = op;
        }

        public void actionPerformed(ActionEvent e) {
            num1 = Double.parseDouble(display.getText());
            operator = op;
            display.setText("0");
        }
    }

    private class EqualListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            num2 = Double.parseDouble(display.getText());
            double result = 0;
            switch (operator) {
                case "+" ->
                    result = num1 + num2;
                case "-" ->
                    result = num1 - num2;
                case "*" ->
                    result = num1 * num2;
                case "/" ->
                    result = num2 != 0 ? num1 / num2 : Double.NaN;
            }

            setDisplayValue(result);
            operator = "";
        }
    }

    private class PercentageListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            double val = Double.parseDouble(display.getText());
            setDisplayValue(val / 100);
        }
    }

    private class DecimalListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }
    }

    private class ClearEntryListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            display.setText("0");
        }
    }

    private class ClearAllListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            display.setText("0");
            num1 = num2 = 0;
            operator = "";
        }
    }

    private class BackspaceListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String text = display.getText();
            if (text.length() > 1) {
                display.setText(text.substring(0, text.length() - 1));
            } else {
                display.setText("0");
            }
        }
    }

    private class InverseListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            double val = Double.parseDouble(display.getText());
            if (val != 0) {
                setDisplayValue(1 / val);
            } else {
                display.setText("Error");
            }
        }
    }

    private class SquareListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            double val = Double.parseDouble(display.getText());
            setDisplayValue(val * val);
        }
    }

    private class SquareRootListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            double val = Double.parseDouble(display.getText());
            if (val >= 0) {
                setDisplayValue(Math.sqrt(val));
            } else {
                display.setText("Error");
            }
        }
    }

    private class SignChangeListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            double val = Double.parseDouble(display.getText());
            setDisplayValue(-val);
        }
    }

    // === Main method ===
    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        SwingUtilities.invokeLater(Calculator::new);
    }
}
