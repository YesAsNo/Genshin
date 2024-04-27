package Files;

import javax.swing.ImageIcon;

public abstract class Item {
    public final String name;
    public final String type;

    public transient ImageIcon icon;
    public Item(String itemName, String itemType){
        name = itemName;
        type = itemType;
    }
    public void printInfo(){
        System.out.println(name + " " + type + " ");
    }
}
