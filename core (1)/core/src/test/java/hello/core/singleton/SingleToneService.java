package hello.core.singleton;

public class SingleToneService {

    private static final SingleToneService instance = new SingleToneService();

    public static SingleToneService getInstance(){
        return instance;
    }

    private SingleToneService(){

    }

    public void login(){
        System.out.println("싱글톤 객체 로직 호출");
    }


}
