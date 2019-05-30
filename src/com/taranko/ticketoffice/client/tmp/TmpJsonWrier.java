package com.taranko.ticketoffice.client.tmp;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TmpJsonWrier {

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("test-write.json");
        JSONWriter jsonWriter = new JSONWriter(writer);


        jsonWriter.object().key("response").value("Authentication successful!").endObject();


        writer.close();

        FileReader reader = new FileReader("test-write.json");
        JSONTokener tokener = new JSONTokener(reader);

        JSONObject jsonObject = (JSONObject) tokener.nextValue();
        String key = jsonObject.getString("response");
        System.out.println(key);
    }
}