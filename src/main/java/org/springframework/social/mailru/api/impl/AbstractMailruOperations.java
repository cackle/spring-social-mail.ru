package org.springframework.social.mailru.api.impl;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.mailru.api.Mailru;
import org.springframework.util.DigestUtils;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractMailruOperations {

    private static final String MAILRU_REST_URL = "http://appsmail.ru/platform/api?";

    private final SortedMap<String, String> params = new TreeMap<String, String>(new Comparator<String>() {
        @Override
        public int compare(String str, String str2) {
            return str.compareTo(str2);
        }
    });

    private final boolean isAuthorized;

    private final String accessToken;

    private final String clientId;

    private final String clientSecret;

    public AbstractMailruOperations(String clientId, String clientSecret, String accessToken, boolean isAuthorized) {

        this.isAuthorized = isAuthorized;
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        params.put("app_id", this.clientId);
        params.put("session_key", this.accessToken);
        params.put("secure", "1");
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException(Mailru.PROVIDER_ID);
        }
    }

    protected String makeOperationURL(Map<String, String> params) {
        this.params.putAll(params);

        StringBuilder url = new StringBuilder(MAILRU_REST_URL);
        StringBuilder signature = new StringBuilder();

        for (String param : this.params.keySet()) {
            String value = this.params.get(param);
            signature.append(param).append("=").append(value);
            url.append(param).append("=").append(URLEncoder.encode(value)).append("&");
        }
        signature.append(clientSecret);
        url.append("sig=").append(encodeSignature(signature.toString()));

        return url.toString();
    }

    private String encodeSignature(String sign) {
        return DigestUtils.md5DigestAsHex(sign.getBytes());
    }
}
