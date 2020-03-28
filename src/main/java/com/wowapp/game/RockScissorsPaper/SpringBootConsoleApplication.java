package com.wowapp.game.RockScissorsPaper;

import com.wowapp.game.repository.impl.GameDataSourceRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wowapp.game.controller.impl.GameController;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
SpringBootConsoleApplication class for entry point into application
 */
@SpringBootApplication(scanBasePackages = {
        "com.wowapp.game.service", "com.wowapp.game.service.impl",
        "com.wowapp.game.controller", "com.wowapp.game.repository"})
@EnableAsync(proxyTargetClass = true)
public class SpringBootConsoleApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameDataSourceRepositoryImpl.class.getName());

    @Value("${corepoolsize}") /*  number of working thread by application start*/
    private Integer corePoolSize = 3;
    @Value("${maxpoolsize}") /*  max number of working thread by application start*/
    private Integer maxPoolSize = 5;
    @Value("${queuecapacity}") /*  max number of queues */
    private Integer queueCapacity = 800;
    @Autowired
    private GameController gameController;

    /*main method of application*/
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    /* possibility to make multhithreading game by calling processExecutor for games methods*/
    @Bean(name = "processExecutor")
    public TaskExecutor getAsyncExecutor() {
        LOGGER.info("<<getAsyncExecutor ");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("Async-");
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.afterPropertiesSet();
        LOGGER.info("<<ThreadPoolTaskExecutor set");
        return threadPoolTaskExecutor;
    }

    public void run(String... arg0) throws Exception {
        System.out.println("Loading game...");
        gameController.startGame();
    }


}