package VinSTTNorm;

import VinSTTNormV2.online.*;
import VinSTTNormV2.online.Number;
import org.junit.Test;

import java.util.Hashtable;

public class OnlineTest {
    @Test
    public void test() {
        Hashtable<String, BaseOutputNormalizer> handlers = new Hashtable<>();

        handlers.put("exotic", new Exotic());
        handlers.put("link", new Link());
        handlers.put("number", new Number());
        handlers.put("date", new Date());
        handlers.put("address", new Address());
        handlers.put("normal", new Mix());
        handlers.put("score", new Score());


        String[] text = {"một ki lô mét rưỡi"};
        String[] term = {"number"};

        for (int i = 0; i < text.length; i++){
            String normedText = handlers.get(term[i]).normAll(text[i]);
            System.out.println(normedText);
        }
    }

}
