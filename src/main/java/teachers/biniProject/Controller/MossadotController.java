package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Mosaddot;
import teachers.biniProject.Repository.MosaddotRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mossadot")
public class MossadotController {

    @Autowired
    private MosaddotRepository mosaddotRepository;

    @PostMapping("/save")
    public List<Mosaddot> save(@RequestBody Mosaddot mosaddot) {
        return (List<Mosaddot>) this.mosaddotRepository.save(mosaddot);
    }

    @GetMapping("/byId")
    public List<Mosaddot> getByEmpId(@RequestParam("empId") String empId) {
        return this.mosaddotRepository.findAll().stream().
                filter(el -> el.getEmpId() == empId).
                collect(Collectors.toList());
    }

    @GetMapping("/byMossad")
    public List<Mosaddot> getByMossadId(@RequestParam("mossadId") int mossadId) {
        return this.mosaddotRepository.findAll().stream().
                filter(el -> el.getMossadId() == mossadId).
                collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<Mosaddot> getAll() {
        return this.mosaddotRepository.findAll();
    }
}
