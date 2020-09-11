create DATABASE if not exists inventory;
use inventory;
drop table if exists `PRODUCT`;

CREATE TABLE `PRODUCT` (
  `name` varchar(20) NOT NULL DEFAULT '',
  `type` varchar(100) NOT NULL,
  `total` varchar(20) NOT NULL,
  `paid` varchar(20) NOT NULL,
  `remain` varchar(20) NOT NULL,
  `owner` varchar(20) NOT NULL,
  `worker` varchar(20) NOT NULL,
  `year` varchar(20) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `carNum` varchar(20) NOT NULL,
  `note` varchar(20) NOT NULL,
  `image` varchar(250) NOT NULL,
  `carType` varchar(250) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
