package com.example.mygit.handler;

import com.example.mygit.executors.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandHandler {
    private final Map<String, CommandExecutor> executorMap;

    @Autowired
    public CommandHandler(Collection<CommandExecutor> executors) {
        executorMap = new HashMap<>();
        for (CommandExecutor executor : executors) {
            executorMap.put(executor.getCommandToExecute(), executor);
        }
    }

    public void handle(String... command) {
        CommandExecutor executor = executorMap.get(command[0]);
        if (executor == null) {
            System.out.println("Комманды " + command[0] + " не найдено");
            System.out.println("Возможные команды:");
            for (String supportCommand : executorMap.keySet()) {
                System.out.println(supportCommand);
            }
            return;
        }
        executor.execute(command);
    }
}
