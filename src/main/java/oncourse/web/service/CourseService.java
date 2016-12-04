package oncourse.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import oncourse.model.Course;
import oncourse.model.dao.CourseDao;

@RestController
public class CourseService{	
	@Autowired
    private CourseDao courseDao;
	
	@RequestMapping(value = "/service/courses", method = RequestMethod.POST)
    public Course addCourse(@RequestBody Course course){
        return courseDao.saveCourse(course);
    }
	
	@RequestMapping(value = "/service/course/{id}", method = RequestMethod.PUT)
    public void updateCourse(@PathVariable Long id, @RequestBody Course course){
        course.setId(id);
        courseDao.saveCourse(course);
    }
}
