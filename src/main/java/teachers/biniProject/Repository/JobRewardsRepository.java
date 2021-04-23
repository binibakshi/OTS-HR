package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import teachers.biniProject.Entity.JobRewards;

import java.util.List;

public interface JobRewardsRepository extends JpaRepository<JobRewards, Integer> {

    @Query(value = "select distinct employment_code " +
            "from job_rewards " +
            "where min_hours <> 0 OR max_hours <> 0",
            nativeQuery = true)
    List<Integer> getAllCodes();
}
