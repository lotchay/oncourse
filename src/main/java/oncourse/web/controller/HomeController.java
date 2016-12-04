package oncourse.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import oncourse.model.User;
import oncourse.model.dao.CourseDao;
import oncourse.model.dao.DepartmentDao;
import oncourse.model.dao.GradeDao;
import oncourse.model.dao.GradeRecordDao;
import oncourse.model.dao.ProgramDao;
import oncourse.security.SecurityUtils;

@Controller
public class HomeController {
	
	@Autowired
    private CourseDao courseDao;
	
	@Autowired
    private DepartmentDao departmentDao;
    
    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private GradeRecordDao gradeRecordDao;
    
    @Autowired
    private ProgramDao programDao;
    
    @RequestMapping("/")
    public String list( ModelMap models )
    {
        int year = Calendar.getInstance().get( Calendar.YEAR );
        List<Integer> years = new ArrayList<Integer>();
        for( int i = 0; i < 4; ++i )
            years.add( year - i );
        models.put( "years", years );
        models.put( "courses", courseDao.getCourses() );
        models.put( "grades", gradeDao.getGrades() );

        User user = SecurityUtils.getUser();
        models.put( "user", user );
        models.put( "gradeRecords", gradeRecordDao.getGradeRecords( user ) );
        models.put( "programs", programDao.getPrograms() );
        models.put( "departments", departmentDao.getDepartments() );
        models.put( "courses", courseDao.getCourses() );
        return "index";
    }
}