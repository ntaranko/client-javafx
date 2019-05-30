package com.taranko.ticketoffice.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.function.Consumer;

public class MainWindowAuthController {
    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button authorizeBtn;

    @FXML
    public void initialize() {

        authorizeBtn.setOnAction(event -> {

            String login = loginField.getText();
            String password = passwordField.getText();

            try {
                Properties programSettings = new Properties();
                BufferedReader propertiesReader = new BufferedReader(new FileReader("settings.properties"));
                programSettings.load(propertiesReader);

                String serverHost = programSettings.getProperty("serverHost");
                int serverPort = Integer.parseInt(programSettings.getProperty("serverPort"));
                Socket socket = new Socket(serverHost, serverPort);

                OutputStream clientOS = socket.getOutputStream();
                OutputStreamWriter osWriter = new OutputStreamWriter(clientOS);

                JSONWriter jsonWriter = new JSONWriter(osWriter);
                jsonWriter.object().key("meta-data").object().key("command").value("login").endObject();
                jsonWriter.key("data").object().key("login").value(login).key("password").value(password).endObject();
                jsonWriter.endObject();

                osWriter.flush();

                InputStream serverResponse = socket.getInputStream();
                JSONTokener tokener = new JSONTokener(serverResponse);

                JSONObject serverJson = (JSONObject) tokener.nextValue();

                JSONObject responseData = serverJson.getJSONObject("response-data");
                Integer responseCode = responseData.getInt("response-code");
                JSONObject response = serverJson.getJSONObject("response");

                if (responseCode == 405) {
                    Alert loginWarning = new Alert(Alert.AlertType.ERROR);
                    loginWarning.setHeaderText("Authorization");
                    loginWarning.setContentText("Wrong login or password! Try again");
                    loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                        @Override
                        public void accept(ButtonType buttonType) {
                            if (buttonType == ButtonType.OK) {
                                loginWarning.hide();
                            }
                        }
                    });
                } else {

                    Alert loginWarning = new Alert(Alert.AlertType.INFORMATION);
                    loginWarning.setContentText("Successful authorization");
                    loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                        @Override
                        public void accept(ButtonType buttonType) {
                            if (buttonType == ButtonType.OK) {
                                loginWarning.hide();
                                //authorizeBtn.getScene().getWindow().hide();

                                String firstName = response.getString("firstName");
                                String lastName = response.getString("lastName");

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("../fxml/requestTickets.fxml"));
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

                            }
                        }
                    });

                }

                osWriter.close();
                serverResponse.close();

            } catch (IOException e) {

            }

        });

    }
}
