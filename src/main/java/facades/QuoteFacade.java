package facades;

import dtos.QuoteDTO;
import dtos.UserDTO;

import entities.Quote;
import entities.User;


import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class QuoteFacade {

    private static QuoteFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private QuoteFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static QuoteFacade getQuoteFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new QuoteFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }



    public QuoteDTO createQuote(QuoteDTO qdto){
        Quote h = new Quote(qdto.getQuote());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new QuoteDTO(h);
    }

    public boolean removeQuoteFromUser(Long userId, Long QuoteId){
        EntityManager em = getEntityManager();
        User u = em.find(User.class, userId);
        Quote q = em.find(Quote.class, QuoteId);
        if(u == null || q == null)
            throw new IllegalArgumentException("user or Quote not found");
        u.removeQuote(q);
        try {
            em.getTransaction().begin();
            em.merge(u);
            //em.merge(h);
            em.getTransaction().commit();
        }catch (Exception e){
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public UserDTO addQuote(Long userId, Long QuoteId){
        EntityManager em = getEntityManager();
        User u = em.find(User.class, userId);
        Quote q = em.find(Quote.class, QuoteId);
        if(u == null || q == null)
            throw new IllegalArgumentException("user or Quote not found");
        u.addQuote(q);
        try {
            em.getTransaction().begin();
            em.merge(u);
            //em.merge(h);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(u);
    }

    public UserDTO getById(long id) { //throws RenameMeNotFoundException {
        EntityManager em = emf.createEntityManager();
        User u = em.find(User.class, id);
//        if (rm == null)
//            throw new RenameMeNotFoundException("The RenameMe entity with ID: "+id+" Was not found");
        return new UserDTO(u);
    }



    public List<QuoteDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Quote> query = em.createQuery("SELECT q FROM Quote q", Quote.class);
        List<Quote> quotes = query.getResultList();
        return QuoteDTO.getDtos(quotes);
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        QuoteFacade qf = getQuoteFacade(emf);
        qf.getAll().forEach(dto->System.out.println(dto));
    }

}
