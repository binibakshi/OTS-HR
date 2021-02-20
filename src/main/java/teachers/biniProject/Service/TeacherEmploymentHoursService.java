package teachers.biniProject.Service;


import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.Entity.TeacherEmploymentHours;
import teachers.biniProject.Repository.TeacherEmploymentHoursRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TeacherEmploymentHoursService {

    private TeacherEmploymentHoursRepository teacherEmploymentHoursRepository;

    public TeacherEmploymentHours save(TeacherEmploymentHours teacherEmploymentHours) {
        return this.teacherEmploymentHoursRepository.save(teacherEmploymentHours);
    }

//    public List<TeacherEmploymentHours> saveAll(List<TeacherEmploymentHours> teacherEmploymentHours) {
//        return this.teacherEmploymentHoursRepository.saveAll(teacherEmploymentHours);
//    }

    public List<TeacherEmploymentHours> saveAll(List<TeacherEmploymentDetails> teacherEmploymentDetails) {
        int index = 0;
        List<TeacherEmploymentHours> teacherEmploymentHoursList = null;
        TeacherEmploymentHours teacherEmploymentHours;

        for (TeacherEmploymentDetails el : teacherEmploymentDetails) {
            teacherEmploymentHours = teacherEmploymentHoursList.stream().
                    filter(o -> o.getEmpCode() == el.getEmpCode()).findFirst().orElse(null);

            if (teacherEmploymentHours != null) {
                index = teacherEmploymentHoursList.indexOf(teacherEmploymentHours);
                teacherEmploymentHoursList.get(index).setHours(teacherEmploymentHours.getHours() + el.getHours());
            }
        }
        return this.teacherEmploymentHoursRepository.saveAll(teacherEmploymentHoursList);
    }

    public void delete(TeacherEmploymentHours teacherEmploymentHours) {
        this.teacherEmploymentHoursRepository.delete(teacherEmploymentHours);
    }

    public void deleteAll(List<TeacherEmploymentHours> teacherEmploymentHours) {
        this.teacherEmploymentHoursRepository.deleteAll(teacherEmploymentHours);
    }

    public void deleteOverLapps(String empId, int mossadId, int reformType, Date begda, Date endda) {
        this.teacherEmploymentHoursRepository.deleteOverlapps(empId, mossadId, reformType, begda, endda);
    }
    public void deleteOverLappsByEmpCodes(String empId, int mossadId, int reformType, Date begda, Date endda) {
        ArrayList<Integer> empCodesToIgnore = new ArrayList<>();
        empCodesToIgnore.add(9671);
        empCodesToIgnore.add(2598);
        this.teacherEmploymentHoursRepository.deleteOverlappsByEmpCodes(empId, mossadId, reformType, begda, endda,empCodesToIgnore);
    }


}
