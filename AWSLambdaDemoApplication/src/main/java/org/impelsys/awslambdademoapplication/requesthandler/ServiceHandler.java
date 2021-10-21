package org.impelsys.awslambdademoapplication.requesthandler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

public class ServiceHandler  implements RequestHandler<Map<String,String>,Object>{

	@Autowired
	AmazonS3 s3;
	
	@Override
	public String handleRequest(Map<String,String> input, Context context) {
		context.getLogger().log("Input is"+input);
		return "Hello world" + input;
	}

}
