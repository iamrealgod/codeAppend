# codeAppend - idea代码追加插件

## Install   
- Using IDE built-in plugin system on Windows:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "codeAppend"</kbd> > <kbd>Install Plugin</kbd>
- Using IDE built-in plugin system on MacOs:
  - <kbd>Preferences</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "codeAppend"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release](https://github.com/laoziyaonitian/codeAppend/releases/latest) and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
  - From official jetbrains store from [download](https://plugins.jetbrains.com/plugin/11501-codeappend)
  
Restart IDE.

### 功能清单
- [x] 支持追加/生成不存在的sql字段代码（entity,mapper）
- [x] 支持lombok风格代码生成
- [x] 生成mybatis代码（entity,service,dao,mapper）
- [x] 支持选择是否删除表名/字段前缀
- [x] 配置中心
- [x] 支持get/set代码风格生成
- [x] 支持配置entity是否生成@TableField
- [x] 支持配置是否生成mybatis-plus格式代码
- [x] 配置变动实时预览
- [x] 支持自定义Mapper文件后缀

### 使用说明
#### append方式使用说明（method of application）：
![append使用方法](https://github.com/laoziyaonitian/codeAppend/blob/master/file/append.gif)
> * append方式不会覆盖文件，只会追加不存在的字段
> * 默认删除表名前缀
> * ps:mapper文件必须为 className+Mapper.xml 格式才能追加

#### override方式使用说明（method of application）：
![override使用方法](https://github.com/laoziyaonitian/codeAppend/blob/master/file/override.gif)
> * override会生成代码文件，如果已存在会覆盖代码文件
> * 默认删除表名前缀
> * ps:不需要生成的文件可以不填写文件生成目录


#### sql code example:
```sql
/*
Navicat MySQL Data Transfer
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pre_test
-- ----------------------------
DROP TABLE IF EXISTS `pre_test`;
CREATE TABLE `pre_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `num_no` varchar(64) NOT NULL COMMENT '订单号',
  `fu_address` varchar(32) DEFAULT NULL COMMENT '详细地址',
  `longitude` varchar(16) DEFAULT NULL COMMENT '地址经度',
  `latitude` varchar(16) DEFAULT NULL COMMENT '地址纬度',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` bigint(20) NOT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
-- Table structure for pre_test1
-- ----------------------------
DROP TABLE IF EXISTS `pre_test1`;
CREATE TABLE `pre_test2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bill_no` varchar(64) NOT NULL,
  `attach_type` varchar(32) NOT NULL COMMENT '附件类型',
  `store_id` varchar(128) NOT NULL COMMENT '存储id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` tinyint(4) NOT NULL COMMENT '是否删除(0-否、1-是）',
  PRIMARY KEY (`id`),
  KEY `idx_store_id` (`store_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';
```
#### 界面说明：
> * ![界面说明](https://github.com/laoziyaonitian/codeAppend/blob/master/file/%E7%95%8C%E9%9D%A2.png)

### release note:
> * v1.0.0
> * 输入sql，追加生成lombok风格的mybatis相关代码
> * 支持追加新字段（append）和全量覆盖（override）两种方式

> * v1.0.1
> * 新增配置界面
> * dir默认填充上一次的目录
> * 支持前缀配置是否处理
> * 修复bug

> * v1.0.2
> * 支持getset
> * 支持entity是否生成@TableField
> * 配置界面新增参数
> * 配置变动可实时预览

> * v1.0.3
> * 支持配置mapper后缀
> * bug fix.

