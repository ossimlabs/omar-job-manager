package omar.job.manager.task.trigger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.cloud.task.launcher.TaskLaunchRequest
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component


@Component
@EnableBinding( Source.class )
class TaskTriggerService
{
	@Autowired
	Source source;
	
	void launchTask( String payload, String taskId )
	{
		
		//maven://[groupid]:[artifactid]:jar:[version]
		String bucketScanUrl = "maven://omar-job-manager:omar-task-bucket-scan:jar:0.0.1-SNAPSHOT"
//		String blueUrl = "maven://serverless-demo:serverless-demo-task-blue:jar:0.0.1-SNAPSHOT"
		
		List<String> input = new ArrayList<String>( Arrays.asList( payload.split( "," ) ) )
		
		if ( taskId.equals( "bucketScan" ) )
		{
			TaskLaunchRequest request = new TaskLaunchRequest( bucketScanUrl, input, null, null, "" )
			GenericMessage<TaskLaunchRequest> message = new GenericMessage<>( request )
			this.source.output().send( message )
		}
		
//		if ( color.equals( "blue" ) )
//		{
//			TaskLaunchRequest request = new TaskLaunchRequest( blueUrl, input, null, null, "" )
//			GenericMessage<TaskLaunchRequest> message = new GenericMessage<>( request )
//			this.source.output().send( message )
//		}
//		System.out.println("created task launch request ...");
//
//		GenericMessage<TaskLaunchRequest> message = new GenericMessage<>(request);
//
//		this.source.output().send(message);
	}
	
}
