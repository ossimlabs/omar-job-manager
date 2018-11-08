package omar.job.manager.task.trigger

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class TaskTriggerController
{
	@Autowired
	TaskTriggerService taskProcessor
	
	//pass in POST payload values
	@RequestMapping( path = "/taskTrigger/launchTask/{taskId}", method = RequestMethod.POST )
	@ResponseBody
	String launchTask( @RequestBody String payload, @PathVariable String taskId )
	{
		
		println "request made done"
		
		taskProcessor.launchTask( payload, taskId )
		
		println "request made"
		
		return "success"
	}
}
