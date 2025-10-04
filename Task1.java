import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Task1 extends JFrame implements ActionListener {
    private JLabel titleLabel, messageLabel, attemptsLabel, scoreLabel;
    private JTextField guessField;
    private JButton guessButton, newGameButton, exitButton;
    private int randomNumber, attemptsLeft, score, round;
    private final int MAX_ATTEMPTS = 7;

    public Task1() {
        setTitle(" Number Guessing Game ");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(220, 240, 255));

        // Title
        titleLabel = new JLabel("Welcome to Number Guessing Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(40, 20, 420, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Message
        messageLabel = new JLabel("Guess a number between 1 and 100:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setBounds(70, 80, 350, 30);
        add(messageLabel);

        // Input field
        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessField.setBounds(150, 120, 180, 30);
        add(guessField);

        // Guess button
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.setBackground(new Color(100, 200, 100));
        guessButton.setBounds(180, 170, 120, 35);
        guessButton.addActionListener(this);
        add(guessButton);

        // Attempts label
        attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        attemptsLabel.setBounds(150, 220, 250, 30);
        add(attemptsLabel);

        // Score label
        scoreLabel = new JLabel("Score: 0 | Round: 1");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setBounds(150, 250, 250, 30);
        add(scoreLabel);

        // New Game button
        newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        newGameButton.setBackground(new Color(100, 149, 237));
        newGameButton.setBounds(100, 300, 120, 35);
        newGameButton.addActionListener(this);
        add(newGameButton);

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(new Color(255, 99, 71));
        exitButton.setBounds(250, 300, 120, 35);
        exitButton.addActionListener(this);
        add(exitButton);

        startNewRound();
        setVisible(true);
    }

    // Start a new round
    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1; // random 1-100
        attemptsLeft = MAX_ATTEMPTS;
        round++;
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        messageLabel.setText("Guess a number between 1 and 100:");
        guessField.setText("");
        guessField.setEditable(true);
        guessButton.setEnabled(true);
        scoreLabel.setText("Score: " + score + " | Round: " + round);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            String input = guessField.getText();
            try {
                int guess = Integer.parseInt(input);
                attemptsLeft--;

                if (guess == randomNumber) {
                    messageLabel.setText("Correct! The number was " + randomNumber);
                    score += 10;
                    guessButton.setEnabled(false);
                    guessField.setEditable(false);
                } else if (guess < randomNumber) {
                    messageLabel.setText("Too Low! Try again.");
                } else {
                    messageLabel.setText("Too High! Try again.");
                }

                if (attemptsLeft <= 0 && guess != randomNumber) {
                    messageLabel.setText("Out of attempts! Number was " + randomNumber);
                    guessButton.setEnabled(false);
                    guessField.setEditable(false);
                }

                attemptsLabel.setText("Attempts left: " + attemptsLeft);
                scoreLabel.setText("Score: " + score + " | Round: " + round);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == newGameButton) {
            startNewRound();
        } else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(this, "Thanks for playing! Final Score: " + score);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Task1();
    }
}
