package com.beccychan.cats;

import java.io.Serializable;

public class Stat implements Serializable {
    public String key;
    public String value;

    public Stat(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
