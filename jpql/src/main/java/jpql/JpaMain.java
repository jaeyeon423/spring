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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("member2");
            member3.setTeam(teamB);


            em.persist(member1);
            em.persist(member2);
            em.persist(member3);


            em.flush();
            em.clear();

            List<Member> resultList = em.createNamedQuery("Member.findByUserName", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();

            System.out.println("========================================");
            for (Member member : resultList) {
                System.out.println("member = " + member);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }

}
