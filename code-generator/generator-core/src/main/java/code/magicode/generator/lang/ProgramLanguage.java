package code.magicode.generator.lang;

public class ProgramLanguage {

    public static final ProgramLanguage JAVA = new ProgramLanguage("Java");

    private String name;

    public ProgramLanguage(String name) {
        this.name = name;
    }
}