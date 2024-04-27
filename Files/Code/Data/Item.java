package Files.Code.Data;

import javax.swing.ImageIcon;

public abstract class Item {
    public final String name;
    public final String type;

    public transient ImageIcon icon;

    public Item(String itemName, String itemType) {
        name = itemName;
        type = itemType;
    }

    public abstract void printInfo();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return this.name.equalsIgnoreCase(item.name) && this.type.equalsIgnoreCase(item.type);
        } else {
            return false;
        }
    }
}
