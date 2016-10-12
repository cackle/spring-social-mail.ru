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
package org.springframework.social.mailru.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Model class containing a Mailru user's profile information.
 * @author Cackle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailruProfile {

    private String uid;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private String link;

    @JsonProperty("pic_big")
    private String photo;

    private int sex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    private Date birthday;

    public MailruProfile() {
    }

    public MailruProfile(String uid, String firstName, String lastName, String email, String link) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.link = link;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        LocalDate localBirthday = LocalDate.from(birthday.toInstant());
        return (int) localBirthday.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    public int getSex() {
        return sex;
    }

    public Date getBirthdy() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getLink() {
        return link;
    }

    public String getPhoto() {
        return photo;
    }
}
