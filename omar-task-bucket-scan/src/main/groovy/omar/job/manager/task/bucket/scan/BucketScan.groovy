package omar.job.manager.task.bucket.scan

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import org.springframework.boot.CommandLineRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class BucketScanTask implements CommandLineRunner
{

	/**
	 * Autowired AWSCredentialsProvider used to authenticate the s3Client object with an AWS account
	 */
	@Autowired
	AWSCredentialsProvider awsCredentialsProvider

	@Override
	void run( String... strings ) throws Exception
	{
		def clientRegion = 'us-east-1'
		def bucketName = 'dg-1b-3090-t1'
		def startAfter = null
		def delimiter = '/'
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
			.withCredentials( awsCredentialsProvider )
			.withRegion( clientRegion )
			.build()
		
		ListObjectsV2Request req = new ListObjectsV2Request()
			.withBucketName( bucketName )
			.withDelimiter( delimiter )
		
		if ( startAfter )
		{
			req = req.withStartAfter( startAfter )
		}
		
		ListObjectsV2Result result
		
		while ( ( result = s3Client.listObjectsV2( req ) )?.isTruncated() )
		{
			result?.commonPrefixes?.each { prefix ->
				println prefix
			}
			
			req.continuationToken = result.nextContinuationToken
		}
	}
}