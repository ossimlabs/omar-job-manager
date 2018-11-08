package omar.job.manager.task.trigger

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaskTriggerApplication
{
	@Bean
	TaskTriggerService taskProcessor()
	{
		return new TaskTriggerService()
	}
	
	static void main( String[] args )
	{
		SpringApplication.run TaskTriggerApplication, args
	}
}
