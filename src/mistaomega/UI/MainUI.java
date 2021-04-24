package mistaomega.UI;

import com.google.zxing.WriterException;
import mistaomega.Utilities;
import mistaomega.qr.QRGen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainUI implements Runnable {
    private JPanel mainPanel;
    private JButton btnQuestion1;
    private JButton btnQuestion2;
    private JButton btnQuestion3;
    private JButton btnQuestion4;
    private JLabel lblQuestionTitle;
    private ImagePanel imagePanel;
    private boolean answeringQuestion;
    private JSONObject question;

    private JSONArray Questions;
    private final Object lock = new Object();

    public MainUI() {

        btnQuestion1.addActionListener(actionEvent -> {
            handleAnswer(btnQuestion1.getText());
        });
        btnQuestion2.addActionListener(actionEvent -> {
            handleAnswer(btnQuestion2.getText());
        });
        btnQuestion3.addActionListener(actionEvent -> {
            handleAnswer(btnQuestion3.getText());
        });
        btnQuestion4.addActionListener(actionEvent -> {
            handleAnswer(btnQuestion4.getText());
        });

    }

    private void handleAnswer(String answer){
        System.out.println("HEllo");
        if(answer.equals(question.get("answerCorrect"))){
            //todo
        }
        synchronized(lock){
            lock.notify();
        }
        setAnsweringQuestion(false);
    }

    private void createUIComponents() throws IOException, WriterException {
        imagePanel = new ImagePanel(QRGen.GenerateQRAsBufferedImage("www.google.com", "UTF-8", 200, 200));
    }

    public boolean isAnsweringQuestion() {
        return answeringQuestion;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setAnsweringQuestion(boolean answeringQuestion) {
        this.answeringQuestion = answeringQuestion;
    }

    @Override
    public void run() {
        Questions = Utilities.DecodeJSON();
        List<String> answers = new ArrayList<>();
        Questions.forEach(item -> {
            answeringQuestion = true;
            question = (JSONObject) item;
            answers.clear();
            System.out.println(question.toJSONString());

            lblQuestionTitle.setText((String) question.get("title"));

            // get a list of all answers
            question.forEach((k,v) ->{
                if(k.equals("answer1") ||  k.equals("answer2") || k.equals("answer3") ||  k.equals("answerCorrect")){
                    answers.add((String) v);
                }
            });

            try {
                imagePanel.setImage(QRGen.GenerateQRAsBufferedImage((String) question.get("url"), "UTF-8", 200, 200));
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }


            Collections.shuffle(answers);
            btnQuestion1.setText(answers.get(0));
            btnQuestion2.setText(answers.get(1));
            btnQuestion3.setText(answers.get(2));
            btnQuestion4.setText(answers.get(3));
            synchronized(lock) {
                while(isAnsweringQuestion()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        });
    }
}
