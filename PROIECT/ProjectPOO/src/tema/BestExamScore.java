package tema;

import java.util.ArrayList;

public class BestExamScore implements Strategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Grade bestGrade = grades.get(0);
        for (int i = 2; i < grades.size(); ++i){
            Grade g = grades.get(i);
            if(g.getExamScore() > bestGrade.getExamScore()){
                bestGrade = g;
            }
        }
        return bestGrade.getStudent();
    }
}
