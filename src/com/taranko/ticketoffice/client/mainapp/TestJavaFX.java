package com.taranko.ticketoffice.client.mainapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestJavaFX extends Application {

    private static final String FXML_MAIN = "../fxml/mainWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(FXML_MAIN));

        primaryStage.setTitle("Train Tickets Booking");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
