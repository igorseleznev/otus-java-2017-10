package ru.seleznev.otus_java_2017_10.homework1;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static ru.seleznev.otus_java_2017_10.homework1.Game.*;

public class Main {

    /**
     * Enter several arguments to start the game.<br>
     * Characters in the word length should not be less than 3.
     */
    public static void main(String[] args) throws InterruptedException {
        if (args == null || args.length == 0) {
            SOMEONE_VOICE.info("Come here when you are ready to enter arguments");
            return;
        }
        final List<String> raffleWords = asList(raffleWords(args));
        if (raffleWords.isEmpty()) {
            SOMEONE_VOICE.info("Raffle word list is empty. The game can't be started");
            return;
        }
        Game.start(raffleWords);
    }

    private static String[] raffleWords(String[] words) {
        return shuffleList(asList(words))
                .stream()
                .distinct()
                .filter(word -> {
                    if (word.length() < MIN_WORD_LENGTH_FOR_GAME) {
                        SOMEONE_VOICE.info("The word '{}' is too short. It's boring. Are you gambler?)", word);
                        return false;
                    }
                    return true;
                })
                .limit(MAX_ATTEMPTS)
                .toArray(String[]::new);
    }

    private static List<String> shuffleList(List<String> list) {
        shuffle(list);
        return list;
    }

}
