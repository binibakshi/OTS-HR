package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.MossadClasses;
import teachers.biniProject.Repository.MossadClassesRepository;

import java.util.List;

@RestController
@RequestMapping("/mossadClasses")
public class MossadClassesController {

    @Autowired
    private MossadClassesRepository mossadClassesRepository;

    @GetMapping("/all")
    public List<MossadClasses> getAll() {
        return this.mossadClassesRepository.findAll();
    }

    @GetMapping("/getByYear")
    public List<MossadClasses> getByYear(@RequestParam(name = "year") int year) {
        return this.mossadClassesRepository.findAllByYear(year);
    }

    @PostMapping("/saveAll")
    public List<MossadClasses> saveAll(@RequestBody List<MossadClasses> mossadClassesList) {
        return this.mossadClassesRepository.saveAll(mossadClassesList);
    }

    @PostMapping("/save")
    public MossadClasses save(@RequestBody MossadClasses mossadClasses) {
        return this.mossadClassesRepository.save(mossadClasses);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody MossadClasses mossadClasses) {
        this.mossadClassesRepository.delete(mossadClasses);
    }
}
