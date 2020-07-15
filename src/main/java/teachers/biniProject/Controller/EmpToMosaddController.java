package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.empToMossad;
import teachers.biniProject.Repository.EmpToMossadRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empToMossad")
public class EmpToMosaddController {
    @Autowired
    private EmpToMossadRepository empToMossadRepository;

    @PostMapping("/save")
    public List<empToMossad> save(@RequestBody empToMossad mosaddot) {
        return (List<empToMossad>) this.empToMossadRepository.save(mosaddot);
    }

    @GetMapping("/byId")
    public List<empToMossad> getByEmpId(@RequestParam("empId") String empId) {
        return this.empToMossadRepository.findAll().stream().
                filter(el -> el.getEmpId().equals(empId)).
                collect(Collectors.toList());
    }

    @GetMapping("/byMossad")
    public List<empToMossad> getByMossadId(@RequestParam("mossadId") int mossadId) {
        return this.empToMossadRepository.findAll().stream().
                filter(el -> el.getMossadId() == mossadId).
                collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<empToMossad> getAll() {
        return this.empToMossadRepository.findAll();
    }
}

