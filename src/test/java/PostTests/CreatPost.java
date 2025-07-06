package PostTests;

import POJO.Deserilization.POST.CreatePostResponseModel;
import POJO.Deserilization.POST.InvalidResponseMessage;
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

public class CreatPost {
    private CreatUserResponse userResponse;
    String id = "";
    CreatePostRequestModel createPostRequestModel = new CreatePostRequestModel();

    @BeforeClass
    public void Precondition() throws JsonProcessingException {
       //createUserRequestModel==CreateUser
        CreatUserRequest userRequest = Builder.buildDefaultUserRequest();
        userResponse = CreateUser(userRequest, 201);
        id = userResponse.getId();
    }
    @Test(priority = 1)
    public void createNewPost() {

        createPostRequestModel.setTitle("The Programmer's Best Friend");
        createPostRequestModel.setBody("Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");
        CreatePostResponseModel PostResponseModel = PostRequest.CreatePosts(createPostRequestModel,id,201);
        Assert.assertNotNull(PostResponseModel.getId(), "Id is not As Expected");
        Assert.assertEquals(PostResponseModel.getTitle(),"The Programmer's Best Friend");
        Assert.assertEquals(PostResponseModel.getBody(),"Every great coder knows that a cup of coffee fuels productivity. Here’s how to brew the perfect cup!");

    }

    @Test (priority = 2)
    public void CreatePostWithEmptyBody() {
        createPostRequestModel.setTitle("");
        createPostRequestModel.setBody("");
        POJO.Deserilization.POST.InvalidResponseMessage[] MessageResponseModel = PostRequest.InvalidCreatePosts(createPostRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"title");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"can't be blank");
        Assert.assertEquals(MessageResponseModel[1].getField(),"body");
        Assert.assertEquals(MessageResponseModel[1].getMessage(),"can't be blank");
    }
    @Test (priority = 3)
    public void CreatePostWithDoesNotExistedUser() {
        UserReq.deleteUserUsingId(id);
        createPostRequestModel.setTitle("Stay Organized");
        createPostRequestModel.setBody("Use a to-do list or a planner to keep track of tasks and deadlines. Organization boosts efficiency!");
        InvalidResponseMessage[] MessageResponseModel = PostRequest.InvalidCreatePosts(createPostRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"user");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"must exist");
    }



    @AfterClass
    public void teardown(){
        UserReq.deleteUserUsingId(id);
    }


}
