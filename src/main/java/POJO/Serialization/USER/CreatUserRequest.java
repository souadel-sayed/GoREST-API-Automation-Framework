package POJO.Serialization.USER;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "email",
        "gender",
        "status"
})
public class CreatUserRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("status")
    private String status;

    // Modified setters for method chaining
    public CreatUserRequest setName(String name) {
        this.name = name;
        return this;
    }

    public CreatUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public CreatUserRequest setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public CreatUserRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    // Keep existing getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }
}