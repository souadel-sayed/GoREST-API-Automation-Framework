package TodoTests;

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


public class GetTodoTests {
    String id ="";
    static String Todo_id="";
    static String Title="";
    static String Status="";
    static String DueOn="";
    private CreatUserResponse userResponse;
    CreateTodoRequestModel createTodoRequestModel=new CreateTodoRequestModel();
    @BeforeClass
    public void Precondition() throws JsonProcessingException {
        CreatUserRequest userRequest = Builder.buildDefaultUserRequest();
        userResponse = CreateUser(userRequest, 201);
        id = userResponse.getId();
        createTodoRequestModel.setTitle("Check emails/messages for urgent tasks");
        createTodoRequestModel.setDueOn("2024-10-28");
        createTodoRequestModel.setStatus("pending");
        GetTodoResponseModel getTodo=TodoRequests.createTodo(createTodoRequestModel,id,201);
        Todo_id = String.valueOf(getTodo.getId());
        Title=getTodo.getTitle();
        Status=getTodo.getStatus();
        DueOn =getTodo.getDueOn();

    }
    @Test(priority = 1)
    public static void ValidateKeysInTodos() {
        GetTodoResponseModel[] getPostResponseModel = TodoRequests.GetAllTodos(200);
        for (GetTodoResponseModel Todo : getPostResponseModel) {
            Assert.assertNotNull(Todo.getId(), "Missing field: id");
            Assert.assertNotNull(Todo.getUserId(), "Missing field: user_id");
            Assert.assertNotNull(Todo.getTitle(), "Missing field: title");
            Assert.assertNotNull(Todo.getDueOn(), "Missing field: DueOn");
            Assert.assertNotNull(Todo.getStatus(), "Missing field: Status");
        }
    }
    @Test(priority = 2)
    public static void GetSingleTodoUsingId(){
        GetTodoResponseModel getTodoResponseModel =TodoRequests.GetSingleTodo(Todo_id,200);
        Assert.assertEquals(getTodoResponseModel.getTitle(),Title,"Status are Not Same");
        Assert.assertEquals(getTodoResponseModel.getStatus(),Status,"Status are Not Same");
        Assert.assertEquals(getTodoResponseModel.getDueOn(),DueOn,"Due On are Not Same");

    }

    @AfterClass
    public void teardown(){
        UserReq.deleteUserUsingId(id);
    }
}
