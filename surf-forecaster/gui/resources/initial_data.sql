﻿USE `test`;

/*Data for the table `area` */

insert  into `area`(`id`,`code`) values (1,1);
insert  into `area`(`id`,`code`) values (2,2);

/*Data for the table `area_i18nkeyvalue` */

insert  into `area_i18nkeyvalue`(`Area_id`,`names_id`) values (1,1);
insert  into `area_i18nkeyvalue`(`Area_id`,`names_id`) values (1,2);
insert  into `area_i18nkeyvalue`(`Area_id`,`names_id`) values (2,3);
insert  into `area_i18nkeyvalue`(`Area_id`,`names_id`) values (2,4);

/*Data for the table `comparation` */

/*Data for the table `comparation_spot` */

/*Data for the table `country` */

insert  into `country`(`id`,`area_id`) values (1,1);
insert  into `country`(`id`,`area_id`) values (2,1);

/*Data for the table `country_i18nkeyvalue` */

insert  into `country_i18nkeyvalue`(`Country_id`,`names_id`) values (1,5);
insert  into `country_i18nkeyvalue`(`Country_id`,`names_id`) values (1,6);
insert  into `country_i18nkeyvalue`(`Country_id`,`names_id`) values (2,7);
insert  into `country_i18nkeyvalue`(`Country_id`,`names_id`) values (2,8);

/*Data for the table `description` */

/*Data for the table `description_i18nkeyvalue` */

/*Data for the table `i18nkeyvalue` */

insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (1,'es','América del Sur');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (2,'en','South America');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (3,'es','América del Norte');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (4,'en','North America');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (5,'es','Argentina');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (6,'en','Argentine');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (7,'en','Brazil');
insert  into `i18nkeyvalue`(`id`,`language`,`text`) values (8,'es','Brasil');

/*Data for the table `spot` */

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`lastName`,`email`,`password`,`userName`,`userType`) values (1,'Administrador','Administrador','admin@gmail.com','admin','admin','ADMINISTRATOR');
insert  into `user`(`id`,`name`,`lastName`,`email`,`password`,`userName`,`userType`) values (2,'maxi','maxi','maxi@gmail.com','maxii','maxi','REGISTERED_USER');

/*Data for the table `zone` */

insert  into `zone`(`id`,`name`,`country_id`) values (1,'Mar del Plata - Sur',1);
insert  into `zone`(`id`,`name`,`country_id`) values (2,'Mar del Plata - Centro',1);
