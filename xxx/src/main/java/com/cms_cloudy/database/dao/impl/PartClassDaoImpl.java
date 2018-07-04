package com.cms_cloudy.database.dao.impl;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cms_cloudy.database.dao.IPartClassDao;
import com.cms_cloudy.database.pojo.FileImg;
import com.cms_cloudy.database.pojo.PartClass;
import com.cms_cloudy.database.pojo.PartDefineAttributes;

@Component("IPartClassDao")
public class PartClassDaoImpl implements IPartClassDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<Map<String, Object>> selectPartClass(Map<String, Object> map) {
		return sqlSession.selectList("com.cms_cloudy.dao.database.IPartClassDao.selectAllPartClass",map);
	}

	public void insertPartClass(PartClass p) {
		sqlSession.insert("com.cms_cloudy.dao.database.IPartClassDao.insertPartClass", p);
	}

	public void updatePartClass(PartClass p) {
		sqlSession.update("com.cms_cloudy.dao.database.IPartClassDao.updatePartClass", p);
	}

	public void deletePartClass(int id) {
		sqlSession.delete("com.cms_cloudy.dao.database.IPartClassDao.deletePartClass", id);
	}

	public void insertPartDefineAttributes(PartDefineAttributes p) {
		sqlSession.insert("com.cms_cloudy.dao.database.IPartClassDao.insertPartDefineAttributes", p);
	}

	public void updatePartDefineAttributes(PartDefineAttributes p) {
		sqlSession.update("com.cms_cloudy.dao.database.IPartClassDao.updatePartDefineAttributes", p);
	}

	public int selectPartDefineAttributes(int classId) {
		return sqlSession.selectOne("com.cms_cloudy.dao.database.IPartClassDao.selectPartDefineAttributes", classId);
	}

	public void deletePartDefineAttributes(int classId) {
		sqlSession.delete("com.cms_cloudy.dao.database.IPartClassDao.deletePartDefineAttributes", classId);
	}

	public int childNumIsRepeat(Map<String, Object> paramMap) {
		return sqlSession.selectOne("com.cms_cloudy.dao.database.IPartClassDao.childNumIsRepeat", paramMap);
	}

	public List<Map<String, Object>> getPnCode(Map<String, Object> parentNumMap) {
		if (parentNumMap.get("parentNumList") == null) {
			return sqlSession.selectList("com.cms_cloudy.dao.database.IPartClassDao.getAllPnCode",parentNumMap);
		} else {
			return sqlSession.selectList("com.cms_cloudy.dao.database.IPartClassDao.getPnCode", parentNumMap);
		}
	}
	
	public List<Map<String, Object>> getNodeByPnCode(Map<String, Object> parentNumMap) {
		return sqlSession.selectList("com.cms_cloudy.dao.database.IPartClassDao.getNodeByPnCode", parentNumMap);
	}

	public void insertFileImg(FileImg fileImg) {
		sqlSession.insert("com.cms_cloudy.dao.database.IPartClassDao.insertFileImg", fileImg);
	}

	public void deleteFileImg(long partClassId) {
		sqlSession.delete("com.cms_cloudy.dao.database.IPartClassDao.deleteFileImg", partClassId);
	}

	public List<FileImg> selectImgByName(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("com.cms_cloudy.dao.database.IPartClassDao.selectImgByName", map);
	}
	
}
