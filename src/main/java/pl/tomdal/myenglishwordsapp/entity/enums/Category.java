package pl.tomdal.myenglishwordsapp.entity.enums;

public enum Category {
    NOUN("noun"),
    VERB("verb"),
    PHRASAL_VERB("phrasal_verb"),
    ANTONYM("antonym"),
    ADJECTIVE("adjective"),
    ADVERB("adverb"),
    EXPRESSION("expression");

    private final String text;

    Category(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
