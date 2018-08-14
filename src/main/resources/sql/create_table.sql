/*
  该文件为数据库建表语句备份,添加建表语句格式请统一
  说明：系统表前缀为：t_sys_  业务表前缀：t_yx_
*/

CREATE TABLE t_sys_job_type  (
  id int(11) NOT NULL AUTO_INCREMENT 	  comment '工种id',
  name varchar(50) NOT NULL 			  comment '工种名称',
  level int(11) NOT NULL DEFAULT 1 		  comment '工种级别 1:一级，2:二级',
  parent_id int(11) NOT NULL DEFAULT 0 	  comment '父级id,根级为0',
  create_time datetime NOT NULL 		  comment '创建时间',
  create_user int(11) NULL 				  comment '创建人',
  update_time datetime NULL 			  comment '更新时间',
  update_user int(11) NULL				  comment '更新人',
  PRIMARY KEY (id)
) comment='工种表';

CREATE TABLE t_sys_dictionary  (
  id int(11) NOT NULL AUTO_INCREMENT 	  comment '字典id',
  type varchar(30) NOT NULL				  comment '字典种类',
  code varchar(30) NOT NULL 			  comment '字典值',
  name varchar(50) 						  comment '字典名称',
  sort int(11) NOT NULL DEFAULT 1 		  comment '排序',
  create_time datetime NOT NULL 		  comment '创建时间',
  create_user int(11) NULL 				  comment '创建人',
  update_time datetime NULL 			  comment '更新时间',
  update_user int(11) NULL 				  comment '更新人',
  PRIMARY KEY (id)
) comment='字典表';

CREATE TABLE t_sys_role  (
  id int(11) NOT NULL AUTO_INCREMENT      comment '角色id',
  name varchar(50) NOT NULL               comment '角色名称',
  description varchar(200)                comment '描述',
  create_time datetime NOT NULL           comment '创建时间',
  create_user int(11) NULL                comment '创建人',
  update_time datetime NULL               comment '更新时间',
  update_user int(11) NULL                comment '更新人',
  PRIMARY KEY (id)
) comment='角色表';

CREATE TABLE t_sys_menu  (
  id int(11) NOT NULL AUTO_INCREMENT      comment '菜单id',
  name varchar(50) NOT NULL               comment '菜单名称',
  path varchar(200) NULL                  comment '路径url',
  type int(11) NOT NULL DEFAULT 1         comment '菜单类型 0:文件夹，1:链接',
  parent_id int(11) NOT NULL DEFAULT 0    comment '父级id,根级为0',
  create_time datetime NOT NULL           comment '创建时间',
  create_user int(11) NULL                comment '创建人',
  update_time datetime NULL               comment '更新时间',
  update_user int(11) NULL                comment '更新人',
  PRIMARY KEY (id)
) comment='菜单表';

CREATE TABLE t_sys_role_menu  (
  id int(11) NOT NULL AUTO_INCREMENT      comment 'id',
  role_id int(11) NOT NULL                comment '角色id',
  menu_id int(11) NOT NULL                comment '菜单id',
  create_time datetime NOT NULL           comment '创建时间',
  create_user int(11) NULL                comment '创建人',
  update_time datetime NULL               comment '更新时间',
  update_user int(11) NULL                comment '更新人',
  PRIMARY KEY (id)
) comment='角色-菜单关联表';

CREATE TABLE t_yx_company  (
  id int(11) NOT NULL AUTO_INCREMENT      comment '企业id',
  name varchar(100) NOT NULL              comment '企业名称',
  code varchar(50) NULL                   comment '组织机构代码',                       
  industry varchar(100) NULL              comment '所属行业',
  address varchar(255) NULL               comment '企业地址',
  contact_name varchar(30) NULL           comment '联系人',
  contact_phone varchar(20) NULL          comment '联系电话',
  description varchar(255) NULL           comment '备注',
  create_time datetime NOT NULL           comment '创建时间',
  create_user int(11) NULL                comment '创建人',
  update_time datetime NULL               comment '更新时间',
  update_user int(11) NULL                comment '更新人',
  PRIMARY KEY (id)
) comment='企业表';
