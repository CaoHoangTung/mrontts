package VinSTTNorm.asrnormalizer.numerical;


import VinSTTNorm.asrnormalizer.abstractentity.SimpleRegexEntity;
import org.json.JSONObject;

import java.util.Locale;

public class WrittenSerialNumberCharacterRegexEntity extends SimpleRegexEntity {
    private static String TAG = "WRITTEN_SERIAL_NUMBER_CHARACTRER_ENTITY";

    public WrittenSerialNumberCharacterRegexEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "written_serial_number_character";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        return spokenFormEntityString.toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
