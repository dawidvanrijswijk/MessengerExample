package project.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class ClientView extends Application {


    private Scene loginScene;
    private Scene mainScene;
    private static final Integer PORT = 8434;
    private static final String HOST_NAME = "localhost";
    private ClientThreadService clientThreadService;
    private TextArea userArea;
    static TextArea messageTextArea;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button loginButton = new Button();
        Button disconnectButton = new Button();
        TextField textField1 = new TextField();
        TextField messageInput = new TextField();
        messageInput.setOnKeyPressed(b -> {
            if (b.getCode() == KeyCode.ENTER && !messageInput.getText().equals("")) {
                clientThreadService.sendMessage(textField1.getText() + messageInput.getText());
                messageInput.clear();
                messageInput.requestFocus();
            }
        });
        GridPane mainGridPane = new GridPane();
        GridPane loginGridPane = new GridPane();
        messageTextArea = new TextArea();
        userArea = new TextArea();
        Label label1 = new Label();

        primaryStage.setTitle("Messenger");
        loginButton.setText("Login");
        label1.setText("Enter Your Login:");
        loginButton.setOnAction(b -> {
            primaryStage.setTitle(textField1.getText() + "'s messenger");
            primaryStage.setScene(mainScene);
            messageTextArea.setText(textField1.getText() + " has joined to the chat");
            userArea.setText(textField1.getText());

            connect();
        });
        mainGridPane.setPadding(new Insets(5, 5, 5, 5));
        mainGridPane.setHgap(5);
        GridPane.setConstraints(label1, 12, 0);
        GridPane.setConstraints(loginButton, 16, 0);
        GridPane.setConstraints(textField1, 14, 0);
        mainGridPane.getChildren().addAll(loginButton, textField1, label1);
        loginScene = new Scene(mainGridPane, 500, 150);
        primaryStage.setScene(loginScene);

        disconnectButton.setText("Disconnect");
        disconnectButton.setOnAction(b -> primaryStage.setScene(loginScene));
        loginGridPane.setPadding(new Insets(5, 5, 5, 5));
        loginGridPane.setHgap(5);
        GridPane.setConstraints(disconnectButton, 1, 0);
        GridPane.setConstraints(messageInput, 1, 15);
        GridPane.setConstraints(messageTextArea, 1, 10);
        GridPane.setConstraints(userArea, 12, 10);
        loginGridPane.getChildren().addAll(disconnectButton, messageInput, messageTextArea, userArea);
        mainScene = new Scene(loginGridPane, 600, 500);
        primaryStage.show();
    }

    private void connect() {
        try {
            Socket socket = new Socket(HOST_NAME, PORT);
            clientThreadService = new ClientThreadService(socket);
            Thread thread = new Thread(clientThreadService);
            thread.start();
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}
