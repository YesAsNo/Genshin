package Files.Code.Auxiliary;

import Files.Code.Data.Weapon;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeaponAdapter extends TypeAdapter<List<Weapon>> {
    @Override
    public void write(JsonWriter jsonWriter, List<Weapon> weaponList) throws IOException {

    }

    @Override
    public List<Weapon> read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        String name = "";
        String rarity = "";
        String weaponType = "";
        String ascensionMaterial = "";
        List<Weapon> weapons = new ArrayList<>();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if ("name".equalsIgnoreCase(fieldName)) {
                    name = jsonReader.nextString();
                } else if ("rarity".equalsIgnoreCase(fieldName)) {
                    rarity = jsonReader.nextString();
                } else if ("weaponType".equalsIgnoreCase(fieldName)) {
                    weaponType = jsonReader.nextString();
                } else if ("ascensionMaterial".equalsIgnoreCase(fieldName)) {
                    ascensionMaterial = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            weapons.add(new Weapon(name, rarity, weaponType, ascensionMaterial));
        }
        jsonReader.endArray();

        return weapons;
    }
}
