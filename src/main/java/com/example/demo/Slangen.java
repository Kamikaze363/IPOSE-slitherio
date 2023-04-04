package com.example.demo;

public class Slangen {
    private int lengte;
    private String naam;
    private String score;

    public Slangen(int lengte, String naam, String score) {
        this.lengte = lengte;
        this.naam = naam;
        this.score = score;
    }

    public int getLengte() {
        return lengte;
    }

    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
