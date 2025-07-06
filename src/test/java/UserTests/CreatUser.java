package UserTests;
import Requests.UserReq;
import Utils.Builder;
import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Deserilization.USER.CreatUserWithInvalidbodyResponse;
import POJO.Deserilization.USER.InvalidResponseMessageModel;
import POJO.Serialization.USER.CreatUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class CreatUser {
    String id="";
    SoftAssert softAssert=new SoftAssert();
    CreatUserResponse createUserResponseModel =new CreatUserResponse();
   /* @Test(priority =1)
   public void createUser() throws JsonProcessingException {
        // Build request using Builder
        CreatUserRequest request = Builder.buildDefaultUserRequest()
                .setName(Builder.DEFAULT_USERNAME)
                .setEmail(Builder.DEFAULT_EMAIL)
                .setGender(Builder.GENDER_FEMALE)
                .setStatus(Builder.STATUS_ACTIVE);

        createUserResponseModel = UserReq.CreateUser(request, 201);
        id = createUserResponseModel.getId();
        softAssert.assertEquals(createUserResponseModel.getName(), Builder.DEFAULT_USERNAME, "Name mismatch");
        softAssert.assertNotNull(createUserResponseModel.getId(), "ID should not be null");
        softAssert.assertEquals(createUserResponseModel.getGender(), Builder.GENDER_FEMALE, "Gender mismatch");
        softAssert.assertAll();
}*/
    @Test(priority =2)
    public void testInvalidUserCreation() {
        // Build invalid request
        CreatUserRequest invalidRequest = Builder.buildUserRequestWithInvalidEmail();

        // Execute and verify error response
        CreatUserWithInvalidbodyResponse[] errors = UserReq.createUserWithInvalidFields(invalidRequest, 422);
        CreatUserWithInvalidbodyResponse[] expectedErrors = Builder.buildExpectedValidationErrors("email");

        softAssert.assertEquals(errors[0].getField(), expectedErrors[0].getField(), "Field mismatch");
        softAssert.assertAll();
    }


    @Test(priority =3)
    public void createUserWithEmptyData() throws JsonProcessingException {
        // Build invalid request
        CreatUserRequest request = Builder.buildUserWithEmptyData();

        // Execute and verify error response
        CreatUserWithInvalidbodyResponse[] response = UserReq.createUserWithInvalidFields(request, 422);
        Map<String, String> expectedErrors = Builder.getExpectedEmptyDataErrors();

        for (CreatUserWithInvalidbodyResponse error : response) {
            String field = error.getField();
            softAssert.assertTrue(expectedErrors.containsKey(field),
                    "Unexpected field: " + field);
            softAssert.assertEquals(error.getMessage(), expectedErrors.get(field),
                    "Message does not match for field: " + field);
        }
        softAssert.assertAll();
    }
    @Test(priority =4)
    public void createUserWithoutAuth() {
        CreatUserRequest request = Builder.buildValidUserWithoutAuth();

        InvalidResponseMessageModel response =
                UserReq.CreateUserWithOutAuth(request, 401);

        InvalidResponseMessageModel expectedError = Builder.buildExpectedAuthError();

        softAssert.assertEquals(response.message, expectedError.message,
                "Message does not match");
        softAssert.assertAll();
    }



}