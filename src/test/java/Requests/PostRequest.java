package Requests;

import POJO.Deserilization.POST.CreatePostResponseModel;
import POJO.Deserilization.POST.GetPostResponseModel;
import POJO.Deserilization.POST.InvalidResponseMessage;
import POJO.Deserilization.USER.InvalidResponseMessageModel;
import POJO.Serialization.POST.CreatePostRequestModel;
import Utils.Builder;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PostRequest {
    public static GetPostResponseModel[] GetAllPosts(){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .get(Builder.base_url + Builder.POST_ENDPOINT).as(GetPostResponseModel[].class);
    }

    public static GetPostResponseModel getSinglePost(String id){
        return  RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .get(Builder.base_url+ Builder.POST_ENDPOINT+id)
                .then().statusCode(200).extract().as(GetPostResponseModel.class);
    }
    public static  GetPostResponseModel[] getPostsOfSpecificUser(String id,Integer statusCode){
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .get(Builder.base_url+Builder.userEndPoint+id+ Builder.POST_ENDPOINT)
                .then().statusCode(statusCode).extract().as(GetPostResponseModel[].class);
    }
    public static GetPostResponseModel[]  getPostUsingTitle(String title,int StatusCode){
        return  RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .queryParam("title", title)
                .get(Builder.base_url+ Builder.POST_ENDPOINT).then().statusCode(StatusCode).extract().as(GetPostResponseModel[].class);
    }
    public static CreatePostResponseModel CreatePosts(CreatePostRequestModel createPostRequestModel, String id, int statusCode) {
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(createPostRequestModel)
                .post(Builder.base_url + Builder.userEndPoint+id+Builder.POST_ENDPOINT)
                .then().statusCode(statusCode)
                .extract().as(CreatePostResponseModel.class);
    }
    public static InvalidResponseMessage[] InvalidCreatePosts(CreatePostRequestModel createPostRequestModel, String id, int statusCode) {
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(createPostRequestModel)
                .post(Builder.base_url + Builder.userEndPoint+id+Builder.POST_ENDPOINT)
                .then().statusCode(statusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
    public static Response deletePostWithValidId(String id){
        return  RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .delete(Builder.base_url+ Builder.POST_ENDPOINT+id);
    }
    public static InvalidResponseMessageModel deletePostWithInvalidId(String id, int StatusCode){
        return   deletePostWithValidId(id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessageModel.class);
    }




    public static GetPostResponseModel UpdatePost(CreatePostRequestModel updatePost,String id,Integer StatusCode)  {
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(Builder.base_url + Builder.POST_ENDPOINT+ id)
                .then().statusCode(StatusCode)
                .extract().as(GetPostResponseModel.class);
    }
    public static InvalidResponseMessage[] InvalidUpdatePost(CreatePostRequestModel updatePost,String id,Integer StatusCode) throws JsonProcessingException {
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(Builder.base_url + Builder.POST_ENDPOINT+ id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage[].class);
    }
    public static InvalidResponseMessage InvalidIDUpdatePost(CreatePostRequestModel updatePost, String id, Integer StatusCode)  {
        return RestAssured.given().log().all()
                .headers(Builder.getHeaders())
                .contentType(ContentType.JSON)
                .body(updatePost)
                .patch(Builder.base_url + Builder.POST_ENDPOINT+ id)
                .then().statusCode(StatusCode)
                .extract().as(InvalidResponseMessage.class);
    }
}
