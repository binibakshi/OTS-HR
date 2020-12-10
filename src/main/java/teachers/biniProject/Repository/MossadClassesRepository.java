package teachers.biniProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teachers.biniProject.Entity.MossadClasses;
import teachers.biniProject.HelperClasses.MossadClassesComositeKey;

import java.util.List;

public interface MossadClassesRepository extends JpaRepository<MossadClasses, MossadClassesComositeKey> {

    List<MossadClasses> findAllByYear(int year);
}
