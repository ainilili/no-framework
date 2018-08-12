package org.nico.cat.mvc.router;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.nico.aoc.book.Book;
import org.nico.aoc.book.shop.BookShop;
import org.nico.asm.contains.entity.ASMParameterEntity;
import org.nico.cat.config.ConfigKey;
import org.nico.cat.mvc.container.NomvcContainer;
import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.cat.mvc.exception.VerifyException;
import org.nico.cat.mvc.parameter.RequestMethod;
import org.nico.cat.mvc.scan.annotations.Body;
import org.nico.cat.mvc.scan.annotations.QueryParam;
import org.nico.cat.mvc.scan.annotations.PathParam;
import org.nico.cat.mvc.scan.annotations.RestCinema;
import org.nico.cat.mvc.scan.annotations.Verify;
import org.nico.cat.mvc.util.AsmUtils;
import org.nico.cat.mvc.util.PathValidMatchUtils;
import org.nico.cat.mvc.util.TypeConvertUtils;
import org.nico.cat.mvc.verify.ObjectVerify;
import org.nico.cat.server.container.moudle.realize.entry.Api;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Session;
import org.nico.cat.server.response.Response;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.noson.Noson;
import org.nico.util.collection.ArrayUtils;
import org.nico.util.reflect.TypeUtils;

/**
 * The MVC forward entry center command is responsible 
 * for assigning requests to the corresponding mapping.
 * 
 * @author nico
 * @version createTime：2018年1月15日 下午10:06:02
 */
public class RouterForNocat extends Api {

	private Logging logging = LoggingHelper.getLogging(RouterForNocat.class);

	/**
	 * MVC routing distribution center.
	 */
	@Override
	public void doService(Request request, Response response) throws Exception {
		try{
			NomvcContainer container = NomvcContainer.getInstance();
			RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toString());
			if(container.containsLobbyEntity(request.getUri(), requestMethod)){
				LobbyEntity lobbyEntity = null;
				if((lobbyEntity = container.getLobbyEntity(request.getUri(), requestMethod)) != null){
					Map<String, Object> pathValidMap = PathValidMatchUtils.parsePathValid(lobbyEntity.getUnbrokenMapping(), request.getUri());
					if(lobbyEntity.getMethod() != null){
						Method method = lobbyEntity.getMethod();
						String[] sequenceParameters = AsmUtils.getMethodParameters(method);
						Parameter[] params = lobbyEntity.getMethod().getParameters();
						Object[] arguments = new Object[params.length];
						if(ArrayUtils.isNotBlank(params)){
							int index = 0;
							ObjectVerify verify = new ObjectVerify();
							VerifyResult verifyResult = new VerifyResult();
							for(Parameter param: params){
								Object argument = null;
								if(param.getType().isAssignableFrom(Request.class)){
									argument = request;
								}else if(param.getType().isAssignableFrom(Response.class)){
									argument = response;
								}else if(param.getType().isAssignableFrom(VerifyResult.class)){
									argument = verifyResult;
								}else if(Session.class.isAssignableFrom(param.getType())){
									argument = request.getSession();
								}else if(param.isAnnotationPresent(QueryParam.class)){
									argument = TypeUtils.convert(param.getType(), request.getProperty(sequenceParameters[index]));
								}else if(param.isAnnotationPresent(PathParam.class)){
									argument = TypeUtils.convert(param.getType(), pathValidMap.get(sequenceParameters[index]));
								}else if(param.isAnnotationPresent(Body.class)){
									argument = Noson.convert(request.getBody(), param.getType());
								}else{
									argument = TypeUtils.convert(param.getType(), null);
								}
								try{
									verify.verify(param, argument);
									if(param.isAnnotationPresent(Verify.class)) {
										verify.verify(argument);
									}
								}catch(VerifyException e){
									verifyResult.setHasError(true);
									verifyResult.addError(e);
								}
								arguments[index ++] = argument;
							}
						}
						Book controlObj = BookShop.getInstance().get(lobbyEntity.getClazz().getSimpleName());
						Object obj = TypeConvertUtils.convert(controlObj.getClazz(), controlObj.getObject());
						if(obj instanceof Proxy){
							method = obj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
						}
						Object ret = method.invoke(obj, arguments);
						if(! response.isPushed()) {
							if(ret != null){
								if(method.isAnnotationPresent(Body.class) || lobbyEntity.getClazz().isAnnotationPresent(RestCinema.class)){
									if(ret instanceof String){
										//NOTHING TO DO
									}else if(ret instanceof Object){
										ret = Noson.reversal(ret);
									}
									response.getHeaders().putLast("Content-Type", "application/json;charset=" + ConfigKey.server_charset);
									response.setResponseBody(String.valueOf(ret));
									response.push();
								}else{
									request.setUriRedirect(String.valueOf(ret));
								}
							}else{
								response.setResponseBody(new NullPointerException("Response value is null"));
								response.push();
							}
						}
					}
				}
			}
			if(! response.isPushed()){
				request.setUriRedirect(request.getUri());
			}
		}catch(Exception e){
			logging.error(e);
			response.setResponseBody(e);
			response.push();
		}
	}
	
	public static class VerifyResult{
		
		private boolean hasError;
		
		private List<String> errorMessages;
		
		private List<Throwable> errors;

		public boolean hasError(){
			return hasError;
		}
		
		private void setHasError(boolean hasError) {
			this.hasError = hasError;
		}

		public List<String> getErrorMessages() {
			return errorMessages;
		}

		public List<Throwable> getErrors() {
			return errors;
		}
		
		private void addErrorMessage(String errorMessage){
			if(errorMessages == null){
				errorMessages = new ArrayList<String>();
			}
			errorMessages.add(errorMessage);
		}

		private void addError(Throwable error){
			if(errors == null){
				errors = new ArrayList<Throwable>();
			}
			errors.add(error);
			if(error != null){
				addErrorMessage(error.getMessage());
			}
		}
	}
}
