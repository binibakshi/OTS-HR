package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.Mossadot;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Repository.MossadotRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;

import java.util.List;

@Service
public class MossadotService {

    @Autowired
    MossadotRepository mossadotRepository;

    @Autowired
    TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

    @Autowired
    ConvertHoursService convertHoursService;

    public int fixMossadotHours(int mossadId) {

        double frontalSum = 0;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();
        Mossadot mossad = new Mossadot(this.mossadotRepository.findById(mossadId).get());

        frontalSum = this.teacherEmploymentDetailsRepository.findByMossadId(mossadId)
                .stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                .mapToDouble(TeacherEmploymentDetails::getHours).sum();
        mossad.setCurrHours((int) frontalSum);
        this.mossadotRepository.save(mossad);
        return (int) frontalSum;
    }
}
