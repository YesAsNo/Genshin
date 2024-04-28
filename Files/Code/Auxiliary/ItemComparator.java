package Files.Code.Auxiliary;

import Files.Code.Data.FarmableItem;
import Files.Code.Data.Item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item item1, Item item2) {
        return item1.name.compareToIgnoreCase(item2.name);
    }
}
