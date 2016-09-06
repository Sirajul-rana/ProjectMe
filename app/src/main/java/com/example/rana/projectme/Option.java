package com.example.rana.projectme;

/**
 * Created by Rana on 25-Aug-16.
 */
public class Option implements Comparable<Option>{

    private String name;
    private String data;
    private String path;

    public Option(String n, String d, String p){
        name = n;
        data = d;
        path = p;
    }

    public String getName(){
        return name;
    }

    public String getData(){
        return data;
    }

    public String getPath(){
        return path;
    }


    @Override
    public int compareTo(Option o) {
        if (this.name != null){
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
