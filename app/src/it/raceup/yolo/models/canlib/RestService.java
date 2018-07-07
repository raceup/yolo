package it.raceup.yolo.models.canlib;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class RestService {
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;

    private String url;
    private int requestType;
    private ArrayList<ParcelableNameValuePair> params;

    public RestService(String url, int RequestType) {
        this.url = url;
        this.requestType = RequestType;
        params = new ArrayList<>();
    }

    public void addParam(String name, String value) {
        params.add(new ParcelableNameValuePair(name, value));
    }

    public JSONObject execute() throws Exception {
        System.out.println(url);
        URL requestUrl = new URL(url);
        URLConnection connection = requestUrl.openConnection();
        connection.setDoOutput(true);

        Scanner scanner = new Scanner(requestUrl.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        JSONObject json = new JSONObject(response);
        scanner.close();
        return json;
    }

    public void setParam(String name, String value) {
        for (int i = 0; i < this.params.size(); i++) {
            if (this.params.get(i).getName().matches(name)) {
                this.params.remove(i);
            }
        }
        params.add(new ParcelableNameValuePair(name, value));
    }
}
