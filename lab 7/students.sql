
INSERT INTO `university`.`students` (`id`,`age`,`password`,`name`) VALUES (1,25,'$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','admin');
INSERT INTO `university`.`students` (`id`,`age`,`password`,`name`) VALUES (2,35,'$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','max');
INSERT INTO `university`.`students` (`id`,`age`,`password`,`name`) VALUES (3,45,'$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.','lion');


INSERT INTO `university`.`students_roles` (`student_id`,`role_id`) VALUES (2,2);
INSERT INTO `university`.`students_roles` (`student_id`,`role_id`) VALUES (1,3);
INSERT INTO `university`.`students_roles` (`student_id`,`role_id`) VALUES (3,1);



INSERT INTO `university`.`roles` (`id`,`name`) VALUES (2,'ROLE_ADMIN');
INSERT INTO `university`.`roles` (`id`,`name`) VALUES (1,'ROLE_MANAGER');
INSERT INTO `university`.`roles` (`id`,`name`) VALUES (3,'ROLE_SUPERADMIN');

