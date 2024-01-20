package com.example.mygit.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineHandlerImpl implements CommandLineRunner {
    private final CommandHandler commandHandler;

    @Override
    public void run(String... args) throws Exception {
        commandHandler.handle(args);
    }
}
