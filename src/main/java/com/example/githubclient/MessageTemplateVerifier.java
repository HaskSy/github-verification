package com.example.githubclient;

public class MessageTemplateVerifier {

    private static final String VERIFICATION_RESULT = "Auto-Verifier finding";
    private static final String regex = "^(GENERATOR|LEETCODE)\\s(1021|1022|1013|2021|2022)\\s(Added|Deleted|Refactored|Moved|Fixed).+";
    private static final String OK_MESSAGE = "";
    private static final String RAISE_MESSAGE =
            "Check result:\n" +
            "\n" +
            "    Commit title must start with prefix in ['GENERATOR', 'LEETCODE']\n" +
            "    Commit title must contain group number in ['1021', '1022', '1013', '2021', '2022']\n" +
            "    Commit title action must start with ['Added', 'Deleted', 'Refactored', 'Moved', 'Fixed']\n" +
            "\n";

    public static boolean process(String title, String regex) {
        return title.matches(regex);
    }

    public static boolean process(String title) {
        return process(title, regex);
    }

    public static String buildMessage(String title) {
        if (process(title)) {
            return OK_MESSAGE;
        }
        else {
            return VERIFICATION_RESULT + "\n" +
                    "\n" +
                    "Your Commit title: " + title + "\n" +
                    "\n" +
                    RAISE_MESSAGE;
        }
    }
}
