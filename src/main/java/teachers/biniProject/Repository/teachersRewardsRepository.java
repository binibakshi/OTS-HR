package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;

import java.util.List;

public interface TeachersRewardsRepository extends JpaRepository<TeachersRewards, teachersRewardsCompositeKey> {

    List<TeachersRewards> findAllByEmpIdAndMossadIdAndYearAndRewardType(String empId, int mossadId, int year, int rewardType);

    List<TeachersRewards> findAllByEmpIdAndMossadIdAndYear(String empId, int mossadId, int year);

    List<TeachersRewards> findAllByYearAndRewardType(int year, int rewardType);

    List<TeachersRewards> findAllByYear(int year);

    List<TeachersRewards> findAllByMossadIdAndYearAndRewardType(int mossadId, int year, int rewardType);

    List<TeachersRewards> findAllByMossadIdAndYear(int mossadId, int year);

    @Query(value = "select distinct rewards.emp_id as empId, " +
            "ROUND(sum(rewards.hours) ,2), " +
            "ROUND((select sum(hours.hours) " +
            "from teacher_info as hours " +
            "where hours.emp_id = rewards.emp_id " +
            "AND hours.employment_code = rewards.employment_code " +
            "AND YEAR(begda) <= :year " +
            "AND YEAR(endda) >= :year " +
            "AND hours.mossad_id = :mossadId),2) as actualHours, " +
            "e.first_name as firstName, " +
            "e.last_name as lastName, " +
            "rewards.reward_type as rewardType " +
            "from teachers_rewards as rewards " +
            "left join employees e on rewards.emp_id = e.emp_id " +
            "where rewards.year = :year AND rewards.hours <> 0 " +
            "AND rewards.mossad_Id = :mossadId " +
            "group by rewards.emp_id, rewards.reward_type, actualHours ",
            nativeQuery = true)
    List<Object[]> findAllGaps(@Param("year") int year,
                               @Param("mossadId") int mossadId);

    void deleteAllByReformIdAndRewardType(int rewardId, int rewardType);


}
