package application;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) throws Exception {
        var exitCode = new CommandLine(CLIRootCommand.class, new DependencyInjectionConfigFactory()).execute(args);
    //    var exitCode = new CommandLine(CLIRootCommand.class, new DependencyInjectionConfigFactory())
    //            .execute("item", "approve", "DomCasmurro");
        System.exit(exitCode);
    }
}
