package org.apache.hc.core5.http.config;


/**
 * Generic lookup by low-case string ID.
 */
public interface Lookup<I> {

    I lookup(String name);

}
