import java.util.ArrayList;

public class CharacterInPlay {
    private
    int coordinatesX = 1;
    int coordinatesY = 1;
    int healPoint = 10;
    int moveMax = 10;
    int move = moveMax;
    int action = 4;
    char nameOnTable ='1';
    ArrayList<Integer> cubePower = new ArrayList<Integer>();


    CharacterInPlay(int x, int y,char c){
        coordinatesX= x;
        coordinatesY = y;
        nameOnTable = c;
        reRollCubePower(4);
    }

    public ArrayList<Integer> getCubePower(){
        return cubePower;
    }

    public void reRollCubePower(int colvo){
        for (int i = 0; i<colvo; i++){
            cubePower.add(((int)(Math.random() * 10)) % 6+1);
        }
    }

    public char getNameOnTable(){
        return nameOnTable;
    }

    public int getMoveMax(){
        return moveMax;
    }

    public int getCoordinatesX(){
        return coordinatesX;
    }

    public int getCoordinatesY(){
        return coordinatesY;
    }

    public int getHealPoint(){
        return healPoint;
    }

    public int getMove(){
        return move;
    }

    public void moveDown(){
        move--;
    }

    public void setCoordinatesX(int x){
        coordinatesX += x;
    }

    public void setCoordinatesY(int y){
        coordinatesY += y;
    }

    public void setCoordinatesXY(int x, int y){
        coordinatesX += x;
        coordinatesY += y;
    }


    public void setHealPoint(int h){
        healPoint = h;
    }

    public void setMove(int m){
        move = m;
    }

}
