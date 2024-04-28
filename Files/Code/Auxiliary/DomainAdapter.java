package Files.Code.Auxiliary;

import Files.Code.Data.Artifact;
import Files.Code.Data.Domain;
import Files.Code.Data.FarmableItem;
import Files.Code.Data.TalentMaterial;
import Files.Code.Data.WeaponMaterial;
import Files.Code.Data.WeeklyTalentMaterial;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomainAdapter extends TypeAdapter<List<Domain>> {

    @Override
    public void write(JsonWriter jsonWriter, List<Domain> domainAdapters) throws IOException {

    }

    @Override
    public List<Domain> read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        String name = "";
        String type = "";
        List<? extends FarmableItem> materials = new ArrayList<>();
        boolean rotates = false;
        List<Domain> domains = new ArrayList<>();
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

    public List<? extends FarmableItem> getMaterials(JsonReader jsonReader) throws IOException {
        List<FarmableItem> materials = new ArrayList<>();
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
