
-- initial DATA --


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: -
--
insert into customer (id, name, password) values (1, 'Sauron', 'Saurons password');
insert into customer (id, name, password) values (2, 'Darth Vader', 'Darth Vaders password');
insert into customer (id, name, password) values (3, 'Cosmin', 'Cosmins password');
insert into customer (id, name, password) values (4, 'Andres', 'Andress password');
insert into customer (id, name, password) values (5, 'Marko', 'Markos password');

insert into camera (id, name, customer_id, state, url) values (1, 'Cosmins camera St Ursen', 3, 'ONLINE', 'http://admin:darthvaderishere@cosminj.dlinkddns.com:7777/dgvideo.cgi');
insert into camera (id, name, customer_id, state, url) values (2, 'Andress camera', 4, 'ONLINE', 'http://admin:password@shibuya.cputoasters.com:1234/video/mjpg.cgi');
insert into camera (id, name, customer_id, state, url) values (3, 'Markoss father camera Finland', 5, 'ONLINE', 'http://admin:turvam1es@markkutorpo.dlinkddns.com:5000/dgvideo.cgi');
insert into camera (id, name, customer_id, state, url) values (4, 'Managua Nicaragua', 1, 'ONLINE', 'http://208.0.229.146/nphMotionJpeg?Resolution=640x480&Quality=Standard');
insert into camera (id, name, customer_id, state, url) values (5, 'San Sebastian', 1, 'ONLINE', 'http://212.142.228.68/mjpg/video.mjpg');
insert into camera (id, name, customer_id, state, url) values (6, 'Fukushima', 1, 'ONLINE', 'http://dake.miemasu.net/nphMotionJpeg?Resolution=640x480&Quality=Clarity');

