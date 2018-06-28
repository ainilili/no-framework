package org.nico.cat.server.processer.response.chains.segment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.cat.server.util.UriUtils;
import org.nico.cat.server.util.WrapperUtils;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.resource.ResourceUtils;
import org.nico.util.stream.FileUtils;
import org.nico.util.stream.StreamUtils;

public class ProcessUriResource extends AbstractResponseProcess{

	private static final String BASE_PATH;
	
	private static String defaultIndex = "/index.html";
	
	static{
		BASE_PATH = ResourceUtils.RUNNING_IN_JAR 
			? UriUtils.tidyUri(ConfigKey.server_resource_path) : 
			(ConfigKey.server_resource_path.startsWith("/") ? System.getProperty("user.dir") : "".intern()) + UriUtils.tidyUri(ConfigKey.server_resource_path);
		if(CollectionUtils.isNotBlank(Container.getInstance().getWelcomes())) {
			for(String welcome: Container.getInstance().getWelcomes()) {
				String tidiUri = UriUtils.tidyUri(welcome);
				String resourcePath = BASE_PATH + tidiUri;
				if(new File(resourcePath).exists()) {
					defaultIndex = tidiUri;
					break;
				}
			}
		}
	}
	
	@Override
	public Response process(Request request, Response response) throws Exception {
		if(! response.isPushed()){
			
			boolean alreadyRedirected = request.getUriRedirect() != null;
			
			String resourcePath = BASE_PATH + (alreadyRedirected ? request.getUriRedirect() : request.getUri());
			
			if(request.getUri().equals("/")) {
				resourcePath = BASE_PATH + defaultIndex;
			}
			if(alreadyRedirected) {
				response.setHttpcode(HttpCode.HS302);
				response.getHeaders().putLast("Location", request.getUriRedirect());
			}else {
				InputStream inputStream = null;
				InputStream backupsStream = null;
				try {
					/**
					 * The repeated flow prevents the flow from being read by the isImage(InputStream stream) method, 
					 * causing the response flow to be empty.
					 */
					if(ResourceUtils.RUNNING_IN_JAR) {
						inputStream = ResourceUtils.getClasspathResource(resourcePath);
						backupsStream = ResourceUtils.getClasspathResource(resourcePath);
					}else {
						inputStream = ResourceUtils.getResource(resourcePath);
						backupsStream = ResourceUtils.getResource(resourcePath);
					}
					if(inputStream == null) {
						throw new FileNotFoundException("Can not found resource: " + resourcePath);
					}
					
					if(FileUtils.isImage(inputStream)){
						response.setContentType(ContentType.IMAGE);
						response.write(backupsStream);
					}else if(WrapperUtils.specialContentType.containsKey(UriUtils.getSuffix(request.getUri()))) {
						response.setContentType(ContentType.APPLICATION);
						response.write(backupsStream);
					}else{
						response.setContentType(ContentType.TEXT);
						String html = StreamUtils.readStream2Str(backupsStream);
						response.print(html);
						backupsStream.close();
					}
					response.setHttpcode(HttpCode.HS200);
				} catch (FileNotFoundException e) {
					response.setHttpcode(HttpCode.HS404);
				}finally{
					if(inputStream != null){
						inputStream.close();
					}
				}
			}
			
		}
		return next(request, response);
	}

}
