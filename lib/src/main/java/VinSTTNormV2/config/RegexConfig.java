package VinSTTNormV2.config;

public class RegexConfig {
    static public RegexConfig getDefault(){
        return new RegexConfig("","","",0);
    }

    private String prefix;
    private String postfix;
    private String pattern;
    private int group;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public RegexConfig(String prefix, String postfix, String pattern, int group){
        this.prefix = prefix;
        this.postfix = postfix;
        this.pattern = pattern;
        this.group = group;
    }

    @Override
    public String toString(){
        return String.format("%s%s%s", this.prefix, this.pattern, this.postfix);
    }
}
