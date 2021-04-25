package mistaomega.UI;

import javax.swing.*;

public class Results implements Runnable{
    private JPanel mainPanel;
    private JLabel scoreLbl;
    private JButton btnExit;

    public Results(int totalScore){
        scoreLbl.setText("You've scored: " + totalScore + " out of 10");
        btnExit.addActionListener(actionEvent -> System.exit(0));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void run() {

    }
}
