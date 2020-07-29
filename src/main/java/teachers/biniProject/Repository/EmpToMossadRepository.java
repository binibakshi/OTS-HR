package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.EmpToMossad;

import java.util.List;

public interface EmpToMossadRepository extends JpaRepository<EmpToMossad, Integer> {

    List<EmpToMossad> findAllByMossadId(int mossadId);

    void deleteAllByEmpId(String empId);

}
