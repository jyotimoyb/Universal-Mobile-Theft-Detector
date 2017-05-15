
package hello;

import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;




@RestController
public class Controller {

    
    private final AtomicLong counter = new AtomicLong();
    
    @Inject
    ReceiverService r;
    
    private final Logger log = LoggerFactory.getLogger(Controller.class);

    @CrossOrigin
    @RequestMapping(value = "/api/publish",
    		 method = RequestMethod.POST,
    		    produces = MediaType.APPLICATION_JSON_VALUE)
    public String getmessages(@RequestBody String json) { 	   	 
    	 	 
    	JsonParser parser = new JsonParser();           
        JsonObject rootObj = parser.parse(json).getAsJsonObject(); 
        String userId = rootObj.get("userId").getAsString();
        String cmd = rootObj.get("cmd").getAsString();
    	r.pubSample(userId,cmd);
    	JsonObject j = new JsonObject();
    	  	
    	return j.toString();
    }
    
    @RequestMapping(value = "/api/updateUser",
   		 method = RequestMethod.POST,
   		    produces = MediaType.APPLICATION_JSON_VALUE)
   public String updateUser(@RequestBody String json) { 	   	 
   	 	 
   	JsonParser parser = new JsonParser();           
       JsonObject rootObj = parser.parse(json).getAsJsonObject(); 
       String userId = rootObj.get("userId").getAsString();
     
       return r.updateOneUser(userId, rootObj);  	
   }
    
    @RequestMapping(value = "/api/getUser",
   		 method = RequestMethod.GET,
   		    produces = MediaType.APPLICATION_JSON_VALUE)
   public String getUser(@RequestParam String userId) { 	   	 
   	 	 
   	 	return r.getOneUser(userId);
   }
    
}
