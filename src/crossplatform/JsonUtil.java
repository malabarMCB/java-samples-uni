package crossplatform;

        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    /**
     * converts object to json string
     * @param object object
     * @return json string
     */
    public static String convertToJson(Object object){
        ObjectMapper mapper= new ObjectMapper();
        String json= null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
