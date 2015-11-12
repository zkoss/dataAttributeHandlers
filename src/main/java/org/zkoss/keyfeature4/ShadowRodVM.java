package org.zkoss.keyfeature4;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.NotifyCommands;
import org.zkoss.bind.annotation.SmartNotifyChange;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModelList;

@ToServerCommand({ShadowRodVM.LOAD_DATA_COMMAND, ShadowRodVM.SHOW_PREVIEW_COMMAND, ShadowRodVM.SCROLL_INTO_VIEW_COMMAND})
@ToClientCommand({
	ShadowRodVM.INIT_DATA,
	ShadowRodVM.LOAD_DATA_COMMAND,
	ShadowRodVM.UPDATE_BEGIN,
	ShadowRodVM.INIT_PREVIEW,
	ShadowRodVM.UPDATE_PREVIEW,
	ShadowRodVM.UPDATE_SCROLL
})
@NotifyCommands({
	@NotifyCommand(value = ShadowRodVM.INIT_DATA, onChange="_vm_.totalSize"),
	@NotifyCommand(value = ShadowRodVM.INIT_PREVIEW, onChange="_vm_.totalCitySize"),
	@NotifyCommand(value = ShadowRodVM.UPDATE_BEGIN, onChange="_vm_.begin"),
	@NotifyCommand(value = ShadowRodVM.UPDATE_PREVIEW, onChange="_vm_.preview"),
	@NotifyCommand(value = ShadowRodVM.UPDATE_SCROLL, onChange="_vm_.begin"),
})
public class ShadowRodVM {

	private RestaurantService service = RestaurantService.getInstance(Sessions.getCurrent());
	static final String INIT_DATA = "initData";
	static final String INIT_PREVIEW = "initPreview";
	static final String LOAD_DATA_COMMAND = "loadData";
	static final String UPDATE_BEGIN = "updateBegin";
	static final String SHOW_PREVIEW_COMMAND = "showPreview";
	static final String UPDATE_PREVIEW = "updatePreview";
	static final String SCROLL_INTO_VIEW_COMMAND = "scrollIntoView";
	static final String UPDATE_SCROLL = "updateScroll";
	private int begin = 0;
	private int cachedSize = 30;
	private int totalSize;
	private ListModelList<Restaurant> restaurantList;
	private List<String> cities;
	private List<RestaurantPreview> previewBar;
	private RestaurantPreview preview;
	private Map<String, Integer> numOfCityInit;
	private int totalCitySize;

	@Init
	public void init() {
		totalSize = service.getRestaurantsCount();
		restaurantList = new ListModelList<Restaurant>(loadRestaurant(0));
		previewBar = service.countGroupByCity();
		cities = service.getAllCities();
		totalCitySize = cities.size();
	}

	@Command(LOAD_DATA_COMMAND)
	@SmartNotifyChange({"begin"})
	public void loadData(@BindingParam("loadingIndex") int index, @BindingParam("direction") String direction) {
		restaurantList.clear();
		if("down".equals(direction)) {
			restaurantList.addAll(loadRestaurant(index));
		} else if("up".equals(direction)) {
			restaurantList.addAll(loadRestaurant(index - cachedSize));
		}
	}

	@Command(SCROLL_INTO_VIEW_COMMAND)
	@SmartNotifyChange({"begin"})
	public void scrollIntoView(@BindingParam("loadingIndex") int index) {
		String cityName = cities.get(index);
		int loadingIndex = 0;
		for (RestaurantPreview rp : previewBar) {
			if (cityName.equals(rp.getCityName())) {
				break;
			} else {
				loadingIndex += rp.getNumberOfCity();
			}
		}
		loadData(loadingIndex, "down");
	}

	@Command(SHOW_PREVIEW_COMMAND)
	@SmartNotifyChange({"preview"})
	public void showPreview(@BindingParam("showIndex") int index) {
		String cityName = cities.get(index);
		List<Restaurant> rlist = service.getRestaurantsByCity(cityName);
		Map<String, Integer> numberOfType = new TreeMap<String, Integer>();
		for (Restaurant res : rlist) {
			String type = res.getType();
			Integer number = numberOfType.get(type);
			if (number == null) {
				numberOfType.put(type, new Integer(1));
			} else {
				numberOfType.put(type, ++number);
			}
		}
		for (RestaurantPreview rp : previewBar) {
			if (cityName.equals(rp.getCityName())) {
				preview = rp;
				preview.setNumberOfType(numberOfType);
				break;
			}
		}
	}

	private List<Restaurant> loadRestaurant(int index) {
		this.begin = Math.min(Math.max(index, 0), totalSize - cachedSize);
		int end = Math.min(begin + cachedSize, totalSize);
		return service.getRestaurants(begin, end);
	}

	public ListModelList<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public Map<String, Integer> getNumOfCityInit() {
		if (numOfCityInit == null) {
			numOfCityInit = new TreeMap<String, Integer>();
			for (RestaurantPreview rp : previewBar) {
				String key = rp.getCityInitial();
				Integer number = numOfCityInit.get(key);
				if (number == null) {
					numOfCityInit.put(key, 1);
				} else {
					numOfCityInit.put(key, ++number);
				}
			}
		}
		return numOfCityInit;
	}

	public int getTotalCitySize() {
		return totalCitySize;
	}

	public int getBegin() {
		return begin;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public RestaurantPreview getPreview() {
		return preview;
	}

}
