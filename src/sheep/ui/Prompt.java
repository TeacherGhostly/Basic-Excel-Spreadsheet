package sheep.ui;

import java.util.Optional;

public interface Prompt {
    Optional<String> ask(String prompt);
    Optional<String[]> askMany(String[] prompts);
    boolean askYesNo(String prompt);
    void message(String prompt);
}
