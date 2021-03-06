package net.adoptopenjdk.icedteaweb.http;

/**
 * An enumeration of the most commonly used methods for HTTP as supported by
 * {@link java.net.HttpURLConnection#setRequestMethod(java.lang.String) }.
 */
public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    OPTIONS,
    DELETE,
    TRACE;
}
