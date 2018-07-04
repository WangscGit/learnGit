package com.cms_cloudy.util;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

public class TestXml {
	public static void main(String args[]) {
		createxml();
//		testxml();
	}
	//创建xml
	public static void createxml() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element svg = doc.createElement("svg");
			Attr sattr1 = doc.createAttribute("xmlns");
			Attr sattr2 = doc.createAttribute("version");
			sattr1.setNodeValue("http://www.w3.org/2000/svg");
			sattr2.setNodeValue("1.1");
			svg.setAttributeNode(sattr1);
			svg.setAttributeNode(sattr2);
			doc.appendChild(svg);
			
			Element line1 = doc.createElement("line");
			Attr x1 = doc.createAttribute("x1");
			Attr y1 = doc.createAttribute("y1");
			Attr x2 = doc.createAttribute("x2");
			Attr y2 = doc.createAttribute("y2");
			Attr style = doc.createAttribute("style");
			x1.setNodeValue("0");
			y1.setNodeValue("0");
			x2.setNodeValue("200");
			y2.setNodeValue("200");
			style.setNodeValue("stroke:rgb(255,0,0);stroke-width:2");
			line1.setAttributeNode(x1);
			line1.setAttributeNode(y1);
			line1.setAttributeNode(x2);
			line1.setAttributeNode(y2);
			line1.setAttributeNode(style);
			svg.appendChild(line1);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = null;
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(outStream));

			System.out.println(outStream.toString().split("\\?>")[1].trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testxml() {
		// 1.创建解析工厂：
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 2.指定DocumentBuilder
		DocumentBuilder db;
		try {
			db = factory.newDocumentBuilder();

			// 3.从文件构造一个Document,因为XML文件中已经指定了编码，所以这里不必了
			Document xmlDoc = db.parse(new File("src/main/resources/test.xml"));
			// 首先创建转化工厂
			TransformerFactory transFactory = TransformerFactory.newInstance();
			// 创建Transformer，它能够将源树转换为结果树
			Transformer transformer = transFactory.newTransformer();
			// 接下来设置输出属性
			transformer.setOutputProperty("indent", "yes");
			DOMSource source = new DOMSource();
			// 得到Document的根
			Element docEle = xmlDoc.getDocumentElement();
			NodeList list = docEle.getElementsByTagName("bean");
			for (int i = 0; i < list.getLength(); i++) {
				Node n = list.item(i);
				for(int j=0;j<n.getChildNodes().getLength();j++){
					if(n.getChildNodes().item(j).getNodeType()==Element.ELEMENT_NODE){
					System.out.println(n.getChildNodes().item(j).getNodeName());
				}
				}
//				source.setNode(n);
//				StreamResult result = new StreamResult();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				result.setOutputStream(baos);
//				transformer.transform(source, result);
//				String s = baos.toString();
//				System.out.println(s.split("\\?>")[1].trim());
			}
			System.out.println(list.getLength());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
