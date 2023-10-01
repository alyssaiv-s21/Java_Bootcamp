package edu.school21.chaselogic;

public class ChaseLogic {

    public static int[] ChaseLogic(int[][] playingField, int row, int column, int rowGoal, int columnGoal) {
        int[] result = new int[] {row, column};
        if(!(row + 1 == playingField.length) && !(playingField[row+1][column] == 1)) {
            if (playingField[row + 1][column] == 2) {
                result[0] = row + 1;
                result[1] = column;
                return result;
            } else {
                if (row < rowGoal) {
                    result[0] = row + 1;
                    result[1] = column;
                }
            }
        }
        if(!(row - 1 == -1) && !(playingField[row-1][column] == 1)) {
            if (playingField[row - 1][column] == 2) {
                result[0] = row - 1;
                result[1] = column;
                return result;
            } else {
                if (row > rowGoal) {
                    result[0] = row - 1;
                    result[1] = column;
                }
            }
        }
        if(!(column + 1 == playingField.length) && !(playingField[row][column + 1] == 1)) {
            if (playingField[row][column +1] == 2) {
                result[0] = row;
                result[1] = column + 1;
                return result;
            } else {
                if(column < columnGoal) {
                    result[0] = row;
                    result[1] = column + 1;
                }
            }
        }
        if(!(column -1 == -1) && !(playingField[row][column-1] == 1)) {

            if (playingField[row][column - 1] == 2) {
                result[0] = row;
                result[1] = column - 1;
                return result;
            } else {
                if (column > columnGoal) {
                    result[0] = row;
                    result[1] = column - 1;
                }
            }
        }

        return result;
    }
}
