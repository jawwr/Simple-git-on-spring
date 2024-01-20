package com.example.mygit.executors;

public interface CommandExecutor {
    void execute(String... params);

    String getCommandToExecute();
}
