package Files.Code.Data;

import javax.swing.ImageIcon;

/**
 * Represents all items(characters, domains, weapons, etc) from the game Genshin Impact used in GDApp.
 */
public abstract class Item {
    /**
     * Name of the item
     */
    public final String name;
    /**
     * Icon of the item.
     */
    public transient ImageIcon icon;

    /**
     * Constructor of the item class. Note that all inheritors must use this constructor, while the class Item itself is abstract.
     * @param itemName item name
     */

    public Item(String itemName) {
        name = itemName;
    }

    /**
     * Prints information about the item
     */
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
