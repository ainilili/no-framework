package org.nico.cat.server.processer.response.chains.segment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.exception.error.StreamReadException;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.cat.server.util.UriUtils;
import org.nico.cat.server.util.WrapperUtils;
import org.nico.util.resource.ResourceUtils;
import org.nico.util.stream.FileUtils;
import org.nico.util.stream.StreamUtils;

public class ProcessUriResource extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		if(! response.isPushed()){

			String basePath = ConfigKey.server_resource_path;
			if(basePath.startsWith("/")){
				basePath = System.getProperty("user.dir") + basePath;
			}
			String resourcePath = basePath + request.getUri();
			FileInputStream inputStream = null;
			FileInputStream backupsStream = null;
			try {
				/**
				 * The repeated flow prevents the flow from being read by the isImage(InputStream stream) method, 
				 * causing the response flow to be empty.
				 */
				inputStream = ResourceUtils.getResourceAsFile(resourcePath);
				backupsStream = ResourceUtils.getResourceAsFile(resourcePath);
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
		return next(request, response);
	}

}
