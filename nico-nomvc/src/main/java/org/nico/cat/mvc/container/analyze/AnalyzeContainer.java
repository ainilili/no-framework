package org.nico.cat.mvc.container.analyze;

import java.util.List;
import java.util.Map.Entry;

import org.nico.cat.mvc.config.NomvcConfig;
import org.nico.cat.mvc.container.NomvcContainer;
import org.nico.cat.mvc.container.entity.CinemaEntity;
import org.nico.cat.mvc.container.entity.LobbyEntity;
import org.nico.cat.mvc.util.UriUtils;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.collection.CollectionUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月16日 下午9:39:58
 */

public class AnalyzeContainer implements Analyze{

	private Logging logging = LoggingHelper.getLogging(AnalyzeContainer.class);
	
	@Override
	public void analyze(List<CinemaEntity> cinemaEntities) {
		NomvcContainer container = NomvcContainer.getInstance();
		if(CollectionUtils.isNotBlank(cinemaEntities)){
			for(CinemaEntity cinemaEntity: cinemaEntities){
				container.putCinema(cinemaEntity.getClazz(), cinemaEntity);
				if(cinemaEntity.getLobbies() != null && cinemaEntity.getLobbies().size() > 0){
					for(LobbyEntity lobby: cinemaEntity.getLobbies()){
						String api = NomvcConfig.CONFIG_CONTEXT_PATH + UriUtils.assemblyUri(cinemaEntity.getLobbyEntity(), lobby);
						lobby.setUnbrokenMapping(api);
						container.putLobby(api, lobby);
						logging.debug("Analyze lobby [" + api + "]-[" + lobby.getLobby().requestMethod() + "]");
					}
				}
			}
		}
	}
	
	
}
