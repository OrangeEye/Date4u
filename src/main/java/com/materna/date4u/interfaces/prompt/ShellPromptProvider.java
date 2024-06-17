package com.materna.date4u.interfaces.prompt;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShellPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        // Nicht jede Konsole zeigt die Farbe an
        return new AttributedString(
                "date4u:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)
        );
    }
}
