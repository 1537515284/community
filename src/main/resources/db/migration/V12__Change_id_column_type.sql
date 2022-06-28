-- mysql
alter table question modify id bigint auto_increment not null;
alter table `user` modify id bigint auto_increment not null;

-- 上面的兼容h2，h2的不兼容mysql
-- alter table question alter column id bigint auto_increment not null;
-- alter table `user` alter column id bigint auto_increment not null;
