import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Class representing the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double checkBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }
}

// ATM class with GUI
public class ATM extends JFrame implements ActionListener {

    private BankAccount account;
    private JButton withdrawButton, depositButton, balanceButton, clearButton;
    private JTextArea messageArea;

    public ATM() {
        account = new BankAccount(5000); // initial balance

        setTitle("ATM Machine");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(128, 0, 128)); // purple background

        // Heading
        JLabel heading = new JLabel("Welcome to ATM");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setBounds(130, 20, 300, 30);
        add(heading);

        // Buttons
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(50, 80, 180, 50);
        withdrawButton.setBackground(new Color(255, 153, 51));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 16));
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(300, 80, 180, 50);
        depositButton.setBackground(new Color(51, 153, 255));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFont(new Font("Arial", Font.BOLD, 16));
        depositButton.addActionListener(this);
        add(depositButton);

        balanceButton = new JButton(" Check Balance");
        balanceButton.setBounds(50, 150, 180, 50);
        balanceButton.setBackground(new Color(0, 204, 102));
        balanceButton.setForeground(Color.WHITE);
        balanceButton.setFont(new Font("Arial", Font.BOLD, 16));
        balanceButton.addActionListener(this);
        add(balanceButton);

        clearButton = new JButton(" Clear");
        clearButton.setBounds(300, 150, 180, 50);
        clearButton.setBackground(new Color(204, 0, 204));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.addActionListener(this);
        add(clearButton);

        // Message area
        messageArea = new JTextArea();
        messageArea.setBounds(50, 220, 430, 160);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
        messageArea.setBackground(new Color(245, 245, 245)); // light background
        messageArea.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(messageArea);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (account.withdraw(amount)) {
                        messageArea.setText("Withdrawal successful!\nAmount withdrawn: ₹" + amount +
                                "\nCurrent balance: ₹" + account.checkBalance());
                    } else {
                        messageArea.setText("Withdrawal failed! Insufficient balance or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == depositButton) {
            String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
            if (input != null) {
                try {
                    double amount = Double.parseDouble(input);
                    if (account.deposit(amount)) {
                        messageArea.setText("Deposit successful!\nAmount deposited: ₹" + amount +
                                "\nCurrent balance: ₹" + account.checkBalance());
                    } else {
                        messageArea.setText("Deposit failed! Enter a valid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, " Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == balanceButton) {
            messageArea.setText("Current Balance: ₹" + account.checkBalance());
        } else if (e.getSource() == clearButton) {
            messageArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATM());
    }
}
