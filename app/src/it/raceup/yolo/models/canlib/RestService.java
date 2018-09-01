package it.raceup.yolo.models.canlib;

import it.raceup.yolo.logging.Logger;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contacts remote CableKvaser web-server
 */
public class RestService extends Logger {
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
    public final static int DELETE = 4;
    private String url;
    private ArrayList<ParcelableNameValuePair> params;

    public RestService(String baseUrl, String type, int ident) {
        TAG = "REST SERVICE";
        this.url = getUrl(baseUrl, type, ident);
        params = new ArrayList<>();
    }

    public void addParam(String name, String value) {
        params.add(new ParcelableNameValuePair(name, value));
    }

    private static String getUrl(String baseUrl, String type, int ident) {
        try {
            baseUrl += type;  // add path
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            uriBuilder.setParameter("ident", Integer.toString(ident));
            return uriBuilder.toString();
        } catch (Exception e) {
            return baseUrl;
        }
    }

    private URL getFullUrl() {
        try {
            URIBuilder builder = new URIBuilder(url);
            for (ParcelableNameValuePair pair : params) {
                builder.setParameter(pair.name, pair.value);
            }
            URI uri = builder.build();
            return uri.toURL();
        } catch (Exception e) {
            return null;
        }
    }

    public void setParam(String name, String value) {
        for (int i = 0; i < this.params.size(); i++) {
            if (this.params.get(i).getName().matches(name)) {
                this.params.remove(i);
            }
        }
        params.add(new ParcelableNameValuePair(name, value));
    }

    public JSONObject get() {
        try {
            URL requestUrl = getFullUrl();
            logAction("GET " + requestUrl.toString());

            URLConnection connection = requestUrl.openConnection();
            connection.setDoOutput(true);
            Scanner scanner = new Scanner(requestUrl.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            JSONObject json = new JSONObject(response);
            scanner.close();
            return json;
        } catch (Exception e) {
            logError(e.toString());
            return null;
        }
    }
}
