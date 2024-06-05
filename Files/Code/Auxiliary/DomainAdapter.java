package Files.Code.Auxiliary;

import Files.Code.Data.Artifact;
import Files.Code.Data.Domain;
import Files.Code.Data.FarmableItem;
import Files.Code.Data.TalentMaterial;
import Files.Code.Data.ToolData;
import Files.Code.Data.WeaponMaterial;
import Files.Code.Data.WeeklyTalentMaterial;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * This is a custom parser for domains.json.
 */
public class DomainAdapter extends TypeAdapter<Set<Domain>> {

    @Override
    public void write(JsonWriter jsonWriter, Set<Domain> domainAdapters) {

    }

    @Override
    public Set<Domain> read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        String name = "";
        String type = "";
        Set<? extends FarmableItem> materials = new TreeSet<>(ToolData.comparator);
        boolean rotates = false;
        Set<Domain> domains = new TreeSet<>(ToolData.comparator);
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if ("name".equalsIgnoreCase(fieldName)) {
                    name = jsonReader.nextString();
                } else if ("type".equalsIgnoreCase(fieldName)) {
                    type = jsonReader.nextString();
                } else if ("rotates".equalsIgnoreCase(fieldName)) {
                    rotates = jsonReader.nextBoolean();
                } else if ("materials".equalsIgnoreCase(fieldName)) {
                    materials = getMaterials(jsonReader);
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            domains.add(new Domain(name, type, materials, rotates));
        }
        jsonReader.endArray();

        return domains;
    }

    private Set<? extends FarmableItem> getMaterials(JsonReader jsonReader) throws IOException {
        Set<FarmableItem> materials = new TreeSet<>(ToolData.comparator);
        jsonReader.beginArray();
        String name = "";
        String type = "";
        String availability = "";
        String descr_2p = "";
        String descr_4p = "";
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if ("name".equalsIgnoreCase(fieldName)) {
                    name = jsonReader.nextString();
                } else if ("type".equalsIgnoreCase(fieldName)) {
                    type = jsonReader.nextString();
                } else if ("availability".equalsIgnoreCase(fieldName)) {
                    availability = jsonReader.nextString();
                } else if ("2p".equalsIgnoreCase(fieldName)) {
                    descr_2p = jsonReader.nextString();
                } else if ("4p".equalsIgnoreCase(fieldName)) {
                    descr_4p = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (type.equalsIgnoreCase("artifact")) {
                materials.add(new Artifact(name, descr_2p, descr_4p));
            } else if (type.equalsIgnoreCase("Weapon Material")) {
                materials.add(new WeaponMaterial(name, availability, new ArrayList<>()));
            } else if (type.equalsIgnoreCase("Talent Book")) {
                materials.add(new TalentMaterial(name, availability, new ArrayList<>()));
            } else if (type.equalsIgnoreCase("Weekly Boss Material")) {
                materials.add(new WeeklyTalentMaterial(name, availability, new ArrayList<>()));
            }
        }
        jsonReader.endArray();
        return materials;
    }
}
