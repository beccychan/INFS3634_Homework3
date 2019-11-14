package com.beccychan.cats;

import java.io.Serializable;
import java.util.ArrayList;

public class CatImage implements Serializable {
    private String width;
    private String id;
    private String url;
    private ArrayList<CatBreed> breeds;
    private String height;

    public String getWidth () {
        return width;
    }

    public String getId () {
        return id;
    }

    public String getUrl () {
        return url;
    }

    public ArrayList<CatBreed> getBreeds () {
        return breeds;
    }

    public String getHeight () {
        return height;
    }

}