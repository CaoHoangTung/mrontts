package VinSTTNormV2.taggers;



public class SpanObject implements Comparable<SpanObject>{
    public int characterStart;
    public int characterEnd;
    public String type;
    public String text;
    public String replacement;

    public SpanObject(int characterStart, int characterEnd, String type, String text, String replacement) {
        this(characterStart, characterEnd, type, text);
        this.replacement = replacement;
    }

    public SpanObject(int characterStart, int characterEnd, String type, String text) {
        this.characterStart = characterStart;
        this.characterEnd = characterEnd;
        this.type = type;
        this.text = text;
        this.replacement = null;
    }

    public int compareTo(SpanObject span) {
        if (this.characterStart == span.characterStart)
            return span.characterEnd - this.characterEnd;
        else
            return this.characterStart - span.characterStart;
    }

    public String toString() {
        return String.format("(%s->%s ; type=%s ; text=%s; norm=%s)", characterStart, characterEnd, type, text, replacement);
    }
}
