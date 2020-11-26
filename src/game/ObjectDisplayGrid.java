package game;

import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    private static AsciiPanel terminal;
    private Char[][] objectGrid = null;
    private Char[][] finalGrid = null;
    private Char[][] bottomGrid = null;
    private Char[] topGrid = null;
    private int[] playerCoords = null;
    private List<InputObserver> inputObservers = null;
    public List<Room> rooms;
    public List<Char> monsterChars = new ArrayList<>();
    public List<Passage> passages;
    public List<Item> droppedItems = new ArrayList<>();
    public List<Char> displayableChars = new ArrayList<>();
    private Player player;
    private static int height;
    private static int width;
    private static int topHeight;
    private static int gameHeight;
    private static int bottomHeight;
    private char lastChar;

    public void setTopMessageHeight(int topHeight){
        System.out.println("ObjectDisplayGrid (setTopMessageHeight)");
    }

    public ObjectDisplayGrid(RogueXMLHandler handler) {
        width = handler.width;
        topHeight = handler.topHeight;
        bottomHeight = handler.bottomHeight;
        height = handler.gameHeight + handler.topHeight + handler.bottomHeight;
        gameHeight = handler.gameHeight;
        terminal = new AsciiPanel(width, height);
        rooms = handler.rooms;
        passages = handler.passages;
        objectGrid = new Char[width][gameHeight];
        bottomGrid = new Char[width][bottomHeight];
        finalGrid = new Char[width][height];
        playerCoords = new int[2];
        player = handler.player;
        topGrid = new Char[width];
        setTopGrid();
        displayableChars.add(new Char('X'));
        displayableChars.add(new Char('#'));
        displayableChars.add(new Char('+'));
        displayableChars.add(new Char('.'));
        displayableChars.add(new Char('T'));
        displayableChars.add(new Char('S'));
        displayableChars.add(new Char('H'));
        displayableChars.add(new Char('@'));
        displayableChars.add(new Char(')'));
        displayableChars.add(new Char(']'));
        displayableChars.add(new Char('?'));

        //find player location
        for (Room r : rooms) {
            if (r.player != null) {
                playerCoords[0] = r.posX + r.player.posX;
                playerCoords[1] = r.posY + r.player.posY;
                r.player = null;
            }
        }
        resetBottom();
        setObjectGrid();
        initializeDisplay();
        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // super.repaint();
        // terminal.repaint( );
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }

    private void setObjectGrid() {
        //initialize dungeon display
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                objectGrid[i][j] = new Char(' ');
            }
        }
        //displays rooms - walls, interior, then monsters and items
        for (Room r : rooms) {
            for (int i = r.posX; i < r.posX + r.width; i++) {
                for (int j = r.posY; j < r.posY + r.height; j++) {
                    if (j == r.posY || j == r.posY + r.height - 1 || i == r.posX || i == r.posX + r.width - 1) {
                        objectGrid[i][j] = new Char('X');
                    }
                    else {
                        objectGrid[i][j] = new Char('.');
                    }
                }
            }
            for (Monster monster : r.monsters) {
                objectGrid[r.posX + monster.posX][r.posY + monster.posY] = monster.aChar;
                if (monsterChars.contains(monster.aChar) != true) {
                    monsterChars.add(monster.aChar);
                }
            }
            for (Item item : r.items) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }
        //displays passages, determining whether to go up, down, left, or right, and whether door or passage
        for (Passage p : passages) {
            for (int index = 0; index < p.xCoords.size() - 1; index++) {
                if (p.xCoords.get(index) == p.xCoords.get(index + 1)) {
                    if (p.yCoords.get(index) < p.yCoords.get(index + 1)) {
                        for (int y = p.yCoords.get(index); y <= p.yCoords.get(index + 1); y++) {
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = new Char('+');
                            } else {
                                objectGrid[p.xCoords.get(index)][y] = new Char('#');
                            }
                        }
                    } else {
                        for (int y = p.yCoords.get(index); y >= p.yCoords.get(index + 1); y--) {
                            if (objectGrid[p.xCoords.get(index)][y].getChar() == 'X' || objectGrid[p.xCoords.get(index)][y].getChar() == '+') {
                                objectGrid[p.xCoords.get(index)][y] = new Char('+');
                            } else {
                                objectGrid[p.xCoords.get(index)][y] = new Char('#');
                            }
                        }
                    }
                } else {
                    if (p.xCoords.get(index) < p.xCoords.get(index + 1)) {
                        for (int x = p.xCoords.get(index); x <= p.xCoords.get(index + 1); x++) {
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = new Char('+');
                            } else {
                                objectGrid[x][p.yCoords.get(index)] = new Char('#');
                            }
                        }
                    } else {
                        for (int x = p.xCoords.get(index); x >= p.xCoords.get(index + 1); x--) {
                            if (objectGrid[x][p.yCoords.get(index)].getChar() == 'X' || objectGrid[x][p.yCoords.get(index)].getChar() == '+') {
                                objectGrid[x][p.yCoords.get(index)] = new Char('+');
                            } else {
                                objectGrid[x][p.yCoords.get(index)] = new Char('#');
                            }
                        }
                    }
                }
            }
        }
        //display all dropped items wherever they were dropped
        for (Item item: droppedItems) {
            objectGrid[item.posX][item.posY] = item.aChar;
        }
        //display player icon
        objectGrid[playerCoords[0]][playerCoords[1]] = player.aChar;
    }

    private void resetTop() {
        //set all Chars of bottomGrid to ' '
        for (int i = 0; i < width; i++) {
                topGrid[i] = new Char(' ');
        }
    }

    private void setTopGrid(){
        String hpRemaining = "GAME OVER";
        if (player.hp > 0) {
            hpRemaining = "Player HP: " + player.hp;
        }
        for (int i = 0; i < width; i++){
            if (i < hpRemaining.length()) {
                topGrid[i] = new Char(hpRemaining.charAt(i));
            }
            else {
                topGrid[i] = new Char(' ');
            }
        }
    }

    private void checkForMonster() {
        //check if moved to monster
        Monster temp = null;
        for (Room r : rooms) {
            for (Monster m : r.monsters) {
                if (playerCoords[0] == m.posX + r.posX && playerCoords[1] == m.posY + r.posY) {
                    temp = m;
                }
            }
            if (temp != null) {
                attackMonster(r, temp);
                temp = null;
            }
        }
    }

    private void attackMonster(Room room, Monster monster) {
        Random rand = new Random();
        int attack = rand.nextInt(player.maxHit + 1);
        if (player.sword != null) {
            attack = attack + player.sword.intValue;
        }
        /*
        call function that updates bottom display with attack, defend
         */
        resetBottom();
        monster.setHp(monster.hp - attack);
        if (monster.hp <= 0) {
            room.monsters.remove(monster);
            displayAttack(attack, -1);
        }
        else {
            int defend = rand.nextInt(monster.maxHit + 1);
            displayAttack(attack, defend);
            if (player.armor != null) {
                if (defend > player.armor.intValue) {
                    defend -= player.armor.intValue;
                }
                else {
                    defend = 0;
                }
            }
            player.setHp(player.hp - defend);
            resetTop();
            setTopGrid();
            if (player.hp <= 0) {
                gameOver();
            }
        }
    }

    private void displayAttack(int attack, int defend) {
        //attack is the damage the player does to monster, defend is damage monster does to player
        String monHit = "";
        if (defend != -1) {
            monHit = "The monster hit you for " + defend + " hitpoints!";
        }
        String playHit = "You hit the monster for " + attack + " hitpoints!";

        for (int i = 0; i < monHit.length(); i++){
            bottomGrid[i][1] = new Char(monHit.charAt(i));
        }
        for (int i = 0; i < playHit.length(); i++){
            bottomGrid[i][0] = new Char(playHit.charAt(i));
        }
    }

    private void resetBottom() {
        //set all Chars of bottomGrid to ' '
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < bottomHeight; j++)
            bottomGrid[i][j] = new Char(' ');
        }
    }

    private void displayPack() {
        //display contents of pack in bottom grid
        for (Item item : player.pack) {
            String str = item.serial + " - " + item.name + " ";
            for (int i = 0; i < str.length(); i++) {
                bottomGrid[i][player.pack.indexOf(item)] = new Char(str.charAt(i));
            }
        }
    }

    private void gameOver() {
        System.out.println("GAME OVER");
    }

    private void checkForItem() {
        Item tempItem = null;
        for (Item i : droppedItems) {
            if (playerCoords[0] == i.posX && playerCoords[1] == i.posY) {
                tempItem = i;
            }
        }
        if (tempItem != null) {
            player.pack.add(tempItem);
            tempItem.setOwner(player);
            droppedItems.remove(tempItem);
        }
        else {
            for (Room r : rooms) {
                for (Item i : r.items) {
                    if (playerCoords[0] == i.posX + r.posX && playerCoords[1] == i.posY + r.posY) {
                        tempItem = i;
                    }
                }
                if (tempItem != null) {
                    pickUpItem(r, tempItem);
                    tempItem = null;
                }
            }
        }
    }

    private void pickUpItem(Room r, Item i) {
        //remove item from objectGrid, room
        r.items.remove(i);
        //objectGrid[r.posX + i.posX][r.posY + i.posY] = new Char('.');
        /*for (Item item : r.items) {
            if (item.posX == i.posX && item.posY == i.posY) {
                objectGrid[r.posX + item.posX][r.posY + item.posY] = item.aChar;
            }
        }*/
        //add item to pack, set owner to player
        i.setOwner(player);
        player.addItem(i);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = e;
        if (keypress.getKeyChar() == 'h' || keypress.getKeyChar() == 'j' || keypress.getKeyChar() == 'k' || keypress.getKeyChar() == 'l') {
            if (keypress.getKeyChar() == 'h') {
                playerCoords[0]--;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[0]++;
                }
            } else if (keypress.getKeyChar() == 'j') {
                playerCoords[1]++;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[1]--;
                }
            } else if (keypress.getKeyChar() == 'k') {
                playerCoords[1]--;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[1]++;
                }
            } else if (keypress.getKeyChar() == 'l') {
                playerCoords[0]++;
                checkForMonster();
                if (objectGrid[playerCoords[0]][playerCoords[1]].getChar() == 'X' || objectGrid[playerCoords[0]][playerCoords[1]].getChar() == ' ' || monsterChars.contains(objectGrid[playerCoords[0]][playerCoords[1]])) {
                    playerCoords[0]--;
                }
            }
            player.totalMoves += 1;
            if (player.totalMoves % player.hpMoves == 0) {
                player.hp += 1;
                resetTop();
                setTopGrid();
            }
            if (player.hallucinate != null) {
                if (player.hallucinate.duration <= player.totalMoves - player.hallucinate.startMoves) {
                    player.hallucinate = null;
                }
            }
        }
        else if (keypress.getKeyChar() == 'p'){
            checkForItem();
        }
        else if (keypress.getKeyChar() == 'd'){
            //removes LAST item from pack, updates location and adds to dropped items
            //can drop anywhere player can go - including on monsters, which will probably need to be changed
            if (player.pack.size() > 0) {
                Item dropped = player.pack.get(player.pack.size() - 1);
                dropped.posX = playerCoords[0];
                dropped.posY = playerCoords[1];
                droppedItems.add(dropped);
                dropped.setOwner(null);
                player.pack.remove(dropped);
            }
        }
        else if (keypress.getKeyChar() == 'i'){
            //displays pack in bottom display
            resetBottom();
            displayPack();
        }
        else if (keypress.getKeyChar() == 'r'){
            //item actions of scroll execute
        }
        else if (keypress.getKeyChar() == 'T'){
            //modify player damage based on whether they are wielding sword
        }
        else if (keypress.getKeyChar() == 'w'){
            //modify player hp based on whether they are wearing armor
        }
        else if (keypress.getKeyChar() == 'c'){
            if (player.armor == null) {
                resetBottom();
                String errorMessage = "Player is not currently wearing armor.";
                for (int i = 0; i < errorMessage.length(); i++){
                    bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                }
            }
            else {
                player.pack.add(player.armor);
                player.armor = null;
            }
            //if no armor on then display message
        }
        else if (keypress.getKeyChar() == '1' || keypress.getKeyChar() == '2' || keypress.getKeyChar() == '3' || keypress.getKeyChar() == '4' || keypress.getKeyChar() == '5' || keypress.getKeyChar() == '6' || keypress.getKeyChar() == '7' || keypress.getKeyChar() == '8' || keypress.getKeyChar() == '9') {
            int index = Character.getNumericValue(keypress.getKeyChar()) - 1;
            Item packItem = null;
            if (lastChar == 'H') {
                //ADD HELP COMMAND
            }
            else if (lastChar == 'w') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Armor)) {
                    resetBottom();
                    String errorMessage = "Item is not wearable or does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else if (player.armor != null) {
                    resetBottom();
                    String errorMessage = "Player is already wearing armor. Press 'c' to change.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    player.pack.remove(packItem);
                    player.setArmor(packItem);
                }
            }
            else if (lastChar == 'r') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Scroll)) {
                    resetBottom();
                    String errorMessage = "Item is not readable. Select a scroll to read.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    for (Action action : packItem.actions) {
                        if (action.actionCharValue == 'a') {
                            String message;
                            if (player.armor == null) {
                                message = "No armor being worn, scroll unable to curse item.";
                            } else {
                                player.armor.intValue -= action.actionIntValue;
                                if (player.armor.intValue < 0) {
                                    player.armor.intValue = 0;
                                }
                                message = "Armor cursed! " + action.actionIntValue + " taken from its effectiveness.";
                            }
                            resetBottom();
                            for (int i = 0; i < message.length(); i++) {
                                bottomGrid[i][1] = new Char(message.charAt(i));
                            }
                        } else if (action.actionCharValue == 'w') {
                            String message;
                            if (player.sword == null) {
                                message = "No sword being yielded, scroll unable to curse item.";
                            } else {
                                player.sword.intValue -= action.actionIntValue;
                                if (player.sword.intValue < 0) {
                                    player.sword.intValue = 0;
                                }
                                message = "Sword cursed! " + action.actionIntValue + " taken from its effectiveness.";
                            }
                            resetBottom();
                            for (int i = 0; i < message.length(); i++) {
                                bottomGrid[i][1] = new Char(message.charAt(i));
                            }
                        }
                        else {
                            //HALLUCINATE CODE
                            player.hallucinate = new Hallucinate(player, action);
                        }
                    }
                    player.pack.remove(packItem);
                }
            }
            else if (lastChar == 'T') {
                try {
                    packItem = player.pack.get(index);
                }
                catch(Exception ex) {
                    resetBottom();
                    String errorMessage = "Item does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                if (packItem == null || !(packItem instanceof Sword)) {
                    resetBottom();
                    String errorMessage = "Item is not a weapon or does not exist.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else if (player.sword != null) {
                    resetBottom();
                    String errorMessage = "Player is already wielding a sword. Drop it to wield a different one.";
                    for (int i = 0; i < errorMessage.length(); i++){
                        bottomGrid[i][1] = new Char(errorMessage.charAt(i));
                    }
                }
                else {
                    player.setWeapon(packItem);
                }
            }
        }

        notifyInputObservers(keypress.getKeyChar());
        setObjectGrid();
        initializeDisplay();
        lastChar = keypress.getKeyChar();
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }

    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        for (int i = 0; i < width; i++){
            addObjectToDisplay(topGrid[i], i, 0);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < gameHeight; j++) {
                if (player.hallucinate != null) {
                    if (objectGrid[i][j].getChar() != ' '){
                        int rnd = new Random().nextInt(displayableChars.size());
                        addObjectToDisplay(displayableChars.get(rnd), i, j + topHeight);
                    }
                }
                else {
                    addObjectToDisplay(objectGrid[i][j], i, j + topHeight);
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < bottomHeight; j++){
                addObjectToDisplay(bottomGrid[i][j], i, j + topHeight + gameHeight);
            }
        }
        terminal.repaint();
    }

    public void addObjectToDisplay(Char ch, int x, int y) {
        if ((0 <= x) && (x < finalGrid.length)) {
            if ((0 <= y) && (y < finalGrid[0].length)) {
                finalGrid[x][y] = ch;
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = finalGrid[x][y].getChar();
        terminal.write(ch, x, y);
        terminal.repaint();
    }
}
