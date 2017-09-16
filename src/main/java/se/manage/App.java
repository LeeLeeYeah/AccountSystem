package se.manage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public App() {
    }

    public static void main(String[] args) {
        String log4jPath = App.class.getClassLoader().getResource("").getPath() + "/log4j.properties";
        PropertyConfigurator.configure(log4jPath);
        SpringApplication.run(App.class, args);
    }
}
