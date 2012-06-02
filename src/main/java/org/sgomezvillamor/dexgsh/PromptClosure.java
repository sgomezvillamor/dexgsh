package org.sgomezvillamor.dexgsh;

import groovy.lang.Closure;

/**
 * 
 * @author sgomez
 * 
 */
public class PromptClosure extends Closure<Object> {

    /**
     * 
     */
    private static final String PROMPT = "dexgsh $ ";

    /**
     * 
     * @param owner
     */
    public PromptClosure(Object owner) {
        super(owner);
    }

    @Override
    public Object call() {
        return PROMPT;
    }
}