package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.teachersRewards;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;

import java.util.List;

public interface teachersRewardsRepository extends JpaRepository<teachersRewards, teachersRewardsCompositeKey> {

    List<teachersRewards> findAllByEmpIdAndMossadIdAndYear(int empId, int mossadId, int year);

    List<teachersRewards> findAllByYear(int year);

    List<teachersRewards> findAllByMossadIdAndYear(int mossadId, int year);


}
