package VinSTTNorm.asrnormalizer.entityobject;

public class EntityObject implements Comparable<EntityObject>{
    public int characterStart;
    public int characterEnd;
    public String type;
    public String text;
    public String replacement;

    public EntityObject(int characterStart, int characterEnd, String type, String text, String replacement) {
        this(characterStart, characterEnd, type, text);
        this.replacement = replacement;
    }

    public EntityObject(int characterStart, int characterEnd, String type, String text) {
        this.characterStart = characterStart;
        this.characterEnd = characterEnd;
        this.type = type;
        this.text = text;
        this.replacement = null;
    }

    public int compareTo(EntityObject entity) {
        if (this.characterStart == entity.characterStart)
            return entity.characterEnd - this.characterEnd;
        else
            return this.characterStart - entity.characterStart;
    }

    public String toString() {
        return String.format("(%s->%s ; type=%s ; text=%s; norm=%s)", characterStart, characterEnd, type, text, replacement);
    }
}
