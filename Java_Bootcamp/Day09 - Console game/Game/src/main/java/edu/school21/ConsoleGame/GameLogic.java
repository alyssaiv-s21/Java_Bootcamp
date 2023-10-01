package edu.school21.ConsoleGame;

import edu.school21.ConsoleGame.unit.Point;
import edu.school21.ConsoleGame.unit.Unit;
import edu.school21.ConsoleGame.unit.UnitType;
import edu.school21.chaselogic.ChaseLogic;

import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {

    private PlayingField playingField;
    private Unit[][] field;
    private Unit player;

    private Unit goal;
    private ArrayList<Unit> enemyList;

    private Scanner sc;

    private int size;
    public GameLogic(PlayingField playingField, Scanner sc) {
        this.sc = sc;
        this.playingField = playingField;
        field = playingField.getPlayingField();
        player = playingField.getPlayer();
        enemyList = playingField.getEnemyList();
        goal = playingField.getGoal();
        size = field.length;
    }
    public GameState OneRound(KeyPressed userDir) throws InterruptedException {
        if(IfPossiblePlayerMove(userDir)) {
            GameState playerResult = PlayerMove(userDir);
            if(playerResult == GameState.CONTINUE) {
                for(Unit enemy : enemyList) {
                    GameState enemyResult = EnemyMove(enemy);
                    Thread.sleep(100);
                    Game.CleanTerminal();
                    playingField.PrintPlayingField();
                    if(Game.mode == GameMode.DEVELOPMENT) {
                        KeyPressed devConfirmation = KeyPressed.UP;
                        while(devConfirmation != KeyPressed.CONFIRM) {
                            devConfirmation = PressEvent.onePress(sc);
                        }
                    }
                    if(enemyResult != GameState.CONTINUE) {
                        return enemyResult;
                    }
                }
                if(IfPlayerHasMove()) {
                    return GameState.CONTINUE;
                } else {
                    return GameState.LOOSE;
                }
            }
            return playerResult;
        }
        return GameState.CONTINUE;
    }

    public GameState EnemyMove(Unit enemy) {
        int[][] intField = FieldToIntArray();
        int[] coord = ChaseLogic.ChaseLogic(intField, enemy.getRow(), enemy.getColumn(), player.getRow(), player.getColumn());
        if(coord[0] == player.getRow() && coord[1] == player.getColumn()) {
            return GameState.LOOSE;
        } else if (field[coord[0]][coord[1]].getType() == UnitType.EMPTY) {
            field[enemy.getRow()][enemy.getColumn()] = playingField.CreateEmptyCell();
            enemy.SetLocation(coord[0], coord[1]);
            field[coord[0]][coord[1]] = enemy;
            return GameState.CONTINUE;
        }
        return GameState.CONTINUE;
    }


    private int[][] FieldToIntArray() {
        int[][] result = new int[size][size];
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                if(field[i][j].getType() == UnitType.PLAYER) {
                    result[i][j] = 2;
                } else if (field[i][j].getType() == UnitType.EMPTY) {
                    result[i][j] = 0;
                } else {
                    result[i][j] = 1;
                }
            }
        }
        return result;
    }


    public boolean IfPlayerHasMove() {
        int row = player.getRow();
        int column = player.getColumn();
        int possibleDir = 0;
        if(row + 1 < size && (field[row + 1][column].getType() == UnitType.EMPTY
                ||  field[row + 1][column].getType() == UnitType.GOAL)) {
            ++possibleDir;
        }
        if (row - 1 >= 0 && (field[row - 1][column].getType() == UnitType.EMPTY
                ||  field[row - 1][column].getType() == UnitType.GOAL)) {
            ++possibleDir;
        }
        if(column + 1 < size && (field[row][column+1].getType() == UnitType.EMPTY
                ||  field[row][column + 1].getType() == UnitType.GOAL)) {
            ++possibleDir;
        }
        if(column - 1 >= 0 && (field[row][column - 1].getType() == UnitType.EMPTY
                ||  field[row][column - 1].getType() == UnitType.GOAL)) {
            ++possibleDir;
        }
        return possibleDir > 0;
    }

    public boolean IfGoalAvailable() {
        int row = goal.getRow();
        int column = goal.getColumn();
        int possibleDir = 0;
        if(row + 1 < size && (field[row + 1][column].getType() == UnitType.EMPTY
                ||  field[row + 1][column].getType() == UnitType.PLAYER)) {
            ++possibleDir;
        }
        if (row - 1 >= 0 && (field[row - 1][column].getType() == UnitType.EMPTY
                ||  field[row - 1][column].getType() == UnitType.PLAYER)) {
            ++possibleDir;
        }
        if(column + 1 < size && (field[row][column+1].getType() == UnitType.EMPTY
                ||  field[row][column + 1].getType() == UnitType.PLAYER)) {
            ++possibleDir;
        }
        if(column - 1 >= 0 && (field[row][column - 1].getType() == UnitType.EMPTY
                ||  field[row][column - 1].getType() == UnitType.PLAYER)) {
            ++possibleDir;
        }
        return possibleDir > 0;
    }

    public GameState PlayerMove(KeyPressed userDir) {
        Point newPosition = player.getNewLocation(userDir);
        int newRow = newPosition.getRow();
        int newColumn = newPosition.getColumn();
        if(field[newRow][newColumn].getType() == UnitType.ENEMY) {
            return GameState.LOOSE;
        }
        if(field[newRow][newColumn].getType() == UnitType.GOAL) {
            return GameState.WIN;
        }
        field[player.getRow()][player.getColumn()] = playingField.CreateEmptyCell();
        player.SetLocation(newRow, newColumn);
        field[newRow][newColumn] = player;
        return GameState.CONTINUE;
    }

    public boolean IfPossiblePlayerMove(KeyPressed userDir) {
        //Point currentPosition = player.getLocation();
        Point newPosition = player.getNewLocation(userDir);
        int newRow = newPosition.getRow();
        int newColumn = newPosition.getColumn();
        if(newRow <0 || newColumn <0 || newRow == size || newColumn == size) {
            return false;
        }
        if(field[newRow][newColumn].getType() == UnitType.WALL) {
            return false;
        }
        return true;
    }




}
