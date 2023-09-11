package ru.aston.oshchepkov_aa.task4.cli;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aston.oshchepkov_aa.task4.cli.command.*;

import java.util.Scanner;
import java.util.StringTokenizer;

@Cli(
        name = "api",
        description = "Default api",
        defaultCommand = Help.class,
        commands = {
                Help.class,
                GetAllUsers.class,
                UserById.class,
                DeleteUserById.class,
                CreateUser.class,
                UpdateUser.class,
                GetAllOrders.class,
                OrderById.class,
                DeleteOrderById.class,
                CreateOrder.class,
                UpdateOrder.class,
                UserOrders.class,
                AssignOrderToUser.class,
                UnAssignOrderToUser.class,
                Terminate.class
        }
)
public class CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(CommandLineRunner.class);
    private static CommandLineRunner runner;
    private com.github.rvesse.airline.Cli<Runnable> cli;

    private CommandLineRunner() {
        cli = new com.github.rvesse.airline.Cli<>(CommandLineRunner.class);
    }

    public static CommandLineRunner getRunner() {
        if (null == runner) {
            runner = new CommandLineRunner();
        }
        return runner;
    }

    public void start() {
        printWelcomeMessage();
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                log.info("Insert a command: ");

                var input = readInput(scanner);
                var cmd = cli.parse(input);
                cmd.run();

                if (cmd instanceof Terminate) {
                    break;
                }
            }
        } catch (RuntimeException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    private String[] readInput(Scanner s) {
        var b = s.nextLine();
        var tokenizer = new StringTokenizer(b, " ");
        var size = tokenizer.countTokens();

        var result = new String[size];

        for (var i = 0; i < size; i++) {
            result[i] = tokenizer.nextToken();
        }

        return result;
    }

    private void printWelcomeMessage() {
        var builder = new StringBuilder(255)
                .append(System.lineSeparator())
                .append("Welcome to console db interactor!")
                .append(System.lineSeparator())
                .append("Use 'help' command to get all available features");

        log.info("{}", builder);
    }
}
