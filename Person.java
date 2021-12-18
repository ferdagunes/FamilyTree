package FamilyTree;

public class Person {
    private String name;
    private String surname;
    private Gender gender;
    private int age;


    public Person(String name, String surname, Gender gender, int age) {
        this.name = name;
        this.surname =surname;
        this.gender = gender;
        this.age =age; }

    public enum Gender{MALE, FEMALE}



    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Gender getGender() { return gender; }
    public int getAge(){ return age; }
    public void setName(String name) { this.name= name; }
    public void setSurname(String surname) { this.surname= surname; }


    public void setMother( Person mother){
        if (mother.getGender()== Gender.FEMALE){
        }else{
            throw new IllegalArgumentException("Mother cannot be male");}
    }
    public void setFather(Person father){
        if(father.getGender()==Gender.MALE){

        }else{
            throw new IllegalArgumentException("Father cannot be female");
        }
}
    }



