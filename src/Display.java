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
       final var textArea = new JTextArea("STARTING..", 15, 50);

        SwingUtilities.invokeLater(() -> createAndShowGUI(textArea));

        return textArea;
    }

    public static void endMessage(int scenario, JTextArea textArea)
    {
        var appendment = switch (scenario)
        {
            case 1 -> "Rabbit population exceeded 1000.";
            case 2 -> "There are no rabbits left.";
            case 3 -> "All wolfs have starvated";
            default -> throw new IllegalStateException("Unexpected value: " + scenario);
        };

        textArea.append(appendment);
    }
}