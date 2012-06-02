package org.sgomezvillamor.dexgsh;

import groovy.lang.Closure;

import java.io.BufferedReader;

import org.codehaus.groovy.tools.shell.IO;

/**
 * 
 * @author sgomez
 * 
 */
public class ErrorHandlerClosure extends Closure<Object> {

    /**
     * 
     */
    private IO io;

    /**
     * 
     * @param owner
     * @param io
     */
    public ErrorHandlerClosure(Object owner, IO io) {
        super(owner);
        this.io = io;
    }

    @Override
    public Object call(Object... args) {
        if (args.length > 0) {
            try {
                Throwable e = (Throwable) args[0];
                io.err.println(e.getMessage());
                io.err.println("Stack trace? [yN] ");
                String line = new BufferedReader(io.in).readLine().trim();
                if (line.trim().equals("y") || line.trim().equals("Y")) {
                    e.printStackTrace(io.err);
                }
                return null;
            } catch (Exception e) {
                io.err.println("Cannot manage error: " + args[0]);
                return null;
            }
        } else {
            io.err.println("Undefined error.");
            return null;
        }
    }
}