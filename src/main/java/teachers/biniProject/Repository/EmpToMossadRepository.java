package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.EmpToMossad;

import java.util.List;

public interface EmpToMossadRepository extends JpaRepository<EmpToMossad, Integer> {

    @Query(value = "SELECT  * FROM EMP_TO_MOSSAD WHERE mosad_id =:mossadId",
            nativeQuery = true)
    List<EmpToMossad> findAllByMossad(@Param("mossadId") int empId);

    @Modifying
    @Query(value = "DELETE FROM EMP_TO_MOSSAD WHERE emp_id =:empId",
            nativeQuery = true)
    void deletebyEmpId(@Param("empId") String empId);

}
