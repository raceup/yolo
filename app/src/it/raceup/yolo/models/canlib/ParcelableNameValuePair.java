package it.raceup.yolo.models.canlib;

import org.apache.http.NameValuePair;

public class ParcelableNameValuePair implements NameValuePair {
    final String name;
    final String value;

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
}
