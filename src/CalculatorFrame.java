import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorFrame extends JFrame {

    private String previousNum, currentNum, operator;
    private JButton[] numbersBtn;
    private JButton addBtn, subtractBtn, multiplyBtn, divideBtn, equalsBtn, clearBtn;
    private JTextField resultTextField;

    public CalculatorFrame(int width, int height, int locationX, int locationY, String title) {
        operator = "";
        currentNum = "";
        previousNum = "";
        this.initUI(width, height, locationX, locationY, title);
    }

    private void initUI(int width, int height, int locationX, int locationY, String title) {
        this.setSize(width, height);
        this.setResizable(true);
        this.setLocation(locationX, locationY);
        this.setTitle(title);
        this.setLayout(new GridLayout(6, 3));

        // Without this the application will continue running after the window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        NumberBtnHandler numberBtnHandler = new NumberBtnHandler();
        OperatorBtnHandler operatorBtnHandler = new OperatorBtnHandler();
        OtherBtnHandler otherBtnHandler = new OtherBtnHandler();

        //Initialize buttons with action listeners & add them to calculator frame (this)
        numbersBtn = new JButton[]{
                new JButton("1"),
                new JButton("2"),
                new JButton("3"),
                new JButton("4"),
                new JButton("5"),
                new JButton("6"),
                new JButton("7"),
                new JButton("8"),
                new JButton("9"),
                new JButton("0")
        };

        for (JButton btn : numbersBtn) {
            btn.addActionListener(numberBtnHandler);
            this.add(btn);
        }

        addBtn = new JButton("+");
        addBtn.addActionListener(operatorBtnHandler);
        this.add(addBtn);

        subtractBtn = new JButton("-");
        subtractBtn.addActionListener(operatorBtnHandler);
        this.add(subtractBtn);

        multiplyBtn = new JButton("*");
        multiplyBtn.addActionListener(operatorBtnHandler);
        this.add(multiplyBtn);

        divideBtn = new JButton("/");
        divideBtn.addActionListener(operatorBtnHandler);
        this.add(divideBtn);

        equalsBtn = new JButton("=");
        equalsBtn.addActionListener(otherBtnHandler);
        this.add(equalsBtn);

        clearBtn = new JButton("C");
        clearBtn.addActionListener(otherBtnHandler);
        this.add(clearBtn);

        //Initialize text field & add it to calculator frame(this)
        resultTextField = new JTextField(16);
        resultTextField.setEditable(false);
        this.add(resultTextField);
    }

    private void clear() {
        previousNum = "";
        currentNum = "";
        operator = "";
        resultTextField.setText("");
    }

    private void calculate() {
        if (currentNum.isBlank() || operator.isBlank()) return;

        double num1 = Double.parseDouble(previousNum);
        double num2 = Double.parseDouble(currentNum);
        double result = 0.0;

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
                result = num1 / num2; //TODO: Handle division by zero
                break;
        }

        clear();
        previousNum = String.valueOf(result);
        resultTextField.setText(String.valueOf(result));
    }

    private class NumberBtnHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String numText = ((JButton) e.getSource()).getText();
            if (previousNum.isBlank()) {
                previousNum = numText;
            }
            else if (currentNum.isBlank()) {
                currentNum = numText;
            }

            resultTextField.setText(numText);
        }
    }

    private class OperatorBtnHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selected = (JButton) e.getSource();
            if (!previousNum.isBlank() && !currentNum.isBlank()) {
                calculate();
            }
            operator = selected.getText();
        }
    }

    private class OtherBtnHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selected = (JButton) e.getSource();
            if (selected == clearBtn) {
                clear();
            }
            else if (selected == equalsBtn) {
                calculate();
            }
        }
    }
}
