package project.server;

import java.io.*;
import java.net.Socket;

public class ServerThreadService implements Runnable {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    ServerThreadService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean running = true;
            while (true) {
                String message;
                if ((message = reader.readLine()) != null) {
                    for (Socket socket : project.server.ChatServer.sockets) {
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        printWriter.println(message);
                        printWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                socket.close();
                reader.close();
            } catch (IOException e) {
                System.err.println("Can't close stream");
                e.printStackTrace();
            }
        }
    }
}