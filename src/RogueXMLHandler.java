package Step1;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RogueXMLHandler extends DefaultHandler{
    private StringBuilder data = null;

    private Room[] rooms;
    private Passage[] passages;
    private Dungeon dungeon;
    private ObjectDisplayGrid objectDisplayGrid;
    private String name;
    private int width;
    private int gameHeight;
    private int topHeight;
    private int bottomHeight;

    private Structure structureBeingParsed = null;
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

    public RogueXMLHandler() {
    }

    //@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("Dungeon")) {
            name = attributes.getValue("name");
            width = Integer.parseInt(attributes.getValue("width"));
            topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));

        } else if (qName.equalsIgnoreCase("Rooms")) {
            //anything in here?? rooms = new Room[];
        } else if (qName.equalsIgnoreCase("Passages")) {
            //anything in here?? passages = new Passage[];
        } else if (qName.equalsIgnoreCase("Room")) {
            int r = Integer.parseInt(attributes.getValue("room"));
            Room room = new Room(r); //string?

            //addRoom(room);  //?
            structureBeingParsed = room;

        } else if (qName.equalsIgnoreCase("Passages")) {
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(room1, room2);
            //addPassage(passage);
            structureBeingParsed = passage;

        } else if (qName.equalsIgnoreCase("Monster")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            //set creature to room?
            Monster monster = new Monster();
            monster.setName(name);
            monster.setID(room, serial);
            creatureBeingParsed = monster;

        } else if (qName.equalsIgnoreCase("Player")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            //set player to room?
            //what to do with name, room, serial
            Player player = new Player();

            creatureBeingParsed = player;


        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");

        } else if (qName.equalsIgnoreCase("Scroll")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);
            scroll.setID(room, serial);
            scroll.setOwner(creatureBeingParsed);
            itemBeingParsed = scroll;

        } else if (qName.equalsIgnoreCase("Armor")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);
            armor.setID(room, serial);
            armor.setOwner(creatureBeingParsed);
            itemBeingParsed = armor;

        } else if (qName.equalsIgnoreCase("Sword")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);
            sword.setID(room, serial);
            sword.setOwner(creatureBeingParsed);
            itemBeingParsed = sword;

        }

        else if (qName.equalsIgnoreCase("posX")) {
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
        }

        else {
            System.out.println("Unknown qname: " + qName);
        }
        data = new StringBuilder();
    }

    //@Override
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
                creatureBeingParsed.setHP(Integer.parseInt(data.toString()));
                bHp = false;
            } else if (bHpMoves) {
                creatureBeingParsed.setHpMoves(Integer.parseInt(data.toString()));
                bHpMoves = false;
            } else if (bMaxHit) {
                creatureBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
                bMaxHit = false;
            } else if (bType) {
                creatureBeingParsed.setType(data.toString().charAt(0));
                bType = false;
            }
            //<CreatureAction?
        } else if (structureBeingParsed != null) {
            if (bPosX) {
                structureBeingParsed.setPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            } else if (bPosY) {
                structureBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            } else if (bWidth) {
                structureBeingParsed.setWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            } else if (bHeight) {
                structureBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            } else if (bVisible) {
                if (Integer.parseInt(data.toString()) == 1) {
                    structureBeingParsed.setVisible();
                } else {
                    structureBeingParsed.setInvisible();
                }
                bVisible = false;
            }
        }


        //what to do if rooms passages dungeon
        if (qName.equalsIgnoreCase("Students")) {

        } else if (qName.equalsIgnoreCase("Room")) {
            dungeon.addRoom(structureBeingParsed);
            structureBeingParsed = null;
        } else if (qName.equalsIgnoreCase("Passage")) {
            dungeon.addPassage(structureBeingParsed);
            structureBeingParsed = null;
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
        }
    }
}
