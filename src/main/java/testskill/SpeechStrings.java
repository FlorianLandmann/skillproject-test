package main.java.testskill;

public class SpeechStrings {

    private SpeechStrings() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TEST_SKILL_NAME = "TestSkill!!";
    public static final String WELCOME = "Hallo <break time=\"1s\"/>. Willkommen beim Test Skill.";
    public static final String WELCOME_REPROMPT = "Hey du Schlafmütze <break time=\"1s\"/>. Der Test Skill ist offen.";
    public static final String STOP = "Auf Wiedersehen";
}
