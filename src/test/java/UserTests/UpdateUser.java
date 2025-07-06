package UserTests;

import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Deserilization.USER.CreatUserWithInvalidbodyResponse;
import POJO.Deserilization.USER.GetSingleUserResponse;
import POJO.Deserilization.USER.InvalidResponseMessageModel;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateUser {
    private static String id;
    private static String name;
    private static String status;
    private static String gender;
    private static String email;
    private static CreatUserResponse createdUser = new CreatUserResponse();
    private static CreatUserRequest createUserRequest = new CreatUserRequest();

    @BeforeClass
    public static void setup() throws JsonProcessingException {
        // Create test user using builder constants
        createUserRequest.setName(Builder.DEFAULT_USERNAME);
        createUserRequest.setEmail(Builder.DEFAULT_EMAIL);
        createUserRequest.setGender(Builder.GENDER_FEMALE);
        createUserRequest.setStatus(Builder.STATUS_ACTIVE);

        createdUser = UserReq.CreateUser(createUserRequest, 201);
        id = createdUser.getId();
        name = createdUser.getName();
        status = createdUser.getStatus();
        gender = createdUser.getGender();
        email = createdUser.getEmail();
    }

    @Test(priority = 1)
    public void updateWithValidData() {
        // Prepare updated data
        CreatUserRequest updateRequest = new CreatUserRequest()
                .setName("Updated Name")
                .setEmail(email)  // Keep same email
                .setGender(Builder.GENDER_MALE)  // Change gender
                .setStatus("inactive");  // Change status

        // Execute update
        GetSingleUserResponse response = UserReq.updateUserUsingId(updateRequest, id);

        // Verify updates
        Assert.assertEquals(response.getId(), id, "User ID should remain same");
        Assert.assertEquals(response.getName(), "Updated Name", "Name should be updated");
        Assert.assertEquals(response.getGender(), Builder.GENDER_MALE, "Gender should be updated");
        Assert.assertEquals(response.getStatus(), "inactive", "Status should be updated");
    }

    @Test(priority = 2)
    public void updateWithInvalidData() {
        CreatUserRequest invalidRequest = new CreatUserRequest()
                .setName("")
                .setEmail("")
                .setGender("")
                .setStatus("");

        CreatUserWithInvalidbodyResponse[] errors =
                UserReq.updateUserUsingInvalidBody(invalidRequest, id);

        // Verify validation errors
        Assert.assertTrue(errors.length > 0, "Should return validation errors");
        for (CreatUserWithInvalidbodyResponse error : errors) {
            switch (error.getField()) {
                case "name":
                    Assert.assertEquals(error.getMessage(), "can't be blank");
                    break;
                case "email":
                    Assert.assertEquals(error.getMessage(), "can't be blank");
                    break;
                case "gender":
                    Assert.assertEquals(error.getMessage(), "can't be blank, can be male of female");
                    break;
                case "status":
                    Assert.assertEquals(error.getMessage(), "can't be blank");
                    break;
                default:
                    Assert.fail("Unexpected error field: " + error.getField());
            }
        }
    }

    @Test(priority = 3)
    public void updateWithInvalidEmailFormat() {
        CreatUserRequest invalidRequest = new CreatUserRequest()
                .setName(name)
                .setEmail(Builder.INVALID_EMAIL)
                .setGender(gender)
                .setStatus(status);

        CreatUserWithInvalidbodyResponse[] errors =
                UserReq.updateUserUsingInvalidBody(invalidRequest, id);

        Assert.assertEquals(errors[0].getField(), "email");
        Assert.assertEquals(errors[0].getMessage(), "is invalid");
    }

 /*   @Test(priority = 4)
    public void updateWithExistingEmail() {
        CreatUserRequest invalidRequest = new CreatUserRequest()
                .setName(name)
                .setEmail(Builder.REGISTERED_EMAIL)
                .setGender(gender)
                .setStatus(status);

        CreatUserWithInvalidbodyResponse[] errors =
                UserReq.updateUserUsingInvalidBody(invalidRequest, id);

        Assert.assertEquals(errors[0].getField(), "email");
        Assert.assertEquals(errors[0].getMessage(), "has already been taken");
    }*/

    @Test(priority = 5)
    public void updateWithInvalidId() {
        // First delete the user to make ID invalid
        UserReq.deleteUserUsingId(id);

        CreatUserRequest updateRequest = new CreatUserRequest()
                .setName("Should Fail")
                .setEmail(email)
                .setGender(gender)
                .setStatus(status);

        InvalidResponseMessageModel errorResponse =
                UserReq.updateUserUsingInvalidId(updateRequest, id);

        Assert.assertEquals(errorResponse.getMessage(), "Resource not found");
    }

    @AfterClass
    public static void cleanup() {
        try {
            if (id != null) {
                UserReq.deleteUserUsingId(id);
            }
        } catch (Exception e) {
            System.out.println("Cleanup warning: " + e.getMessage());
        }
    }
}