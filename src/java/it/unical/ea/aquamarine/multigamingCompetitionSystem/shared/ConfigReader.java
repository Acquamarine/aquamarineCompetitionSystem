
package it.unical.ea.aquamarine.multigamingCompetitionSystem.shared;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigReader {
	private Element xmlNode;

	public ConfigReader(URI filename){
		File xmlFile = new File(filename);
		Document xmlDocument;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDocument = dBuilder.parse(xmlFile);
			xmlDocument.getDocumentElement().normalize();
			xmlNode = xmlDocument.getDocumentElement();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private ConfigReader(Element node){
		xmlNode = node;
	}

	public boolean getBoolean(String name){
		return Boolean.parseBoolean(getSingleContent(name));
	}

	public int getHexadecimal(String name){
		return (int) Long.parseLong(getSingleContent(name), 16);
	}

	public int getInteger(String name){
		return Integer.parseInt(getSingleContent(name));
	}

	public float getFloat(String name){
		return Float.parseFloat(getSingleContent(name));
	}

	public double getDouble(String name){
		return Double.parseDouble(getSingleContent(name));
	}

	public long getLong(String name){
		return Long.parseLong(getSingleContent(name));
	}

	public String getString(String name){
		return getSingleContent(name);
	}

	public ConfigReader getConfigReader(String name){
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		return new ConfigReader((Element) tempNodeList.item(0));
	}

	public Collection<Boolean> getBooleanList(String name){
		List<Boolean> tempBooleanList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempBooleanList.add(Boolean.parseBoolean(tempNodeList.item(i).getTextContent()));
		}
		return tempBooleanList;
	}

	public Collection<Integer> getIntegerList(String name){
		List<Integer> tempIntegerList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempIntegerList.add(Integer.parseInt(tempNodeList.item(i).getTextContent()));
		}
		return tempIntegerList;
	}

	public Collection<Float> getFloatList(String name){
		List<Float> tempFloatList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempFloatList.add(Float.parseFloat(tempNodeList.item(i).getTextContent()));
		}
		return tempFloatList;
	}

	public Collection<Double> getDoubleList(String name){
		List<Double> tempDoubleList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempDoubleList.add(Double.parseDouble(tempNodeList.item(i).getTextContent()));
		}
		return tempDoubleList;
	}

	public Collection<String> getStringList(String name){
		List<String> tempStringList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempStringList.add(tempNodeList.item(i).getTextContent());
		}
		return tempStringList;
	}

	public Collection<ConfigReader> getConfigReaderList(String name){
		List<ConfigReader> tempConfigReaderList = new ArrayList<>();
		NodeList tempNodeList = xmlNode.getElementsByTagName(name);
		for(int i = 0; i < tempNodeList.getLength(); i++){
			tempConfigReaderList.add(new ConfigReader((Element) tempNodeList.item(i)));
		}
		return tempConfigReaderList;
	}

	private String getSingleContent(String nodeName){
		return xmlNode.getElementsByTagName(nodeName).item(0).getTextContent();
	}

	public boolean doesElementExist(String nodeName){
		return xmlNode.getElementsByTagName(nodeName).getLength() != 0;
	}

	public String getStringAttribute(String attributeName){
		return xmlNode.getAttribute(attributeName);
	}

	public double getDoubleAttribute(String attributeName){
		return Double.parseDouble(xmlNode.getAttribute(attributeName));
	}

	public float getFloatAttribute(String attributeName){
		return Float.parseFloat(xmlNode.getAttribute(attributeName));
	}

	public int getIntegerAttribute(String attributeName){
		return Integer.parseInt(xmlNode.getAttribute(attributeName));
	}

	public long getLongAttribute(String attributeName){
		return Long.parseLong(xmlNode.getAttribute(attributeName));
	}

}