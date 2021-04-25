package mistaomega.qr;


import com.google.zxing.WriterException;
import mistaomega.UI.MainUI;

import javax.swing.*;
import java.io.IOException;

public class Main {

    /**
     * Driver for the code
     *
     * @param args input arguments /ignored
     * @throws IOException     exceptions produced by failed or interrupted I/O operations.
     * @throws WriterException range of exceptions which may occur when encoding a barcode using the Writer framework.
     */
    public static void main(String[] args){
//        String data = "www.liverpoolmuseums.org.uk/whatson/world-museum/exhibition/space-and-time-gallery"; // data the QR code is going to contain
//        String path = "test.png"; // path where the QR code will be saved
//
//        // Encoding charset
//        String charset = "UTF-8";
//        QRGen.GenerateQR(data, path, charset, 200, 200);
//        System.out.println("QR code created");

        setLookAndFeel();

        SwingUtilities.invokeLater(() -> { //invoke on new thread
            MainUI gui = new MainUI();
            Thread thread = new Thread(gui);
            thread.start();
            JFrame frame = new JFrame();
            frame.setTitle("Question UI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(gui.getMainPanel());
            frame.pack();
            frame.setVisible(true);

        });
    }

    /**
     * Set style to be OS specific
     */
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unsupported look and feel, standard will be used");
            e.printStackTrace();
        }
    }

}
