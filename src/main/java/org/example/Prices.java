package org.example;


public class Prices {
    private final int[] centPerHour;
    private final String[] hourGraph;
    private int[] sortedCentPerHour;
    private String[] sortedHourGraph;


    public Prices() {
        centPerHour = new int[24];
        hourGraph = new String[]{
                "00-01", "01-02", "02-03", "03-04", "04-05", "05-06", "06-07", "07-08", "08-09",
                "09-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-19", "19-20",
                "20-21", "21-22", "22-23", "23-24"
        };


    }


    public void setCentPerHour(int i, int cent) {
        this.centPerHour[i] = cent;
    }


    public int[] getCentPerHour() {
        return this.centPerHour;
    }


    public int getNumFormCentPerHour (int num){
        return this.centPerHour[num];
    }


    public String[] getHourGraph() {
        return this.hourGraph;
    }


    public String getTimeFromHourGraph(int time) {
        return this.hourGraph[time];
    }


    public void setSortedCentPerHour(int[] sortedCentPerHour) {
        this.sortedCentPerHour = sortedCentPerHour;
    }


    public int[] getSortedCentPerHour() {
        return this.sortedCentPerHour;
    }


    public void setSortedHourGraph(String[] sortedHourGraph) {
        this.sortedHourGraph = sortedHourGraph;
    }


    public String[] getSortedHourGraph() {
        return this.sortedHourGraph;
    }
}