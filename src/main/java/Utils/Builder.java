package Utils;

import POJO.Deserilization.USER.CreatUserWithInvalidbodyResponse;
import POJO.Deserilization.USER.InvalidResponseMessageModel;
import POJO.Serialization.USER.CreatUserRequest;
import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Builder {
    private static final Faker faker = new Faker();

    // API Configuration
    public static final String base_url = "https://gorest.co.in/public/v2";
    public static final String userEndPoint = "/users/";
    public static final String POST_ENDPOINT = "/posts/";
    public static final String TODO_ENDPOINT = "/todos/";

    // Test Data Constants
    public static final String DEFAULT_USERNAME = "souad";
    public static final String DEFAULT_EMAIL = faker.internet().emailAddress();
    public static final String DEFAULT_EMAIL_de = "User2@email.com";

    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_MALE = "male";
    public static final String STATUS_ACTIVE = "active";
    public static final String INVALID_EMAIL = "invalidEmail";
    public static final String REGISTERED_EMAIL = "souadabdelmay@gmail.com";

    // Content Types
    public static final String CONTENT_TYPE_JSON = "application/json";


    // Default Headers
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String token = System.getenv("API_TOKEN"); //set API_TOKEN=your_token_here IN CMD

        if(token == null || token.isEmpty()) {
            token = "6821c8a8b597394d5488a49035570e7c4962b19cc933bfb37a80bc1b9082dc0b";
        }

        headers.put("Authorization", "Bearer " + token.trim());
        headers.put("Content-Type", CONTENT_TYPE_JSON);
        headers.put("Accept", CONTENT_TYPE_JSON);
        return headers;
    }

    // User Builder Methods
    @NotNull
    public static CreatUserRequest buildDefaultUserRequest() {
        CreatUserRequest request = new CreatUserRequest();
        request.setName(faker.name().fullName());
        request.setEmail(faker.internet().emailAddress());
        request.setGender(GENDER_FEMALE);
        request.setStatus(STATUS_ACTIVE);
        return request;
    }

    public static CreatUserRequest buildUserRequestWithInvalidEmail() {
        CreatUserRequest request = buildDefaultUserRequest();
        request.setEmail(INVALID_EMAIL);
        return request;
    }

    public static CreatUserRequest buildUserWithEmptyData() {
        return new CreatUserRequest()
                .setName("")
                .setEmail("")
                .setGender("")
                .setStatus("");
    }
    public static Map<String, String> getExpectedEmptyDataErrors() {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("email", "can't be blank");
        errors.put("name", "can't be blank");
        errors.put("gender", "can't be blank, can be male of female");
        errors.put("status", "can't be blank");
        return errors;
    }

    public static CreatUserRequest buildValidUserWithoutAuth() {
        return new CreatUserRequest()
                .setName(DEFAULT_USERNAME)
                .setEmail(DEFAULT_EMAIL)
                .setGender(GENDER_FEMALE)
                .setStatus(STATUS_ACTIVE);
    }

    public static InvalidResponseMessageModel buildExpectedAuthError() {
        InvalidResponseMessageModel error = new InvalidResponseMessageModel();
        error.message = "Authentication failed";
        return error;
    }



    // Error Response Builder (for testing invalid requests)
    public static CreatUserWithInvalidbodyResponse[] buildExpectedValidationErrors(String... fieldNames) {
        CreatUserWithInvalidbodyResponse[] errors = new CreatUserWithInvalidbodyResponse[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            CreatUserWithInvalidbodyResponse error = new CreatUserWithInvalidbodyResponse();
            error.setField(fieldNames[i]);
            error.setMessage("can't be blank"); // Default message
            errors[i] = error;
        }
        return errors;
    }
}

