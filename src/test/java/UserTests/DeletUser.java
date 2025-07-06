package UserTests;

import POJO.Deserilization.USER.InvalidResponseMessageModel;

import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class DeletUser {
    private static String userId;
    private static CreatUserResponse createdUser;

    @BeforeClass
    public static void setupTestUser() throws JsonProcessingException {
        // Create test user for deletion tests
        CreatUserRequest newUser = new CreatUserRequest()
                .setName(Builder.DEFAULT_USERNAME)
                .setEmail(Builder.DEFAULT_EMAIL)
                .setGender(Builder.GENDER_FEMALE)
                .setStatus(Builder.STATUS_ACTIVE);

        createdUser = UserReq.CreateUser(newUser, 201);
        userId = createdUser.getId();
    }

    @Test(priority =1)
    public void deleteUser(){
        Response response=UserReq.deleteUserUsingId(userId);
        response.prettyPrint();
        response.then().statusCode(204);
        Assert.assertTrue(response.body().asString().isEmpty(), "Response body is not empty");
    }

    @Test(priority = 2)
    public void deleteUserWithInvalidId_shouldReturnNotFound() {
        // First ensure user is deleted
        UserReq.deleteUserUsingId(userId);

        // Try to delete again with invalid ID
        InvalidResponseMessageModel errorResponse = UserReq.deleteUserUsingInvalidId(userId);

        // Verify error response
        Assert.assertEquals(errorResponse.getMessage(), "Resource not found",
                "Expected 'not found' message for invalid user ID");
    }

    @AfterClass
    public static void cleanup() {
        // Additional cleanup if needed
        try {
            UserReq.deleteUserUsingId(userId);
        } catch (Exception e) {
            System.out.println("Cleanup warning: User may already be deleted - " + e.getMessage());
        }
    }
}