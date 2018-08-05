package project.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String HOSTNAME = "localhost";
    private static final Integer PORT = 8434;

    public static void main(String[] args) {
        Socket clientSocket = null;
        ClientThreadService threadService = null;
        try {
            System.out.println("Server waiting for client...");
            clientSocket = new Socket(HOSTNAME, PORT);
            threadService = new ClientThreadService(clientSocket);
            Thread thread = new Thread(threadService);
            thread.start();
        } catch (IOException e) {

        }

        System.out.println("Send message");
    }
}