package mistaomega.qr;


import com.google.zxing.WriterException;

import java.io.IOException;

public class Main {

    /**
     * Driver for the code
     *
     * @param args input arguments /ignored
     * @throws IOException     exceptions produced by failed or interrupted I/O operations.
     * @throws WriterException range of exceptions which may occur when encoding a barcode using the Writer framework.
     */
    public static void main(String[] args) throws IOException, WriterException {
        String data = "www.liverpoolmuseums.org.uk/whatson/world-museum/exhibition/space-and-time-gallery"; // data the QR code is going to contain
        String path = "test.png"; // path where the QR code will be saved

        // Encoding charset
        String charset = "UTF-8";
        QRGen.GenerateQR(data, path, charset, 200, 200);
        System.out.println("QR code created");
    }

}
