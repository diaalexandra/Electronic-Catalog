package tema;

import java.util.ArrayList;

public class FullCourse extends Course {
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> result = new ArrayList<Student>();
        for (Grade g: grades) {
            if (g.getStudent() != null && g.getPartialScore() >=3 && g.getExamScore() >= 2) {
                result.add(g.getStudent());
            }
        }
        return result;
    }

    public class FullCourseBuilder extends CourseBuilder{
        public FullCourseBuilder() {
            obj = new FullCourse();
        }
    }
}
