package org.nico.cat.mvc.scan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.aoc.loader.BookLoader;
import org.nico.cat.mvc.config.NomvcConfig;
import org.nico.cat.mvc.container.entity.CinemaEntity;
import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.collection.ArrayUtils;
import org.nico.util.collection.CollectionUtils;
import org.nico.util.reflect.ClassUtils;

/** 
 * Simple cinema scanner
 * @author nico
 * @version createTime：2018年1月16日 下午8:53:54
 */

public class SimpleCinemaScanner extends AbstractMVCScanner{

	private Logging logging = LoggingHelper.getLogging("NOMVC");
	
	@Override
	public List<CinemaEntity> scan(Map<String, Object> config) throws IOException {
		return loaderCinemas(config);
	}
	
	/**
	 * Loader Cinemas from scan-pack
	 * @throws IOException 
	 */
	public List<CinemaEntity> loaderCinemas(Map<String, Object> config) throws IOException{
		String scanpack = (String) config.get(NomvcConfig.CONFIG_FIELD_SCANPACK);
		//Temporarily out of use
		//scanByAoc(scanpack);
		List<Class<?>> classes = ClassUtils.getClasses(scanpack);
		List<CinemaEntity> cinemaEntities = null;
		if(CollectionUtils.isNotBlank(classes)){
			for(Class<?> clazz: classes){
				CinemaEntity entity = scanCinema(clazz);
				if(entity != null){
					synchronized (this) {
						if(cinemaEntities == null){
							cinemaEntities = new ArrayList<CinemaEntity>();
						}
						cinemaEntities.add(entity);
					}
				}
			}
		}
		return cinemaEntities;
	}
	
	private void scanByAoc(List<String> scanpack){
		try {
			BookLoader.loaderByAnnotation(scanpack);
		} catch (Throwable e) {
			logging.error(e);
		}
	}
	
	/**
	 * A depth scan of a Cinema is carried out. 
	 * @param clazz Class to scan
	 */
	public CinemaEntity scanCinema(Class<?> clazz){
		if(isCinema(clazz)){
			return scanNormalCinema(clazz);
		}else if(isRestCinema(clazz)){
			return scanRestCinema(clazz);
		}
		return null;
	}
	
	/**
	 * Scan the common Cinema.
	 * @param clazz Class to scan
	 * @return {@link CinemaEntity}
	 */
	public CinemaEntity scanNormalCinema(Class<?> clazz){
		CinemaEntity cinemaEntity = new CinemaEntity(clazz, getCinema(clazz));
		logging.debug("Scaning router -> " + clazz.getName());
		if(isMapping(clazz)){
			cinemaEntity.setLobbyEntity(scanLobby(clazz));
			Method[] methods = clazz.getDeclaredMethods();
			if(ArrayUtils.isNotBlank(methods)){
				for(Method method: methods){
					if(isMapping(method)){
						LobbyEntity lobbyEntity = scanLobby(method, cinemaEntity);
						cinemaEntity.getLobbies().add(lobbyEntity);
					}
				}
			}
		}
		return cinemaEntity;
	}

	/**
	 * A deep scan of the lobby inside the class.
	 * This method values the lobby on the class.
	 * 
	 * @param clazz Class to scan
	 * @return {@link LobbyEntity}
	 */
	public LobbyEntity scanLobby(Class<?> clazz){
		LobbyEntity lobbyEntity = new LobbyEntity(clazz, getLobby(clazz));
		return lobbyEntity;
	}
	
	/**
	 * A deep scan of the lobby inside the class.
	 * This method scans the lobby on all methods within the class.
	 * 
	 * @param method Method to scan.
	 * @param cinemaEntity  the {@link CinemaEntity} from the class
	 * @return {@link LobbyEntity}
	 */
	public LobbyEntity scanLobby(Method method, CinemaEntity cinemaEntity){
		LobbyEntity lobbyEntity = new LobbyEntity(method, getLobby(method));
		lobbyEntity.setClazz(cinemaEntity.getClazz());
		lobbyEntity.setSuperLobby(cinemaEntity.getLobbyEntity().getLobby());
		return lobbyEntity;
	}

	/**
	 * ScanRestCinema **Unrealized.**
	 * @param clazz
	 */
	public CinemaEntity scanRestCinema(Class<?> clazz){
		return scanNormalCinema(clazz);
	}

}
