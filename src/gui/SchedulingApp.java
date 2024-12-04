
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * SchedulingApp is a Java Swing application for managing shift schedules.
 * It allows users to view a calendar, select dates, and add tasks for specific dates.
 * The calendar is displayed in a tabular format with each month visible in a 6x7 grid,
 * and users can select the year and month to view different schedules.
 */
public class SchedulingApp {
    private JFrame frame;
    private JTable calendarTable;
    private JComboBox<Integer> yearSelector;
    private JComboBox<String> monthSelector;
    private LocalDate currentDate;
    private HashMap<LocalDate, List<String>> taskMap;
    private LocalDate[][] calendarDates;

    /**
     * Main method to launch the SchedulingApp.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SchedulingApp window = new SchedulingApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor for SchedulingApp.
     * Initializes the current date and task map, then calls initialize() to set up the GUI.
     */
    public SchedulingApp() {
        currentDate = LocalDate.now();
        taskMap = new HashMap<>();
        initialize();
    }

    /**
     * Initializes the GUI components of the application.
     * Sets up the main frame, top panel with year and month selectors, and the calendar table.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel lblTitle = new JLabel("Skemalægningsprogram", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        yearSelector = new JComboBox<>(getYearRange());
        yearSelector.setSelectedItem(currentDate.getYear());
        yearSelector.addActionListener(e -> updateCalendarView());
        controls.add(yearSelector);

        monthSelector = new JComboBox<>(getMonths());
        monthSelector.setSelectedItem(currentDate.getMonth().toString());
        monthSelector.addActionListener(e -> updateCalendarView());
        controls.add(monthSelector);

        topPanel.add(controls, BorderLayout.SOUTH);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        calendarTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable.setCellSelectionEnabled(true);
        calendarTable.setRowHeight(100);
        calendarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = calendarTable.rowAtPoint(evt.getPoint());
                int col = calendarTable.columnAtPoint(evt.getPoint());
                try {
					handleCalendarClick(row, col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        updateCalendarView();
    }

    /**
     * Updates the calendar view based on the selected year and month.
     * Sets the calendar dates for the specified month and year.
     */
    private void updateCalendarView() {
        int year = (int) yearSelector.getSelectedItem();
        String month = (String) monthSelector.getSelectedItem();
        LocalDate selectedDate = LocalDate.of(year, getMonthIndex(month), 1);

        renderMonthView(selectedDate);
    }

    /**
     * Renders the calendar view for the given month and year.
     * Populates the JTable with dates for the specified month.
     * @param date The LocalDate representing the year and month to display.
     */
    private void renderMonthView(LocalDate date) {
        String[] columnNames = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
        calendarDates = new LocalDate[6][7];

        DefaultTableModel model = new DefaultTableModel(6, 7);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate firstDayToDisplay = startOfMonth.minusDays((startOfMonth.getDayOfWeek().getValue() + 6) % 7);

      
        int row = 0, col = 0;
        for (int i = 0; i < 42; i++) { // 6 rows x 7 columns
            LocalDate currentDay = firstDayToDisplay.plusDays(i);
            calendarDates[row][col] = currentDay;
            model.setValueAt(currentDay.getDayOfMonth(), row, col);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

        calendarTable.setModel(model);
        for (int i = 0; i < 7; i++) {
            calendarTable.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }

        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                LocalDate cellDate = calendarDates[row][column];
                if (cellDate != null && (cellDate.getMonth() != date.getMonth())) {
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                return cell;
            }
        });
    }

    /**
     * Handles click events on the calendar cells.
     * Opens a dialog to add a task for the selected date.
     * @param row The row index of the clicked cell.
     * @param col The column index of the clicked cell.
     * @throws SQLException 
     */
    private void handleCalendarClick(int row, int col) throws SQLException {
        if (calendarDates != null && calendarDates[row][col] != null) {
            LocalDate selectedDate = calendarDates[row][col];
            if (selectedDate.getMonth() == currentDate.getMonth()) {
                openAddTaskDialog(selectedDate);
            }
        }
    }

    /**
     * Opens a dialog to add a task for the specified date.
     * @param date The date for which the task should be added.
     * @throws SQLException 
     */
    private void openAddTaskDialog(LocalDate date) throws SQLException {
        AddTaskDialog dialog = new AddTaskDialog(date, taskMap);
        dialog.setVisible(true);
    }

    /**
     * Returns an array of years to populate the year selector dropdown.
     * @return An array of Integer values representing a range of years.
     */
    private Integer[] getYearRange() {
        int currentYear = LocalDate.now().getYear();
        Integer[] years = new Integer[20];
        for (int i = 0; i < 20; i++) {
            years[i] = currentYear - 10 + i;
        }
        return years;
    }

    /**
     * Returns an array of month names to populate the month selector dropdown.
     * @return An array of Strings representing the months of the year.
     */
    private String[] getMonths() {
        return new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    }

    /**
     * Converts the name of a month to its corresponding index.
     * @param monthName The name of the month.
     * @return The integer value of the month (1 for January, 12 for December).
     */
    private int getMonthIndex(String monthName) {
        return java.time.Month.valueOf(monthName).getValue();
    }
}
