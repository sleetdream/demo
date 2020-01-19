package com.citicguoanbn.codeBuild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 运行main函数
 * 功能：生成本框架的基础代码，包括java类，xml文件，前台文件
 * date:2018-05-08
 */																																																																																																																																																																																																																																																																																																																																																																																						
public class CodeEngine {
	private static final String PACKAGENAME = "com.example.applycore";
	private static final String PATHNAME = "com/example/applycore";
	private static final String SERVICEAPPNAME="applycore";//service项目名,以及模板文件所在项目名
	private static final String CLIENTAPPRNAME="";//client项目名
	
	//数据库表名
	private static final String TABLENAME = "test";
	private static final String TABLESCHEMA = "demo";//
	private static final String TABLEKEY = "id";   //表主键
	
	private String INDEXDISFIELD = ""; //列表显示字段
	private String SEARCHFIELD = "";   //列表搜索字段
	private static final String FUNNAME = "enterprise";  //功能名称
	private static final String FUNCPATH = "";
	
	//功能名称
	private static final String AUTHOR = "sleetdream";

	public static final String SRC_PATH = "src/main/java/";  //src根目录
	public static final String ROOT_SAVEPATH = SRC_PATH+PATHNAME; //源码根目录
	public static final String RESOURCES_PATH = "src/main/resources";  //src根目录
	public static final String TEMPLATEPATH = "src/main/java/com/citicguoanbn/codeBuild/";  //模板路径

	/**** 生成后文件存放的位置  ******/
	public static final String POJO_SAVEPATH = ROOT_SAVEPATH+"/po";  //pojo类存放的位置
	public static final String MAPPER_SAVEPATH = RESOURCES_PATH+"/mapper";  //pojo类存放的位置
	public static final String DAO_SAVEPATH = ROOT_SAVEPATH+"/mapper";  //dao接口存放的位置
	public static final String SQLMAP_SAVEPATH = ROOT_SAVEPATH+"/dao";  //ibatis的sql.xml文件存放的位置
	public static final String DAOIMPL_SAVEPATH = ROOT_SAVEPATH+"/dao/impl";  //dao接口实现类存放的位置
	public static final String SERVICE_SAVEPATH = ROOT_SAVEPATH+"/service";  //service实现类存放的位置
	public static final String SERVICEIMPL_SAVEPATH_IMPL = ROOT_SAVEPATH+"/service/impl";  //service实现类存放的位置

	public static final String WEBROOT_PATH = "src/main/webapp/";  //WEB根目录

	//表名第一个字母大写
	private final String CLASSNAME;	
	
	//主键第一个字母大写
	private final String TABLEKEYUPPER;
	
	//当前日期
	private final String DATE;
	
	//项目的根目录
	private static String rootpath = PropertiesUtil.getRootpath().replace("target/", "").replace("classes/", "").replace(SERVICEAPPNAME+"/", "");

	//模板路径
	private static String templatePath = TEMPLATEPATH;
	
	//文件工具类
	private FileUtil fileUtil;
	
	//数据库工具类
	private DbUtil dbOperate;
	
	//替换内容
	private String fileContent;
	
	public CodeEngine(){
		CLASSNAME = oneWordUpper(TABLENAME);
		TABLEKEYUPPER = oneWordUpper(TABLEKEY);
		DATE = getDate();
		fileUtil = new FileUtil();
		dbOperate = new DbUtil();
	}
	
	/*
	 * 主入口方法
	 */
	public static void main(String args[]){
		
		CodeEngine codeEngine = new CodeEngine();
		String className=codeEngine.oneWordUpper(TABLENAME);
		ArrayList fieldList = codeEngine.dbOperate.getTableDescList(TABLENAME,TABLESCHEMA);
		//java类、ibatis sql文件自动生成
		codeEngine.javaCode(codeEngine,className,fieldList);
		
	}
	
	public void javaCode(CodeEngine codeEngine, String className, ArrayList fieldList){
		codeEngine.createFile("sqlmap.txt",className+"DOMapper.xml",MAPPER_SAVEPATH,fieldList,"");
		codeEngine.createFile("daoInterface.txt",className+"DOMapper.java",DAO_SAVEPATH,fieldList,"");
		codeEngine.createFile("pojoClass.txt",className+"DO.java",POJO_SAVEPATH,fieldList,"");   //  "/"+FUNNAME
		codeEngine.createFile("service.txt",className+"Service.java",SERVICE_SAVEPATH,fieldList,"");
		codeEngine.createFile("serviceImpl.txt",className+"ServiceImpl.java",SERVICEIMPL_SAVEPATH_IMPL,fieldList,"");
	}
	
	public void webCode(CodeEngine codeEngine, String className, ArrayList fieldList){
		//codeEngine.createFile("controller.txt",className+"Controller.java",CONTROLLER_SAVEPATH,fieldList);
//		codeEngine.createFile("view_add.txt","add.jsp",WEB_PATH+TABLENAME,fieldList);
		//codeEngine.createFile("view_index.txt","index.jsp",WEB_PATH+TABLENAME,fieldList);
//		codeEngine.createFile("view_update.txt","update.jsp",WEB_PATH+TABLENAME,fieldList);
		//codeEngine.createFile("view_update.txt","view.jsp",WEB_PATH+TABLENAME,fieldList);
	}
	
	public void createFoldFile(String creathPath,String templateName,String fileName,ArrayList fieldList){
		//模板的详细地址
		String file_path = creathPath+CLASSNAME;
		//创建文件夹
		fileUtil.createFolder(file_path);
		//模板的详细地址
		String template_path = rootpath+templatePath+templateName;
		//得到模板的内容
		String fileCon = fileUtil.readTxt(template_path);
		//替换模板中的标签为具体代码
		fileCon = replaceTemplate(fileCon,fieldList);
		//创建文件 和 写入文件内容
		fileUtil.writeTxt(file_path,fileName,fileCon);
		System.out.println(file_path+"/"+fileName+"文件生成成功");
	}
	
	
	
	public void createSqlMapFile(String templateName,String sqlMapFile,String fileName,String filePath,ArrayList fieldList){
		//模板的详细地址
		String file_path = rootpath+templatePath+templateName;
		//得到模板的内容
		String fileCon = fileUtil.readTxt(file_path);
		//替换模板中的标签为具体代码
		fileContent = replaceTemplate(fileCon,fieldList);				
		String oldCon= fileUtil.readTxt(sqlMapFile);
		if(oldCon.indexOf(fileContent)==-1){
			oldCon = replaceTemplate(oldCon,fieldList);
		}		
		//创建文件 和 写入文件内容
		fileUtil.writeTxt(sqlMapFile,"", oldCon);
		System.out.println(sqlMapFile+"文件更新成功");
	}
	
	/*
	 * templateName:模板名称
	 * fileName：生成后的文件名
	 * fileCon：文件内容
	 * filePath：生成后的文件存放地址
	 */
	public void createFile(String templateName,String fileName,String filePath,ArrayList fieldList,String functionName){
		//模板的详细地址
		String file_path = rootpath+templatePath+templateName;
		//得到模板的内容
		String fileCon = fileUtil.readTxt(file_path);
		//替换模板中的标签为具体代码
		fileCon = replaceTemplate(fileCon,fieldList);
		//创建文件 和 写入文件内容
		fileUtil.writeTxt(rootpath+filePath+functionName,fileName, fileCon);
		System.out.println(rootpath+filePath+"/"+fileName+"文件生成成功");
	}
	
	public String replaceTemplate(String fileCon,ArrayList fieldList){
		fileCon = fileCon.replace("{PACKAGENAME}", PACKAGENAME);
		fileCon = fileCon.replace("{TABLENAME}", TABLENAME);
		fileCon = fileCon.replace("{CLASSNAME}", CLASSNAME);
		fileCon = fileCon.replace("{FUNNAME}", FUNNAME);
		fileCon = fileCon.replace("{FUNCPATH}", FUNCPATH);
		fileCon = fileCon.replace("{AUTHOR}", AUTHOR);
		fileCon = fileCon.replace("{DATE}", DATE);
		fileCon = fileCon.replace("{TABLEKEYUPPER}", TABLEKEYUPPER);
		fileCon = fileCon.replace("{TABLEKEY}", TABLEKEY);
		fileCon = fileCon.replace("<!--tagbody-->", fileContent+"<!--tagbody-->");		
		
		String tagname = "fieldlist";
		while(fileCon.indexOf(tagname) > -1){
			fileCon = replaceLoopField(fileCon,tagname,fieldList);
		}
		tagname = "fieldkeylist";
		while(fileCon.indexOf(tagname) > -1){
			fileCon = replaceLoopField(fileCon,tagname,fieldList);
		}
		tagname = "searchlist";
		while(fileCon.indexOf(tagname) > -1){
			fileCon = replaceLoopField(fileCon,tagname,fieldList);
		}
		tagname = "indexdislist";
		while(fileCon.indexOf(tagname) > -1){
			fileCon = replaceLoopField(fileCon,tagname,fieldList);
		}
		tagname = "wherelist";
		while(fileCon.indexOf(tagname) > -1){
			fileCon = replaceLoopField(fileCon,tagname,fieldList);
		}
		return fileCon;
	}
	
	//循环替换表字段
	@SuppressWarnings("unchecked")
	public String replaceLoopField(String fileCon,String tagname,ArrayList fieldList){
		String startTag = "{"+tagname+"}";
		String enTag = "{/"+tagname+"}";
		if(fileCon.indexOf(startTag) == -1) return fileCon;
		int i = fileCon.indexOf(startTag);
		int j = fileCon.indexOf(enTag)+enTag.length();
		//得到标签体
		String tagBody = fileCon.substring(i,j);
		String bodyCon = tagBody.replace(startTag, "").replace(enTag, "");
		//取出此表数据库描述
		//ArrayList fieldList = dbOperate.getTableDescList(TABLENAME);
		StringBuffer sb = new StringBuffer();
		if(fieldList!=null && fieldList.size()>0){
			HashMap fieldMap = new HashMap();
			for(int k=0;k<fieldList.size();k++){
				fieldMap = (HashMap)fieldList.get(k);
				String field = "",Null = "";
				String fieldComment = "";
				String fieldType = "";
				String fieldLen = "";
				if(fieldMap.get("Null")!=null){
					Null = fieldMap.get("Null").toString();
				}
				if(fieldMap.get("Field")!=null){
					field = fieldMap.get("Field").toString();
				}
				if(fieldMap.get("Comment")!=null){
					fieldComment = fieldMap.get("Comment").toString();
				}
				if(fieldMap.get("Type")!=null){
					fieldType = fieldMap.get("Type").toString();
				}
				if(fieldMap.get("CharLen")!=null){
					fieldLen = fieldMap.get("CharLen").toString();
				}
				if(field.equals("")) continue;
				if(tagname.equals("fieldlist") && field.equals(TABLEKEY)){
					continue;
				}
				if(tagname.equals("searchlist") && !isExistGroup(SEARCHFIELD,field)){
					continue;
				}
				if(tagname.equals("indexdislist") && !isExistGroup(INDEXDISFIELD,field)){
					continue;
				}
				String temp = bodyCon;
				//直接替换字段
				temp = temp.replace("[field_name]", field);
				temp = temp.replace("[field_comment]", fieldComment);
				if(null!=fieldLen&&!fieldLen.equals("")){
					temp = temp.replace("[field_length]", "length="+fieldLen+"\"");
				}
				//根据子段类型，替换
				if(fieldType!=null && (fieldType.equalsIgnoreCase("tinyint"))){
					temp = temp.replace("[field_Type]", "Byte");
					temp = temp.replace("[field_Jdbc_Type]", "TINYINT").replace("[varchar_notnull]","");
				}
				if(fieldType!=null && (fieldType.equalsIgnoreCase("smallint"))){
					temp = temp.replace("[field_Type]", "Short");
					temp = temp.replace("[field_Jdbc_Type]", "INTEGER").replace("[varchar_notnull]","");
				}
				if(fieldType!=null && (fieldType.equalsIgnoreCase("int"))){
					temp = temp.replace("[field_Type]", "Integer");
					temp = temp.replace("[field_Jdbc_Type]", "INTEGER").replace("[varchar_notnull]","");
				}
				if(fieldType!=null  && fieldType.equalsIgnoreCase("bigint")){
					temp = temp.replace("[field_Type]", "Long");
					temp = temp.replace("[field_Jdbc_Type]", "BIGINT").replace("[varchar_notnull]","");
				}
				if(fieldType!=null && (fieldType.equalsIgnoreCase("float") || fieldType.equalsIgnoreCase("decimal"))){
					temp = temp.replace("[field_Type]", "Double");
					temp = temp.replace("[field_Jdbc_Type]", "Double").replace("[varchar_notnull]","");
				}
				if(fieldType!=null && (fieldType.equalsIgnoreCase("varchar") || fieldType.equalsIgnoreCase("char") || fieldType.equalsIgnoreCase("text") || fieldType.equalsIgnoreCase("longtext")))
					temp = temp.replace("[field_Type]", "String").replace("[field_Jdbc_Type]", "VARCHAR").replace("[varchar_notnull]","and "+oneWordLowwer(field)+" != ''");
				if(fieldType!=null && (fieldType.equalsIgnoreCase("date") || fieldType.equalsIgnoreCase("datetime") || fieldType.equalsIgnoreCase("timestamp")))
					temp = temp.replace("[field_Type]", "java.util.Date").replace("[field_Jdbc_Type]", "TIMESTAMP").replace("[varchar_notnull]","");
				//替换第一个字母大写的字段
				temp = temp.replace("[fieldname]", oneWordLowwer(field));
				temp = temp.replace("[FieldnName]", oneWordUpper(field));
				temp = temp.replace("[field_name_must]", Null.equalsIgnoreCase("NO")?"*":"&nbsp;");
				
				//替换SQLMap.txt
				temp = temp.replace("[field_node_u]", field);	
				if(k==fieldList.size()-1){
					temp = temp.replace("[field_node],", field);	
				}else{
					temp = temp.replace("[field_node],", field+",");	
				}			
				if(k==fieldList.size()-1){
					temp = temp.replace("#{[field_u_node]},", "#{"+field+"}");	
				}else{
					temp = temp.replace("#{[field_u_node]},", "#{"+field+"},");	
				}		
				sb.append(temp);
			}
		}
		fileCon = fileCon.replace(tagBody, sb.toString());
		
		return fileCon;
	}
	
	//让传进来的字符串第一个字母大写
	public String oneWordUpper(String str){
		if(str.length()==0){
			return "";
		}
		String[] strArr = str.split("_");
		StringBuilder sb = new StringBuilder();
		for(String s : strArr){
			sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
		}
		return sb.toString();
	}

	//让传进来的字符串第一个字母小写
	public String oneWordLowwer(String str){
		if(str.length()==0){
			return "";
		}
		String[] strArr = str.split("_");
		StringBuilder sb = new StringBuilder();
		for(String s : strArr){
			sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
		}
		String result = sb.toString();
		return result.substring(0, 1).toLowerCase() + result.substring(1);
	}

	//得到当前的日期
	public String getDate(){
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sFormat.format(new java.util.Date());
	}
	
	public boolean isExistGroup(String fieldGro,String field){
		String fid[] = fieldGro.split(",");
		boolean ret = false;
		for(int i=0;i<fid.length;i++){
			if(fid[i].equals(field)){
				ret = true;
				break;
			}
		}
		return ret;
	}
	
}
