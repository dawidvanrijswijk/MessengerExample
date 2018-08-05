package project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private static final Integer PORT = 8434;
    static List<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            boolean running = true;
            System.out.println("Running server...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                sockets.add(clientSocket);
                ServerThreadService threadService = new ServerThreadService(clientSocket);
                Thread thread = new Thread(threadService);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Can't create server");
            e.printStackTrace();
        }
    }
}
