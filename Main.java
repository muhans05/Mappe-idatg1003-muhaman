package edu.ntnu.iir.bidata;

/**
 * Entry point. Lager UI and kjører lifecycle metoden.
 */
public class Main {
    public static void main(String[] args) {
        UI ui = new UI();
        ui.init();
        ui.start();
    }
}
