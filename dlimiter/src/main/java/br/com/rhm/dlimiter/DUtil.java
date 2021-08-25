package br.com.rhm.dlimiter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("unchecked")
public class DUtil {
	
	public static String capitalize(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}
	
	public static String uncapitalize(String str){
		return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
	}
	
	public static String removeAndUncapitalize(String removableContent, String str){
		return uncapitalize(str.replace(removableContent, ""));
	}
	
	public static String generateSetterMethodName(String str){
		return appendPrefixAndCapitalize("set", str);
	}
	
	public static String generateGetterMethodName(String str){
		return appendPrefixAndCapitalize("get", str);
	}
	
	public static String generatePrimitiveBooleanGetterMethodName(String str){
		return appendPrefixAndCapitalize("is", str);
	}
	
	private static String appendPrefixAndCapitalize(String prefix, String str){
		return prefix + capitalize(str);
	}
	
	public static String onlyDigits(String str){
		return str.replaceAll("[^\\d]", "");
	}
	
	public static BigDecimal commaLeft(BigDecimal number, int nTimes){
		if (nTimes > 0)
			for (int i=0; i < nTimes; i++)
				number = number.divide(BigDecimal.TEN);
		
		return number;
	}
	
	public static <T extends Serializable> T copyObject(T obj) {
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			
			oos.close();
			byte[] serObj = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(serObj);
			ObjectInputStream ois = new ObjectInputStream(bais);
			
			return (T) ois.readObject();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public static String padLeft(String value, String charFill, int maxLenght) {
		int validation = validateMaxLenght(value, maxLenght);
		if (validation == 1)
			return value.substring(value.length() - maxLenght);
		else if (validation == 0)
			return value;
		else{
			int maxI = maxLenght - value.length();
			for (int i = 0; i < maxI; i++)
				value = charFill + value;
			return value;
		}
	}

	public static String padRight(String value, String charFill, int maxLenght) {
		int validation = validateMaxLenght(value, maxLenght);
		if (validation == 1)
			return value.substring(0, maxLenght);
		else if (validation == 0)
			return value;
		else{
			int maxI = maxLenght - value.length();
			for (int i = 0; i < maxI; i++)
				value = value + charFill;
			return value;
		}
	}
	
	private static int validateMaxLenght(String value, int maxLenght){
		if (value.length() > maxLenght)
			return 1;//overflow
		else if (value.length() == maxLenght)
			return 0;//equal
		else
			return -1;//lesser
	}

	
}
