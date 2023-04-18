package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ExternalApiFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * @author lam@cphbusiness.dk
 */
@Path("ext")
public class ExternalApiResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExternalApiFacade FACADE =  ExternalApiFacade.getExternalApiFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getInfoForAll() {
//        return "{\"msg\":\"Hello anonymous person\"}";
//    }
//
    @GET
    @Path("kanye")
    @Produces(MediaType.APPLICATION_JSON)
    public String getKanyeQuote() throws Exception {

        try {
            return "{\"quote\":\"" + FACADE.getKanyeQuote("https://api.kanye.rest") + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Quote not found";
    }

}