package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
    lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig(
        authType = AuthType.NONE,
        invokeMode = InvokeMode.BUFFERED
)

public class HelloWorld implements RequestHandler<APIGatewayV2HTTPEvent, Map<String, Object>> {

    public Map<String, Object> handleRequest(APIGatewayV2HTTPEvent request, Context context) {
        Map<String, Object> response = new HashMap<>();
        String method = getMethod(request);
        String path = getPath(request);

        if ("/hello".equals(path) && "GET".equals(method)) {
            response.put("statusCode", 200);
            response.put("message", "Hello from Lambda");
        } else {
            response.put("statusCode", 400);
            response.put("message", "Bad request syntax or unsupported method. Request path: " + path +". HTTP method: " + method);
        }
        return response;
    }

    private String getMethod(APIGatewayV2HTTPEvent requestEvent) {
        return requestEvent.getRequestContext().getHttp().getMethod();
    }

    private String getPath(APIGatewayV2HTTPEvent requestEvent) {
        return requestEvent.getRequestContext().getHttp().getPath();
    }
}
