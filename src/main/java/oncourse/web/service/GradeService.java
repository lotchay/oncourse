package oncourse.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import oncourse.model.GradeRecord;
import oncourse.model.GradeRecordWrapper;
import oncourse.model.Term;
import oncourse.model.dao.CourseDao;
import oncourse.model.dao.GradeDao;
import oncourse.model.dao.GradeRecordDao;
import oncourse.security.SecurityUtils;

@RestController
public class GradeService {
	
	@Autowired
    private CourseDao courseDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private GradeRecordDao gradeRecordDao;
	
	@RequestMapping(value = "/service/grades", method = RequestMethod.POST)
    public GradeRecord addGrade(@RequestBody GradeRecordWrapper gradeRecordWrapper){
		String season = gradeRecordWrapper.getSeason();
		Integer year = gradeRecordWrapper.getYear();
		Long courseId = gradeRecordWrapper.getCourseId();
		Long gradeId = gradeRecordWrapper.getGradeId();
		GradeRecord gradeRecord = new GradeRecord();
        gradeRecord.setStudent(SecurityUtils.getUser());
        gradeRecord.setTerm(new Term(year, season));
        gradeRecord.setCourse(courseDao.getCourse(courseId));
        gradeRecord.setGrade(gradeDao.getGrade(gradeId));
		return gradeRecordDao.saveGradeRecord(gradeRecord);
    }

}
