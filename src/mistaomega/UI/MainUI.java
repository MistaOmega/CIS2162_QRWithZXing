package mistaomega.UI;

import com.google.zxing.WriterException;
import mistaomega.Utilities;
import mistaomega.qr.QRGen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainUI {
    private JPanel mainPanel;
    private JButton btnQuestion1;
    private JButton btnQuestion2;
    private JButton btnQuestion3;
    private JButton btnQuestion4;
    private JLabel lblQuestionTitle;
    private JPanel imagePan;

    private JSONArray Questions;

    public MainUI() {

    }

    @SuppressWarnings("unchecked") // KEKW
    public void initialize() {
        Questions = Utilities.DecodeJSON();
        List<String> answers = new ArrayList<>();
        Questions.forEach(item -> {
            JSONObject question = (JSONObject) item;
            System.out.println(question.toJSONString());

            lblQuestionTitle.setText((String) question.get("title"));

            // get a list of all answers
            question.forEach((k,v) ->{
                if(k.equals("answer1") ||  k.equals("answer2") || k.equals("answer3") ||  k.equals("answerCorrect")){
                    answers.add((String) v);
                }
            });


            Collections.shuffle(answers);
            btnQuestion1.setText(answers.get(0));
            btnQuestion2.setText(answers.get(1));
            btnQuestion3.setText(answers.get(2));
            btnQuestion4.setText(answers.get(3));
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() throws IOException, WriterException {
        // TODO: place custom component creation code here
        imagePan = new ImagePanel(QRGen.GenerateQRAsBufferedImage("https://www.liverpoolmuseums.org.uk/whatson/world-museum/exhibition/space-and-time-gallery", "UTF-8", 200, 200));
    }
}
