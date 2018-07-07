package it.raceup.yolo.models.canlib;

import org.apache.http.NameValuePair;

public class ParcelableNameValuePair implements NameValuePair {
    String name, value;

    public ParcelableNameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public int describeContents() {
        return 0;
    }
}
