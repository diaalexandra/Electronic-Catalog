package tema;

import java.util.ArrayList;

public class PartialCourse extends Course {
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> result = new ArrayList<>();
        for (Grade g: grades) {
            if (g.getStudent() != null && g.getTotal() > 5) {
                result.add(g.getStudent());
            }
        }
        return result;
    }

    public class PartialCourseBuilder extends CourseBuilder{
        public PartialCourseBuilder() {
            obj = new FullCourse();
        }
    }
}
