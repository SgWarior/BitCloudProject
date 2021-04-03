import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import com.google.gson.Gson;




public class PostExample {
    public static void main(String[] args){
        String url = "https://api.bitclout.com/api/v1/block";
        Gson gson= new Gson();

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    //    builder.addTextBody("param1", param1Value, ContentType.TEXT_PLAIN);
    //    builder.addTextBody("param2", param2Value, ContentType.TEXT_PLAIN);
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
    //    HttpResponse response = httpClient.execute(httpMethod);

        //{"Height":11427,"FullBlock":true}

    }
}