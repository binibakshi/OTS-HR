package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.EmpToMossad;
import teachers.biniProject.Repository.EmpToMossadRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empToMossad")
public class EmpToMosaddController {
    @Autowired
    private EmpToMossadRepository empToMossadRepository;

    @PostMapping("/save")
    public List<EmpToMossad> save(@RequestBody EmpToMossad mosaddot) {
        return (List<EmpToMossad>) this.empToMossadRepository.save(mosaddot);
    }

    @GetMapping("/byId")
    public List<EmpToMossad> getByEmpId(@RequestParam("empId") String empId) {
        return this.empToMossadRepository.findAll().stream().
                filter(el -> el.getEmpId().equals(empId)).
                collect(Collectors.toList());
    }

    @GetMapping("/byMossad")
    public List<EmpToMossad> getByMossadId(@RequestParam("mossadId") int mossadId) {
        return this.empToMossadRepository.findAll().stream().
                filter(el -> el.getMossadId() == mossadId).
                collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<EmpToMossad> getAll() {
        return this.empToMossadRepository.findAll();
    }
}

