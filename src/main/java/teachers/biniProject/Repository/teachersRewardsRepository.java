package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teachers.biniProject.Entity.TeachersRewards;
import teachers.biniProject.HelperClasses.teachersRewardsCompositeKey;

import java.util.List;

public interface TeachersRewardsRepository extends JpaRepository<TeachersRewards, teachersRewardsCompositeKey> {

    List<TeachersRewards> findAllByEmpIdAndMossadIdAndYear(int empId, int mossadId, int year);

    List<TeachersRewards> findAllByYear(int year);

    List<TeachersRewards> findAllByMossadIdAndYear(int mossadId, int year);

    @Query(value = "select distinct rewards.emp_id as empId, " +
            "sum(rewards.hours) as hours, " +
            "(select hours.hours " +
            "from teacher_info as hours " +
            "where hours.emp_id = rewards.emp_id " +
            "AND employment_code = :empCode " +
            "AND YEAR(begda) <= :year " +
            "AND YEAR(endda) >= :year " +
            "AND hours.mossad_id = :mossadId) as actualHours, " +
            "e.first_name as firstName, " +
            "e.last_name as lastName " +
            "from teachers_rewards as rewards " +
            "left join employees e on rewards.emp_id = e.emp_id " +
            "where year = :year AND reform_id = :reformId " +
            "AND mossad_Id = :mossadId " +
            "group by rewards.emp_id",
            nativeQuery = true)
    List<Object[]> findAllGaps(@Param("year") int year,
                               @Param("mossadId") int mossadId,
                               @Param("reformId") int reformId,
                               @Param("empCode") int empCode);

    void deleteAllByReformId(int rewardId);


}
