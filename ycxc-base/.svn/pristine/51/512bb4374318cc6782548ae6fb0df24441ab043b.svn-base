package com.ycxc.base.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * Json工具类
 * @author ly
 *
 */

public class JsonUtil {
	
	/**
	 * json转List
	 * @param str
	 * @param cls
	 * @return
	 */
	public static final <T> List<T> getList(String str,Class<T> cls){
		List<T> list = JSON.parseArray(str,cls);
		return list;
	}
	
	/**
	 * 将Json文本数据信息转换为JsonObject对象,获取Value
	 * @param key    "name"
	 * @param json    {"name":"中车信息"}
	 * @return "中车信息"
	 */
	public static String getValue(String key,String json){
		JSONObject object = JSON.parseObject(json);
		return object.get(key).toString();
	}
	
	/**
	 * 通过json格式，返回JavaBean对象
	 * @param <T>
	 * @param json eg:{"name":"中车信息"}
	 * @param cls  User.class
	 * @return 
	 */
	public static <T> T jsonToObject(String json,Class<T> cls){
		return JSON.parseObject(json, cls);
	}
	
	//以下方法是将对象转成Json格式字符串
	/**
	 * 通过对象转换成json字符串,返回有指定key值的json
	 * @param <T>
	 * @return eg:{"name":"中车信息"}
	 */
	public static <T> String toJson(String key,T o){
		JSONObject json = new JSONObject();
		json.put(key,o);
		return json.toString();
	}
	
	/**
	 * 传入对象直接返回Json
	 * @param object
	 * @return
	 */
	 public static <T> String serialize(T object)
	 {
		 return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
     }
	 
	 /**
	  * 将对象转换成Json格式字符串，并过滤多余的字段。
	  *
	  * @param object 
	  * @param filter 过滤器
	  * @return
	  */
	 public static <T> String serialize(T object, PropertyFilter filter)
	 {
		return JSON.toJSONString(object,filter,SerializerFeature.DisableCircularReferenceDetect);
	 }
	
	
	
	
//	public static void main(String[] args) {
//		/******************示例1*************/
//		User user = new User();
//		user.setName("张三");
//		user.setAge(20);	
//		System.out.println(toJson("user",user));
//		User user2 = new User();
//		user2.setName("李四");
//		user2.setAge(25);	
//		System.out.println(toJson("user2",user2));
//		/******************示例2*************/
//		List<User> list = new ArrayList<User>();
//		list.add(user);
//		list.add(user2);
//		System.out.println(toJson("list",list));
//		System.out.println(serialize(list));
//		/******************示例3*************/
//		String s = "{\"name\":\"王五\",\"age\":30}";  
//		User user3 = (User)jsonToObject(s,User.class);
//        System.out.println("返回类型对象:"+user3.getName()+":"+user3.getAge());  
//      
//		/******************示例4*************/
//        String str="{\"name\":\"中车信息\"}";
//        System.out.println(getValue("name",str));
//		/******************示例5*************/
//        Map<String,User> map = new HashMap<String, User>();  
//        map.put("user", user);   
//        //需要过滤的类 + 属性  
//        Map<Class<?>, Set<String>> includeMap = new HashMap<Class<?>, Set<String>>();  
//        Set<String> set = new HashSet<String>();  
//        set.add("age");  
//        includeMap.put(User.class, set);//去掉User里面的age字段  
//                
//        JsonFilter cfilter = new JsonFilter(includeMap);  
//        SerializeWriter sw = new SerializeWriter();  
//        JSONSerializer serializer = new JSONSerializer(sw);   
//        serializer.getPropertyFilters().add(cfilter);  
//        serializer.write(map);    
//          
//        System.out.println(sw.toString()); 
//	}
//	 public static void main(String[] args) {
//			String str="[{\"name\":\"aaaaa\",\"age\":2},{\"name\":\"bbbb\",\"age\":3}]";
//			List<Ent> list = JsonUtil.getList(str, Ent.class); 
//			for (Ent ent : list) {
//				System.out.println(ent.getName());
//				System.out.println(ent.getAge());
//			}
//	}
}
