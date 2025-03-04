CREATE DATABASE `wooco-local-database`;
GRANT ALL PRIVILEGES ON `wooco-local-database`.* TO `wooco-local-user`@'%' WITH GRANT OPTION;

CREATE DATABASE `wooco-test-database`;
GRANT ALL PRIVILEGES ON `wooco-test-database`.* TO `wooco-local-user`@'%' WITH GRANT OPTION;
