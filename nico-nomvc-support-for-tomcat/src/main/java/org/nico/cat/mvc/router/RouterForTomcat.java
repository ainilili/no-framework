package org.nico.cat.mvc.router;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nico.aoc.book.shop.BookShop;
import org.nico.asm.contains.entity.ASMParameterEntity;
import org.nico.cat.mvc.config.NomvcConfig;
import org.nico.cat.mvc.container.NomvcContainer;
import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.cat.mvc.exception.IllegalRequestException;
import org.nico.cat.mvc.exception.NotFoundMappingException;
import org.nico.cat.mvc.parameter.RequestMethod;
import org.nico.cat.mvc.scan.annotations.Body;
import org.nico.cat.mvc.scan.annotations.QueryParam;
import org.nico.cat.mvc.scan.annotations.PathParam;
import org.nico.cat.mvc.scan.annotations.RestCinema;
import org.nico.cat.mvc.util.AsmUtils;
import org.nico.cat.mvc.util.InputStreamUtils;
import org.nico.cat.mvc.util.PathValidMatchUtils;
import org.nico.cat.mvc.util.UriUtils;
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
public class RouterForTomcat extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logging logging = LoggingHelper.getLogging(RouterForTomcat.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			NomvcContainer container = NomvcContainer.getInstance();
			RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
			String requestUri = request.getRequestURI();
			if(container.containsLobbyEntity(requestUri, requestMethod)){
				LobbyEntity lobbyEntity = null;
				if((lobbyEntity = container.getLobbyEntity(requestUri, requestMethod)) != null){
					Map<String, Object> pathValidMap = PathValidMatchUtils.parsePathValid(lobbyEntity.getLobby().mapping(), requestUri);
					if(lobbyEntity.getMethod() != null){
						Method method = lobbyEntity.getMethod();
						List<ASMParameterEntity> sequenceParameters = AsmUtils.getMethodParameters(method);
						Parameter[] params = lobbyEntity.getMethod().getParameters();
						Object[] arguments = new Object[params.length];
						if(ArrayUtils.isNotBlank(params)){
							int index = 0;
							for(Parameter param: params){
								Object argument = null;
								try{
									if(ServletRequest.class.isAssignableFrom(param.getType())){
										argument = request;
									}else if(ServletResponse.class.isAssignableFrom(param.getType())){
										argument = response;
									}else if(param.isAnnotationPresent(QueryParam.class)){
										argument = TypeUtils.convert(param.getType(), request.getParameter(sequenceParameters.get(index).getName()));
									}else if(param.isAnnotationPresent(PathParam.class)){
										argument = TypeUtils.convert(param.getType(), pathValidMap.get(sequenceParameters.get(index).getName()));
									}else if(param.isAnnotationPresent(Body.class)){
										argument = InputStreamUtils.readStreamByLen(request.getInputStream(), request.getContentLength());
									}else{
										argument = TypeUtils.convert(param.getType(), null);
									}
								}catch(ParseException e){
									e.printStackTrace(response.getWriter());
									response.getWriter().flush();
									response.getWriter().close();
								}

								arguments[index ++] = argument;
							}
						}
						Object ret = method.invoke(BookShop.getInstance().get(lobbyEntity.getClazz().getSimpleName()).getObject(), arguments);
						if(ret != null){
							if(method.isAnnotationPresent(Body.class) || lobbyEntity.getClazz().isAnnotationPresent(RestCinema.class)){
								if(ret instanceof String){
									//NOTHING TO DO
								}else if(ret instanceof Object){
									try{
										ret = Noson.reversal(ret);
										response.setHeader("ContentType", "application/json");
									}catch(Exception e){
										//NOTHING TO DO
									}
								}
								response.getWriter().print(String.valueOf(ret));
								response.getWriter().flush();
								response.getWriter().close();
							}else{
								if(! (ret instanceof String)){
									throw new IllegalRequestException("Request uri must be String type , " + ret.getClass().getName() + " is don't illegal.");
								}
								String newRedirect = String.valueOf(ret);
								if(newRedirect.startsWith("R>")){
									request.getRequestDispatcher(newRedirect).forward(request, response); 
								}else{
									response.sendRedirect(NomvcConfig.CONFIG_CONTEXT_PATH + UriUtils.tidyUri(String.valueOf(newRedirect)));
								}
							}
						}else{
							throw new NullPointerException("Request uri can't be null.");
						}
					}
				}
			}else{
				throw new NotFoundMappingException(requestUri, requestMethod);
			}
		}catch(Exception e){
			logging.error(e);
			e.printStackTrace(response.getWriter());
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
}