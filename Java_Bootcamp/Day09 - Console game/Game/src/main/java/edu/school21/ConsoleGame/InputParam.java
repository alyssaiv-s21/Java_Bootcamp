package edu.school21.ConsoleGame;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class InputParam {
    @Parameter(names = "--enemiesCount", required = true)
    public int enemyNumber;

    @Parameter(names = "--wallsCount", required = true)
    public int wallCount;

    @Parameter(names = "--size", required = true)
    public int fieldSize;

    @Parameter(names = "--profile", required = true)
    public GameMode mode;

    public void CheckInputParam() throws IllegalParametersException{
        if(enemyNumber < 0)  {
            throw  new IllegalParametersException("Enemy number cant be negative");
        }
        if(wallCount < 0) {
            throw new IllegalParametersException("Wall count cant be negative");
        }
        if(fieldSize < 2) {
            throw new IllegalParametersException("Size must be more than 1");
        }
        if((enemyNumber + wallCount) >= fieldSize*fieldSize/3) {
            throw new IllegalParametersException("Too many walls and enemies, please increase field size");
        }
    }


    // добавить валидацию этих полей - только целые числа больше нуля, враги + препятствия меньше размер*размер минус 3
}
