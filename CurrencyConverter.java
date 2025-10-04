import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

public class CurrencyConverter extends JFrame implements ActionListener {

    private JComboBox<String> baseCurrency, targetCurrency;
    private JTextField amountField;
    private JButton convertButton;
    private JLabel resultLabel;
    private HashMap<String, String> currencySymbols;

    // Some common currencies
    private String[] currencies = {"USD", "INR", "EUR", "GBP", "JPY", "AUD", "CAD"};

    public CurrencyConverter() {
        setTitle("💱 Currency Converter");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(230, 248, 255));

        // Heading
        JLabel heading = new JLabel("Currency Converter");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(120, 20, 300, 40);
        heading.setForeground(new Color(0, 102, 204));
        add(heading);

        // Base currency
        JLabel baseLabel = new JLabel("From:");
        baseLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        baseLabel.setBounds(80, 90, 100, 25);
        add(baseLabel);

        baseCurrency = new JComboBox<>(currencies);
        baseCurrency.setBounds(180, 90, 100, 30);
        add(baseCurrency);

        // Target currency
        JLabel targetLabel = new JLabel("To:");
        targetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        targetLabel.setBounds(80, 140, 100, 25);
        add(targetLabel);

        targetCurrency = new JComboBox<>(currencies);
        targetCurrency.setBounds(180, 140, 100, 30);
        add(targetCurrency);

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        amountLabel.setBounds(80, 190, 100, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setBounds(180, 190, 150, 30);
        add(amountField);

        // Convert button
        convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Arial", Font.BOLD, 16));
        convertButton.setBounds(180, 240, 120, 40);
        convertButton.setBackground(new Color(0, 153, 76));
        convertButton.setForeground(Color.WHITE);
        convertButton.addActionListener(this);
        add(convertButton);

        // Result label
        resultLabel = new JLabel("Converted amount will appear here.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setBounds(60, 300, 400, 30);
        resultLabel.setForeground(new Color(204, 0, 0));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel);

        // Currency symbols
        currencySymbols = new HashMap<>();
        currencySymbols.put("USD", "$");
        currencySymbols.put("INR", "₹");
        currencySymbols.put("EUR", "€");
        currencySymbols.put("GBP", "£");
        currencySymbols.put("JPY", "¥");
        currencySymbols.put("AUD", "A$");
        currencySymbols.put("CAD", "C$");

        setVisible(true);
    }

    // Function to fetch conversion rate using API
    private double getExchangeRate(String from, String to) {
        try {
            String urlStr = "https://api.exchangerate-api.com/v4/latest/" + from;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONObject rates = json.getJSONObject("rates");

            return rates.getDouble(to);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error fetching exchange rate!", "API Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) baseCurrency.getSelectedItem();
                String to = (String) targetCurrency.getSelectedItem();

                if (from.equals(to)) {
                    resultLabel.setText("Base and Target currency cannot be same!");
                    return;
                }

                double rate = getExchangeRate(from, to);

                if (rate != -1) {
                    double converted = amount * rate;
                    String symbol = currencySymbols.getOrDefault(to, "");
                    resultLabel.setText(amount + " " + from + " = " +
                            symbol + String.format("%.2f", converted) + " " + to);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, " Please enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter());
    }
}
