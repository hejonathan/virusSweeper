import java.io.*;
import java.util.*;

/**
 *TODO: Graphics (duh) and protect from dumbass users
 * (what if user inputs -1 for an array index?)
 */
public class TestingControlwithMask {
    static final int mines = 10;
    static final int rows = 10;
    static final int cols = 10;

    public static void main(String[] args){
        ControlwithMask game = new ControlwithMask();
        game.initialize(mines, rows, cols);
        System.out.println("MINES:");
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                System.out.print(game.mine[i][j]?'M':'X');
            }
            System.out.println();
        }
        System.out.println("COUNT");
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                System.out.print(game.count[i][j]);
            }
            System.out.println();
        }
        Scanner sc = new Scanner(System.in);

        displayBoard(game);
        while (!game.isFinished()) {

            System.out.println("(F) flag or (R) reveal");
            char c = sc.next().charAt(0);
            if (c=='F') {
                System.out.println("Flag x, y");
                int x = sc.nextInt();
                int y = sc.nextInt();
                game.flag(x, y);
                displayBoard(game);
            }
            else {
                System.out.println("Reveal x, y");
                int x,y;
                boolean pass;
                do {
                    x = sc.nextInt();
                    y = sc.nextInt();
                    pass = x>=0 && x<rows && y>=0 && y<cols;
                    if(!pass){
                        System.out.println("you idiot try again");
                    }
                }while(!pass);
                int nm = game.numMask;
                if (game.reveal(x, y)) {
                    displayBoard(game);
                } else {
                    System.out.println("YOU LOSE");
                }
                if(game.numMask<nm){
                    System.out.println("mask used");
                }
            }
        }
        System.out.println("YOU WIN");
    }
    public static void displayBoard(ControlwithMask game) {
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                if (game.revealed[i][j]) {
                    System.out.print(game.count[i][j]);
                }
                else if (game.flagged[i][j]) {
                    System.out.print('F');
                }
                else {
                    System.out.print('?');
                }
            }
            System.out.println();
        }
    }
}