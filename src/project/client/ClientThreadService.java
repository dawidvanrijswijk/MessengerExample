package project.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThreadService implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    ClientThreadService(Socket socket) throws IOException {
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    @Override
    public void run() {
        boolean running = true;
        try {
            while (true) {
                String message;
                if ((message = reader.readLine()) != null) {
                    ClientView.messageTextArea.appendText(message + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}