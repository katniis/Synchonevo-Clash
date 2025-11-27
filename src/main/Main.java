package main;
import ui.*;
import utils.*;
public class Main {
    public static void main(String[] args) {
        Display display = new Display();
        Utils.clearScreen();
        display.titleScreen();
        int choice = Utils.promptInt("  Enter: ", 1, 5);
        if (choice == 1){
            new Game().start();
        }
    }
}
