import VinSTTNormV2.OfflineNormalizer;

public class main {
    public static void main(String[] args) {
        String text1 = "chỉ đường đến the golden an khánh ba hai t hà nội";
        OfflineNormalizer normalizer = new OfflineNormalizer();
        System.out.println(String.format("Input: %s\nOutput: %s", text1, normalizer.normText(text1)));


//        Hashtable<String, BaseHandler> handlers = new Hashtable<>();
//
//        handlers.put("exotic", new ExoticHandler());
//        handlers.put("link", new LinkHandler());
//        handlers.put("number", new NumberHandler());
//        handlers.put("date", new DateHandler());
//        handlers.put("address", new AddressHandler());
//        handlers.put("normal", new MixHandler());
//        handlers.put("score", new ScoreHandler());
//
//
//        String[] text = {"Chỉ địa điểm đi số ba mươi chín a Khánh Hòa"};
//        String[] term = {"normal"};
//
//        for (int i = 0; i < text.length; i++){
//            String normedText = handlers.get(term[i]).normAll(text[i]);
//            System.out.println(normedText);
//        }


    }

}
