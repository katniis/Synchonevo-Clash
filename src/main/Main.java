package main;
import java.util.*;
import ui.*;
import utils.*;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Display display = new Display();
        Utils.clearScreen();

        display.launch();

        Utils.delay(500);
        while(true){
            Utils.clearScreen();
            display.titleScreen();
            int choice = Utils.promptInt("  Enter: ", 1, 5);
            if (choice == 1){
                new Game().start();
            }else if(choice == 2){
                display.showHowToPlay();
                String enter = input.nextLine();
                if(enter.isEmpty()) continue;
            }else if(choice == 3){
                display.showCredits();
                String enter = input.nextLine();
                if(enter.isEmpty()) continue;
            }else if(choice == 4){
                Utils.clearScreen();
                Utils.textTyper("Goodbye...", 100);
                break;
            }
        }
        input.close();
    }
    
}
