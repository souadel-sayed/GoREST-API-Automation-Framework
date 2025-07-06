
package POJO.Deserilization.USER;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message"
})
public class InvalidResponseMessageModel {
    @JsonProperty("message")
    public String message;
    public String getMessage() {
        return message;
    }
}

