import javax.swing.*;

public class Display {
    private static void createAndShowGUI(JTextArea textArea)  {
        JFrame frame = new JFrame("SheepWolfGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(textArea);
        frame.pack();
        frame.setVisible(true);
    }

    public static JTextArea initiateDisplay() {
       final var textArea = new JTextArea("STARTING...\n\n\n");

        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI(textArea));

        return textArea;
    }
}