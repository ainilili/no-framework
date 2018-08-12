package org.nico.cat.server.processer.response.chains.segment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.cat.server.util.ImageUtils;
import org.nico.cat.server.util.UriUtils;
import org.nico.cat.server.util.WrapperUtils;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.resource.ResourceUtils;

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
			
			String resourcePath = null;
			
			if(request.getUri().equals("/")) {
				resourcePath = BASE_PATH + defaultIndex;
			}else{
				resourcePath = BASE_PATH + (alreadyRedirected ? request.getUriRedirect() : request.getUri());
			}
			
			if(alreadyRedirected) {
				response.setHttpcode(HttpCode.HS302);
				response.getHeaders().putLast("Location", request.getUriRedirect());
			}else {
				InputStream backupsStream = null;
				try {
					/**
					 * The repeated flow prevents the flow from being read by the isImage(InputStream stream) method, 
					 * causing the response flow to be empty.
					 */
					if(ResourceUtils.RUNNING_IN_JAR) {
						backupsStream = ResourceUtils.getClasspathResource(resourcePath);
					}else {
						backupsStream = ResourceUtils.getResource(resourcePath);
					}
					if(backupsStream == null) {
						throw new FileNotFoundException("Can not found resource: " + resourcePath);
					}
					String uriSuffix = UriUtils.getSuffix(request.getUri());
					
					if(ImageUtils.isImage(uriSuffix)){
						response.setContentType(ContentType.IMAGE);
						response.write(backupsStream);
					}else if(WrapperUtils.specialContentType.containsKey(uriSuffix)) {
						response.setContentType(ContentType.APPLICATION);
						response.write(backupsStream);
					}else{
						response.setContentType(ContentType.TEXT);
						response.write(backupsStream);
					}
					response.setHttpcode(HttpCode.HS200);
				} catch (FileNotFoundException e) {
					response.setHttpcode(HttpCode.HS404);
				}finally{
				}
			}
			
		}
		return next(request, response);
	}

}
