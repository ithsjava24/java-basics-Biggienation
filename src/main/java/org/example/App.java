package org.example;


import java.util.Arrays;
import java.util.Scanner;


public class App {
    public static Prices prices = new Prices();
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        menu(scanner);
    }




    //code for menu
    public static void menu(Scanner scanner) {
        boolean loopController = true;
        while (loopController) {
            printMenu();
            loopController = menuSwitch(scanner);
        }
    }


    public static void printMenu() {
        System.out.println("""
              
               Elpriser
               ========
               1. Inmatning
               2. Min, Max och Medel
               3. Sortera
               4. Bästa Laddningstid (4h)
               5. Visualisering
               e. Avsluta
               """);
    }


    public static boolean menuSwitch(Scanner scanner) {
        boolean loopController = true;
        String input = scanner.nextLine();
        switch (input.toLowerCase()) {
            case "e" -> loopController = false;
            case "1" -> {
                inmatning(scanner);
                bubble();
            }
            case "2" -> minMaxMedel();
            case "3" -> Sortering();
            case "4" -> cheapestHours();
            case "5" -> diagram();
        }
        return loopController;
    }


    //case 1
    public static void inmatning(Scanner scanner) {
        int input;
        System.out.println("Hur mycket kostade elen vid:");
        for (int i = 0; i < 24; i++) {
            printInmatning(i);
            input = scanner.nextInt();
            prices.setCentPerHour(i, input);
        }
    }

    public static void printInmatning(int time) {
        System.out.println(prices.getTimeFromHourGraph(time));
    }


    public static void bubble (){
        prices.setSortedCentPerHour(Arrays.copyOf(prices.getCentPerHour(), prices.getCentPerHour().length));
        prices.setSortedHourGraph(Arrays.copyOf(prices.getHourGraph(), prices.getHourGraph().length));
        bubbleSorting(prices.getSortedCentPerHour(), prices.getSortedHourGraph());
    }


    public static void bubbleSorting(int[] centPerHour, String[] getHourGraph){
        for (int i = 0; i < centPerHour.length-1; i++) {
            for (int j = 0; j < centPerHour.length-i-1; j++) {
                if (centPerHour[j] < centPerHour[j+1]) {


                    int tempPricing = centPerHour[j];
                    centPerHour[j] = centPerHour[j+1];
                    centPerHour[j+1] = tempPricing;


                    String tempTime = getHourGraph[j];
                    getHourGraph[j] = getHourGraph[j+1];
                    getHourGraph[j+1] = tempTime;
                }
            }


        }


        prices.setSortedCentPerHour(centPerHour);
        prices.setSortedHourGraph(getHourGraph);
    }


    //case 2
    public static void minMaxMedel() {
        String average = averageStringFormat();
        printMinMaxAve(prices.getSortedCentPerHour(),prices.getSortedHourGraph(),average);


    }


    private static String averageStringFormat() {
        float average = centPerHourAverage(prices.getCentPerHour());
        return String.format("%.2f", average).replace(".", ",");
    }


    private static float centPerHourAverage(int[] centPerHour) {
        float average = 0;
        for(int i: centPerHour) {
            average += i;
        }
        return average / centPerHour.length;
    }


    private static void printMinMaxAve(int[] sortedCentPerDay, String[] sortedHourGraph, String average) {
        System.out.println("Lägsta pris: " + sortedHourGraph[23] + ", " + sortedCentPerDay[23] +" öre/kWh");
        System.out.println("Högsta pris: " + sortedHourGraph[0] + ", " + sortedCentPerDay[0] +" öre/kWh");
        System.out.println("Medelpris: " + average + " öre/kWh");
    }


    //case 3
    public static void Sortering(){
        printSorting(prices.getSortedCentPerHour(), prices.getSortedHourGraph());
    }


    private static void printSorting(int[] pricingCopy, String[] timeCopy) {
        for (int i = 0; i < pricingCopy.length; i++) {
            System.out.println(timeCopy[i] + " " + pricingCopy[i] + " öre");
        }
    }


    //case 4
    public static  void cheapestHours () {
        int[] startHourValues = cheapestHoursCalc();
        String startHourSubstring = getStarHourSubstring (startHourValues[0]);
        float cheapestPriceAverage = getCheapestHoursDivByFour(startHourValues[1]);
        String cheapestPriceAverageSv = SwedishDecimal(cheapestPriceAverage);
        printCheapestHours(cheapestPriceAverageSv, startHourSubstring);
    }


    public static int[] cheapestHoursCalc (){
        int[] startHourValues = new int[2];
        int fourHourPrice = 0;
        double tempHourPrice = Integer.MAX_VALUE;
        int start = 0;


        for (int i = 0; i < prices.getCentPerHour().length -4 ; i++) {
            fourHourPrice = prices.getNumFormCentPerHour(i) + prices.getNumFormCentPerHour(i+1) + prices.getNumFormCentPerHour(i+2) + prices.getNumFormCentPerHour(i+3);
            if (fourHourPrice < tempHourPrice) {
                tempHourPrice = fourHourPrice;
                start = i;
            }


        }
        startHourValues[0] = start;
        startHourValues[1] = fourHourPrice;


        return startHourValues;
    }


    private static String getStarHourSubstring(int startHour) {
        return prices.getTimeFromHourGraph(startHour).substring(0,2);
    }


    private static float getCheapestHoursDivByFour(int startHourValue) {
        return startHourValue/4f;
    }


    private static void printCheapestHours(String priceAverage, String startHourSubstring) {
        System.out.println("Påbörja laddning klockan " + startHourSubstring +"\n" +
                           "Medelpris 4h: " + priceAverage + " öre/kWh");
    }


    private static String SwedishDecimal(float englishDecimal) {
        return String.valueOf(englishDecimal).replace(".", ",");
    }


    //case 5
    public static void diagram(){
        String[][] visualRep = new String[8][26];
        creatBodyOfDiagram(visualRep,prices.getSortedCentPerHour());
        printVisualRep(visualRep);


    }


    private static void creatBodyOfDiagram(String[][] visualRep, int [] sortedCentPerHour) {
        addYMaxAndMin(visualRep,sortedCentPerHour);
        addPipeToY(visualRep);
        addDashToX(visualRep);
        addHourToX(visualRep);
        addPricesToX(visualRep, sortedCentPerHour);


    }


    private static void addYMaxAndMin(String[][] visualRep, int[] sortedCentPerHour) {
        int emptySpace = String.valueOf(sortedCentPerHour[0]).length();
        int emptySpaceHelp = String.valueOf(sortedCentPerHour[23]).length();
        visualRep[0][0] = String.valueOf(sortedCentPerHour[0]);
        addEmptySpaceYAndMin(visualRep,emptySpace,emptySpaceHelp, sortedCentPerHour);
    }


    private static void addEmptySpaceYAndMin(String[][] visualRep, int emptySpace, int emptySpaceHelp, int[] sortedCentPerHour) {
        for (int i = 1; i < 8; i++) {
            visualRep[i][0] = addEmptySpaceToDiagram(emptySpace) ;
            if (i == 5 ) {
                visualRep[i][0] = addEmptySpaceToDiagram(emptySpace - emptySpaceHelp) + sortedCentPerHour[23];
            }
        }
    }


    private static String addEmptySpaceToDiagram(int emptySpace) {
        String emptySpaceString = "";
        for (int i = 0; i < emptySpace; i++) {
            emptySpaceString += " ";
        }
        return emptySpaceString;
    }


    private static void addPipeToY(String[][] visualRep) {
        for (int i = 0; i < 8; i++) {
            visualRep[i][1] = "|";
        }
    }


    private static void addDashToX(String[][] visualRep) {
        for (int i = 2; i < 26; i++) {
            visualRep[6][i] = "---";
        }
    }


    private static void addHourToX(String[][] visualRep) {
        for (int i = 0; i < 24; i++) {
            visualRep[7][2+i] = " " + getStarHourSubstring(i);
        }
    }


    private static void addPricesToX(String[][] visualRep, int[] sortedCentPerHour) {
        float pricesCheck = ((sortedCentPerHour[0] - sortedCentPerHour[23])/5f);
        int divider = 5;


        for (int i = 0; i < 6; i++) {
            for (int j = 2; j < 26; j++) {
                addPriceToGraph(prices.getCentPerHour(),visualRep,sortedCentPerHour,i,j,pricesCheck,divider);
            }
            divider--;
        }
    }


    private static void addPriceToGraph(int[] centPerHour, String[][] visualRep, int[] sortedCentPerHour, int i, int j, float pricesCheck, int divider) {
        if(centPerHour[j-2] >= (int) (pricesCheck*divider+sortedCentPerHour[23])){
            visualRep[i][j] = "  x";
        } else {
            visualRep[i][j] = "   ";
        }
    }




    private static void printVisualRep(String[][] visualRep) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.print(visualRep[i][j]);
            }
            System.out.println();
        }
    }


}

