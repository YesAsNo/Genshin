package Files.Code.Data;

import javax.swing.ImageIcon;

public abstract class Item {
    public final String name;

    public transient ImageIcon icon;

    public Item(String itemName) {
        name = itemName;
    }

    public abstract void printInfo();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return this.name.equalsIgnoreCase(item.name) && this.getClass().equals(item.getClass());
        } else {
            return false;
        }
    }
}
