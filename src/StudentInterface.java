import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class StudentInterface extends JFrame {

    // COLORS
    private final Color PINK = new Color(250, 225, 230);
    private final Color CREAM = new Color(255, 250, 245);
    private final Color DARK = new Color(65, 75, 65);
    private final Color GREEN = new Color(80, 150, 100);
    private final Color RED = new Color(220, 80, 80);
    private final Color ORANGE = new Color(230, 150, 50);
    private final Color SIDEBAR = new Color(235, 240, 225);

    // COMPUTER COMPONENTS
    private final JLabel[] statusLabels = new JLabel[10];
    private final JLabel[] availabilityLabels = new JLabel[10];
    private final JButton[] selectButtons = new JButton[10];
    private final JPanel[] computerCards = new JPanel[10];

    // SUMMARY LABELS
    private JLabel availableCount;
    private JLabel occupiedCount;
    private JLabel defectiveCount;

    // PAGE NAVIGATION
    private final CardLayout pageLayout = new CardLayout();
    private JPanel pagePanel;
    private JButton studentButton;
    private JButton aboutButton;

    public StudentInterface() {

        setTitle("Com & Go - Student Interface");
        setSize(1150, 750);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CREAM);

        // LEFT SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(165, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(
            BorderFactory.createEmptyBorder(30, 12, 20, 12)
        );

        // LOGO
        JLabel logoIcon = new JLabel(loadIcon("logo.png", 115, 115));
        logoIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoName = new JLabel("Com & Go");
        logoName.setFont(new Font("Arial", Font.BOLD, 22));
        logoName.setForeground(DARK);
        logoName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Click. Check. Go!");
        tagline.setFont(new Font("Arial", Font.PLAIN, 11));
        tagline.setForeground(DARK);
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logoIcon);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(logoName);
        sidebar.add(tagline);
        sidebar.add(Box.createVerticalStrut(55));

        // STUDENT AND ABOUT BUTTONS
        studentButton = createNavigationButton(
            "Student", "student.png"
        );

        aboutButton = createNavigationButton(
            "About", "info.png"
        );

        studentButton.addActionListener(e -> showPage("STUDENT"));
        aboutButton.addActionListener(e -> showPage("ABOUT"));

        sidebar.add(studentButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(aboutButton);
        sidebar.add(Box.createVerticalGlue());

        // PAGE CONTAINER
        pagePanel = new JPanel(pageLayout);
        pagePanel.setBackground(CREAM);

        pagePanel.add(createStudentPage(), "STUDENT");
        pagePanel.add(createAboutPage(), "ABOUT");

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(pagePanel, BorderLayout.CENTER);

        add(mainPanel);

        showPage("STUDENT");
        updateSummary();

        setVisible(true);
    }

    // NAVIGATION BUTTON DESIGN
    private JButton createNavigationButton(
        String text,
        String iconFile
    ) {

        JButton button = new JButton(text);

        button.setIcon(loadIcon(iconFile, 20, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(140, 42));
        button.setPreferredSize(new Dimension(140, 42));
        button.setBackground(SIDEBAR);
        button.setForeground(DARK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));

        return button;
    }

    // SWITCH BETWEEN PAGES
    private void showPage(String pageName) {

        pageLayout.show(pagePanel, pageName);

        if ("STUDENT".equals(pageName)) {

            studentButton.setBackground(PINK);
            aboutButton.setBackground(SIDEBAR);

        } else {

            studentButton.setBackground(SIDEBAR);
            aboutButton.setBackground(PINK);
        }

        pagePanel.revalidate();
        pagePanel.repaint();
    }

    // STUDENT PAGE
    private JPanel createStudentPage() {

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(CREAM);
        rightPanel.setBorder(
            BorderFactory.createEmptyBorder(25, 25, 20, 25)
        );

        // STATUS SUMMARY
        JPanel summaryPanel = new JPanel(
            new GridLayout(1, 3, 12, 0)
        );

        summaryPanel.setBackground(CREAM);

        availableCount = new JLabel("Available  10");
        occupiedCount = new JLabel("Occupied  0");
        defectiveCount = new JLabel("Defective  0");

        summaryPanel.add(
            createSummaryCard(
                "available.png",
                availableCount,
                new Color(230, 242, 225)
            )
        );

        summaryPanel.add(
            createSummaryCard(
                "occupied.png",
                occupiedCount,
                new Color(255, 239, 220)
            )
        );

        summaryPanel.add(
            createSummaryCard(
                "defective.png",
                defectiveCount,
                PINK
            )
        );

        rightPanel.add(summaryPanel, BorderLayout.NORTH);

        // COMPUTER CARDS
        JPanel computerPanel = new JPanel(
            new GridLayout(2, 5, 12, 12)
        );

        computerPanel.setBackground(CREAM);
        computerPanel.setBorder(
            BorderFactory.createEmptyBorder(18, 0, 10, 0)
        );

        for (int i = 0; i < 10; i++) {

            final int computerIndex = i;

            JPanel card = new JPanel();
            card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
            );

            card.setBackground(Color.WHITE);

            card.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                        new Color(235, 220, 225), 1
                    ),
                    BorderFactory.createEmptyBorder(
                        12, 5, 12, 5
                    )
                )
            );

            computerCards[i] = card;

            // COMPUTER ICON
            JLabel computerIcon = new JLabel(
                loadIcon("computer.png", 90, 90)
            );

            computerIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

            // COMPUTER NAME
            JLabel computerName = new JLabel(
                String.format("PC-%02d", i + 1)
            );

            computerName.setFont(
                new Font("Arial", Font.BOLD, 23)
            );

            computerName.setForeground(DARK);
            computerName.setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            // COMPUTER STATUS
            statusLabels[i] = new JLabel("Available");

            statusLabels[i].setFont(
                new Font("Arial", Font.BOLD, 15)
            );

            statusLabels[i].setForeground(GREEN);
            statusLabels[i].setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            availabilityLabels[i] = new JLabel("Available Now");

            availabilityLabels[i].setFont(
                new Font("Arial", Font.PLAIN, 12)
            );

            availabilityLabels[i].setForeground(DARK);
            availabilityLabels[i].setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            // SELECT BUTTON
            selectButtons[i] = new JButton("Select");

            selectButtons[i].setAlignmentX(
                Component.CENTER_ALIGNMENT
            );

            selectButtons[i].setBackground(PINK);
            selectButtons[i].setForeground(DARK);
            selectButtons[i].setFocusPainted(false);

            selectButtons[i].addActionListener(e -> {
                showStudentForm(computerIndex);
            });

            card.add(computerIcon);
            card.add(Box.createVerticalStrut(3));
            card.add(computerName);
            card.add(Box.createVerticalStrut(5));
            card.add(statusLabels[i]);
            card.add(availabilityLabels[i]);
            card.add(Box.createVerticalStrut(8));
            card.add(selectButtons[i]);

            computerPanel.add(card);
        }

        rightPanel.add(computerPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    // ABOUT PAGE
    private JPanel createAboutPage() {

        JPanel aboutPanel = new JPanel(new BorderLayout());

        aboutPanel.setBackground(CREAM);
        aboutPanel.setBorder(
            BorderFactory.createEmptyBorder(35, 40, 35, 40)
        );

        // PAGE TITLE
        JLabel heading = new JLabel("About Com & Go");

        heading.setFont(
            new Font("Arial", Font.BOLD, 30)
        );

        heading.setForeground(DARK);
        heading.setBorder(
            BorderFactory.createEmptyBorder(0, 0, 20, 0)
        );

        // CONTENT PANEL
        JPanel content = new JPanel();

        content.setLayout(
            new BoxLayout(content, BoxLayout.Y_AXIS)
        );

        content.setBackground(Color.WHITE);

        content.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(235, 220, 225), 1
                ),
                BorderFactory.createEmptyBorder(
                    28, 30, 28, 30
                )
            )
        );

        // APP NAME
        JLabel appName = new JLabel("Com & Go");

        appName.setFont(
            new Font("Arial", Font.BOLD, 25)
        );

        appName.setForeground(DARK);
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);

        // TAGLINE
        JLabel taglineLabel = new JLabel("Click. Check. Go!");

        taglineLabel.setFont(
            new Font("Arial", Font.ITALIC, 15)
        );

        taglineLabel.setForeground(GREEN);
        taglineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ABOUT DESCRIPTION
        JLabel descriptionTitle = createAboutHeading(
            "About the System"
        );

        JLabel description = createAboutText(
            "Com & Go is a GUI-based Computer Laboratory Monitoring "
            + "and Student Usage Management System. It helps students "
            + "check computer availability and register their use of "
            + "a computer in the laboratory."
        );

        // PURPOSE
        JLabel purposeTitle = createAboutHeading("Purpose");

        JLabel purpose = createAboutText(
            "The system aims to make computer laboratory monitoring "
            + "more organized by displaying computer statuses and "
            + "recording student usage information."
        );

        // FEATURES
        JLabel featuresTitle = createAboutHeading(
            "Student Interface Features"
        );

        JLabel features = createAboutText(
            "• View the status of laboratory computers\n"
            + "• Select an available computer\n"
            + "• Enter student information\n"
            + "• Choose Time In and Expected Time Out using time pickers"
        );

        // ADD CONTENT
        content.add(appName);
        content.add(Box.createVerticalStrut(4));
        content.add(taglineLabel);
        content.add(Box.createVerticalStrut(28));

        content.add(descriptionTitle);
        content.add(Box.createVerticalStrut(7));
        content.add(description);
        content.add(Box.createVerticalStrut(22));

        content.add(purposeTitle);
        content.add(Box.createVerticalStrut(7));
        content.add(purpose);
        content.add(Box.createVerticalStrut(22));

        content.add(featuresTitle);
        content.add(Box.createVerticalStrut(7));
        content.add(features);

        aboutPanel.add(heading, BorderLayout.NORTH);
        aboutPanel.add(content, BorderLayout.CENTER);

        return aboutPanel;
    }

    // ABOUT PAGE HEADINGS
    private JLabel createAboutHeading(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        label.setForeground(DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // ABOUT PAGE DESCRIPTION TEXT
    private JLabel createAboutText(String text) {

        JLabel label = new JLabel(
            "<html><div style='width:650px;'>"
            + text.replace("\n", "<br>")
            + "</div></html>"
        );

        label.setFont(
            new Font("Arial", Font.PLAIN, 15)
        );

        label.setForeground(DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // SUMMARY CARD
    private JPanel createSummaryCard(
        String iconFile,
        JLabel countLabel,
        Color background
    ) {

        JPanel card = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 8, 15)
        );

        card.setBackground(background);

        card.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        JLabel iconLabel = new JLabel(
            loadIcon(iconFile, 25, 25)
        );

        countLabel.setFont(
            new Font("Arial", Font.BOLD, 13)
        );

        countLabel.setForeground(DARK);

        card.add(iconLabel);
        card.add(countLabel);

        return card;
    }

    // FORM LABEL WITH PNG ICON
    private JLabel createFormLabel(
        String text,
        String iconFile
    ) {

        JLabel label = new JLabel(
            text,
            loadIcon(iconFile, 20, 20),
            SwingConstants.LEFT
        );

        label.setForeground(DARK);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        return label;
    }

    // STUDENT INFORMATION POP-UP
    private void showStudentForm(int index) {

        JTextField nameField = new JTextField(15);
        JTextField srCodeField = new JTextField(15);

        // NUMBERS ONLY FOR STUDENT NUMBER
        AbstractDocument document =
            (AbstractDocument) srCodeField.getDocument();

        document.setDocumentFilter(
            new NumericDocumentFilter()
        );

        // TIME IN PICKER
        JComboBox<String> timeInHour =
            new JComboBox<>(createHourOptions());

        JComboBox<String> timeInMinute =
            new JComboBox<>(createMinuteOptions());

        JComboBox<String> timeInPeriod =
            new JComboBox<>(new String[]{"AM", "PM"});

        timeInHour.setSelectedItem("02");
        timeInMinute.setSelectedItem("00");
        timeInPeriod.setSelectedItem("PM");

        JPanel timeInPicker = createTimePicker(
            timeInHour,
            timeInMinute,
            timeInPeriod
        );

        // EXPECTED TIME OUT PICKER
        JComboBox<String> timeOutHour =
            new JComboBox<>(createHourOptions());

        JComboBox<String> timeOutMinute =
            new JComboBox<>(createMinuteOptions());

        JComboBox<String> timeOutPeriod =
            new JComboBox<>(new String[]{"AM", "PM"});

        timeOutHour.setSelectedItem("03");
        timeOutMinute.setSelectedItem("00");
        timeOutPeriod.setSelectedItem("PM");

        JPanel timeOutPicker = createTimePicker(
            timeOutHour,
            timeOutMinute,
            timeOutPeriod
        );

        // FORM PANEL
        JPanel formPanel = new JPanel(
            new GridLayout(4, 2, 12, 16)
        );

        formPanel.setPreferredSize(
            new Dimension(530, 250)
        );

        formPanel.setBorder(
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        );

        formPanel.add(
            createFormLabel("Student Name:", "name.png")
        );

        formPanel.add(nameField);

        formPanel.add(
            createFormLabel("Student Number:", "student-id.png")
        );

        formPanel.add(srCodeField);

        formPanel.add(
            createFormLabel("Time In:", "time-in.png")
        );

        formPanel.add(timeInPicker);

        formPanel.add(
            createFormLabel("Expected Time Out:", "time-out.png")
        );

        formPanel.add(timeOutPicker);

        // SHOW POP-UP
        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Student Information - PC-"
                + String.format("%02d", index + 1),
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String studentName = nameField.getText().trim();
        String srCode = srCodeField.getText().trim();

        String timeIn =
            timeInHour.getSelectedItem() + ":"
            + timeInMinute.getSelectedItem() + " "
            + timeInPeriod.getSelectedItem();

        String expectedTimeOut =
            timeOutHour.getSelectedItem() + ":"
            + timeOutMinute.getSelectedItem() + " "
            + timeOutPeriod.getSelectedItem();

        // VALIDATE INPUT
        if (studentName.isEmpty() || srCode.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please complete all required information.",
                "Incomplete Information",
                JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CONFIRM ASSIGNMENT
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Student: " + studentName
            + "\nStudent Number: " + srCode
            + "\nComputer: PC-"
            + String.format("%02d", index + 1)
            + "\nTime In: " + timeIn
            + "\nExpected Time Out: " + expectedTimeOut
            + "\n\nConfirm computer assignment?",
            "Confirm Usage",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            statusLabels[index].setText("Occupied");
            statusLabels[index].setForeground(RED);

            availabilityLabels[index].setText(
                "Until: " + expectedTimeOut
            );

            selectButtons[index].setEnabled(false);
            selectButtons[index].setText("Occupied");

            computerCards[index].setBackground(
                new Color(255, 245, 230)
            );

            updateSummary();

            JOptionPane.showMessageDialog(
                this,
                "Computer successfully assigned!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // CREATE TIME PICKER
    private JPanel createTimePicker(
        JComboBox<String> hour,
        JComboBox<String> minute,
        JComboBox<String> period
    ) {

        JPanel panel = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 4, 0)
        );

        panel.setBackground(CREAM);

        hour.setPreferredSize(new Dimension(55, 30));
        minute.setPreferredSize(new Dimension(55, 30));
        period.setPreferredSize(new Dimension(75, 30));

        panel.add(hour);
        panel.add(new JLabel(":"));
        panel.add(minute);
        panel.add(period);

        return panel;
    }

    // HOUR OPTIONS: 01 TO 12
    private String[] createHourOptions() {

        String[] hours = new String[12];

        for (int i = 0; i < 12; i++) {
            hours[i] = String.format("%02d", i + 1);
        }

        return hours;
    }

    // MINUTE OPTIONS: 00 TO 59
    private String[] createMinuteOptions() {

        String[] minutes = new String[60];

        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }

        return minutes;
    }

    // ALLOW NUMBERS ONLY
    private class NumericDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(
            FilterBypass fb,
            int offset,
            String text,
            AttributeSet attr
        ) throws BadLocationException {

            if (text != null && text.matches("[0-9]+")) {
                super.insertString(fb, offset, text, attr);
            }
        }

        @Override
        public void replace(
            FilterBypass fb,
            int offset,
            int length,
            String text,
            AttributeSet attrs
        ) throws BadLocationException {

            if (text == null || text.matches("[0-9]*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    // UPDATE COMPUTER COUNTS
    private void updateSummary() {

        int available = 0;
        int occupied = 0;
        int defective = 0;

        for (int i = 0; i < 10; i++) {

            String status = statusLabels[i].getText();

            if (status.equals("Available")) {
                available++;
            } else if (status.equals("Occupied")) {
                occupied++;
            } else if (status.equals("Defective")) {
                defective++;
            }
        }

        availableCount.setText("Available  " + available);
        occupiedCount.setText("Occupied  " + occupied);
        defectiveCount.setText("Defective  " + defective);
    }

    
    // MARK COMPUTER AS DEFECTIVE
    public void setComputerDefective(int index) {

        if (index < 0 || index >= 10) {
            return;
        }

        statusLabels[index].setText("Defective");
        statusLabels[index].setForeground(ORANGE);

        availabilityLabels[index].setText("Unavailable");

        selectButtons[index].setEnabled(false);
        selectButtons[index].setText("Defective");

        computerCards[index].setBackground(
            new Color(255, 235, 225)
        );

        updateSummary();
    }

    // ==========================================
    // LOAD AND RESIZE PNG ICONS
    // ==========================================

    private ImageIcon loadIcon(
        String fileName,
        int width,
        int height
    ) {

        String path = "icons/" + fileName;
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Icon not found: " + path);
            return null;
        }

        ImageIcon originalIcon = new ImageIcon(path);

        Image image = originalIcon.getImage().getScaledInstance(
            width,
            height,
            Image.SCALE_SMOOTH
        );

        return new ImageIcon(image);
    }

    // MAIN METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentInterface::new);
    }
}