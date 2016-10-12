/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.mailru.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.mailru.api.Mailru;
import org.springframework.social.mailru.api.MailruErrorHandler;
import org.springframework.social.mailru.api.UsersOperations;
import org.springframework.social.mailru.api.WallOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

import java.util.LinkedList;
import java.util.List;

/**
 * Mailru template
 * @author Cackle
 */
public class MailruTemplate extends AbstractOAuth2ApiBinding implements Mailru {

    private UsersOperations usersOperations;

    private WallOperations wallOperations;

    private final String clientId;

    private final String clientSecret;

    private final String accessToken;

    public MailruTemplate(String clientId, String clientSecret, String accessToken) {
        super(accessToken);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessToken = accessToken;
        initialize();
    }

    private void initialize() {
        registerJsonModule();
        getRestTemplate().setErrorHandler(new MailruErrorHandler());
        initSubApis();
    }

    private void registerJsonModule() {
        List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

                List<MediaType> mTypes = new LinkedList<>(jsonConverter.getSupportedMediaTypes());
                mTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                jsonConverter.setSupportedMediaTypes(mTypes);

                ObjectMapper objectMapper = new ObjectMapper();
                //objectMapper.registerModule(new MailruModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }

    private void initSubApis() {
        usersOperations = new UsersTemplate(clientId, clientSecret, getRestTemplate(), accessToken, isAuthorized());
        wallOperations = new WallTemplate(clientId, clientSecret, getRestTemplate(), accessToken, isAuthorized());
    }

    @Override
    public UsersOperations usersOperations() {
        return usersOperations;
    }

    @Override
    public WallOperations wallOperations() {
        return wallOperations;
    }
}
