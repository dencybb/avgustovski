package org.randomplace.dto;

public class GeoNamesResponse {
    public Nearest nearest;

    public static class Nearest {
        public String latt;
        public String longt;
    }
}