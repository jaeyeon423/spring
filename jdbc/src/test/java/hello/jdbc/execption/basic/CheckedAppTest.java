package hello.jdbc.execption.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

public class CheckedAppTest {

    @Test
    void checked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request()).isInstanceOf(SQLException.class);
    }

    static class Controller {
        Service service = new Service();

        public void request() throws Exception {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws Exception {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() throws Exception {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() throws Exception {
            throw new SQLException("ex");
        }
    }
}
