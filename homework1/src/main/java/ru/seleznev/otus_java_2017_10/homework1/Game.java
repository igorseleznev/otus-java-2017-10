package ru.seleznev.otus_java_2017_10.homework1;

import org.slf4j.Logger;
import java.util.*;


public class Game {
    public static final Logger SOMEONE_VOICE = org.slf4j.LoggerFactory.getLogger("(Someone's voice)");
    public static final Logger RABBIT = org.slf4j.LoggerFactory.getLogger("(Virtual Plush Cat)");
    public static final int MIN_WORD_LENGTH_FOR_GAME = 3;
    public static final int MAX_ATTEMPTS = 3;

    private static final int HOUR_MAX_VALUE = 24;
    private static final int SEED1 = Calendar.getInstance().get(Calendar.HOUR);
    private static final int SEED2 = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + HOUR_MAX_VALUE;
    private static final int SEED3 = Calendar.getInstance().get(Calendar.MILLISECOND);
    private static final Random INT_RND_GENERATOR1 = new Random(SEED1);
    private static final Random INT_RND_GENERATOR2 = new Random(SEED2);
    private static final Random INT_RND_GENERATOR3 = new Random(SEED3);
    private static final ArrayList<String> COMFORTING_PHRASES = new ArrayList<>(Arrays.asList(
            "Better late than never"
            , "Rome wasn't built in a day"
            , "Practice makes perfect"
            , "Where there's a will, there's a way"
            , "The early bird catches the worm"
            , "Actions speak louder than words"
            , "It's no use crying over spilled milk"
    ));

    /**
     * Words like a lottery tickets.<br>
     * Return result of lottery - true (win) or false (loose).
     */
    public static boolean start(final List<String> words) {
        SOMEONE_VOICE.info("The game began");
        for (final String word : words) {
            for (final String winnerWord : lottery(word)) {
                SOMEONE_VOICE.info("Yahoo! You are win on the word '{}'", winnerWord);
                RABBIT.info("A little present\n" +
                        " /\\_/\\ \n" +
                        " >^•^< \n" +
                        "(,) (,)\n" +
                        "");
                return true;
            }
            SOMEONE_VOICE.info("You guessed wrong (word '{}'). {}. Sleeping time...", word, rndComfortingPhrase());
            forcedSleep();
        }
        SOMEONE_VOICE.info("Game over =(  Maybe luck next time");
        return false;
    }


    private static List<String> lottery(final String word) {
        final int firstRnd = Math.abs(INT_RND_GENERATOR1.nextInt() % word.length());
        final int secondRnd = Math.abs(INT_RND_GENERATOR2.nextInt() % word.length());
        return (firstRnd == secondRnd) ? Arrays.asList(word) : Arrays.asList();
    }

    private static void forcedSleep() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            SOMEONE_VOICE.warn("I'm hurt");
            throw new RuntimeException(e);
        }
    }

    private static String rndComfortingPhrase() {
        if (COMFORTING_PHRASES.isEmpty()) {
            return "Sorry, comforting phrases are over";
        }
        return COMFORTING_PHRASES.remove(Math.abs(INT_RND_GENERATOR3.nextInt() % COMFORTING_PHRASES.size()));
    }
}
