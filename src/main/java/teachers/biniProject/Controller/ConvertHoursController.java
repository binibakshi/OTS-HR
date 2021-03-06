package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teachers.biniProject.Entity.ConvertHours;
import teachers.biniProject.Service.ConvertHoursService;

import java.util.List;

@RestController
@RequestMapping("/convertHours")
public class ConvertHoursController {

    @Autowired
    private ConvertHoursService convertHoursService;

    @GetMapping("/all")
    public List<ConvertHours> getAll() {
        return this.convertHoursService.findAll();
    }

    @GetMapping("/byReform")
    public List<ConvertHours> getByReform(@RequestParam(name = "reformType") int reformType) {
        return this.convertHoursService.getByReform(reformType);
    }

    @GetMapping("/allFrontal")
    public List<Integer> getAllFrontal(){
       return this.convertHoursService.getAllFrontal();
    }
}
