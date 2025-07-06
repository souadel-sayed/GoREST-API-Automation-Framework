package UserTests;

import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Deserilization.USER.GetSingleUserResponse;
import POJO.Deserilization.USER.GetUsersResponse;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GetUser {
    private static String id;
    private static String name;
    private static String status;
    private static String gender;
    private static CreatUserResponse createUserResponseModel = new CreatUserResponse();
    private static CreatUserRequest createUserRequestModel = new CreatUserRequest();


    @BeforeClass
    public static void setup() throws JsonProcessingException {
        // Create test user using builder constants
        createUserRequestModel.setName(Builder.DEFAULT_USERNAME);
        createUserRequestModel.setEmail(Builder.DEFAULT_EMAIL);
        createUserRequestModel.setGender(Builder.GENDER_FEMALE);
        createUserRequestModel.setStatus(Builder.STATUS_ACTIVE);

        createUserResponseModel = UserReq.CreateUser(createUserRequestModel, 201);
        id = createUserResponseModel.getId();
        name = createUserResponseModel.getName();
        status = createUserResponseModel.getStatus();
        gender = createUserResponseModel.getGender();
    }

    @Test(priority = 1)
    public void getSingleUserUsingId() {
        GetSingleUserResponse response = UserReq.getSingleUserUsingId(id);

        Assert.assertEquals(response.getId(), id, "User ID mismatch");
        Assert.assertEquals(response.getName(), Builder.DEFAULT_USERNAME, "Username mismatch");
        Assert.assertEquals(response.getEmail(), Builder.DEFAULT_EMAIL, "Email mismatch");
        Assert.assertEquals(response.getGender(), Builder.GENDER_FEMALE, "Gender mismatch");
        Assert.assertEquals(response.getStatus(), Builder.STATUS_ACTIVE, "Status mismatch");
    }

    @Test(priority = 2)
    public void getUsersUsingName() {
        GetUsersResponse[] users = UserReq.getUserUsingName(name);

        Assert.assertTrue(users.length > 0, "No users found with the given name");
        boolean found = false;
        for (GetUsersResponse user : users) {
            if (user.getName().equals(name)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Expected user not found in search results");
    }
    @Test(priority = 3)
    public void getUsersUsingStatus() {
        GetUsersResponse[] users = UserReq.getUserUsingStatus(status);

        Assert.assertTrue(users.length > 0, "No users found with the given status");
        boolean statusFound = false;
        for (GetUsersResponse user : users) {
            if (user.getStatus().equals(status)) {
                statusFound = true;
                break;
            }
        }
        Assert.assertTrue(statusFound, "Expected user not found in filtered results");
    }

    @Test(priority = 4)
    public void getUsersUsingGender() {
        GetUsersResponse[] users = UserReq.getUserUsingGender(gender);

        Assert.assertTrue(users.length > 0, "No users found with the given gender");
        boolean statusFound = false;
        for (GetUsersResponse user : users) {
            if (user.getGender().equals(gender)) {
                statusFound = true;
                break;
            }
        }
        Assert.assertTrue(statusFound, "Expected user not found in filtered results");
    }

    @AfterClass
    public static void cleanup() {
        if (id != null) {
            UserReq.deleteUserUsingId(id);
        }
    }
}


