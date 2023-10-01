package edu.school21.ConsoleGame;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


public class Game {
    private static GameState isEnd = GameState.CONTINUE;
    private static PlayingField playingField = new PlayingField();
    public static GameMode mode;

    private static Map<String, String> mySettings;

    public static void main(String[] args) throws InterruptedException {
        InputParam param = new InputParam();
        JCommander.newBuilder()
                .addObject(param)
                .build()
                .parse(args);
        try {
            param.CheckInputParam();
        } catch (IllegalParametersException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        mode = param.mode;
        Settings set = new Settings(mode);
        try {
            mySettings = set.getSettings();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        playingField.FillPlayingField(param.fieldSize, param.enemyNumber, param.wallCount, mySettings);
        GameLogic gameLogic = new GameLogic(playingField, sc);
        boolean playerHasMove = gameLogic.IfPlayerHasMove();
        boolean goalAvailable = gameLogic.IfGoalAvailable();
        while(!(playerHasMove && goalAvailable)) {
            playingField.FillPlayingField(param.fieldSize, param.enemyNumber, param.wallCount, mySettings);
            gameLogic = new GameLogic(playingField, sc);
            playerHasMove = gameLogic.IfPlayerHasMove();
            goalAvailable = gameLogic.IfGoalAvailable();
        }
        CleanTerminal();
        playingField.PrintPlayingField();

        while(isEnd == GameState.CONTINUE) {
            KeyPressed userChoice = PressEvent.onePress(sc);
            if(userChoice == KeyPressed.EXIT) {
                isEnd = GameState.LOOSE;
            } else if (userChoice != KeyPressed.NOT_CORRECT && userChoice != KeyPressed.CONFIRM) {
                isEnd = gameLogic.OneRound(userChoice);
                CleanTerminal();
                playingField.PrintPlayingField();
            }
        }
        sc.close();
        if(isEnd == GameState.WIN) {
            System.out.println("\n\nWOW, you WIN!!!");
        } else {
            System.out.println("\n\nYou loose((( Try again");
        }

    }
    public static void CleanTerminal() {
        if(mode != GameMode.DEVELOPMENT) {
            System.out.print("\033[H\033[J");
            System.out.flush();
        }
    }

}