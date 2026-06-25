import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField nameField, gradeField;
    private JTable table;
    private DefaultTableModel model;

    public StudentGradeTrackerGUI() {

        setTitle("Student Grade Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton statsButton = new JButton("Show Statistics");

        inputPanel.add(addButton);
        inputPanel.add(statsButton);

        // Table
        String[] columns = {"Student Name", "Grade"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add Student Action
        addButton.addActionListener(e -> addStudent());

        // Statistics Action
        statsButton.addActionListener(e -> showStatistics());

        setVisible(true);
    }

    private void addStudent() {

        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);

            model.addRow(new Object[]{name, grade});

            nameField.setText("");
            gradeField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid grade!");
        }
    }

    private void showStatistics() {

        int rows = model.getRowCount();

        if (rows == 0) {
            JOptionPane.showMessageDialog(this,
                    "No student data available!");
            return;
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        String highestStudent = "";
        String lowestStudent = "";

        for (int i = 0; i < rows; i++) {

            String name = model.getValueAt(i, 0).toString();
            double grade = Double.parseDouble(
                    model.getValueAt(i, 1).toString());

            total += grade;

            if (grade > highest) {
                highest = grade;
                highestStudent = name;
            }

            if (grade < lowest) {
                lowest = grade;
                lowestStudent = name;
            }
        }

        double average = total / rows;

        JOptionPane.showMessageDialog(this,
                "Total Students: " + rows +
                        "\nAverage Score: " + String.format("%.2f", average) +
                        "\nHighest Score: " + highest + " (" + highestStudent + ")" +
                        "\nLowest Score: " + lowest + " (" + lowestStudent + ")",
                "Statistics",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new StudentGradeTrackerGUI());
    }
}