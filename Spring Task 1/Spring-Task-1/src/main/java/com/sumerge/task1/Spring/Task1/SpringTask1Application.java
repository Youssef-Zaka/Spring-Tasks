package com.sumerge.task1.Spring.Task1;

import com.sumerge.task1.Spring.Task1.Service.CourseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.sumerge.task1.Spring.Task1",
		"com.sumerge.task2.Spring.Task2",
})
public class SpringTask1Application {

	public static void main(String[] args) {
//		SpringApplication.run(SpringTask1Application.class, args);

		//test task 1: Recommending courses
		ConfigurableApplicationContext context = SpringApplication.run(SpringTask1Application.class, args);
		CourseService courseService = context.getBean(CourseService.class);
		courseService.getRecommendedCourses().forEach(System.out::println);
	}
}
