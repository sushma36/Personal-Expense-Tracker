import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ExpenseTracker {
    private ArrayList<Expense> expenses;
    private JFrame frame;
    private JTextField amountField, categoryField, dateField, searchCategoryField, searchDateField;
    private JList<String> displayList;
    private JButton saveButton, searchButton,viewMonthlyButton,viewDailyButton;
    private JTextArea displayArea;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Personal Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Use null layout

        // Input fields
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(10, 10, 100, 25); // x, y, width, height
        frame.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(120, 10, 200, 25); // x, y, width, height
        frame.add(amountField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(10, 40, 100, 25); // x, y, width, height
        frame.add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(120, 40, 200, 25); // x, y, width, height
        frame.add(categoryField);

        JLabel dateLabel = new JLabel("Date(yyyy/MM/dd):");
        dateLabel.setBounds(10, 70, 100, 25); // x, y, width, height
        frame.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(120, 70, 200, 25); // x, y, width, height
        frame.add(dateField);

        // Search fields
        JLabel searchCategoryLabel = new JLabel("Search by Category:");
        searchCategoryLabel.setBounds(10, 100, 120, 25); // x, y, width, height
        frame.add(searchCategoryLabel);

        searchCategoryField = new JTextField();
        searchCategoryField.setBounds(140, 100, 150, 25); // x, y, width, height
        frame.add(searchCategoryField);

        JLabel searchDateLabel = new JLabel("Search by Date:");
        searchDateLabel.setBounds(300, 100, 120, 25); // x, y, width, height
        frame.add(searchDateLabel);

        searchDateField = new JTextField();
        searchDateField.setBounds(430, 100, 150, 25); // x, y, width, height
        frame.add(searchDateField);

        searchButton = new JButton("Search");
        searchButton.setBounds(590, 100, 100, 25); // x, y, width, height
        searchButton.addActionListener(e -> searchExpenses());
        frame.add(searchButton);

        // Display area
        displayList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(displayList);
        scrollPane.setBounds(10, 130, 570, 130); // x, y, width, height
        frame.add(scrollPane);

        // Buttons
        JButton addButton = new JButton("Add Expense");
        addButton.setBounds(10, 340, 130, 25); // x, y, width, height
        addButton.addActionListener(e -> addExpense());
        frame.add(addButton);
        
        JButton viewButton = new JButton("View Expenses");
        viewButton.setBounds(150, 340, 140, 25);
        viewButton.addActionListener(e -> viewExpenses());
        
        displayArea = new JTextArea();
        displayArea.setBounds(10, 130, 570, 130);
        frame.add(displayArea);
           
       
        frame.add(viewButton);


        JButton editButton = new JButton("Edit Expense");
        editButton.setBounds(300, 340, 130, 25); // x, y, width, height
        editButton.addActionListener(e -> editExpense());
        frame.add(editButton);

        JButton deleteButton = new JButton("Delete Expense");
        deleteButton.setBounds(450, 340, 140, 25); // x, y, width, height
        deleteButton.addActionListener(e -> deleteExpense());
        frame.add(deleteButton);

        saveButton = new JButton("Save");
        saveButton.setBounds(620, 340, 80, 25); // x, y, width, height
        saveButton.addActionListener(e -> saveExpense());
        saveButton.setEnabled(false);
        frame.add(saveButton);
        
        viewMonthlyButton = new JButton("View Monthly Expenses");
        viewMonthlyButton.setBounds(10, 380, 180, 25); // x, y, width, height
        viewMonthlyButton.addActionListener(e -> calculateMonthlyExpenses());
        frame.add(viewMonthlyButton);

        frame.setSize(700, 400); // Set frame size
        frame.setResizable(true); // Disable resizing
        frame.setVisible(true);
        

        frame.setSize(700, 400); // Set frame size
        frame.setResizable(true); // Disable resizing
        frame.setVisible(true);
        
        viewDailyButton = new JButton("View Daily Expenses");
        viewDailyButton.setBounds(200, 380, 180, 25); // x, y, width, height
        viewDailyButton.addActionListener(e -> calculateDailyExpenses());
        frame.add(viewDailyButton);

        frame.setSize(700, 400); // Set frame size
        frame.setResizable(true); // Disable resizing
        frame.setVisible(true);
        

        frame.setSize(700, 400); // Set frame size
        frame.setResizable(true); // Disable resizing
        frame.setVisible(true);
    }

    private void viewExpenses() {
        displayArea.setText("");
        for (Expense expense : expenses) {
            displayArea.append("Amount: " + expense.getAmount() + ", Category: " + expense.getCategory() + ", Date: " + expense.getDate() + "\n");
        }
    }
    private void calculateMonthlyExpenses() {
        // Map to store total expenses for each month
        Map<String, Double> monthlyExpenses = new HashMap<>();

        // Iterate through expenses to calculate total expenses for each month
        for (Expense expense : expenses) {
            String monthYear = getMonthYear(expense.getDate());
            double amount = expense.getAmount();
            if (monthlyExpenses.containsKey(monthYear)) {
                amount += monthlyExpenses.get(monthYear);
            }
            monthlyExpenses.put(monthYear, amount);
        }

        // Display the total expenses for each month
        StringBuilder result = new StringBuilder("Monthly Expenses:\n");
        for (Map.Entry<String, Double> entry : monthlyExpenses.entrySet()) {
            result.append(entry.getKey()).append(": $").append(entry.getValue()).append("\n");
        }

        JOptionPane.showMessageDialog(frame, result.toString(), "Monthly Expenses", JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method to extract month and year from date string
    
    private String getMonthYear(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            int month = cal.get(Calendar.MONTH) + 1; // Month starts from 0
            int year = cal.get(Calendar.YEAR);
            return String.format("%02d/%d", month, year);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    private void calculateDailyExpenses() {
        // Map to store total expenses for each day
        Map<String, Double> dailyExpenses = new HashMap<>();

        // Iterate through expenses to calculate total expenses for each day
        for (Expense expense : expenses) {
            String day = expense.getDate(); // Assuming the date is in yyyy/MM/dd format
            double amount = expense.getAmount();
            dailyExpenses.put(day, dailyExpenses.getOrDefault(day, 0.0) + amount);
        }

        // Building the result string to display
        StringBuilder result = new StringBuilder("Daily Expenses:\n");
        for (Map.Entry<String, Double> entry : dailyExpenses.entrySet()) {
            result.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }

        // Displaying the daily expenses
        JOptionPane.showMessageDialog(frame, result.toString(), "Daily Expenses", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String getDateMonthYear(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            int date1 = cal.get(Calendar.DATE);
            int month = cal.get(Calendar.MONTH) + 1; // Month starts from 0
            int year = cal.get(Calendar.YEAR);
            return String.format("%02d/%02d/%d", date, month, year);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void searchExpenses() {
        String category = searchCategoryField.getText().trim();
        String dateString = searchDateField.getText().trim();
        
        // Check if both search fields are empty
        if (category.isEmpty() && dateString.isEmpty()) {
            // If both fields are empty, do not perform the search and return
        	 JOptionPane.showMessageDialog(frame, "No expenses match the search criteria.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        ArrayList<Expense> filteredExpenses = new ArrayList<>();
        int selectedIndex = -1; // Track the selected index
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            if ((category.isEmpty() || expense.getCategory().equalsIgnoreCase(category)) &&
                (dateString.isEmpty() || expense.getDate().equals(dateString))) {
                filteredExpenses.add(expense);
                selectedIndex = i; // Set the selected index to the last matching item
            }
        }

        if (filteredExpenses.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No expenses match the search criteria.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayFilteredExpenses(filteredExpenses);
            
            // Set the selected index in the JList
            if (selectedIndex != -1) {
                displayList.setSelectedIndex(selectedIndex);
                displayList.ensureIndexIsVisible(selectedIndex); // Ensure the selected item is visible
            } else {
                // If no matching items found, deselect any currently selected item
                displayList.clearSelection();
            }
        }
        
        // Clear the search fields only when there are matches found
        searchCategoryField.setText("");
        searchDateField.setText("");
    }

    private void displayFilteredExpenses(ArrayList<Expense> filteredExpenses) {
        String[] expenseStrings = new String[filteredExpenses.size()];
        for (int i = 0; i < filteredExpenses.size(); i++) {
            Expense expense = filteredExpenses.get(i);
            expenseStrings[i] = "Amount: " + expense.getAmount() + ", Category: " + expense.getCategory() + ", Date: " + expense.getDate();
        }
        displayList.setListData(expenseStrings);
    }


    private void saveExpense() {
        int selectedIndex = displayList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to save.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String amountText = amountField.getText();
        String category = categoryField.getText();
        String dateText = dateField.getText();

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateText);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date in yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Expense expense = expenses.get(selectedIndex);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(dateText);

        updateDisplayList();

        // Disable the Save button after saving
        saveButton.setEnabled(false);
        amountField.setText("");
        categoryField.setText("");
        dateField.setText("");
        
    }


    private void addExpense() {
        String amountText = amountField.getText();
        String category = categoryField.getText();
        String dateText = dateField.getText();

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateText);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date in yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        expenses.add(new Expense(amount, category, dateText));
        updateDisplayList();
        amountField.setText("");
        categoryField.setText("");
        dateField.setText("");
    }

    private void updateDisplayList() {
        String[] expenseStrings = new String[expenses.size()];
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            expenseStrings[i] = "Amount: " + expense.getAmount() + ", Category: " + expense.getCategory() + ", Date: " + expense.getDate();
        }
        displayList.setListData(expenseStrings);
    }

    
    	private void editExpense() {
    	    int selectedIndex = displayList.getSelectedIndex();
    	    if (selectedIndex == -1) {
    	        JOptionPane.showMessageDialog(frame, "Please select an expense to edit.", "Error", JOptionPane.WARNING_MESSAGE);
    	        return;
    	    }

    	    Expense expense = expenses.get(selectedIndex);
    	    amountField.setText(String.valueOf(expense.getAmount()));
    	    categoryField.setText(expense.getCategory());
    	    dateField.setText(expense.getDate());

    	    // Enable the Save button
    	    saveButton.setEnabled(true);
    	}


    private void deleteExpense() {
        int selectedIndex = displayList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an expense to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        expenses.remove(selectedIndex);
        updateDisplayList();
        amountField.setText("");
        categoryField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}

class Expense {
    private double amount;
    private String category;
    private String date;

    public Expense(double amount, String category, String date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
