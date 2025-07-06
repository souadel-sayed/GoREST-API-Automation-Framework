package Requests;

import POJO.Deserilization.USER.*;
import POJO.Serialization.USER.CreatUserRequest;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserReq {
    public static CreatUserResponse CreateUser(CreatUserRequest CreatUserRequest, int statusCode) throws JsonProcessingException {
        return given().log().all()
                .headers(Builder.getHeaders())
                .body(CreatUserRequest)
                .post(Builder.base_url + Builder.userEndPoint)
                .then().statusCode(statusCode)
                .extract().as(CreatUserResponse.class);
    }

    public static CreatUserWithInvalidbodyResponse[] createUserWithInvalidFields(CreatUserRequest CreatUserRequest, int statusCode) {
        return given().log().all()
                .headers(Builder.getHeaders())
                .body(CreatUserRequest)
                .post(Builder.base_url + Builder.userEndPoint)
                .then().statusCode(statusCode)
                .extract().as(CreatUserWithInvalidbodyResponse[].class);
    }
    public static InvalidResponseMessageModel CreateUserWithOutAuth(CreatUserRequest createUserRequestModel, int statuscode) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(createUserRequestModel)
                .post(Builder.base_url + Builder.userEndPoint)
                .then().statusCode(statuscode)
                .extract().as(InvalidResponseMessageModel.class);
    }

    public static GetSingleUserResponse getSingleUserUsingId(String id) {
        return given().log().all()
                .headers(Builder.getHeaders())
                .get(Builder.base_url + Builder.userEndPoint + id)
                .then().statusCode(200).extract().as(GetSingleUserResponse.class);
    }

    public static GetUsersResponse[] getUserUsingName(String name) {
        return given().log().all()
               // .contentType(ContentType.JSON)
                .headers(Builder.getHeaders())
                .queryParam("name", name)
                .get(Builder.base_url + Builder.userEndPoint).as(GetUsersResponse[].class);}


    public static GetUsersResponse[] getUserUsingStatus(String status) {
        return given().log().all()
                //.contentType(ContentType.JSON)
                .headers(Builder.getHeaders())
                .queryParam("status", status)
                .get(Builder.base_url + Builder.userEndPoint)
                .as(GetUsersResponse[].class);}
    public static GetUsersResponse[] getUserUsingGender(String gender) {
        return given().log().all()
                .headers(Builder.getHeaders())
                .queryParam("gender", gender)
                .get(Builder.base_url + Builder.userEndPoint)
                .as(GetUsersResponse[].class);
    }
    public static Response deleteUserUsingId(String id){
        return  given().log().all()
                .headers(Builder.getHeaders())
                .delete(Builder.base_url+ Builder.userEndPoint+id);
    }





    public static InvalidResponseMessageModel deleteUserUsingInvalidId(String id){
        return  given().log().all()
                .headers(Builder.getHeaders())
                .delete(Builder.base_url+ Builder.userEndPoint+id)
                .then().statusCode(404)
                .extract()
                .as(InvalidResponseMessageModel.class);
    }
    public static InvalidResponseMessageModel deleteUserWithOutAuth(String id){
        return  given().log().all()
                .delete(Builder.base_url+ Builder.userEndPoint+id)
                .then().statusCode(401)
                .extract()
                .as(InvalidResponseMessageModel.class);

    }
    public static GetSingleUserResponse updateUserUsingId(CreatUserRequest updatedUser,String id){
        return  given().log().all()
                .headers(Builder.getHeaders())
                .body(updatedUser)
                .put(Builder.base_url+ Builder.userEndPoint+id)
                .then().statusCode(200)
                .extract().as(GetSingleUserResponse.class);

    }
    public static CreatUserWithInvalidbodyResponse[] updateUserUsingInvalidBody(CreatUserRequest updatedUser,String id){
        return  given().log().all()
                .headers(Builder.getHeaders())
                .body(updatedUser)
                .put(Builder.base_url+ Builder.userEndPoint+id)
                .then().statusCode(422)
                .extract().as(CreatUserWithInvalidbodyResponse[].class);

    }
    public static InvalidResponseMessageModel updateUserUsingInvalidId(CreatUserRequest updatedUser,String id){
        return  given().log().all()
                .headers(Builder.getHeaders())
                .body(updatedUser)
                .put(Builder.base_url+ Builder.userEndPoint+id)
                .then().statusCode(404)
                .extract().as(InvalidResponseMessageModel.class);
    }

}
