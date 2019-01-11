package omar.job.manager.task.bucket.scan

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import org.springframework.boot.CommandLineRunner
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BucketScan implements CommandLineRunner
{

	@Value('${cloud.aws.credentials.accessKey}')
	String accessKey

	@Value('${cloud.aws.credentials.secretKey}')
	String secretKey

	@Value('${cloud.aws.s3.bucket}')
	String bucketName

	@Override
	void run( String... strings ) throws Exception
	{
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey)


		def clientRegion = 'us-east-1'
		def startAfter = null
		def delimiter = '/'
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
			.withCredentials( new AWSStaticCredentialsProvider(awsCredentials) )
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