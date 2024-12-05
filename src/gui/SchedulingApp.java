package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ctrl.TaskCtrl;
import model.Task;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class SchedulingApp {
    private JFrame frame;
    private JTable calendarTable;
    private JComboBox<Integer> yearSelector;
    private JComboBox<String> monthSelector;
    private LocalDate currentDate;
    private HashMap<LocalDate, List<String>> taskMap;
    private LocalDate[][] calendarDates;
    private List<Task> tasks;

    // Fields to track hovered cell
    private int hoveredRow = -1;
    private int hoveredCol = -1;

    // Popup menu for right-click
    private JPopupMenu cellPopup;
    private JMenuItem createTaskItem;
    private JMenuItem showTasksItem;

    public static void main(String[] args) {
        // Use Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback to system look and feel if Nimbus not available
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
        }

        SwingUtilities.invokeLater(() -> {
            try {
                SchedulingApp window = new SchedulingApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SchedulingApp() throws Exception {
        currentDate = LocalDate.now();
        taskMap = new HashMap<>();
        initialize();
    }

    private void initialize() throws Exception {
        frame = new JFrame("Skemalægningsprogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblTitle = new JLabel("Skemalægningsprogram", JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        yearSelector = new JComboBox<>(getYearRange());
        yearSelector.setSelectedItem(currentDate.getYear());
        yearSelector.addActionListener(e -> {
            try {
                updateCalendarView();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        monthSelector = new JComboBox<>(getMonths());
        monthSelector.setSelectedItem(currentDate.getMonth().toString());
        monthSelector.addActionListener(e -> {
            try {
                updateCalendarView();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        controlsPanel.add(new JLabel("År:"));
        controlsPanel.add(yearSelector);
        controlsPanel.add(new JLabel("Måned:"));
        controlsPanel.add(monthSelector);

        topPanel.add(controlsPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        calendarTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable.setRowHeight(90);
        calendarTable.setCellSelectionEnabled(true);

        // Mouse listener for clicks
        calendarTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = calendarTable.rowAtPoint(evt.getPoint());
                int col = calendarTable.columnAtPoint(evt.getPoint());

                // Left-click on a valid date cell opens the Add Task dialog directly
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    try {
                        handleCalendarClick(row, col);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                // Show popup on right-click if on a valid date cell
                if (evt.isPopupTrigger() || SwingUtilities.isRightMouseButton(evt)) {
                    if (isValidDateCell(row, col)) {
                        showCellPopup(evt, row, col);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                // On some platforms, popup trigger is on mouse press
                if (evt.isPopupTrigger()) {
                    int row = calendarTable.rowAtPoint(evt.getPoint());
                    int col = calendarTable.columnAtPoint(evt.getPoint());
                    if (isValidDateCell(row, col)) {
                        showCellPopup(evt, row, col);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                // On some platforms, popup trigger is on mouse release
                if (evt.isPopupTrigger()) {
                    int row = calendarTable.rowAtPoint(evt.getPoint());
                    int col = calendarTable.columnAtPoint(evt.getPoint());
                    if (isValidDateCell(row, col)) {
                        showCellPopup(evt, row, col);
                    }
                }
            }
        });

        // Mouse motion listener to track hover
        calendarTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());

                if (row != hoveredRow || col != hoveredCol) {
                    hoveredRow = row;
                    hoveredCol = col;
                    calendarTable.repaint(); // Re-render to show hover effect
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        setupCellPopup();

        updateCalendarView();
    }

    private boolean isValidDateCell(int row, int col) {
        return row >= 0 && col >= 0 && calendarDates != null
               && row < calendarDates.length && col < calendarDates[0].length
               && calendarDates[row][col] != null;
    }

    private void setupCellPopup() {
        cellPopup = new JPopupMenu();
        createTaskItem = new JMenuItem("Create Task");
        showTasksItem = new JMenuItem("Show All Tasks");

        createTaskItem.addActionListener(e -> {
            if (isValidDateCell(hoveredRow, hoveredCol)) {
                LocalDate date = calendarDates[hoveredRow][hoveredCol];
                try {
                    openAddTaskDialog(date);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        showTasksItem.addActionListener(e -> {
            if (isValidDateCell(hoveredRow, hoveredCol)) {
                LocalDate date = calendarDates[hoveredRow][hoveredCol];
                showAllTasksForDate(date);
            }
        });

        cellPopup.add(createTaskItem);
        cellPopup.add(showTasksItem);
    }

    private void showCellPopup(MouseEvent evt, int row, int col) {
        hoveredRow = row;
        hoveredCol = col;
        cellPopup.show(calendarTable, evt.getX(), evt.getY());
    }

    private void updateCalendarView() throws Exception {
        int year = (int) yearSelector.getSelectedItem();
        String month = (String) monthSelector.getSelectedItem();
        LocalDate selectedDate = LocalDate.of(year, getMonthIndex(month), 1);
        renderMonthView(selectedDate);
    }

    private void renderMonthView(LocalDate date) throws Exception {
        String[] columnNames = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
        calendarDates = new LocalDate[6][7];
        DefaultTableModel model = new DefaultTableModel(6, 7);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate firstDayToDisplay = startOfMonth.minusDays((startOfMonth.getDayOfWeek().getValue() + 6) % 7);

        TaskCtrl tc = new TaskCtrl();
        tasks = tc.findAllTasks(date.getYear(), date.getMonth().toString());

        int dayCounter = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                LocalDate currentDay = firstDayToDisplay.plusDays(dayCounter++);
                calendarDates[r][c] = currentDay;
                model.setValueAt(currentDay.getDayOfMonth(), r, c);
            }
        }

        calendarTable.setModel(model);
        for (int i = 0; i < 7; i++) {
            calendarTable.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }

        // Center the column headers (days of week)
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) calendarTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        calendarTable.getTableHeader().setDefaultRenderer(headerRenderer);

        // Default renderer for cells, align center and highlight conditions
        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(CENTER);

                LocalDate cellDate = calendarDates[row][column];

                // Reset to default colors
                if (isSelected) {
                    cell.setBackground(new Color(173, 216, 230));
                } else {
                    cell.setBackground(Color.WHITE);
                }

                if (cellDate != null) {
                    if (tasks.stream().anyMatch(task -> task.getDate().isEqual(cellDate))) {
                        // Days with tasks highlighted
                        cell.setBackground(new Color(200, 230, 201));
                    } else if (!cellDate.getMonth().equals(date.getMonth())) {
                        // Gray out days not in the current month
                        cell.setBackground(new Color(240, 240, 240));
                    }

                    // Highlight hovered cell if not selected
                    if (row == hoveredRow && column == hoveredCol && !isSelected) {
                        cell.setBackground(new Color(220, 220, 250));
                    }
                } else {
                    setText("");
                }

                return cell;
            }
        });
    }

    private void handleCalendarClick(int row, int col) throws SQLException {
        if (isValidDateCell(row, col)) {
            LocalDate selectedDate = calendarDates[row][col];
            // Remove the month-check condition:
            openAddTaskDialog(selectedDate);
        }
    }

    private void openAddTaskDialog(LocalDate date) throws SQLException {
        AddTaskDialog dialog = new AddTaskDialog(date, taskMap);
        dialog.setVisible(true);
    }

    private void showAllTasksForDate(LocalDate date) {
        // Show all tasks for this date
        List<Task> tasksForDate = tasks.stream()
            .filter(task -> task.getDate().isEqual(date))
            .toList();

        if (tasksForDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tasks on this date.");
        } else {
            StringBuilder sb = new StringBuilder("Tasks on " + date + ":\n");
            for (Task t : tasksForDate) {
                sb.append("- ").append(t.getDescription() + "\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString(), "All Tasks", JOptionPane.INFORMATION_MESSAGE);
        }
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
        return new String[]{
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
    }

    private int getMonthIndex(String monthName) {
        return java.time.Month.valueOf(monthName).getValue();
    }
}
