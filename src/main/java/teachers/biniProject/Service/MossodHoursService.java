package teachers.biniProject.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teachers.biniProject.Entity.MossadHours;
import teachers.biniProject.Entity.TeacherEmploymentDetails;
import teachers.biniProject.HelperClasses.MossadHoursComositeKey;
import teachers.biniProject.Repository.MossadHoursRepository;
import teachers.biniProject.Repository.TeacherEmploymentDetailsRepository;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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

    public static final int getMonthsDifference(Date date1, Date date2) {
        YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
        YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

        return (int) m1.until(m2, ChronoUnit.MONTHS) + 1;
    }

    // add or sub the total hours
    public void updateMossadHours(int mossadId, Date begda, Date endda, float newHours, float oldHours) {
//        double monthDiff = MossodHoursService.getMonthsDifference(begda, endda);
        Calendar cal = Calendar.getInstance();
        cal.setTime(begda);
        int year = cal.get(Calendar.YEAR);
        if (cal.get(Calendar.MONTH) >= 8) {
            year++;
        }
//        double currNewHours = monthDiff / 10.00 * newHours - oldHours;
        double currNewHours = newHours - oldHours;

        // set the new hours in total sum hours per mossad
        MossadHours mossadHours = this.mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get();
        mossadHours.setCurrHours(mossadHours.getCurrHours() + (float) currNewHours);
        this.mossadHoursRepository.save(mossadHours);
    }

    public MossadHours findById(MossadHoursComositeKey mossadHoursComositeKey) {
        return this.mossadHoursRepository.findById(mossadHoursComositeKey).orElse(null);
    }

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
