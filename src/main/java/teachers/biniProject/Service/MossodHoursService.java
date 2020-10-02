package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.MossadHours;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.HelperClasses.MossadHoursComositeKey;
import teachers.biniProject.Repository.MossadHoursRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;

import java.util.Date;
import java.util.List;

@Service
public class MossodHoursService {

    @Autowired
    MossadHoursRepository mossadHoursRepository;

    @Autowired
    TeacherEmploymentDetailsRepository teacherEmploymentDetailsRepository;

    @Autowired
    ConvertHoursService convertHoursService;

    public int fixMossadotHours(int mossadId, int year) {
        Date begda = new Date(year - 1, 8, 1);
        Date endda = new Date(year, 5, 20);


        double frontalSum = 0;
        List<Integer> frontalCodes = this.convertHoursService.getAllFrontal();
        MossadHours mossad = this.mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get();

        frontalSum = this.teacherEmploymentDetailsRepository.findByMossadId(mossadId, begda, endda)
                .stream().filter(el -> frontalCodes.contains(el.getEmpCode()))
                .mapToDouble(TeacherEmploymentDetails::getHours).sum();

        mossad.setCurrHours((int) frontalSum);
        this.mossadHoursRepository.save(mossad);
        return (int) frontalSum;
    }

}
