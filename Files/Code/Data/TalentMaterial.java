package Files.Code.Data;

import java.util.List;

/**
 * This class represents all Talent Materials used by characters in the game Genshin Impact.
 */
public class TalentMaterial extends FarmableItem {
    /**
     * All characters that can use this talent material.
     */
    public final List<String> usedBy;

    /**
     * Constructor of Talent Material class
     * @param itemName name of talent material
     * @param availability availability of talent material
     * @param usedBy list of characters that can use it.
     */

    public TalentMaterial(String itemName, String availability, List<String> usedBy) {
        super(itemName, availability);
        this.usedBy = usedBy;
    }
}
