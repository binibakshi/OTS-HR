package teachers.biniProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import teachers.biniProject.Entity.convertHours;
import teachers.biniProject.Service.ConvertHoursService;

import java.util.List;

@RestController
@RequestMapping("/convertHours")
public class ConvertHoursController {

    @Autowired
    private ConvertHoursService convertHoursService;

    @GetMapping("/all")
    public List<convertHours> getAll() {
        return this.convertHoursService.findAll();
    }

    @GetMapping("/byReform")
    public List<convertHours> getByReform(@RequestParam(name = "reformType") int reformType) {
        return this.convertHoursService.getByReform(reformType);
    }
}
