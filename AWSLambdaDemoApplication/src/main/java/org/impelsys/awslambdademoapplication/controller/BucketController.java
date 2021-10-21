package org.impelsys.awslambdademoapplication.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.ec2.model.SummaryStatus;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
@RequestMapping("/s3")
public class BucketController {
	
	@Autowired
	AmazonS3 s3;
	
//	@Autowired
//	@Qualifier("s3_1")
//	AmazonS3 s3_1;
	
	@Value("${aws.s3BucketName}")
	String bucketName;
	
	@PutMapping(value="/copy/image/{imageName}")
	public String copyImage(@PathVariable String imageName)
	{
		s3.copyObject(bucketName,"lotusimg","tanvi-demo-images","image-key1");
		return "Image Copied";
	}
	@PutMapping(value="/images/{imageName}")
	public ResponseEntity<String> uploadImage(@PathVariable String imageName)
	{
		try 
		{
			File file = new ClassPathResource("/images/"+imageName+".jpg").getFile();
			s3.putObject(bucketName,"lotusimg",file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	//	s3.putObject(bucketName,"lotusimg", new File("/images/"+imageName+".jpg"));
		return new ResponseEntity<String>("Image uploaded!",HttpStatus.OK);
		
	}
	@GetMapping(value="/listObjects")
	public List<Bucket> listObjects()
	{
		List<Bucket> list = new ArrayList<Bucket>();
		
		list = s3.listBuckets();
		list.stream().forEach(bucket->
		{
			ObjectListing objectListing = s3.listObjects(bucket.getName());
			System.out.println("name : "+ bucket.getName() );
		//	if(bucket.getName().equals("tanvilambdabucket"))
//				objectListing = s3_1.listObjects(bucket.getName());
//			else
//				objectListing = s3.listObjects(bucket.getName());
				objectListing.getObjectSummaries().stream().forEach(summary->{
			System.out.println("Objects: "+ summary.getKey());
		});
		});
		return list;
	}
}

//		ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
//		ListObjectsV2Result result;
//		result = s3.listObjectsV2(req);
//		
//		result.getObjectSummaries().stream()
//		.forEach(S3ObjectSummary-> {
//			list.add(S3ObjectSummary.getKey());
//			System.out.println(S3ObjectSummary.getKey());
//		});
	
