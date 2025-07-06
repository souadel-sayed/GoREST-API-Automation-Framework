package Requests;


import POJO.Deserilization.POST.InvalidResponseMessage;
import POJO.Deserilization.TODO.GetTodoResponseModel;
import POJO.Serialization.TODO.CreateTodoRequestModel;
import Utils.Builder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TodoRequests {
    public static GetTodoResponseModel[] GetAllTodos(Integer statusCode){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .get(Builder.base_url + Builder.TODO_ENDPOINT)
                .then().statusCode(statusCode).extract()
                .as(GetTodoResponseModel[].class);
    }


    public static GetTodoResponseModel createTodo(CreateTodoRequestModel createTodoRequestModel, String Id, Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(createTodoRequestModel)
                .post(Builder.base_url + Builder.userEndPoint+Id+Builder.TODO_ENDPOINT)
                .then().statusCode(StatusCode)
                .extract().as(GetTodoResponseModel.class);
    }

    public static InvalidResponseMessage[] InvalidCreateTodo(CreateTodoRequestModel createTodoRequestModel, String Id, Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(createTodoRequestModel)
                .post(Builder.base_url + Builder.userEndPoint+Id+Builder.TODO_ENDPOINT)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
    public static GetTodoResponseModel GetSingleTodo(String id,Integer StatusCode){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .get(Builder.base_url +Builder.TODO_ENDPOINT+id)
                .then().statusCode(StatusCode)
                .extract().as(GetTodoResponseModel.class);
    }

}
