package TodoTests;


import POJO.Deserilization.POST.InvalidResponseMessage;
import POJO.Deserilization.TODO.GetTodoResponseModel;
import POJO.Deserilization.USER.CreatUserResponse;
import POJO.Serialization.TODO.CreateTodoRequestModel;
import POJO.Serialization.USER.CreatUserRequest;
import Requests.TodoRequests;
import Requests.UserReq;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Requests.UserReq.CreateUser;

public class CreatTodo {
    String id = "";

    CreateTodoRequestModel createTodoRequestModel=new CreateTodoRequestModel();
    private CreatUserResponse userResponse;
    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        CreatUserRequest userRequest = Builder.buildDefaultUserRequest();
        userResponse = CreateUser(userRequest, 201);
        id = userResponse.getId();
    }

    @Test(priority = 1)
    public void CreateTodo(){
        createTodoRequestModel.setTitle("Check emails/messages for urgent tasks");
        createTodoRequestModel.setDueOn("2024-10-28");
        createTodoRequestModel.setStatus("pending");
        GetTodoResponseModel getTodo= TodoRequests.createTodo(createTodoRequestModel,id,201);
        Assert.assertNotNull(getTodo.getId());
        Assert.assertEquals(getTodo.getTitle(),"Check emails/messages for urgent tasks");
        Assert.assertEquals(getTodo.getDueOn(),"2024-10-28T00:00:00.000+05:30");
        Assert.assertEquals(getTodo.getStatus(),"pending");
    }
    @Test (priority = 2)
    public void CreateTodoWithEmptyBody() {
        createTodoRequestModel.setTitle("");
        createTodoRequestModel.setDueOn("");
        createTodoRequestModel.setStatus("");
        InvalidResponseMessage[] MessageResponseModel = TodoRequests.InvalidCreateTodo(createTodoRequestModel,id,422);
        for (InvalidResponseMessage TodoResponse :MessageResponseModel){
            if (TodoResponse.getField().equals("title")){
                Assert.assertEquals(TodoResponse.getMessage(),"can't be blank","Title validation message mismatch");
            }
            if (TodoResponse.getField().equals("status")){
                Assert.assertEquals(TodoResponse.getMessage(),"can't be blank, can be pending or completed","Status validation message mismatch");
            }
        }
    }


    @Test(priority =3)
    public void CreatePostWithDoesNotExistedUser(){
        UserReq.deleteUserUsingId(id);
        createTodoRequestModel.setTitle("Check emails/messages for urgent tasks");
        createTodoRequestModel.setDueOn("2024-10-28");
        createTodoRequestModel.setStatus("pending");
        InvalidResponseMessage[] MessageResponseModel =TodoRequests.InvalidCreateTodo(createTodoRequestModel,id,422);
        Assert.assertEquals(MessageResponseModel[0].getField(),"user");
        Assert.assertEquals(MessageResponseModel[0].getMessage(),"must exist");
    }

    @AfterClass
    public void teardown(){
        UserReq.deleteUserUsingId(id);
    }
}