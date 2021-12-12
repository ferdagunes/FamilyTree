package FamilyTree;
public class Person {
    private String name;
    private String surname;
    private char gender;
    private int age;


    public Person(String name, String surname, char gender, int age) {
        this.name = name;
        this.surname =surname;
        this.gender = gender;
        this.age =age;

    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public char getGender() {
        return gender;
    }
    public int getAge(){
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public void setAge(int age) {
        this.age = age;
    }
}



