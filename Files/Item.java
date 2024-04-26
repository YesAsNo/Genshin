package Files;

import javax.swing.ImageIcon;

public class Item {
    public final String name;
    public final String type;
    public final String availability;
    public transient ImageIcon icon;
    public Item(String itemName, String itemType, String itemAvailability){
        name = itemName;
        type = itemType;
        availability = itemAvailability;
    }
    public void printInfo(){
        System.out.println(name + " " + type + " "+ availability);
    }
}
