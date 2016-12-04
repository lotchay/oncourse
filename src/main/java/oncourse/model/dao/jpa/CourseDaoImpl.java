package oncourse.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import oncourse.model.Course;
import oncourse.model.dao.CourseDao;

@Repository
public class CourseDaoImpl implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Course getCourse( Long id )
    {
        return entityManager.find( Course.class, id );
    }

    @Override
    public List<Course> getCourses()
    {
        String query = "from Course where obsolete = false order by code";

        return entityManager.createQuery( query, Course.class ).getResultList();
    }

    @Override
    @Transactional
    public Course saveCourse( Course course )
    {
        return entityManager.merge( course );
    }
    
    /*@Override
    public List<String> searchJobNoFTS(String term) {
    	String query = "select j.jobTitle from JobPosting j where upper(j.jobTitle) like ?1";
    	return em
    			.createQuery(query, String.class)
    			.setParameter(1, "%" + term.toUpperCase() + "%")
    			.getResultList();
    }*/

}
