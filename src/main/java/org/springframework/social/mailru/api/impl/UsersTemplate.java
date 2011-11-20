package org.springframework.social.mailru.api.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.social.mailru.api.MailruProfile;
import org.springframework.social.mailru.api.UsersOperations;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestTemplate;

/**
 * User operations.
 * @author Cackle
 */
public class UsersTemplate extends AbstractMailruOperations implements UsersOperations {

    private static final String METHOD = "users.getInfo";

    private final RestTemplate restTemplate;

    public UsersTemplate(String clientId, String clientSecret, RestTemplate restTemplate,
        String accessToken, boolean isAuthorizedForUser) {

        super(clientId, clientSecret, accessToken, isAuthorizedForUser);
        this.restTemplate = restTemplate;
    }

    @Override
    public MailruProfile getProfile() {
        requireAuthorization();

        Map<String, String> params = new HashMap<String, String>();
        params.put("method", METHOD);
        URI uri = URIBuilder.fromUri(makeOperationURL(params)).build();

        List<Map<String, String>> profiles = restTemplate.getForObject(uri, List.class);
        //checkForError(profiles);

        if (!profiles.isEmpty()) {
            Map<String, String> profilesMap = profiles.get(0);
            MailruProfile profile = new MailruProfile(profilesMap.get("uid"), profilesMap.get("first_name"),
                profilesMap.get("last_name"), profilesMap.get("email"), profilesMap.get("link"));

            if (profilesMap.containsKey("pic")) {
                profile.setPhoto(profilesMap.get("pic"));
            }

            return profile;
        }
        return null;
    }
}
