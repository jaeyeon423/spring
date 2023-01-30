package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();
            Object o = resultList.get(0);
            Object[] result = (Object[]) o;

            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);

//            Member findMember = reslt.get(0);
//            findMember.setUsername("jaeyeon");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }

}
