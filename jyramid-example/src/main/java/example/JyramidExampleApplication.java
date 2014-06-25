package example;

import com.jyramid.config.Configurator;
import com.jyramid.controller.Controller;
import com.jyramid.controller.Resource;
import com.jyramid.http.HttpRequest;
import com.jyramid.server.ApplicationServer;
import com.jyramid.server.JettyApplicationServer;

import java.util.concurrent.atomic.AtomicLong;

public class JyramidExampleApplication {

    public static void main(String[] args) throws Exception {
        Configurator config = new Configurator();
        config.addRoute("index", "/");
        config.addRoute("counter", "/counter");
        config.implement(new ExampleController());

        ApplicationServer server = new JettyApplicationServer(config.build());
        server.serve();
    }

    @Controller
    public static class ExampleController {

        public AtomicLong counter = new AtomicLong(0);

        @Resource(routeName = "index")
        public String index(HttpRequest request) {
            return "It works!";
        }

        @Resource(routeName = "counter")
        public Long counter(HttpRequest request) {
            return counter.getAndIncrement();
        }

    }

}
