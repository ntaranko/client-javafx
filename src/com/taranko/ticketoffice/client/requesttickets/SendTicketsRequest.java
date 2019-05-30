package com.taranko.ticketoffice.client.requesttickets;

import com.taranko.ticketoffice.client.utils.DateUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;
import java.util.function.Consumer;

public class SendTicketsRequest {

    public void sendTicketRequest(String requestDispatchStation,
                                  String requestArrivalStation,
                                  Date requestDispatchDate,
                                  Date requestDispatchTime,
                                  int requestNumberTickets) throws IOException {

        Properties programSettings = new Properties();
        BufferedReader propertiesReader = new BufferedReader(new FileReader("settings.properties"));
        programSettings.load(propertiesReader);

        String serverHost = programSettings.getProperty("serverHost");
        int serverPort = Integer.parseInt(programSettings.getProperty("serverPort"));
        Socket socket = new Socket(serverHost, serverPort);

        OutputStream clientOS = socket.getOutputStream();
        OutputStreamWriter osWriter = new OutputStreamWriter(clientOS);

        JSONWriter jsonWriter = new JSONWriter(osWriter);

        DateUtils dateUtils = new DateUtils();
        String requestDispatchDateString = dateUtils.buildStringFromDate(requestDispatchDate);
        String requestDispatchTimeString = dateUtils.buildStringFromTime(requestDispatchTime);

        jsonWriter.object().key("meta-data").object().key("command").value("trainRequest").endObject();
        jsonWriter.key("data").object().key("requestDispatchStation").value(requestDispatchStation)
                .key("requestArrivalStation").value(requestArrivalStation)
                .key("requestDispatchDate").value(requestDispatchDateString)
                .key("requestDispatchTime").value(requestDispatchTimeString)
                .key("requestNumberTickets").value(requestNumberTickets).endObject();
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
            loginWarning.setHeaderText("Tickets Request");
            loginWarning.setContentText("No available tickets! Change search criteria and try again!");
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
            loginWarning.setContentText("Tickets are available");
            loginWarning.showAndWait().ifPresent(new Consumer<ButtonType>() {
                @Override
                public void accept(ButtonType buttonType) {
                    if (buttonType == ButtonType.OK) {
                        loginWarning.hide();
                        // add response parsing!!!

                    }
                }
            });

        }

        osWriter.close();
        serverResponse.close();
    }
}
