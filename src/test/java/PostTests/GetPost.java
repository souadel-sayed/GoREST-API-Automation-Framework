package PostTests;


import POJO.Deserilization.POST.CreatePostResponseModel;
import POJO.Deserilization.POST.GetPostResponseModel;
import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Serialization.POST.CreatePostRequestModel;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.PostRequest;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserReq.CreateUser;
import static Requests.UserReq.updateUserUsingId;

public class GetPost {

    static String id ="";
    static String Title="";
    static String Body="";
    static String Post_id="";
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
        Post_id=PostResponseModel.getId();
        Title =PostResponseModel.getTitle();
        Body =PostResponseModel.getBody();
    }

    @Test(priority = 1)
    public static void ValidateKeysInPosts() {
        GetPostResponseModel[] getPostResponseModel = PostRequest.GetAllPosts();
        for (GetPostResponseModel post : getPostResponseModel) {
            Assert.assertNotNull(post.getId(), "Missing field: id");
            Assert.assertNotNull(post.getUserId(), "Missing field: user_id");
            Assert.assertNotNull(post.getTitle(), "Missing field: title");
            Assert.assertNotNull(post.getBody(), "Missing field: body");
        }
    }

    @Test(priority = 2)
    public static void getSinglePostUsingId(){
        GetPostResponseModel getPostResponse=PostRequest.getSinglePost(Post_id);
        Assert.assertNotNull(getPostResponse.getId(), "Missing field: id");
        Assert.assertEquals(getPostResponse.getTitle(),Title,"title not Equals");
        Assert.assertEquals(getPostResponse.getBody(),Body,"body not Equals");
    }


    @Test(priority = 3)
    public static void getPostsOfSpecificUser() {
        GetPostResponseModel[] getPostResponseModel = PostRequest.getPostsOfSpecificUser(id,200);
        for (GetPostResponseModel postResponse : getPostResponseModel) {
            Assert.assertEquals(String.valueOf(postResponse.getUserId()),id, "Post is missing the id field");
            Assert.assertNotNull(postResponse.getId(), "Post is missing the id field");
            Assert.assertNotNull(postResponse.getTitle(), "Post is missing the Title field");
            Assert.assertNotNull(postResponse.getBody(), "Post is missing the Body field.");

        }
    }

    @Test(priority = 4)
    public static void getPostUsingTitle(){
        GetPostResponseModel[] getPostResponseModels= PostRequest.getPostUsingTitle(Title,200);
        boolean titleExists = false;
        for (GetPostResponseModel Post : getPostResponseModels) {
            if (Post.getTitle().equals(Title)) {
                titleExists = true;
                break;
            }
            Assert.assertTrue(titleExists, "The title is not as expected");

        }
    }

    @AfterClass
    public void deleteUser(){
        UserReq.deleteUserUsingId(id);
    }





}
