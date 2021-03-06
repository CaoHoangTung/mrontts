package VinSTTNorm.asrnormalizer.normalizer;


import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.Utilities;

public class BaseNormalizer {
    private BaseEntity object; // associated object with the normalizer

    public BaseEntity getObject() {
        return object;
    }

    public BaseNormalizer(BaseEntity associatedObject) {
        this.object = associatedObject;
    }

    /**
     * Replace the spoken form entity by written form in full text.
     *
     * @param text
     * @return
     */

    public String normFullText(String text) {

        EntityObject[] entities;
        entities = this.object.getEntities(text);
        for(EntityObject entityObject: entities){
            System.out.println(entityObject);
        }

        return Utilities.replaceString(text, entities);
    }

    public String normSpanText(String span) {

        String normedSpan = this.object.normEntity(span);
        return normedSpan;
    }
}
