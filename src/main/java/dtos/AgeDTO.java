package dtos;

public class AgeDTO {
    private int age;
    private int count;
    private String name;

    public AgeDTO() {}

    public AgeDTO(int age, int count, String name) {
        this.age = age;
        this.count = count;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AgeDTO{" +
                "age=" + age +
                ", count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}