
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class SchedulingApp {
    private JFrame frame;
    private JTable calendarTable;
    private JComboBox<Integer> yearSelector;
    private JComboBox<String> monthSelector;
    private LocalDate currentDate;
    private HashMap<LocalDate, List<String>> taskMap;
    private LocalDate[][] calendarDates;

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

    public SchedulingApp() {
        currentDate = LocalDate.now();
        taskMap = new HashMap<>();
        initialize();
    }

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
                handleCalendarClick(row, col);
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        updateCalendarView();
    }

    private void updateCalendarView() {
        int year = (int) yearSelector.getSelectedItem();
        String month = (String) monthSelector.getSelectedItem();
        LocalDate selectedDate = LocalDate.of(year, getMonthIndex(month), 1);

        renderMonthView(selectedDate);
    }

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

    private void handleCalendarClick(int row, int col) {
        if (calendarDates != null && calendarDates[row][col] != null) {
            LocalDate selectedDate = calendarDates[row][col];
            if (selectedDate.getMonth() == currentDate.getMonth()) {
                openAddTaskDialog(selectedDate);
            }
        }
    }

    private void openAddTaskDialog(LocalDate date) {
        AddTaskDialog dialog = new AddTaskDialog(date, taskMap);
        dialog.setVisible(true);
    }

    private Integer[] getYearRange() {
        int currentYear = LocalDate.now().getYear();
        Integer[] years = new Integer[20];
        for (int i = 0; i < 20; i++) {
            years[i] = currentYear - 10 + i;
        }
        return years;
    }

    private String[] getMonths() {
        return new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    }

    private int getMonthIndex(String monthName) {
        return java.time.Month.valueOf(monthName).getValue();
    }
}
