package VinSTTNorm.asrnormalizer.utilities.binaryencoder;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Load set of int from binary config file and check if string is inside
 */

public class BinaryDictionary {
    static public Set getWords(String binFilePath) {
        Set items = new HashSet();

        try {
            ClassLoader classLoader = BinaryDictionary.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(binFilePath);

            DataInputStream dis = new DataInputStream(inputStream);
            while (true) {
                try {
                    int code;
                    code = dis.readInt();
                    items.add(code);
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    static public boolean checkExist(Set words, String s) {
        return words.contains(s.hashCode());
    }
}
