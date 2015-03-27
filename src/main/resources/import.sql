-- insert some customers
insert into msaas_user (id, name, password, role) values (1, 'Sauron', 'Saurons password', 'CUSTOMER');
insert into msaas_user (id, name, password, role) values (2, 'Darth Vader', 'Darth Vaders password', 'CUSTOMER');

-- insert some test cameras
insert into camera (id, name, customer_id, state, url, tags) values (4, 'Managua Nicaragua', 1, 'ONLINE', 'http://208.0.229.146/nphMotionJpeg?Resolution=640x480&Quality=Standard', 'TAG4');
insert into camera (id, name, customer_id, state, url, tags) values (5, 'San Sebastian', 1, 'ONLINE', 'http://212.142.228.68/mjpg/video.mjpg', 'TAG5');
insert into camera (id, name, customer_id, state, url, tags) values (6, 'Fukushima', 1, 'ONLINE', 'http://dake.miemasu.net/nphMotionJpeg?Resolution=640x480&Quality=Clarity', 'TAG6');

-- insert a test observer
insert into msaas_user(id, name, password, role) values (1, 'Cosmin', 'Cosmins password', 'OBSERVER');

-- update camera states
update camera set state = 'WAITING';
