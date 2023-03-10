package tema;

import java.util.ArrayList;

public class BestPartialScore implements Strategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Grade bestGrade = grades.get(0);
        for (int i = 2; i < grades.size(); ++i){
            Grade g = grades.get(i);
            if(g.getPartialScore() > bestGrade.getPartialScore()){
                bestGrade = g;
            }
        }
        return bestGrade.getStudent();
    }
}
