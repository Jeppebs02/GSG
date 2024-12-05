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
import java.util.concurrent.ExecutionException;

public class SchedulingApp {
    private JFrame frame;
    private JTable calendarTable;
    private JComboBox<Integer> yearSelector;
    private JComboBox<String> monthSelector;
    private LocalDate currentDate;
    private HashMap<LocalDate, List<String>> taskMap;
    private LocalDate[][] calendarDates;
    private List<Task> tasks;

    // Fields to track hovered cell (for mouseover highlights)
    private int hoveredRow = -1;
    private int hoveredCol = -1;

    // Popup menu for right-click actions
    private JPopupMenu cellPopup;
    private JMenuItem createTaskItem;
    private JMenuItem showTasksItem;

    /**
     * Main entry point of the application.
     * Sets the Look & Feel and opens the main window.
     */
    public static void main(String[] args) {
        // Use Nimbus Look and Feel if available
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

        // Launch the application on the Swing Event Dispatch Thread (EDT)
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
     * Initializes current date and task map, then calls initialize() to set up the UI.
     */
    public SchedulingApp() throws Exception {
        currentDate = LocalDate.now();
        taskMap = new HashMap<>();
        initialize();
    }

    /**
     * Initializes the GUI components and sets up the main frame, panels, controls, and table.
     * Also sets a timer to refresh the view every 5 seconds.
     */
    private void initialize() throws Exception {
        frame = new JFrame("Skemalægningsprogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel with title and controls
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblTitle = new JLabel("Skemalægningsprogram", JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        // Year and month selectors
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

        // Calendar table setup
        calendarTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable.setRowHeight(90);
        calendarTable.setCellSelectionEnabled(true);

        // Mouse listeners for the calendar table
        // Allows interaction with calendar cells via clicks
        calendarTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = calendarTable.rowAtPoint(evt.getPoint());
                int col = calendarTable.columnAtPoint(evt.getPoint());

                // Left-click: open Add Task dialog if valid date cell
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    try {
                        handleCalendarClick(row, col);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                // Right-click: show context menu if valid date cell
                if (evt.isPopupTrigger() || SwingUtilities.isRightMouseButton(evt)) {
                    if (isValidDateCell(row, col)) {
                        showCellPopup(evt, row, col);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                // On some platforms, popup trigger might be on press
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
                // On other platforms, popup trigger might be on release
                if (evt.isPopupTrigger()) {
                    int row = calendarTable.rowAtPoint(evt.getPoint());
                    int col = calendarTable.columnAtPoint(evt.getPoint());
                    if (isValidDateCell(row, col)) {
                        showCellPopup(evt, row, col);
                    }
                }
            }
        });

        // Mouse motion listener to track which cell is hovered
        // Used for hover highlighting
        calendarTable.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = calendarTable.rowAtPoint(e.getPoint());
                int col = calendarTable.columnAtPoint(e.getPoint());

                if (row != hoveredRow || col != hoveredCol) {
                    hoveredRow = row;
                    hoveredCol = col;
                    calendarTable.repaint();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        setupCellPopup();

        // Initial load of the calendar view
        updateCalendarView();

        // Automatically refresh the calendar view every 5 seconds
        new javax.swing.Timer(5000, e -> {
            try {
                updateCalendarView();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Checks if a given row/column refers to a valid calendar cell (with a date).
     */
    private boolean isValidDateCell(int row, int col) {
        return row >= 0 && col >= 0 && calendarDates != null
               && row < calendarDates.length && col < calendarDates[0].length
               && calendarDates[row][col] != null;
    }

    /**
     * Sets up the right-click popup menu for calendar cells (Create Task, Show All Tasks).
     */
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

    /**
     * Shows the popup menu at the given event location, for the given cell coordinates.
     */
    private void showCellPopup(MouseEvent evt, int row, int col) {
        hoveredRow = row;
        hoveredCol = col;
        cellPopup.show(calendarTable, evt.getX(), evt.getY());
    }

    /**
     * Updates the entire calendar view (recalculates dates, fetches tasks) based on selected year/month.
     */
    private void updateCalendarView() throws Exception {
        int year = (int) yearSelector.getSelectedItem();
        String month = (String) monthSelector.getSelectedItem();
        LocalDate selectedDate = LocalDate.of(year, getMonthIndex(month), 1);
        renderMonthView(selectedDate);
    }

    /**
     * Renders the month view in the table:
     * - Calculates which LocalDate should appear in each cell
     * - Updates the table model to show day numbers
     * - Initiates background fetching of tasks for the displayed month
     */
    private void renderMonthView(LocalDate date) throws Exception {
        String[] columnNames = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};
        calendarDates = new LocalDate[6][7];
        DefaultTableModel model = new DefaultTableModel(6, 7);

        // Find the first day to display: Monday of the first week containing the 1st of the month
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate firstDayToDisplay = startOfMonth.minusDays((startOfMonth.getDayOfWeek().getValue() + 6) % 7);

        // Fill the table model with the day numbers and store the LocalDate in calendarDates
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

        // Center the column headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) calendarTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        calendarTable.getTableHeader().setDefaultRenderer(headerRenderer);

        // Fetch tasks for this month in the background
        fetchTasksInBackground(date);
    }

    /**
     * Initiates a background thread (SwingWorker) to fetch tasks for the given month.
     * Once done, it calls updateCellRendering() to apply task highlights.
     */
    private void fetchTasksInBackground(LocalDate date) {
        SwingWorker<List<Task>, Void> worker = new SwingWorker<List<Task>, Void>() {
            @Override
            protected List<Task> doInBackground() throws Exception {
                TaskCtrl tc = new TaskCtrl();
                return tc.findAllTasks(date.getYear(), date.getMonth().toString());
            }

            @Override
            protected void done() {
                try {
                    tasks = get();
                    updateCellRendering(date);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    /**
     * Updates the cell rendering after tasks are loaded:
     * - Highlights cells that have tasks
     * - Grays out cells not in the selected month
     * - Applies hover and selection colors
     */
    private void updateCellRendering(LocalDate date) {
        calendarTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(CENTER);

                LocalDate cellDate = calendarDates[row][column];

                // Default background color depends on selection
                if (isSelected) {
                    cell.setBackground(new Color(173, 216, 230));
                } else {
                    cell.setBackground(Color.WHITE);
                }

                // If there's a valid date in the cell
                if (cellDate != null && tasks != null) {
                    // Highlight days that have tasks
                    if (tasks.stream().anyMatch(task -> task.getDate().isEqual(cellDate))) {
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
        calendarTable.repaint();
    }

    /**
     * Called when a valid calendar cell is clicked.
     * Opens the Add Task dialog for the selected date.
     */
    private void handleCalendarClick(int row, int col) throws SQLException {
        if (isValidDateCell(row, col)) {
            LocalDate selectedDate = calendarDates[row][col];
            openAddTaskDialog(selectedDate);
        }
    }

    /**
     * Opens the Add Task dialog for a specific date, allowing the user to add new tasks.
     */
    private void openAddTaskDialog(LocalDate date) throws SQLException {
        AddTaskDialog dialog = new AddTaskDialog(date, taskMap);
        dialog.setVisible(true);
    }

    /**
     * Shows all tasks for a specific date in a message dialog.
     */
    private void showAllTasksForDate(LocalDate date) {
        List<Task> tasksForDate = tasks.stream()
            .filter(task -> task.getDate().isEqual(date))
            .toList();

        if (tasksForDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tasks on this date.");
        } else {
            StringBuilder sb = new StringBuilder("Tasks on " + date + ":\n");
            for (Task t : tasksForDate) {
                sb.append("- ").append(t.getDescription()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString(), "All Tasks", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Returns an array of Integers representing the year range for selection.
     * In this case, 10 years before current year and 9 years after.
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
     * Returns an array of month names (in uppercase English) for selection.
     */
    private String[] getMonths() {
        return new String[]{
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
    }

    /**
     * Converts a month name (like "JANUARY") to its integer value (1-12).
     */
    private int getMonthIndex(String monthName) {
        return java.time.Month.valueOf(monthName).getValue();
    }
}
