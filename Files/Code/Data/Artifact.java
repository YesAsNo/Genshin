package Files.Code.Data;

/**
 * This class represents all Artifact Sets from the game Genshin Impact.
 */
public class Artifact extends FarmableItem {
    /**
     * 2-Piece description
     */
    public final String description_2piece;
    /**
     * 4-Piece description
     */
    public final String description_4piece;

    /**
     * Constructor of Artifact class
     *
     * @param itemName name of artifact set
     * @param descr_2p 2-piece description of artifact set
     * @param descr_4p 4-piece description of artifact set
     */

    public Artifact(String itemName, String descr_2p, String descr_4p) {
        super(itemName, "All");
        description_2piece = descr_2p;
        description_4piece = descr_4p;
    }
}
