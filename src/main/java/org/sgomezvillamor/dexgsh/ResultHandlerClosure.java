package org.sgomezvillamor.dexgsh;

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.groovy.tools.shell.IO;

import com.sparsity.dex.groovy.Objects;

/**
 * 
 * @author sgomez
 * 
 */
public class ResultHandlerClosure extends Closure<Object> {

    /**
     * 
     */
    private static final String PROMPT = ">";

    /**
     * 
     */
    private IO io;

    /**
     * 
     */
    private boolean showOutput;

    /**
     * 
     * @param owner
     * @param io
     * @param resultPrompt
     */
    public ResultHandlerClosure(Object owner, IO io) {
        super(owner);
        this.io = io;
    }

    /**
     * 
     * @param b
     */
    public void showOutput(boolean b) {
        showOutput = b;
    }

    @Override
    public Object call(Object... args) {
        if (showOutput) {
            Object result = args[0];
            if (result != null) {
                Iterator itty;
                if (result instanceof Iterator) {
                    itty = (Iterator) result;
                } else if (result instanceof Iterable) {
                    itty = ((Iterable) result).iterator();
                } else if (result instanceof Object[]) {
                    itty = Arrays.asList(result).iterator();
                } else if (result instanceof Map) {
                    itty = ((Map) result).entrySet().iterator();
                } else if (result instanceof Objects) {
                    itty = ((Objects) result).iterator();
                } else {
                    // this.io.out
                    // .println("The result handler closure should be improved for class: "
                    // + result.getClass());
                    itty = Arrays.asList(result).iterator();
                }

                while (itty.hasNext()) {
                    this.io.out.println(PROMPT + itty.next());
                }
            }
        }

        return null;
    }
}