package tema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 class Tuple<V1, V2, V3> {
    private V1 value1;
    private V2 value2;
    private V3 value3;
    public Tuple(V1 value1, V2 value2, V3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
    public V1 getValue1() {
        return value1;
    }
    public V2 getValue2() {
        return value2;
    }
    public V3 getValue3() {
        return value3;
    }
}

public class ScoreVisitor implements Visitor {
    private HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores = new HashMap<>();
    private HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores = new HashMap<>();

    public void AddGrade(User user, Student student, String courseName, Double gradeValue)
    {
        if(user == null)
            return;

        if(user instanceof Teacher) {
            AddTeacherGrade((Teacher)user, student, courseName, gradeValue);
        } else if (user instanceof Assistant) {
            AddAssistantGrade((Assistant)user, student, courseName, gradeValue);
        }
    }

    public void AddTeacherGrade(Teacher teacher, Student student, String courseName, Double gradeValue) {
        if(!examScores.containsKey(teacher)) {
            ArrayList<Tuple<Student, String, Double>> array = new ArrayList<>();
            examScores.put(teacher, array);
        }
        examScores.get(teacher).add(new Tuple<>(student,courseName, gradeValue));
    }

    public void AddAssistantGrade(Assistant assistant, Student student, String courseName, Double gradeValue) {
        if(!partialScores.containsKey(assistant)) {
            ArrayList<Tuple<Student, String, Double>> array = new ArrayList<>();
            partialScores.put(assistant, array);
        }
        partialScores.get(assistant).add(new Tuple<>(student,courseName, gradeValue));
    }

    public void AddGradesToCatalog()
    {
        for (Teacher t : examScores.keySet()) {
            visit(t);
        }

        for (Assistant a : partialScores.keySet()) {
            visit(a);
        }
    }

    @Override
    public void visit(Assistant assistant) {
        ArrayList<Tuple<Student, String, Double>> grades = partialScores.get(assistant);
        for (Tuple<Student, String, Double> gradeToAdd: grades) {
            Course course = Catalog.getInstance().getCourseByName(gradeToAdd.getValue2());
            if (course != null) {
                Grade g = course.getGrade(gradeToAdd.getValue1());
                if (g == null) {
                    g = new Grade();
                    g.setCourse(course.getName());
                    g.setStudent(gradeToAdd.getValue1());
                    g.setPartialScore(gradeToAdd.getValue3());
                    course.addGrade(g);
                }
                else {
                    g.setPartialScore(gradeToAdd.getValue3());
                    course.notifyObservers(g);
                }
            }
        }
    }

    @Override
    public void visit(Teacher teacher) {
        ArrayList<Tuple<Student, String, Double>> grades = examScores.get(teacher);
        for(Tuple<Student, String, Double> gradeToAdd: grades) {
            Course course = Catalog.getInstance().getCourseByName(gradeToAdd.getValue2());
            if(course != null) {
                Grade g = course.getGrade(gradeToAdd.getValue1());
                if(g == null) {
                    g = new Grade();
                    g.setCourse(course.getName());
                    g.setStudent(gradeToAdd.getValue1());
                    g.setExamScore(gradeToAdd.getValue3());
                    course.addGrade(g);
                }
                else {
                    g.setExamScore(gradeToAdd.getValue3());
                    course.notifyObservers(g);
                }
            }
        }
    }
}
