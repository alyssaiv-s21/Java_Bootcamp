package edu.school21.ConsoleGame;

import edu.school21.ConsoleGame.unit.*;
import java.util.*;

public class PlayingField {

    private Map<String, String> settings;
//    = new HashMap<>() {{
//        put("enemy.char", "X");
//        put("player.char", "o");
//        put("wall.char", "#");
//        put("goal.char", "O");
//        put("empty.char", " ");
//        put("enemy.color", "RED");
//        put("player.color", "GREEN");
//        put("wall.color", "MAGENTA");
//        put("goal.color", "BLUE");
//        put("empty.color", "YELLOW");
//    }};
    private Unit[][] playingField = null;
    private int size = 0;

    private Unit player;

    private Unit goal;
    private ArrayList<Unit> enemyList = new ArrayList<>();
    public void FillPlayingField(int fieldSize, int enemyNumber, int wallCount, Map<String, String> settings) {
        this.settings = settings;
        playingField = new Unit[fieldSize][fieldSize];
        size = fieldSize;
        List<Unit> list = new ArrayList<>();
        player = new Unit(UnitType.PLAYER, settings.get("player.char"), settings.get("player.color"));
        list.add(player);
        goal = new Unit(UnitType.GOAL, settings.get("goal.char"), settings.get("goal.color"));
        list.add(goal);
        for(int i = 0; i < enemyNumber; ++i) {
            Unit enemy = new Unit(UnitType.ENEMY, settings.get("enemy.char"), settings.get("enemy.color"));
            list.add(enemy);
            enemyList.add(enemy);
        }
        for(int i = 0; i < wallCount; ++i) {
            list.add(new Unit(UnitType.WALL, settings.get("wall.char"), settings.get("wall.color")));
        }
        int emptyCell = fieldSize * fieldSize - 2 - enemyNumber - wallCount;
        for(int i = 0; i < emptyCell; ++i) {
            list.add(new Unit(UnitType.EMPTY, settings.get("empty.char"), settings.get("empty.color")));
        }
        Collections.shuffle(list);
        int count = 0;
        for(int i = 0; i < fieldSize; ++i) {
            for(int j = 0; j < fieldSize; ++j) {
                playingField[i][j] = list.get(count);
                playingField[i][j].SetLocation(i, j);
                ++count;
            }
        }
    }
    public void PrintPlayingField() {
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                playingField[i][j].printUnit();
            }
            System.out.println(" ");
        }
    }
    public Unit[][] getPlayingField() {
        return playingField;
    }
    public Unit getPlayer() {
        return player;
    }
    public ArrayList<Unit> getEnemyList() {
        return enemyList;
    }
    public Unit getGoal() {
        return goal;
    }
    public Unit CreateEmptyCell() {
        return new Unit(UnitType.EMPTY, settings.get("empty.char"), settings.get("empty.color"));
    }
}
