package org.springframework.social.mailru.api.impl;

import org.springframework.social.mailru.api.MailruProfile;
import org.springframework.social.mailru.api.UsersOperations;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * User operations.
 * @author Cackle
 */
public class UsersTemplate extends AbstractMailruOperations implements UsersOperations {

    private static final String METHOD = "users.getInfo";

    private final RestTemplate restTemplate;

    public UsersTemplate(String clientId, String clientSecret, String privateKey, RestTemplate restTemplate,
        String accessToken, boolean isAuthorizedForUser) {

        super(clientId, clientSecret, accessToken, privateKey, isAuthorizedForUser);
        this.restTemplate = restTemplate;
    }

    @Override
    public MailruProfile getProfile() {
        requireAuthorization();

        Map<String, String> params = new HashMap<String, String>();
        params.put("method", METHOD);
        URI uri = URIBuilder.fromUri(makeOperationURL(params)).build();

        MailruProfile[] profiles = restTemplate.getForObject(uri, MailruProfile[].class);
        if(profiles.length == 1) {
            return profiles[0];
        }

        throw new IllegalStateException("Invalid answer from server on users.getInfo method");
    }
}
