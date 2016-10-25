package org.springframework.social.mailru.api;

/**
 * Interface defining operations that can be performed on a Mailru wall.
 * @author Cackle
 */
public interface WallOperations {

    String post(String message);

    String post(String message, String link, String photoUrl);
}
