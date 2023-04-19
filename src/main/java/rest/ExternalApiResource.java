package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.QuoteDTO;
import facades.ExternalApiFacade;
import facades.InternalApiFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @author lam@cphbusiness.dk
 */
@Path("ext")
public class ExternalApiResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExternalApiFacade EXTERNAL_API_FACADE =  ExternalApiFacade.getExternalApiFacade(EMF);
    private static final InternalApiFacade INTERNAL_API_FACADE =  InternalApiFacade.getInternalApiFacade(EMF);
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
//            return EXTERNAL_API_FACADE.getKanyeQuote("https://api.kanye.rest");
            return GSON.toJson(EXTERNAL_API_FACADE.getKanyeQuote("https://api.kanye.rest"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Quote not found";
    }

    @POST
    @Path("/kanye/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addQuoteToUser(@PathParam("username") String userName, String quote) {
        QuoteDTO quoteDTO = GSON.fromJson(quote, QuoteDTO.class);
        quoteDTO = INTERNAL_API_FACADE.createQuote(quoteDTO);
        INTERNAL_API_FACADE.addQuote(userName, quoteDTO.getId());
        System.out.println("Quote added to user" + quoteDTO);
        return Response.ok().entity(quoteDTO).build();
    }



}