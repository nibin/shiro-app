
-- use mysql db
-- login as root 
create database shiroDemoDb;

create user shiro identified by 'shiro';
grant all privileges on shiroDemoDb.* to shiro;

-- login as 'shiro' user

CREATE TABLE `users` (
  `obj_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `roles` (
  `role_name` varchar(50) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `privilege_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_name`)
);

CREATE TABLE `users_roles` (
  `user_obj_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL DEFAULT '',
  `auth_type` varchar(50) DEFAULT NULL,
  `auth_value` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`user_obj_id`,`role_name`)
);


CREATE TABLE `sessions` (
  `username` varchar(50) DEFAULT NULL,
  `session_id` varchar(200) NOT NULL,
  `serialized_session` blob,
  `current_privilege_level` int(11) DEFAULT NULL,
  PRIMARY KEY (`session_id`)
);

CREATE TABLE `url_config` (
  `url_path` varchar(500) NOT NULL,
  `filter_list` varchar(500) NOT NULL,
  `priority_order` int(11) NOT NULL,
  UNIQUE KEY `url_path_UNIQUE` (`url_path`)
);

-- inserts
INSERT INTO `roles` (`role_name`, `description`, `privilege_level`) VALUES 
	('admin_role',NULL,3), 
	('basic_role',NULL,1), 
	('super_admin_role',NULL,4),
	('support_role',NULL,2);
	
INSERT INTO `users` (`obj_id`, `username`, `first_name`, `last_name`) VALUES 
	(1,'admin','Marc','Fury');

INSERT INTO `users_roles` (`user_obj_id`, `role_name`, `auth_type`, `auth_value`) VALUES 
	(1,'basic_role','salted_password','13601bda4ea78e55a07b98866d2be6be0744e3866f13c00c811cab608a28f322'),
	(1,'support_role','one_time_password','1000'),
	(1,'admin_role','one_time_password','2000');

INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/access_denied.jsp','anon',1);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/signin.jsp','multiAuthc',2);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/signin_otp.jsp','multiAuthc',3);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/dashboard.jsp','multiAuthc, customRoles[basic_role]',4);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/support.jsp','multiAuthc, customRoles[support_role]',5);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/admin.jsp','multiAuthc, customRoles[admin_role]',6);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/super_admin.jsp','multiAuthc, customRoles[super_admin_role]',7);
INSERT INTO `url_config` (`url_path`,`filter_list`,`priority_order`) VALUES ('/app/logout.jsp','multiAuthc',8);



