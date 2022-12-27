package hello.core;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {

    private String nname;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setNname("jaeyeon");

        String name = helloLombok.getNname();
        System.out.println("name = " + name);
    }
}
