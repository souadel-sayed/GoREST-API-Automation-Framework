package PostTests;

import POJO.Deserilization.POST.CreatePostResponseModel;
import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Deserilization.USER.InvalidResponseMessageModel;
import POJO.Serialization.POST.CreatePostRequestModel;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.PostRequest;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserReq.CreateUser;

public class DeletPost {

    static String id="";
    String Post_Id="";
    CreatePostRequestModel createPostRequestModel = new CreatePostRequestModel();
    private CreatUserResponse userResponse;

    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        CreatUserRequest userRequest = Builder.buildDefaultUserRequest();
        userResponse = CreateUser(userRequest, 201);
        id = userResponse.getId();

        createPostRequestModel.setTitle("The Programmer's Best Friend");
        createPostRequestModel.setBody("Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");
        CreatePostResponseModel PostResponseModel = PostRequest.CreatePosts(createPostRequestModel,id,201);
        Post_Id=PostResponseModel.getId();

    }

    @Test(priority =1)
    public void deletePost(){
        Response response=PostRequest.deletePostWithValidId(Post_Id);
        response.prettyPrint();
        response.then().statusCode(204);
        Assert.assertTrue(response.body().asString().isEmpty(), "Response body is not empty");
    }

    @Test(priority =2)
    public void delete_Post_with_invalid_ID(){
        InvalidResponseMessageModel deleteUsingInvalidIDModel=PostRequest.deletePostWithInvalidId(Post_Id,404);
        Assert.assertEquals(deleteUsingInvalidIDModel.message,"Resource not found");
    }
    @AfterClass
    public void teardown(){
        UserReq.deleteUserUsingId(id);
    }





}
