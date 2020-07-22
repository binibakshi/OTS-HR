package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.Mossadot;
import teachers.biniProject.Repository.MossadotRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/mossadot")
public class MossadotController {

    @Autowired
    private MossadotRepository mossadotRepository;

    @GetMapping("/all")
    public List<Mossadot> getAll() {
        return this.mossadotRepository.findAll();
    }

    @GetMapping("/byId")
    public Mossadot getById(@RequestParam(name = "mossadId") int mossadId) {
        return this.mossadotRepository.findById(mossadId).orElse(null);
    }

    @PostMapping("/save")
    public Mossadot save(@RequestBody Mossadot mossad) {
        return this.mossadotRepository.save(mossad);
    }

    @DeleteMapping("/byId")
    public void deleteByMossadId(@Param("mossadId") int mossadId) {
        this.mossadotRepository.deleteById(mossadId);
    }
}
