package org.apache.hc.core5.http;

/**
 * Represents an element of an HTTP {@link Header header} value consisting of
 * a name / value pair and a number of optional name / value parameters.
 *
 * @since 4.0
 */
public interface HeaderElement {

    /**
     * Returns header element name.
     *
     * @return header element name
     */
    String getName();

    /**
     * Returns header element value.
     *
     * @return header element value
     */
    String getValue();

    /**
     * Returns an array of name / value pairs.
     *
     * @return array of name / value pairs
     */
    NameValuePair[] getParameters();

    /**
     * Returns the first parameter with the given name.
     *
     * @param name parameter name
     * @return name / value pair
     */
    NameValuePair getParameterByName(String name);

    /**
     * Returns the total count of parameters.
     *
     * @return parameter count
     */
    int getParameterCount();

    /**
     * Returns parameter with the given index.
     *
     * @param index index
     * @return name / value pair
     */
    NameValuePair getParameter(int index);

}

