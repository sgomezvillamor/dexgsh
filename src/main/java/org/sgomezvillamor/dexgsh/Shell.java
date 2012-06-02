package org.sgomezvillamor.dexgsh;

import java.io.File;
import java.io.IOException;

import jline.History;

import org.codehaus.groovy.tools.shell.ExitNotification;
import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;
import org.codehaus.groovy.tools.shell.InteractiveShellRunner;

import com.sparsity.dex.groovy.MetaDex;

/**
 * 
 * @author sgomez
 *
 */
public class Shell {

    private static final File HISTORY_FILE = new File(
            System.getProperty("user.home") + "/.dexgsh_history");

    public Shell(IO io) {
        io.out.println("Welcome to the Dex groovy-based shell");
        io.out.println("-------------------------------------");

        Groovysh groovy = new Groovysh();
        ResultHandlerClosure rhc = new ResultHandlerClosure(groovy, io);
        rhc.showOutput(false);
        groovy.setResultHook(rhc);
        groovy.execute("import com.sparsity.dex.gdb.Dex;");
        groovy.execute("import com.sparsity.dex.*;");
        groovy.setHistory(new History());
        rhc.showOutput(true);

        PromptClosure pc = new PromptClosure(groovy);
        InteractiveShellRunner isr = new InteractiveShellRunner(groovy, pc);
        ErrorHandlerClosure ehc = new ErrorHandlerClosure(isr, io);
        isr.setErrorHandler(ehc);
        try {
            isr.setHistory(new History(HISTORY_FILE));
        } catch (IOException e1) {
            // continue with no history file :-(
        }

        MetaDex.ini();

        try {
            isr.run();
        } catch (ExitNotification e) {
        }
    }

    public static void main(String[] args) {
        new Shell(new IO(System.in, System.out, System.err));
    }
}
