import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class AdminDashboard extends JFrame {

    // COLORS
    private final Color bg = new Color(250, 244, 241);
    private final Color sidebarColor = new Color(231, 236, 215);
    private final Color pink = new Color(244, 190, 202);
    private final Color green = new Color(190, 220, 181);
    private final Color yellow = new Color(247, 211, 145);
    private final Color red = new Color(231, 164, 171);
    private final Color dark = new Color(48, 57, 43);
    private final Color white = new Color(255, 251, 248);

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton[] navButtons;

    // FIXED DIMENSIONS
    private final int CONTENT_WIDTH = 1065;

    public AdminDashboard() {

        setTitle("Com & Go - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1350, 820);
        setMinimumSize(new Dimension(1350, 820));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.setBackground(bg);

        mainPanel.add(createDashboardPage(), "Dashboard");
        mainPanel.add(createComputersPage(), "Computers");
        mainPanel.add(createRecordsPage(), "Student Records");
        mainPanel.add(createNotificationsPage(), "Notifications");

        add(createSidebar(), BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    // SIDEBAR
    private JPanel createSidebar() {

        JPanel side = new JPanel();

        side.setPreferredSize(new Dimension(225, 0));
        side.setBackground(sidebarColor);
        side.setBorder(new EmptyBorder(25, 18, 22, 18));

        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("Com & Go");
        brand.setFont(new Font("SansSerif", Font.BOLD, 30));
        brand.setForeground(dark);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Click. Check. Go!");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 15));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        side.add(Box.createVerticalStrut(35));
        side.add(brand);
        side.add(tagline);
        side.add(Box.createVerticalStrut(65));

        String[] names = {
            "Dashboard",
            "Computers",
            "Student Records",
            "Notifications"
        };

        navButtons = new JButton[names.length];

        for (int i = 0; i < names.length; i++) {

            navButtons[i] = navButton(names[i], i == 0);

            final String page = names[i];
            final int index = i;

            navButtons[i].addActionListener(e -> {
                cardLayout.show(mainPanel, page);
                updateSelectedButton(index);
            });

            side.add(navButtons[i]);
            side.add(Box.createVerticalStrut(12));
        }

        side.add(Box.createVerticalGlue());

        JButton logout = navButton("Logout", false);

        logout.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        side.add(logout);

        return side;
    }

    // NAVIGATION BUTTON
    private JButton navButton(String text, boolean selected) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setForeground(dark);
        button.setBackground(selected ? pink : sidebarColor);

        button.setBorder(new CompoundBorder(
            new LineBorder(
                selected ? new Color(225, 155, 174) : sidebarColor,
                1, true
            ),
            new EmptyBorder(12, 14, 12, 14)
        ));

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        button.setPreferredSize(new Dimension(190, 52));
        button.setMinimumSize(new Dimension(180, 52));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // UPDATE SELECTED BUTTON
    private void updateSelectedButton(int selectedIndex) {

        for (int i = 0; i < navButtons.length; i++) {

            boolean selected = i == selectedIndex;

            navButtons[i].setBackground(
                selected ? pink : sidebarColor
            );

            navButtons[i].setBorder(new CompoundBorder(
                new LineBorder(
                    selected ? new Color(225, 155, 174) : sidebarColor,
                    1, true
                ),
                new EmptyBorder(12, 14, 12, 14)
            ));
        }
    }

    // PAGE HEADER
    private JPanel createPageHeader(String title, String subtitle) {

        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 34));
        heading.setForeground(dark);
        heading.setBounds(0, 0, 800, 42);

        JLabel description = new JLabel(subtitle);
        description.setFont(new Font("SansSerif", Font.PLAIN, 17));
        description.setBounds(0, 43, 850, 25);

        panel.add(heading);
        panel.add(description);

        return panel;
    }

    // DASHBOARD PAGE - FIXED LAYOUT
    private JPanel createDashboardPage() {

        JPanel page = new JPanel(null);
        page.setBackground(bg);

        // TITLE
        JPanel header = createPageHeader(
            "Admin Dashboard",
            "Monitor and manage the computer laboratory."
        );

        header.setBounds(30, 25, 800, 70);
        page.add(header);

        // SUMMARY CARDS
        JPanel cards = createSummaryCards();

        cards.setBounds(30, 112, CONTENT_WIDTH, 105);
        page.add(cards);

        // COMPUTER STATUS
        JPanel status = createStatusPanel();

        status.setBounds(30, 235, 525, 245);
        page.add(status);

        // RECENT NOTIFICATIONS
        JPanel notifications = createNotificationsPanel();

        notifications.setBounds(570, 235, 525, 245);
        page.add(notifications);

        // CURRENT STUDENT USAGE
        JPanel usage = createUsagePanel();

        usage.setBounds(30, 495, CONTENT_WIDTH, 220);
        page.add(usage);

        return page;
    }

    // SUMMARY CARDS
    private JPanel createSummaryCards() {

        JPanel cards = new JPanel(new GridLayout(1, 4, 14, 0));
        cards.setOpaque(false);

        cards.add(summaryCard(
            "Total Computers", "20",
            new Color(239, 243, 229)
        ));

        cards.add(summaryCard(
            "Available", "12",
            new Color(239, 243, 229)
        ));

        cards.add(summaryCard(
            "Occupied", "6",
            new Color(253, 240, 220)
        ));

        cards.add(summaryCard(
            "Defective", "2",
            new Color(250, 229, 231)
        ));

        return cards;
    }

    private JPanel summaryCard(
        String label,
        String number,
        Color color
    ) {

        JPanel card = new JPanel();

        card.setBackground(color);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(225, 220, 211), 1, true),
            new EmptyBorder(15, 8, 15, 8)
        ));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(label);
        name.setFont(new Font("SansSerif", Font.PLAIN, 14));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel value = new JLabel(number);
        value.setFont(new Font("SansSerif", Font.BOLD, 29));
        value.setForeground(dark);
        value.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(value);

        return card;
    }

    // PANEL TITLE
    private JPanel panelWithTitle(String title) {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(white);

        panel.setBorder(new CompoundBorder(
            new LineBorder(new Color(225, 220, 211), 1, true),
            new EmptyBorder(13, 15, 13, 15)
        ));

        JLabel heading = new JLabel(title);

        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        heading.setForeground(dark);

        heading.setBorder(new EmptyBorder(0, 0, 12, 0));

        panel.add(heading, BorderLayout.NORTH);

        return panel;
    }

    // COMPUTER STATUS OVERVIEW
    private JPanel createStatusPanel() {

        JPanel panel = panelWithTitle("Computer Status Overview");

        JPanel body = new JPanel(new GridLayout(1, 2, 8, 0));
        body.setOpaque(false);

        body.add(new DonutChart());

        JPanel legend = new JPanel();
        legend.setOpaque(false);
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));

        legend.add(Box.createVerticalGlue());

        legend.add(legendItem("Available", "12", green));
        legend.add(Box.createVerticalStrut(13));

        legend.add(legendItem("Occupied", "6", yellow));
        legend.add(Box.createVerticalStrut(13));

        legend.add(legendItem("Defective", "2", red));

        legend.add(Box.createVerticalGlue());

        body.add(legend);

        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JPanel legendItem(
        String label,
        String value,
        Color color
    ) {

        JPanel row = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 2)
        );

        row.setOpaque(false);

        JLabel indicator = new JLabel("●");
        indicator.setForeground(color);

        JLabel status = new JLabel(label);
        status.setForeground(dark);
        status.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel number = new JLabel(value);
        number.setFont(new Font("SansSerif", Font.BOLD, 14));

        row.add(indicator);
        row.add(status);
        row.add(number);

        return row;
    }

    // RECENT NOTIFICATIONS
    private JPanel createNotificationsPanel() {

        JPanel panel = panelWithTitle("Recent Notifications");

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        list.add(notification(
            "Available",
            "PC-04 is now available.",
            "2:30 PM",
            green
        ));

        list.add(new JSeparator());

        list.add(notification(
            "Occupied",
            "PC-07 occupied by Princess Sahali.",
            "2:00 PM",
            yellow
        ));

        list.add(new JSeparator());

        list.add(notification(
            "Defective",
            "PC-09 marked as defective.",
            "1:45 PM",
            red
        ));

        panel.add(list, BorderLayout.CENTER);

        return panel;
    }

    private JPanel notification(
        String status,
        String message,
        String time,
        Color color
    ) {

        JPanel row = new JPanel(new BorderLayout(8, 4));

        row.setOpaque(false);
        row.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel statusLabel = new JLabel("● " + status);
        statusLabel.setForeground(dark);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel text = new JLabel(
            "<html><body style='width:180px'>" +
            message +
            "</body></html>"
        );

        text.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(135, 100, 105));

        row.add(statusLabel, BorderLayout.WEST);
        row.add(text, BorderLayout.CENTER);
        row.add(timeLabel, BorderLayout.EAST);

        return row;
    }

    // CURRENT STUDENT USAGE
    private JPanel createUsagePanel() {

        JPanel panel = panelWithTitle("Current Student Usage");

        String[] columns = {
            "Student Name",
            "SR Code",
            "Computer",
            "Time In",
            "Expected Time Out",
            "Status"
        };

        Object[][] data = {
            {"Daniella Ramirez", "25-02090", "PC-01",
                "2:15 PM", "3:15 PM", "In Use"},

            {"Kyle Valino", "25-02185", "PC-04",
                "1:50 PM", "3:30 PM", "In Use"},

            {"Princess Sahali", "25-03175", "PC-07",
                "2:00 PM", "4:00 PM", "In Use"}
        };

        JTable table = createTable(data, columns);

        int[] widths = {190, 140, 120, 130, 190, 110};
        setColumnWidths(table, widths);

        JScrollPane scroll = new JScrollPane(table);

        scroll.setBorder(
            new LineBorder(new Color(230, 222, 216), 1)
        );

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // REUSABLE TABLE
    private JTable createTable(
        Object[][] data,
        String[] columns
    ) {

        DefaultTableModel model = new DefaultTableModel(
            data,
            columns
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        table.setRowHeight(34);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getTableHeader().setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );

        table.getTableHeader().setBackground(
            new Color(248, 226, 231)
        );

        table.getTableHeader().setForeground(dark);

        table.setGridColor(new Color(230, 222, 216));
        table.setFillsViewportHeight(true);

        return table;
    }

    // FIXED TABLE COLUMN WIDTHS
    private void setColumnWidths(JTable table, int[] widths) {

        TableColumnModel model = table.getColumnModel();

        for (int i = 0; i < widths.length; i++) {

            TableColumn column = model.getColumn(i);

            column.setPreferredWidth(widths[i]);
            column.setMinWidth(widths[i]);
            column.setMaxWidth(widths[i]);
        }
    }

    // COMPUTERS PAGE - FIXED TABLE
    private JPanel createComputersPage() {

        JPanel page = new JPanel(null);
        page.setBackground(bg);

        JPanel header = createPageHeader(
            "Computers",
            "View and monitor all laboratory computers."
        );

        header.setBounds(30, 25, 800, 70);
        page.add(header);

        JPanel container = panelWithTitle("Computer List");

        container.setBounds(30, 120, CONTENT_WIDTH, 550);

        String[] columns = {
            "Computer ID",
            "Status",
            "Assigned Student",
            "Expected Availability"
        };

        Object[][] data = {
            {"PC-01", "Occupied", "Daniella Ramirez", "3:15 PM"},
            {"PC-02", "Available", "-", "-"},
            {"PC-03", "Available", "-", "-"},
            {"PC-04", "Occupied", "Kyle Valino", "3:30 PM"},
            {"PC-05", "Available", "-", "-"},
            {"PC-06", "Available", "-", "-"},
            {"PC-07", "Occupied", "Princess Sahali", "4:00 PM"},
            {"PC-08", "Available", "-", "-"},
            {"PC-09", "Defective", "-", "-"},
            {"PC-10", "Available", "-", "-"}
        };

        JTable table = createTable(data, columns);

        setColumnWidths(table, new int[]{
            160, 160, 300, 250
        });

        container.add(
            new JScrollPane(table),
            BorderLayout.CENTER
        );

        page.add(container);

        return page;
    }

    // STUDENT RECORDS PAGE - FIXED TABLE
    private JPanel createRecordsPage() {

        JPanel page = new JPanel(null);
        page.setBackground(bg);

        JPanel header = createPageHeader(
            "Student Records",
            "View student computer usage information."
        );

        header.setBounds(30, 25, 800, 70);
        page.add(header);

        JPanel container = panelWithTitle("Usage Records");

        container.setBounds(30, 120, CONTENT_WIDTH, 550);

        String[] columns = {
            "Student Name",
            "SR Code",
            "Computer",
            "Time In",
            "Time Out",
            "Status"
        };

        Object[][] data = {
            {"Daniella Ramirez", "25-02090", "PC-01",
                "2:15 PM", "3:15 PM", "In Use"},

            {"Kyle Valino", "25-02185", "PC-04",
                "1:50 PM", "3:30 PM", "In Use"},

            {"Princess Sahali", "25-03175", "PC-07",
                "2:00 PM", "4:00 PM", "In Use"}
        };

        JTable table = createTable(data, columns);

        setColumnWidths(table, new int[]{
            190, 140, 120, 130, 160, 110
        });

        container.add(
            new JScrollPane(table),
            BorderLayout.CENTER
        );

        page.add(container);

        return page;
    }

    // NOTIFICATIONS PAGE - FIXED POSITION
    private JPanel createNotificationsPage() {

        JPanel page = new JPanel(null);
        page.setBackground(bg);

        JPanel header = createPageHeader(
            "Notifications",
            "View recent computer laboratory updates."
        );

        header.setBounds(30, 25, 800, 70);
        page.add(header);

        JPanel container = panelWithTitle("All Notifications");

        container.setBounds(30, 120, CONTENT_WIDTH, 500);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        list.add(notification(
            "Available",
            "PC-04 is now available.",
            "2:30 PM",
            green
        ));

        list.add(new JSeparator());

        list.add(notification(
            "Occupied",
            "PC-07 occupied by Princess Sahali.",
            "2:00 PM",
            yellow
        ));

        list.add(new JSeparator());

        list.add(notification(
            "Defective",
            "PC-09 marked as defective.",
            "1:45 PM",
            red
        ));

        container.add(list, BorderLayout.NORTH);

        page.add(container);

        return page;
    }

    // DONUT CHART
    private class DonutChart extends JPanel {

        DonutChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(210, 175));
        }

        @Override
        protected void paintComponent(Graphics graphics) {

            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics.create();

            g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            int size = Math.min(
                getHeight() - 18,
                getWidth() - 18
            );

            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            int start = 90;

            int[] values = {12, 6, 2};

            Color[] colors = {
                green,
                yellow,
                red
            };

            for (int i = 0; i < values.length; i++) {

                int angle = (int) Math.round(
                    values[i] * 360.0 / 20
                );

                g.setColor(colors[i]);

                g.fill(new Arc2D.Double(
                    x, y, size, size,
                    start, -angle,
                    Arc2D.PIE
                ));

                start -= angle;
            }

            int hole = (int) (size * 0.57);

            g.setColor(white);

            g.fillOval(
                x + (size - hole) / 2,
                y + (size - hole) / 2,
                hole,
                hole
            );

            g.setColor(dark);
            g.setFont(new Font("SansSerif", Font.BOLD, 21));

            FontMetrics fm = g.getFontMetrics();

            String total = "20";

            g.drawString(
                total,
                getWidth() / 2 - fm.stringWidth(total) / 2,
                getHeight() / 2 + 2
            );

            g.setFont(new Font("SansSerif", Font.PLAIN, 12));

            fm = g.getFontMetrics();

            String label = "Total";

            g.drawString(
                label,
                getWidth() / 2 - fm.stringWidth(label) / 2,
                getHeight() / 2 + 18
            );

            g.dispose();
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            new AdminDashboard().setVisible(true);
        });
    }
}