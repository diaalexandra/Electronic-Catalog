package tema;

import java.lang.Cloneable;
import java.lang.Comparable;

public class Grade implements Cloneable, Comparable<Grade> {
    private Double partialScore = Double.valueOf(0);
    private Double examScore = Double.valueOf(0);
    private Student student;
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getTotal(){
        return partialScore + examScore;
    }

    @Override
    protected Object clone()
    {
        Grade g = new Grade();
        g.partialScore = Double.valueOf(this.partialScore);
        g.examScore = Double.valueOf(this.examScore);
        g.student = this.student;
        g.course = this.course;
        return g;
    }

    @Override
    public int compareTo(Grade o) {
        return this.getTotal().compareTo(o.getTotal());
        //TODO: check with other ppls for this!
    }
}
