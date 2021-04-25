package mistaomega.UI;

import javax.swing.*;

public class Correct_Answer {
    private JPanel Correct;

    private JPanel imagePan;
    private JLabel lblQuestionTitle;
    private Object ImageIcon;

    private void createUIComponents() {
        // TODO: place custom component creation code here"""

        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon("D:\\April Courseworks\spacepic.jpg");
        JLabel Picture = new JLabel(icon);
        Picture.setIcon(icon);
       Picture.isShowing();

    }
}
