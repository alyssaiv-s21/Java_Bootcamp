package edu.school21.ConsoleGame.unit;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import edu.school21.ConsoleGame.KeyPressed;

public class Unit {

    private UnitType type;
    private String symbol;
    private String color;

    private Point location = new Point(0, 0);

    public Unit(UnitType type, String symbol, String color) {
        this.type = type;
        this.symbol = symbol;
        this.color = color;
    }
    public void SetLocation(int row, int column) {
        location.setRow(row);
        location.setColumn(column);
    }
    public int getRow() {
        return location.getRow();
    }

    public int getColumn() {
        return location.getColumn();
    }
    public Point getLocation() { return location; }

    public UnitType getType() { return type; }
    public void printUnit() {
        ColoredPrinter oneCell = new ColoredPrinter.Builder(1, false)
                .background(BColor.valueOf(color.toUpperCase())).build();
        oneCell.print(symbol);
    }
    public Point getNewLocation(KeyPressed userDir) {
        Point newPosition = new Point(location.getRow(), location.getColumn());
        if(userDir == KeyPressed.UP) {
            newPosition.setRow(location.getRow() - 1);
        }
        if(userDir == KeyPressed.DOWN) {
            newPosition.setRow(location.getRow() + 1);
        }
        if(userDir == KeyPressed.RIGHT) {
            newPosition.setColumn(location.getColumn() - 1);
        }
        if(userDir == KeyPressed.LEFT) {
            newPosition.setColumn(location.getColumn() + 1);
        }
        return newPosition;
    }


}
