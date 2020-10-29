package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(final String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setUsername("회원A");
            memberA.setAge(10);
            memberA.changeTeam(teamA);
            memberA.setType(MemberType.ADMIN);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setUsername("회원B");
            memberB.setAge(10);
            memberB.changeTeam(teamA);
            memberB.setType(MemberType.USER);
            em.persist(memberB);

            Member memberC = new Member();
            memberC.setUsername("회원C");
            memberC.setAge(10);
            memberC.changeTeam(teamB);
            memberC.setType(MemberType.USER);
            em.persist(memberC);

            // FLUSH 자동 호출
            em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            em.clear();    
        
            Member findMember = em.find(Member.class, memberA.getId());

            System.out.println("findMember = " + findMember.toString());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally { 
            em.close();
        }

        emf.close();
    }
}
