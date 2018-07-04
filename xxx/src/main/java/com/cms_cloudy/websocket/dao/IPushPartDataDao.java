package com.cms_cloudy.websocket.dao;

import java.util.List;
import java.util.Map;
import com.cms_cloudy.websocket.pojo.PushPartData;
import com.cms_cloudy.websocket.pojo.PushpartDetail;

public interface IPushPartDataDao {

	/**查询足迹表**/
	public List<PushPartData> selectPushPartData(Map<String,Object> map);
    /**增加足迹信息**/
	public void insertPushPartData(PushPartData pushData);
	/**更新足迹信息**/
	public void updatePushPartDatar(PushPartData pushData);
	/**删除足迹信息**/
	public int deletePushPartData(int id);
	/**热门数据初始化**/
	public List<PushPartData> selectHotSearchData(Map<String,Object> map);
	/**最新推荐初始化**/
	public List<PushPartData> selectPushNewPart(Map<String,Object> map);
	/**器件查询**/
	public List<Map<String,Object>> selectFirstPartData(Map<String,Object> map);
	/**推送从表数据添加**/
	public void insertPushPartDetail(PushpartDetail detail);
	/**从表数据删除**/
	public int deletePushPartDetail(int id);
	/**从表数据查询**/
	public List<PushpartDetail> selectPushPartDetail(Map<String,Object> map);
	/**查询重复值**/
	public List<PushPartData> selectDistinctPushPart (Map<String,Object> map);
	/**查询热门搜索bu重复值**/
	public List<PushPartData> selectDistinctPushPartToshow (Map<String,Object> map);
	/**查询浏览最多bu重复值**/
	public List<PushPartData> selectDistinctPushPartToHot (Map<String,Object> map);
	/**汇总重复值搜索次数**/
	public int selectDistinctSum (Map<String,Object> map);
	/**通过物料编码删除器件评价**/
	public int deletePartEvaluationByPartNumber(int id);
	/**通过器件ID删除推送主表数据**/
	public int deletePushPartDataByPartId(int id);
	/**通过物料编码删除历史记录表数据**/
	public int deletePartHistoryByPartNumber(int id);
}
