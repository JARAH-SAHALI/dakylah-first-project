import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            StudentInterface studentWindow = new StudentInterface();
        });

    }
}