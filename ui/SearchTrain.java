package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class SearchTrain extends JFrame implements ActionListener {

    JTextField fromField, toField;
    JButton searchBtn;
    JTextArea resultArea;
    JButton proceedBtn;
    JTextField trainIdField;
    JTextField dateField;

    String selectedDate = ""; // store selected date

    public SearchTrain() {
        setTitle("Search Train");

        fromField = new JTextField(10);
        toField = new JTextField(10);
        searchBtn = new JButton("Search");
        resultArea = new JTextArea(10, 30);

        resultArea.setEditable(false);

        setLayout(new FlowLayout());

        add(new JLabel("From:"));
        add(fromField);

        add(new JLabel("To:"));
        add(toField);

        add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField(10);
        add(dateField);

        add(searchBtn);
        add(new JScrollPane(resultArea));

        add(new JLabel("Enter Train ID to Book:"));
        trainIdField = new JTextField(10);
        add(trainIdField);

        proceedBtn = new JButton("Proceed to Book");
        add(proceedBtn);

        searchBtn.addActionListener(this);

        // PASS DATE TO BOOKING
        proceedBtn.addActionListener(e -> {
            try {
                String trainText = trainIdField.getText();

                if (trainText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter Train ID!");
                    return;
                }

                if (selectedDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please search trains first!");
                    return;
                }

                int trainId = Integer.parseInt(trainText);

                new BookTicket(trainId, selectedDate); // 🔥 pass date

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Enter valid numeric Train ID!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        setSize(450, 450);
        setVisible(true);
    }

    // Convert station name → station_id
    public int getStationId(String stationName) {
        switch (stationName.toLowerCase().trim()) {
            case "new delhi": return 10;
            case "pune":
            case "pune junction": return 1;
            case "mumbai":
            case "mumbai central": return 6;
            case "dadar": return 7;
            case "thane": return 8;
            case "borivali": return 9;
            case "ahmedabad junction": return 12;
            case "bangalore city": return 13;
            default: return -1;
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String from = fromField.getText();
            String to = toField.getText();
            String date = dateField.getText();

            if (from.isEmpty() || to.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter source, destination and date!");
                return;
            }

            int sourceId = getStationId(from);
            int destId = getStationId(to);

            if (sourceId == -1 || destId == -1) {
                JOptionPane.showMessageDialog(this, "Invalid station name!");
                return;
            }

            Connection conn = DBConnection.getConnection();

            String query =
                    "SELECT DISTINCT t.train_id, t.train_name, tr2.departure_time " +
                    "FROM train t " +
                    "JOIN train_route tr1 ON t.train_id = tr1.train_id " +
                    "JOIN train_route tr2 ON t.train_id = tr2.train_id " +
                    "WHERE tr1.station_id = ? AND tr2.station_id = ? " +
                    "AND tr1.stop_number < tr2.stop_number";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, sourceId);
            pst.setInt(2, destId);

            ResultSet rs = pst.executeQuery();

            boolean found = false;
            resultArea.setText("");

            selectedDate = date; // store selected date

            while (rs.next()) {
                found = true;

                resultArea.append(
                        "--------------------------------\n" +
                        "Train ID: " + rs.getInt("train_id") + "\n" +
                        "Train Name: " + rs.getString("train_name") + "\n" +
                        "Date: " + date + "\n" +
                        "Departure Time: " + rs.getTime("departure_time") + "\n" +
                        "--------------------------------\n"
                );
            }

            if (!found) {
                resultArea.setText("No trains found");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new SearchTrain();
    }
}