import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    char [][] lvl;
    ArrayList<CharacterInPlay> charactersList;
    CharacterInPlay playerOne;
    JFrame frame;
    JTextArea text;
    JTextField field;
    Level levelOne;
    int numberHeroe = 0;



    public static void main(String[] args){
        Main playGame = new Main();
        playGame.play();

    }

    public void play() {
        charactersList = new ArrayList<CharacterInPlay>();
        charactersList.add(new CharacterInPlay(1,1, 'q'));
        charactersList.add(new CharacterInPlay(2,2, 'w'));
        playerOne = charactersList.get(0);
        levelOne = new Level();
        lvl = levelOne.getLvl();

        CharacterInPlay playerPrint;
        for(int i=0; i<charactersList.size();i++){
            playerPrint=charactersList.get(i);
            lvl[playerPrint.getCoordinatesX()][playerPrint.getCoordinatesY()]=playerPrint.getNameOnTable();
        }

        createGui();
        printLvl();


    }

    public boolean checkCubs(){
        return false;
    }

    public void printLvl(){

        text.setText("");

        for(int i=0;i<8;i++)
        {
            for(int j=0;j<9;j++){
                String str = Character.toString(lvl[i][j]);
                text.append(Character.toString(lvl[i][j]));
            }
            text.append("\n");
        }


    }


    void createGui(){
        frame = new JFrame("Dark Temple");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background = new JPanel();
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        field = new JTextField("");

        text = new JTextArea(10,20);
        JScrollPane scrroller = new JScrollPane(text);
        text.setLineWrap(true);

        scrroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        Box buttonBox1 = new Box(BoxLayout.X_AXIS);
        Box buttonBox2 = new Box(BoxLayout.X_AXIS);
        Box buttonBox3 = new Box(BoxLayout.X_AXIS);

        JButton buttonVVL = new JButton("\\");
        buttonBox1.add(buttonVVL);
        buttonVVL.addActionListener(new ButtonVVLListener(-1,-1));

        JButton buttonVV = new JButton("^");
        buttonBox1.add(buttonVV);
        buttonVV.addActionListener(new ButtonVVListener(-1,0));

        JButton buttonVVP = new JButton("/");
        buttonBox1.add(buttonVVP);
        buttonVVP.addActionListener(new ButtonVVPListener(-1,1));

        JButton buttonL = new JButton("<");
        buttonBox2.add(buttonL);
        buttonL.addActionListener(new ButtonLListener(0,-1));

        JButton buttonNull = new JButton(" ");
        buttonBox2.add(buttonNull);
        buttonNull.addActionListener(new ButtonNullListener());

        JButton buttonP = new JButton(">");
        buttonBox2.add(buttonP);
        buttonP.addActionListener(new ButtonPListener(0,1));

        JButton buttonVNL = new JButton("/");
        buttonBox3.add(buttonVNL);
        buttonVNL.addActionListener(new ButtonVNLListener(1,-1));

        JButton buttonVN = new JButton("v");
        buttonBox3.add(buttonVN);
        buttonVN.addActionListener(new ButtonVNListener(1,0));

        JButton buttonVNP = new JButton("\\");
        buttonBox3.add(buttonVNP);
        buttonVNP.addActionListener(new ButtonVNPListener(1,1));

        JButton nextStep = new JButton("Next Step");
        buttonBox.add(nextStep);
        nextStep.addActionListener(new ButtonNextStepListener());

        JButton buttonAttack = new JButton("Attack");
        buttonBox.add(buttonAttack);
        buttonAttack.addActionListener(new ButtonAttackListener());


        buttonBox.add(buttonBox1);
        buttonBox.add(buttonBox2);
        buttonBox.add(buttonBox3);


        background.add(BorderLayout.EAST,buttonBox);
        frame.getContentPane().add(background);
        frame.getContentPane().add(BorderLayout.SOUTH,field);
        frame.getContentPane().add(BorderLayout.WEST, scrroller);
        frame.setBounds(50,50,300,300);
        frame.pack();
        frame.setVisible(true);
    }



    public class ButtonAttackListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            CharacterInPlay charFound;
            int [] coord={0,0};
            String str = "";
            String mes = field.getText();
            int j=0;

            String[] stringCoord = mes.split(",");
            coord[0] = Integer.parseInt(stringCoord[0]);
            coord[1] = Integer.parseInt(stringCoord[1]);
            

            if(Math.abs(coord[0]-playerOne.getCoordinatesX())>1 ||  Math.abs(coord[1]-playerOne.getCoordinatesY())>1){
                text.append("Не хватает дистанции\n");
            }else if(lvl[coord[0]][coord[1]]=='0'){
                text.append("Тут никого нет\n");
            }else if(lvl[coord[0]][coord[1]]=='9'){
                text.append("Ну и зачем ты стены бьешь?\n");
            }else{
                for(int i =0; i<lvl.length;i++){
                    charFound = charactersList.get(i);
                    if(lvl[coord[0]][coord[1]]==charFound.getNameOnTable()){
                        text.append("Вы ударили игрока "+charFound.getNameOnTable()+"\n");
                        field.setText("");
                        break;
                    }
                }
            }
        }
    }

    public class ButtonNextStepListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            if (charactersList.size() > numberHeroe) {
                numberHeroe++;
                playerOne = charactersList.get(numberHeroe);
            } else {
                numberHeroe = 0;
                playerOne = charactersList.get(numberHeroe);
            }
        }
    }

    public void moveHeroes(int x,int y){
        if(lvl[playerOne.getCoordinatesX()+x][playerOne.getCoordinatesY()+y]!='0'){
            text.append("Туда прохода нет\n");
        }else if(playerOne.getMove()>0){
            lvl[playerOne.getCoordinatesX()][playerOne.getCoordinatesY()]='0';
            playerOne.setCoordinatesXY(x,y);
            lvl[playerOne.getCoordinatesX()][playerOne.getCoordinatesY()]= playerOne.getNameOnTable();
            playerOne.moveDown();
            printLvl();
        }
        else{
            text.append("Не хватает очков движения\n");
        }

    }

    public class ButtonVVLListener implements ActionListener{

        int x;
        int y;
        ButtonVVLListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonLListener implements ActionListener{

            int x;
            int y;
            ButtonLListener(int xx, int yy){
                x=xx;
                y=yy;
            }
            public void actionPerformed(ActionEvent a){
                moveHeroes(x,y);
            }

    }

    public class ButtonVNLListener implements ActionListener{
        int x;
        int y;
        ButtonVNLListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonVVListener implements ActionListener{
        int x;
        int y;
        ButtonVVListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonNullListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            playerOne.setMove(playerOne.getMoveMax());
        }
    }

    public class ButtonVNListener implements ActionListener{
        int x;
        int y;
        ButtonVNListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonVVPListener implements ActionListener{
        int x;
        int y;
        ButtonVVPListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonPListener implements ActionListener{
        int x;
        int y;
        ButtonPListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }

    public class ButtonVNPListener implements ActionListener{
        int x;
        int y;
        ButtonVNPListener(int xx, int yy){
            x=xx;
            y=yy;
        }
        public void actionPerformed(ActionEvent a){
            moveHeroes(x,y);
        }
    }
}
