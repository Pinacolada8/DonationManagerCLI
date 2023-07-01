package application;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) throws Exception {
        var exitCode = new CommandLine(CLIRootCommand.class, new DependencyInjectionConfigFactory()).execute(args);
//        var exitCode = new CommandLine(CLIRootCommand.class, new DependencyInjectionConfigFactory())
//                .execute("login", "admin", "123456");
        System.exit(exitCode);
    }
}
