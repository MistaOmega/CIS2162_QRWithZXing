package mistaomega.UI;

import com.google.zxing.WriterException;
import mistaomega.Utilities;
import mistaomega.qr.QRGen;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private int total;
    private boolean corFlag;

    private JSONArray Questions;
    private final Object lock = new Object();

    //region setup
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


    private void createUIComponents() throws IOException, WriterException {
        imagePanel = new ImagePanel(QRGen.GenerateQRAsBufferedImage("www.google.com", "UTF-8", 200, 200));
    }
    //endregion



    //region main
    /**
     * Entry point for main UI
     * Decode JSONs file and iterates through each question
     * Each question has an answer list generated
     * UI is decorated with question title, QR code to correct link and answers (which are shuffled before display)
     * Awaits answer, displays correct or incorrect
     * Results shown to user at the end
     */
    @Override
    public void run() {
        Questions = Utilities.DecodeJSON();
        List<String> answers = new ArrayList<>();

        // Go throught each question item by item
        Questions.forEach(item -> {
            // setup for each question
            corFlag = false;
            answeringQuestion = true;
            question = (JSONObject) item;
            answers.clear();
            //System.out.println(question.toJSONString()); // debug for demonstration

            lblQuestionTitle.setText((String) question.get("title"));

            // get a list of all answers
            question.forEach((k,v) ->{
                if(k.equals("answer1") ||  k.equals("answer2") || k.equals("answer3") ||  k.equals("answerCorrect")){
                    answers.add((String) v);
                }
            });

            // set the QR code to check for
            try {
                imagePanel.setImage(QRGen.GenerateQRAsBufferedImage((String) question.get("url"), "UTF-8", 200, 200));
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }

            //shuffle answers to different buttons
            Collections.shuffle(answers);
            btnQuestion1.setText(answers.get(0));
            btnQuestion2.setText(answers.get(1));
            btnQuestion3.setText(answers.get(2));
            btnQuestion4.setText(answers.get(3));
            synchronized(lock) { // this lock can be called from elsewhere, avoids busy-waiting
                while(isAnsweringQuestion()) { //await response
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // check if correct and display correct image
            try {
                if (corFlag) {
                    imagePanel.setImage(ImageIO.read(new File("src/mistaomega/images/Welldone.jpg")));
                    lblQuestionTitle.setText("Correct, please wait 3 seconds");
                } else {
                    imagePanel.setImage(ImageIO.read(new File("src/mistaomega/images/Incorrect.jpg")));
                    lblQuestionTitle.setText("Incorrect, please wait 3 seconds");
                }

                Thread.sleep(3000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }


        });

        // show results
        Results gui = new Results(total);
        Thread th = new Thread(gui);
        th.start();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Results");
        frame.setContentPane(gui.getMainPanel());
        frame.pack();
        frame.setVisible(true);


    }


    private void handleAnswer(String answer){
        if(answer.equals(question.get("answerCorrect"))){
            total += 1;
            corFlag = true;
        }
        synchronized(lock){
            lock.notify();
        }
        setAnsweringQuestion(false);
    }
    //endregion

    //region getters and setters
    public boolean isAnsweringQuestion() {
        return answeringQuestion;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setAnsweringQuestion(boolean answeringQuestion) {
        this.answeringQuestion = answeringQuestion;
    }
    //endregion
}

