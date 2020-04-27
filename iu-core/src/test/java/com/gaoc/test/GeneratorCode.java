package com.gaoc.test;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 代码生成类
 * 
 * @author user
 *
 */
public class GeneratorCode {

	/** 数据库地址 */
	private static final String DB_URL = "localhost:3306/iu_user";

	/** 数据库用户名 */
	private static final String DB_USERNAME = "root";

	/** 数据库密码 */
	private static final String DB_PASSWORD = "123456";

	/** 要生成的表名 */
	private static final String[] TABLE_NAME = { "log_send_sms" };
	// 查询所有表名语句：SELECT GROUP_CONCAT('"', ts.TABLE_NAME, '"') '所有表名' FROM
	// information_schema.`TABLES` ts WHERE ts.TABLE_TYPE = 'BASE TABLE' AND
	// ts.TABLE_SCHEMA = '数据库名称';

	/** 包名称 */
	private static final String PACGAGE_NAME = "com.gaoc";

	/** 模块名称 */
	private static final String MODEL_NAME = "user";

	/** 存放地址 */
	private static final String DIR = "C:\\Users\\user\\Desktop";

	/** 注释作者名称，类似本类上的注释：@author user */
	private static final String AUTHOR_NAME = "Gaoc";

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(DIR);
		gc.setAuthor(AUTHOR_NAME);
		gc.setOpen(false);
		gc.setFileOverride(true);
		// 不需要ActiveRecord特性的请改为false
		gc.setActiveRecord(true);
		// XML 二级缓存
		gc.setEnableCache(false);
		// XML ResultMap
		gc.setBaseResultMap(true);
		// XML columList
		gc.setBaseColumnList(false);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		StringBuilder sb = new StringBuilder("jdbc:mysql://");
		sb.append(DB_URL);
		sb.append(
				"?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&allowPublicKeyRetrieval=true");
		dsc.setUrl(sb.toString());
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername(DB_USERNAME);
		dsc.setPassword(DB_PASSWORD);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(MODEL_NAME);
		pc.setParent(PACGAGE_NAME);
		pc.setEntity("model");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		// freemarker模板引擎
		String templatePath = "/templates/mapper.xml.ftl";
		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return DIR + "/" + pc.getModuleName() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setSuperServiceClass("com.gaoc.core.service.BaseService");
		strategy.setSuperServiceImplClass("com.gaoc.core.service.BaseServiceImpl");
		strategy.setInclude(TABLE_NAME);
		strategy.setControllerMappingHyphenStyle(true);
		TableFill create = new TableFill("create_time", FieldFill.INSERT);
		TableFill update = new TableFill("update_time", FieldFill.UPDATE);
		List<TableFill> tableFillList = new ArrayList<>(2);
		tableFillList.add(create);
		tableFillList.add(update);
		strategy.setTableFillList(tableFillList);
		mpg.setStrategy(strategy);

		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}
