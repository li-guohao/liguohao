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

---- 文件管理-七牛云
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'qiniuoss','qiniuAccessKey','','七牛云权限配置信息' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='qiniuoss' and option_name = 'qiniuAccessKey'
);
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'qiniuoss','qiniuSecretKey','','七牛云权限配置信息' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='qiniuoss' and option_name = 'qiniuSecretKey'
);
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'qiniuoss','qiniuBucket','','七牛云对象储存bucket名称' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='qiniuoss' and option_name = 'qiniuBucket'
);
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'qiniuoss','qiniuPath','','七牛云对象储存外网访问路径' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='qiniuoss' and option_name = 'qiniuPath'
);
INSERT INTO system_option (option_category,option_name,option_value,option_desc)  
SELECT 'qiniuoss','qiniuRegion','','七牛云对象储存地域信息' FROM DUAL WHERE NOT EXISTS(
	SELECT * FROM system_option 
	WHERE option_category='qiniuoss' and option_name = 'qiniuRegion'
);

