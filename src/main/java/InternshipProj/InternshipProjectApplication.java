package InternshipProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InternshipProjectApplication{
	public static void main(String[] args) {
		SpringApplication.run(InternshipProjectApplication.class, args);
	}
	@GetMapping("/test")
	public String test(@RequestParam(value = "name", defaultValue = "Success") String name) {
		return String.format("Test %s", name);
	}
}