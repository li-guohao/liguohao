-- 初始化执行测试语句 如已经存在则不插入数据
--INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
--SELECT 'test','testName','testValue', '测试数据' FROM DUAL WHERE NOT EXISTS(
--	SELECT * FROM system_option 
--	WHERE option_category='test' and option_name = 'testName'
--);

-- 系统设置表初始化 (如已经存在表信息则不执行)
---- 站点公告
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'siteInfo','notice','这是默认的首页公告', '站点公告' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='siteInfo' and option_name = 'notice'
);
---- 站点尾部声名
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'siteInfo','footer','这是默认的尾部声名 ', '站点尾部声名' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='siteInfo' and option_name = 'footer'
);
---- 首页显示文章数量
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'homeInfo','articleNum','10','首页显示文章数量 正整数' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='homeInfo' and option_name = 'articleNum'
);
---- 文件管理的储存策略
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'fileManager','storagePolicies','本地','文件管理的储存策略' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='fileManager' and option_name = 'storagePolicies'
);

