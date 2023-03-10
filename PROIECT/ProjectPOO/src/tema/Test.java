package tema;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import tema.ui.*;

public class Test {
    public static void LoadDataFromJson(String fileName) throws Exception {
        try {
            Catalog catalog = Catalog.getInstance();

            FileReader reader = new FileReader("TestFile.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);

            for (Object o : (JSONArray) jsonObject.get("courses")) {
                JSONObject courseData = (JSONObject) o;

                String courseType = (String) courseData.get("type");
                Course course;
                if (courseType.equals("FullCourse")) {
                    course = new FullCourse();
                }
                else {
                    course = new PartialCourse();
                }

                course.setName((String) courseData.get("name"));
                //course.setCreditPoints((int) courseData.get("credits"));

                String strategyType = (String) courseData.get("strategy");
                Strategy courseStrategy;
                if (strategyType.equals("BestExamScore")) {
                    courseStrategy = new BestExamScore();
                } else if (strategyType.equals("BestPartialScore")) {
                    courseStrategy = new BestPartialScore();
                }
                else {
                    courseStrategy = new BestTotalScore();
                }
                course.setGradesStrategy(courseStrategy);

                JSONObject teacherData = (JSONObject)courseData.get("teacher");
                Teacher teacher = new Teacher((String) teacherData.get("firstName"), (String) teacherData.get("lastName"));
                course.setTeacher(teacher);

                for (Object group : (JSONArray) courseData.get("groups")) {
                    JSONObject groupData = (JSONObject) group;
                    String groupId = (String) groupData.get("ID");

                    // Add assistant
                    JSONObject assistantData = (JSONObject) groupData.get("assistant");
                    Assistant assistant = new Assistant((String) assistantData.get("firstName"), (String) assistantData.get("lastName"));
                    course.addAssistant(groupId, assistant);

                    for (Object student : (JSONArray) groupData.get("students")) {
                        JSONObject studentData = (JSONObject) student;
                        Student studentObj = new Student((String) studentData.get("firstName"), (String) studentData.get("lastName"));

                        JSONObject motherData = (JSONObject) studentData.get("mother");
                        if (motherData != null)
                        {
                            Parent mother = new Parent((String) motherData.get("firstName"), (String) motherData.get("lastName"));
                            studentObj.setMother(mother);
                        }

                        JSONObject fatherData = (JSONObject) studentData.get("father");
                        if(fatherData != null)
                        {
                            Parent father = new Parent((String) fatherData.get("firstName"), (String) fatherData.get("lastName"));
                            studentObj.setFather(father);
                        }

                        course.addStudent(groupId, studentObj);
                    }
                }

                catalog.addCourse(course);
            }

            ScoreVisitor scoreVisitor = new ScoreVisitor();

            for (Object examScore : (JSONArray) jsonObject.get("examScores")) {
                JSONObject examScoreData = (JSONObject)examScore;

                String examCourseName = (String) examScoreData.get("course");
                Double examGradeValue = Double.valueOf(examScoreData.get("grade").toString());

                JSONObject studentData = (JSONObject) examScoreData.get("student");
                JSONObject teacherData = (JSONObject) examScoreData.get("teacher");

                Course course = catalog.getCourseByName(examCourseName);
                if(course != null)
                {
                    scoreVisitor.AddGrade(course.getTeacherByName((String) teacherData.get("firstName"), (String) teacherData.get("lastName")),
                            course.getStudentByName((String) studentData.get("firstName"), (String) studentData.get("lastName")),
                            examCourseName, examGradeValue);
                }
            }

            for (Object partialScore : (JSONArray) jsonObject.get("partialScores")) {
                JSONObject examScoreData = (JSONObject)partialScore;

                String examCourseName = (String) examScoreData.get("course");
                Double examGradeValue = Double.valueOf(examScoreData.get("grade").toString());

                JSONObject studentData = (JSONObject) examScoreData.get("student");
                JSONObject assistantData = (JSONObject) examScoreData.get("assistant");

                Course course = catalog.getCourseByName(examCourseName);
                if(course != null)
                {
                    scoreVisitor.AddGrade(course.getTeacherByName((String) assistantData.get("firstName"), (String) assistantData.get("lastName")),
                            course.getStudentByName((String) studentData.get("firstName"), (String) studentData.get("lastName")),
                            examCourseName, examGradeValue);
                }
            }

            scoreVisitor.AddGradesToCatalog();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void ShowCatalogMenu(Scanner input) {
        System.out.println("Catalog Menu :");

        while (true) {
            System.out.print("> ");
            String command = input.next();

            if (command.equals("help")) {
                System.out.println("Catalog Menu commands:");
                System.out.println("help                             - print commands");
                System.out.println("quit                             - close program");
                System.out.println("ls                               - list courses");
                System.out.println("select <course name>             - go to <course name> course");
                System.out.println("delete <course name>             - delete <course name> course");
                System.out.println("new <Partial/Full> <course name> - create <course name> course");
            } else if (command.equals("quit")) {
                break;
            } else if (command.equals("ls")) {
                System.out.println("Courses:");
                ArrayList<Course> courses = Catalog.getInstance().getCourses();
                for (Course course : courses) {
                    System.out.println(course.getName());
                }
            } else if (command.equals("select")) {
                String courseName = input.nextLine().strip();
                Course course;
                try {
                    int courseNumber = Integer.parseInt(courseName);
                    course = Catalog.getInstance().getCourses().get(courseNumber);
                } catch (Exception e) {
                    course = Catalog.getInstance().getCourseByName(courseName);
                }

                if (course == null) {
                    System.out.println("Invalid course name");
                    continue;
                }
                ShowCourseMenu(input, course);
                System.out.println("Catalog Menu :");
            } else if (command.equals("delete")) {
                String courseName = input.nextLine();
                Course course = Catalog.getInstance().getCourseByName(courseName);
                if (course == null) {
                    System.out.println("Invalid course name");
                    continue;
                }
                Catalog.getInstance().removeCourse(course);
            } else if (command.equals("new")) {
                String courseType = input.next();
                String courseName = input.nextLine();

                Course course;
                if (courseType.equals("Full")) {
                    course = new FullCourse();
                } else if (courseType.equals("Partial")) {
                    course = new PartialCourse();
                } else {
                    System.out.println("Invalid course type");
                    continue;
                }
                course.setName(courseName);
                Catalog.getInstance().addCourse(course);
            } else {
                System.out.println("Invalid command, type 'help' for commands");
            }
        }
    }

    public static void ShowCourseMenu(Scanner input, Course course) {
        System.out.printf("Course Menu for '%s':\n", course.getName());
        while (true) {
            System.out.print("> ");
            String command = input.next();

            if (command.equals("help")) {
                System.out.println("Course Menu commands:");
                System.out.println("help                             - print commands");
                System.out.println("quit                             - close course menu");
                System.out.println("ls                               - print course details");
                System.out.println("groups                           - print course groups");
                System.out.println("grades                           - print student grades");
                System.out.println("backup                           - backup grades");
                System.out.println("restore                          - restore grades");
                System.out.println("addGrade                         - add a grade");
                System.out.println("best <exam/partial/total>       - get best student");
            } else if (command.equals("quit")) {
                break;
            } else if (command.equals("ls")) {
                System.out.printf("Course Name '%s'\n", course.getName());
                System.out.printf("Credit Points: %s\n", course.getCreditPoints());
                System.out.printf("Course Teacher: %s %s \n", course.getTeacher().getFirstName(), course.getTeacher().getLastName());

                Set<Assistant> assistants = course.getAssistants();
                if(assistants.isEmpty()) {
                    System.out.println("Course has no assistants");
                } else {
                    System.out.println("Course assistants:");
                    for(Assistant assistant : assistants) {
                        System.out.printf("\t%s %s\n", assistant.getFirstName(), assistant.getLastName());
                    }
                }
            } else if (command.equals("groups")) {
                System.out.println("Course groups:");
                Map<String,Group> groupMap = course.getGroupMap();
                for (Map.Entry<String,Group> entry : groupMap.entrySet())
                {
                    System.out.printf("\tGroup Id: %s ; assistant: %s %s\n", entry.getKey(),
                            entry.getValue().getAssistant().getFirstName(),
                            entry.getValue().getAssistant().getLastName());
                    System.out.println("\tGroup Students:");
                    for (Student student : entry.getValue()) {
                        System.out.printf("\t\t%s %s\n", student.getFirstName(), student.getLastName());
                    }
                }
            } else if (command.equals("grades")) {
                System.out.println("Student Grades:");
                HashMap<Student, Grade> grades = course.getAllStudentGrades();
                for (Map.Entry<Student, Grade> entry : grades.entrySet()) {
                    System.out.printf("\t%s %s\n", entry.getKey().getFirstName(), entry.getKey().getLastName());
                    System.out.printf("\t\tCourse Name: %s\n", entry.getValue().getCourse());
                    System.out.printf("\t\tPartial Score: %s\n", entry.getValue().getPartialScore());
                    System.out.printf("\t\tExam Score: %s\n", entry.getValue().getExamScore());
                    System.out.printf("\t\tTotal Score: %s\n", entry.getValue().getTotal());
                }
            } else if (command.equals("backup")) {
                course.makeBackup();
            } else if (command.equals("restore")) {
                course.undo();
            } else if (command.equals("addGrade")) {
                Grade grade = new Grade();
                grade.setCourse(course.getName());

                input.nextLine(); //flush buffer

                System.out.println("Enter student first name");
                String firstName = input.nextLine().strip();

                System.out.println("Enter student last name");
                String lastName = input.nextLine().strip();

                Student student = new Student(firstName, lastName);
                grade.setStudent(student);

                System.out.println("Enter partial score");
                grade.setPartialScore(input.nextDouble());

                System.out.println("Enter exam score");
                grade.setExamScore(input.nextDouble());

                course.addGrade(grade);
            } else if (command.equals("best")) {
                String strategyName = input.next();
                Strategy strategy;
                if (strategyName.equals("exam")) {
                    strategy = new BestExamScore();
                } else if (strategyName.equals("partial")) {
                    strategy = new BestPartialScore();
                } else if (strategyName.equals("total")) {
                    strategy = new BestTotalScore();
                } else {
                    System.out.println("Invalid strategy name");
                    continue;
                }

                course.setGradesStrategy(strategy);
                Student student = course.getBestStudent();
                System.out.printf("Best Student: %s %s\n", student.getFirstName(), student.getLastName());
            } else {
                System.out.println("Invalid command, type 'help' for commands");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MainUI.main(args);
        LoadDataFromJson("src\\TestFiles.json");

        Scanner input = new Scanner(System.in);
        ShowCatalogMenu(input);
    }
}
