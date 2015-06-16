package de.fu_berlin.agdb.crepe.writers;

import de.fu_berlin.agdb.crepe.outputadapters.JSONOutputAdapter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Function;

/**
 * Writer for HTTP REST services.
 * @param <T> The type of response expected from the server.
 */
public class RestWriter<T> implements IWriter {
    private final URI target;
    private final HttpMethod method;
    private final Class<T> responseType;
    private final Function<T, Boolean> callback;
    private final RestTemplate restTemplate;
    private String text;
    private MultiValueMap<String, String> headers = new HttpHeaders();


    /**
     * Creates a new {@link RestWriter} with the given options.
     * @param target The target URI to write the contents to.
     * @param method The method with which to send HTTP requests
     * @param responseType The expected type of response from the server.
     *                     Please make sure to add an appropriate message converter, that can convert
     *                     the response into this type.
     * @param callback A function which uses the response from the REST server,
     *                 to determine if writing was successful.
     */
    public RestWriter(URI target, HttpMethod method, Class<T> responseType, Function<T, Boolean> callback) {
        this.target = target;
        this.method = method;
        this.responseType = responseType;
        this.callback = callback;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(JSONOutputAdapter.OBJECT_MAPPER));
    }

    /**
     * Creates a new standard {@link RestWriter} with the method
     * set to {@link HttpMethod#POST} expecting a non-empty String as result.
     * @param target The URI to send the content to
     */
    public static RestWriter<String> makeStdWriter(URI target) {
        return new RestWriter<>(target, HttpMethod.POST, String.class, s -> s != null && !s.isEmpty());
    }

    @Override
    public boolean write() {
        if (text == null)
            return false;

        ResponseEntity<T> responseEntity = restTemplate.exchange(target, method, new HttpEntity<>(text, headers), responseType);
        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return callback != null ? callback.apply(responseEntity.getBody()) : true;
        else
            return false;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the HTTP request headers to the given map.
     * @param headers
     */
    public void setHeaders(MultiValueMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Sets the HTTP request header for the given parameters.
     * @param key
     * @param value
     */
    public void setHeader(String key, String value) {
        headers.set(key, value);
    }

    /**
     * Adds the given value to the HTTP request headers
     * stored under the given key.
     * @param key
     * @param value
     */
    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    /**
     * Returns a list of the message converters associated with the rest template.
     * This list can be manipulated to suit your needs.
     * In a normal instance, a String and a JSON message converter are already added.
     */
    public List<HttpMessageConverter<?>> getMessageConverters() {
        return restTemplate.getMessageConverters();
    }
}
