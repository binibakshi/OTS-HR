package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.EmpToMossad;
import teachers.biniProject.Repository.EmpToMossadRepository;

import java.util.List;

@Service
public class EmpToMossadService {

    @Autowired
    TeacherEmploymentDetailsService teacherEmploymentDetailsService;
    @Autowired
    private EmpToMossadRepository empToMossadRepository;

    public List<EmpToMossad> findAll() {
        return this.empToMossadRepository.findAll();
    }

    public void deleteByEmpId(String empId) {
//        this.empToMossadRepository.deleteAllByEmpId(empId);
        this.teacherEmploymentDetailsService.deleteByEmpId(empId);
    }

    public List<EmpToMossad> findAllByMossad(int mossadId) {
        return this.empToMossadRepository.findAllByMossadId(mossadId);
    }
}
