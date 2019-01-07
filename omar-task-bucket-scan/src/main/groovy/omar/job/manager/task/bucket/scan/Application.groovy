package omar.job.manager.task.bucket.scan

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.context.annotation.Bean

@EnableTask
@SpringBootApplication
class Application
{
	@Bean
	BucketScan bucketScanTask()
	{
		return new BucketScan()
	}
	
	static void main( String[] args )
	{
		SpringApplication.run Application, args
	}
}
