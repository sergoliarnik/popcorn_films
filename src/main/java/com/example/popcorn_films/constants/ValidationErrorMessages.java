package com.example.popcorn_films.constants;

public final class ValidationErrorMessages {
    public static final String POST_TITLE_LENGTH_RANGE_ERROR =
            "The post title should have a minimum of 10 characters and a maximum of 100 characters.";
    public static final String POST_CONTENT_LENGTH_RANGE_ERROR =
            "The post content should have a minimum of 10 characters and a maximum of 100 characters.";
    public static final String USER_NAME_LENGTH_RANGE_ERROR =
            "The user name should have a minimum of 1 characters and a maximum of 30 characters.";
    public static final String USER_SURNAME_LENGTH_RANGE_ERROR =
            "The user surname should have a minimum of 1 characters and a maximum of 30 characters.";
    public static final String USER_DESCRIPTION_LENGTH_RANGE_ERROR =
            "The user description should have a minimum of 1 characters and a maximum of 500 characters.";
    public static final String USER_PASSWORD_LENGTH_RANGE_ERROR =
            "The user password should have a minimum of 8 characters and a maximum of 12 characters.";
    public static final String FILM_COMMENT_TEXT_LENGTH_RANGE_ERROR =
            "The film comment text should have a minimum of 1 characters and a maximum of 1000 characters.";
    public static final String USER_EMAIL_WRONG_FORMAT =
            "The user email should be correct";

    private ValidationErrorMessages() {
    }
}
