package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("userA");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));


            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);
            
            em.flush();
            em.clear();

            System.out.println("===============start==============");
            Member findMember = em.find(Member.class, member.getId()); //<- collection들은 모두 지연 로딩 LAZY

            //homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity"); <- 잘못된 방법 side effect 발생 확률 높음

//            findMember.setHomeAddress(new Address("newCity", "street", "10000")); //<- 새로 값을 넣어줘야한다.
//
//            //치킨을 한식으로 변경
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            //주소 변경
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//            findMember.getAddressHistory().add(new Address("new1", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }

}
