package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teachers.biniProject.Entity.MossadType;
import teachers.biniProject.Repository.MossadTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/mossadType")
public class MossadTypeController {

    @Autowired
    private MossadTypeRepository mossadTypeRepository;

    @GetMapping("/all")
    public List<MossadType> getAll() {
        return this.mossadTypeRepository.findAll();
    }
}
