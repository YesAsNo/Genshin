package Files.Code.Auxiliary;

import Files.Code.Data.Character;
import Files.Code.Data.ToolData;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class CharacterAdapter extends TypeAdapter<Set<Character>> {

    @Override
    public void write(JsonWriter jsonWriter, Set<Character> characters) throws IOException {

    }

    @Override
    public Set<Character> read(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        String name = "";
        String element = "";
        String weaponType = "";
        String talentMaterial = "";
        String weeklyTalentMaterial = "";
        Set<Character> characters = new TreeSet<>(ToolData.comparator);
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if ("name".equalsIgnoreCase(fieldName)) {
                    name = jsonReader.nextString();
                } else if ("element".equalsIgnoreCase(fieldName)) {
                    element = jsonReader.nextString();
                } else if ("weaponType".equalsIgnoreCase(fieldName)) {
                    weaponType = jsonReader.nextString();
                } else if ("talentMaterial".equalsIgnoreCase(fieldName)) {
                    talentMaterial = jsonReader.nextString();
                } else if ("weeklyTalentMaterial".equalsIgnoreCase(fieldName)) {
                    weeklyTalentMaterial = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            characters.add(new Character(name, element, weaponType, talentMaterial, weeklyTalentMaterial));
        }
        jsonReader.endArray();

        return characters;
    }
}
