package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.KanyeDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExternalApiFacade {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static ExternalApiFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ExternalApiFacade() {}


    public static ExternalApiFacade getExternalApiFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ExternalApiFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public String getHttpResponseBody(String url) throws Exception {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest
                .newBuilder(URI.create(url))
                .header("accept", "application/json")
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        KanyeDTO gsonString = gson.fromJson(response.body(), KanyeDTO.class);
        System.out.println(gsonString);

        return gsonString.getQuote();
    }

    public static void main(String[] args) throws Exception {
        ExternalApiFacade facade = new ExternalApiFacade();
        try {
            facade.getHttpResponseBody("https://api.kanye.rest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
