package com.cms_cloudy.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.cms_cloudy.product.dao.IProductBomDao;
import com.cms_cloudy.product.pojo.ProductBomEntity;
import com.cms_cloudy.product.pojo.ProductPn;
import com.cms_cloudy.product.service.IProductBomService;

@Component("iProductBomService")
public class ProductBomServiceImpl implements IProductBomService {
	@Autowired
	private IProductBomDao iproductBomDao;
	@Resource  
    public JdbcTemplate jdbcTemplate; 

	public int insertProductPn(ProductPn productPn) {
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("partNumber", productPn.getPartNumber());
		paramMap.put("productId", productPn.getProductId());
		Map<String,Object> counMap=iproductBomDao.checkProductPn(paramMap);
		if(counMap!=null){//已存在修改coun数量
			productPn.setCoun((Integer) counMap.get("coun")+productPn.getCoun());
			iproductBomDao.updateProductPnCoun(productPn);
			return 0;
		}
		iproductBomDao.insertProductPn(productPn);
		return productPn.getId();
	}

	public Map<String,Object> checkProductPn(Map<String, Object> paramMap) {
		return iproductBomDao.checkProductPn(paramMap);
	}

	public List<ProductBomEntity> selectProductBomList(Map<String, Object> map) {
		return iproductBomDao.selectProductBomList(map);
	}

	public List<ProductBomEntity> selectAllParentData(Map<String, Object> map) {
		return iproductBomDao.selectAllParentData(map);
	}

	public void insertProductBom(ProductBomEntity productBom) {
		iproductBomDao.insertProductBom(productBom);
	}

	public void updateProductBom(ProductBomEntity productBom) {
		iproductBomDao.updateProductBom(productBom);
	}

	public int deleteProductBom(int bomId) {
		return iproductBomDao.deleteProductBom(bomId);
	}

	public List<ProductPn> selectProductPnList(Map<String, Object> map) {
		return iproductBomDao.selectProductPnList(map);
	}

	public int deleteProductPn(int pnId) {
		return iproductBomDao.deleteProductPn(pnId);
	}

	public void updateProductPnCoun(ProductPn productPn) {
		iproductBomDao.updateProductPnCoun(productPn);
	}

	public List<Map<String, Object>>  selectAllParent(String[] ids) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("with cte_parent(id,parent_id,node_stage,part_number,product_name,product_model,product_type,product_no,product_code,product_stage,number,version,receiver,receivetime,createuser,createdate,modifyuser,modifydate,p_tool");
		buffer.append("\r\n");
		buffer.append(")");
		buffer.append("\r\n");
		buffer.append("as");
		buffer.append("\r\n");
		buffer.append("(");
		buffer.append("\r\n");
		buffer.append("select id,parent_id,node_stage,part_number,product_name,product_model,product_type,product_no,product_code,product_stage,number,version,receiver,receivetime,createuser,createdate,modifyuser,modifydate,p_tool from product_bom");
		buffer.append("\r\n");
		int i=0;
		if(ids.length>0){
	    buffer.append("where 1=1 and ");
			for(String id : ids){
				if(i == ids.length-1){
					buffer.append(" id= "+ids[i]);
				}else{
					buffer.append(" id= "+ids[i]+" or ");
				}
				i++;
			}
		}
		buffer.append("\r\n");
		buffer.append("union all");
		buffer.append("\r\n");
		buffer.append("select a.id,a.parent_id,a.node_stage,a.part_number,a.product_name,a.product_model,a.product_type,a.product_no,a.product_code,a.product_stage,a.number,a.version,a.receiver,a.receivetime,a.createuser,a.createdate,a.modifyuser,a.modifydate,a.p_tool");
		buffer.append("\r\n");
		buffer.append("from product_bom a");
		buffer.append("\r\n");
		buffer.append("inner join");
		buffer.append("\r\n");
		buffer.append("cte_parent b");
		buffer.append("\r\n");
		buffer.append("on a.id=b.parent_id");
		buffer.append("\r\n");
		buffer.append(")");
		buffer.append("\r\n");
		buffer.append("select * from cte_parent");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
//		String msg = JSON.toJSONString(list);
//		List<ProductBomEntity> proList = JSON.parseArray(msg, ProductBomEntity.class);
		return list;
	}

	public List<ProductBomEntity> selectAllChildData(Map<String, Object> map) {
		return iproductBomDao.selectAllChildData(map);
	}

	public int deleteProductPnByProductId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return iproductBomDao.deleteProductPnByProductId(map);
	}

	public List<Map<String, Object>> bomPnList(Map<String, Object> map) {
		String sql = "SELECT excel_name,MAX(id) id FROM product_bom_pn where productID="+map.get("id")+" GROUP BY excel_name ORDER BY excel_name";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	public List<Map<String, Object>> selectCompareBomList(Map map) {
		String sql = "select pn.*,pd.Item,pd.Part_Type,pd.Manufacturer,pd.Value,pd.KeyComponent,pd.Datesheet,pd.Part_Reference,pd.PartCode,pd.PartNumber as ptNumber from product_bom_pn pn left join Part_Data pd on pd.PartCode=pn.PartNumber where 1=1";
		if(null != map){
			
			if(null != map.get("excelName") && !"".equals(map.get("excelName").toString())){
				sql += " and pn.excel_name = "+"'"+map.get("excelName").toString()+"'";
			}
			
			if(null != map.get("productID") && !"".equals(map.get("productID").toString())){
				sql += " and pn.productID = "+map.get("productID");
			}
			
		}
		sql += " order by id desc";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	public int deleteProductBomPnByCondition(Map<String, Object> map) {
		return iproductBomDao.deleteProductBomPnByCondition(map);
	}
}
