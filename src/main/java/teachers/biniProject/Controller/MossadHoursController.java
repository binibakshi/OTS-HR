package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.MossadHours;
import teachers.biniProject.HelperClasses.MossadHoursComositeKey;
import teachers.biniProject.Repository.MossadHoursRepository;

import java.util.List;

@RestController
@RequestMapping("/mossadHours")
public class MossadHoursController {

    @Autowired
    private MossadHoursRepository mossadHoursRepository;

    @GetMapping("/all")
    public List<MossadHours> findAll() {
        return this.mossadHoursRepository.findAll();
    }

    @GetMapping("/byId")
    public MossadHours byId(@RequestParam(name = "mossadId") int mossadId,
                            @RequestParam(name = "year") int year) {
        return this.mossadHoursRepository.findById(new MossadHoursComositeKey(mossadId, year)).get();
    }

    @GetMapping("/byYear")
    public List<MossadHours> byId(@RequestParam(name = "year") int year) {
        return this.mossadHoursRepository.findAllByYear(year);
    }

    @PostMapping("/save")
    public MossadHours save(@RequestBody MossadHours mossadHours) {
        return this.mossadHoursRepository.save(mossadHours);
    }

    @DeleteMapping("/byId")
    public void delete(@RequestParam(name = "mossadId") int mossadId,
                       @RequestParam(name = "year") int year) {
        this.mossadHoursRepository.deleteById(new MossadHoursComositeKey(mossadId, year));
    }

}
