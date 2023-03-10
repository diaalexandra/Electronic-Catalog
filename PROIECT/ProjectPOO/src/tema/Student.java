package tema;

public class Student extends User {

    Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        this.mother = null;
        this.father = null;
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getFather() {
        return this.father;
    }

    public Parent getMother() {
        return this.mother;
    }
}
