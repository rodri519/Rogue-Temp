package game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

//clean up code
//dungeon adding rooms/passages
//gray variables
//compare to example

public class RogueXMLHandler extends DefaultHandler{
    private StringBuilder data = null;

    private static final int DEBUG = 1;
    private static final String CLASSID = "StudentXMLHandler";

    private Dungeon dungeon;
    private ObjectDisplayGrid objectDisplayGrid;
    private String name;
    public int width;
    public int gameHeight;
    public int topHeight;
    public int bottomHeight;
    public Player player;

    public List<Room> rooms = new ArrayList<Room>();
    public List<Passage> passages = new ArrayList<Passage>();

    private Room roomBeingParsed = null;
    private Passage passageBeingParsed = null;
    private Creature creatureBeingParsed = null;
    private Item itemBeingParsed = null;
    private Action actionBeingParsed = null;

    private boolean bPosX = false;
    private boolean bPosY = false;
    private boolean bWidth = false;
    private boolean bHeight = false;
    private boolean bVisible = false;
    private boolean bHpMoves = false;
    private boolean bType = false;
    private boolean bHp = false;
    private boolean bItemIntValue = false;
    private boolean bActionCharValue = false;
    private boolean bActionIntValue = false;
    private boolean bMaxHit = false;
    private boolean bActionMessage = false;

    private void addRoom(Room room) {
        rooms.add(room);
    }
    private void addPassage(Passage passage) {
        passages.add(passage);
    }

    public RogueXMLHandler() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("Dungeon")) {
            name = attributes.getValue("name");
            width = Integer.parseInt(attributes.getValue("width"));
            topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            //objectDisplayGrid = new ObjectDisplayGrid(gameHeight, width);//, topHeight, bottomHeight);
            dungeon = new Dungeon(name, width, gameHeight);

        } else if (qName.equalsIgnoreCase("Rooms")) {
            //anything in here?? rooms = new Room[];
        } else if (qName.equalsIgnoreCase("Passages")) {
            //anything in here?? passages = new Passage[];
        } else if (qName.equalsIgnoreCase("Room")) {
            int r = Integer.parseInt(attributes.getValue("room"));
            Room room = new Room();
            room.setID(r);
            roomBeingParsed = room;
        } else if (qName.equalsIgnoreCase("Passage")) {
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(room1, room2);
            passageBeingParsed = passage;
        } else if (qName.equalsIgnoreCase("Monster")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Monster monster = new Monster();
            monster.setName(name);
            monster.setID(room, serial);
            creatureBeingParsed = monster;
            roomBeingParsed.setMonster(monster);

        } else if (qName.equalsIgnoreCase("Player")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            player = new Player();
            player.setName(name);
            player.setChar('@');
            player.setID(room, serial);
            creatureBeingParsed = player;
            roomBeingParsed.setPlayer(player);
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            CreatureAction creatureAction = new CreatureAction(name, type);
            actionBeingParsed = creatureAction;
        } else if (qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            ItemAction itemAction = new ItemAction(name, type);
            actionBeingParsed = itemAction;

        } else if (qName.equalsIgnoreCase("Scroll")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);
            scroll.setID(room, serial);
            scroll.setChar('?');
            scroll.setOwner(creatureBeingParsed);
            itemBeingParsed = scroll;
            if (creatureBeingParsed == null) {
                roomBeingParsed.setItem(scroll);
            }

        } else if (qName.equalsIgnoreCase("Armor")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);
            armor.setID(room, serial);
            armor.setChar(']');
            armor.setOwner(creatureBeingParsed);
            itemBeingParsed = armor;
            if (creatureBeingParsed == null) {
                roomBeingParsed.setItem(armor);
            }
        } else if (qName.equalsIgnoreCase("Sword")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);
            sword.setID(room, serial);
            sword.setChar(')');
            sword.setOwner(creatureBeingParsed);
            itemBeingParsed = sword;
            if (creatureBeingParsed == null) {
                roomBeingParsed.setItem(sword);
            }
        } else if (qName.equalsIgnoreCase("posX")) {
            bPosX = true;
        } else if (qName.equalsIgnoreCase("posY")) {
            bPosY = true;
        } else if (qName.equalsIgnoreCase("width")) {
            bWidth = true;
        } else if (qName.equalsIgnoreCase("height")) {
            bHeight = true;
        } else if (qName.equalsIgnoreCase("visible")) {
            bVisible = true;
        } else if (qName.equalsIgnoreCase("hpMoves")) {
            bHpMoves = true;
        } else if (qName.equalsIgnoreCase("type")) {
            bType = true;
        } else if (qName.equalsIgnoreCase("hp")) {
            bHp = true;
        } else if (qName.equalsIgnoreCase("ItemIntValue")) {
            bItemIntValue = true;
        } else if (qName.equalsIgnoreCase("actionCharValue")) {
            bActionCharValue = true;
        } else if (qName.equalsIgnoreCase("actionIntValue")) {
            bActionIntValue = true;
        } else if (qName.equalsIgnoreCase("maxHit")) {
            bMaxHit = true;
        } else if (qName.equalsIgnoreCase("actionMessage")) {
            bActionMessage = true;
        } else {
            System.out.println("Unknown qname: " + qName);
        }
        data = new StringBuilder();
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (actionBeingParsed != null) {
            //associate action with either creature or item
            if (bActionMessage) {
                actionBeingParsed.setMessage(data.toString());
                bActionMessage = false;
            } else if (bActionIntValue) {
                actionBeingParsed.setIntValue(Integer.parseInt(data.toString()));
                bActionIntValue = false;
            }
            else if (bActionCharValue) {
                actionBeingParsed.setCharValue(data.toString().charAt(0));
                bActionCharValue = false;
            }
        } else if (itemBeingParsed != null) {
            //associate item with either creature or room
            if (bPosX) {
                itemBeingParsed.setPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            } else if (bPosY) {
                itemBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            } else if (bItemIntValue) {
                itemBeingParsed.setIntValue(Integer.parseInt(data.toString()));
                bItemIntValue = false;
            } else if (bVisible) {
                if (Integer.parseInt(data.toString()) == 1) {
                    itemBeingParsed.setVisible();
                } else {
                    itemBeingParsed.setInvisible();
                }
                bVisible = false;
            }
        } else if (creatureBeingParsed != null) {
            if (bPosX) {
                creatureBeingParsed.setPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            } else if (bPosY) {
                creatureBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            } else if (bVisible) {
                if (Integer.parseInt(data.toString()) == 1) {
                    creatureBeingParsed.setVisible();
                } else {
                    creatureBeingParsed.setInvisible();
                }
                bVisible = false;
            } else if (bHp) {
                creatureBeingParsed.setHp(Integer.parseInt(data.toString()));
                bHp = false;
            } else if (bHpMoves) {
                creatureBeingParsed.setHpMove(Integer.parseInt(data.toString()));
                bHpMoves = false;
            } else if (bMaxHit) {
                creatureBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
                bMaxHit = false;
            } else if (bType) {
                creatureBeingParsed.setType(data.toString().charAt(0));
                bType = false;
            }
            //<CreatureAction?
        } else if (roomBeingParsed != null) {
            if (bPosX) {
                roomBeingParsed.setPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            } else if (bPosY) {
                roomBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            } else if (bWidth) {
                roomBeingParsed.setWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            } else if (bHeight) {
                roomBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            } else if (bVisible) {
                if (Integer.parseInt(data.toString()) == 1) {
                    roomBeingParsed.setVisible();
                } else {
                    roomBeingParsed.setInvisible();
                }
                bVisible = false;
            }
        } else if (passageBeingParsed != null) {
            if (bPosX) {
                passageBeingParsed.setPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            } else if (bPosY) {
                passageBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            } else if (bWidth) {
                passageBeingParsed.setWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            } else if (bHeight) {
                passageBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            } else if (bVisible) {
                if (Integer.parseInt(data.toString()) == 1) {
                    passageBeingParsed.setVisible();
                } else {
                    passageBeingParsed.setInvisible();
                }
                bVisible = false;
            }
        }
        //what to do if rooms passages dungeon
        if (qName.equalsIgnoreCase("Students")) {

        } else if (qName.equalsIgnoreCase("Room")) {
            addRoom(roomBeingParsed);
            roomBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Passage")) {
            addPassage(passageBeingParsed);
            passageBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Player")) {
            creatureBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Monster")) {
            creatureBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Sword")) {
            itemBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Armor")) {
            itemBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Scroll")) {
            itemBeingParsed = null;
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            actionBeingParsed = null;
        } else if (qName.equalsIgnoreCase("ItemAction")) {
            actionBeingParsed = null;
        }
    }
}
