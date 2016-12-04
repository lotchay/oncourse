package oncourse.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import oncourse.model.Program;
import oncourse.model.dao.ProgramDao;

@RestController
public class ProgramService {
	@Autowired
    private ProgramDao programDao;
	
	@RequestMapping(value = "/service/program/{id}", method = RequestMethod.GET)
    public Program getProgram(@PathVariable Long id, ModelMap models){
        return programDao.getProgram(id);
    }
}
