package RepresentationJSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Representation <T>{

    private long code;

    @Length(max = 3)
    private T data;

    public Representation() {}

    public Representation(long code, T data) {
        this.code = code;
        this.data = data;
    }

    @JsonProperty
    public long getCode() {
        return code;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}

/*
        Esto es lo que vamos a representar en nuestra clase
    {
     "code": 200,
     "data": {
       "id": 1,
       "name": "Part 1",
       "code": "PART_1_CODE"
    }
}
 */