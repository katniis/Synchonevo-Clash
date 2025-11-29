package main;
import java.util.*;
import ui.*;
import utils.*;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Display display = new Display();
        Utils.clearScreen();
        Utils.textTyper(Utils.color("9 Elixer", Utils.PURPLE) + " Presents", 50);
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
                System.out.println("DELETING OS");
                break;
            }
        }
        input.close();
    }
    
}
