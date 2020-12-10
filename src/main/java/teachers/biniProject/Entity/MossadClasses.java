package teachers.biniProject.Entity;

import teachers.biniProject.HelperClasses.MossadClassesComositeKey;

import javax.persistence.*;

@Entity
@IdClass(MossadClassesComositeKey.class)
@Table(name = "mossad_class")
public class MossadClasses {

    @Id
    @Column(name = "mossad_id")
    private int mossadId;

    @Id
    @Column(name = "year")
    private int year;

    @Id
    @Column(name = "grade")
    private int grade;

    @Id
    @Column(name = "class_number")
    private int classNumber;

    @Column(name = "students")
    private int students;

    public MossadClasses() {
        super();
    }

    public MossadClasses(int mossadId, int year, int grade, int classNumber, int students) {
        this.mossadId = mossadId;
        this.year = year;
        this.grade = grade;
        this.classNumber = classNumber;
        this.students = students;
    }

    public int getMossadId() {
        return mossadId;
    }

    public void setMossadId(int mossadId) {
        this.mossadId = mossadId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }
}
