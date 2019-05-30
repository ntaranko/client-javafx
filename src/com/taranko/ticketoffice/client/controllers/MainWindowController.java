package com.taranko.ticketoffice.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {
    @FXML
    private Button loginBtn;

    @FXML
    public void initialize() {

        loginBtn.setOnAction(event -> {

            loginBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/mainWindowAuth.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Train Tickets Booking");
            stage.setScene(new Scene(root, 1000, 600));
            stage.showAndWait();

        });
    }
}



    