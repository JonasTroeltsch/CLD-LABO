# Database migration

In this task you will migrate the Drupal database to the new RDS database instance.

![Schema](./img/CLD_AWS_INFA.PNG)

## Task 01 - Securing current Drupal data

### [Get Bitnami MariaDb user's password](https://docs.bitnami.com/aws/faq/get-started/find-credentials/)

```bash
bitnami@ip-10-0-14-10:~$ cat /home/bitnami/bitnami_credentials
Welcome to the Bitnami package for Drupal

******************************************************************************
The default username and password is 'user' and '7DWgG95I3W0:'.
******************************************************************************

You can also use this password to access the databases and any other component the stack includes.

Please refer to https://docs.bitnami.com/ for more details.
```

### Get Database Name of Drupal

```bash
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| test               |
+--------------------+
```

### [Dump Drupal Databases](https://mariadb.com/kb/en/mariadb-dump/)

```bash
bitnami@ip-10-0-14-10:~$ mariadb-dump bitnami_drupal -h localhost -u root -p > db-dumb.sql
(file is created)
```

### Create the new Database on RDS

```sql
MariaDB [(none)]> CREATE DATABASE bitnami_drupal;
Query OK, 1 row affected (0.000 sec)
```

### [Import dump in RDS db-instance](https://mariadb.com/kb/en/restoring-data-from-dump-files/)

Note : you can do this from the Drupal Instance. Do not forget to set the "-h" parameter.

```sql
[INPUT]
bitnami@ip-10-0-14-10:~$ mysql -h dbi-devopsteam14.cshki92s4w5p.eu-west-3.rds.amazonaws.com -u admin -p bitnami_drupal < db-dumb.sql
mysql: Deprecated program name. It will be removed in a future release, use '/opt/bitnami/mariadb/bin/mariadb' instead
Enter password:
```

### [Get the current Drupal connection string parameters](https://www.drupal.org/docs/8/api/database-api/database-configuration)

```bash
bitnami@ip-10-0-14-10:~$ tail -n 13 stack/drupal/sites/default/settings.php
$databases['default']['default'] = array (
  'database' => 'bitnami_drupal',
  'username' => 'bn_drupal',
  'password' => '5f336f17ee8ba18d3125f6d984a8fbfa23816fdf50ef8f07a94f6f30ce076f7c',
  'prefix' => '',
  'host' => '127.0.0.1',
  'port' => '3306',
  'isolation_level' => 'READ COMMITTED',
  'driver' => 'mysql',
  'namespace' => 'Drupal\\mysql\\Driver\\Database\\mysql',
  'autoload' => 'core/modules/mysql/src/Driver/Database/mysql/',
);
$settings['config_sync_directory'] = 'sites/default/files/config_UqAxpBxlX-FIJQQOTAxsI9Q1uv1O69qCVJ7XRR1A9yeXn-EO5D6aIvnezW0lbmD9GHvPSTDwtA/sync';
```

### Replace the current host with the RDS FQDN

```bash
bitnami@ip-10-0-14-10:~$ tail -n 13 stack/drupal/sites/default/settings.php
$databases['default']['default'] = array (
  'database' => 'bitnami_drupal',
  'username' => 'bn_drupal',
  'password' => '5f336f17ee8ba18d3125f6d984a8fbfa23816fdf50ef8f07a94f6f30ce076f7c',
  'prefix' => '',
  'host' => 'dbi-devopsteam14.cshki92s4w5p.eu-west-3.rds.amazonaws.com',
  'port' => '3306',
  'isolation_level' => 'READ COMMITTED',
  'driver' => 'mysql',
  'namespace' => 'Drupal\\mysql\\Driver\\Database\\mysql',
  'autoload' => 'core/modules/mysql/src/Driver/Database/mysql/',
);
$settings['config_sync_directory'] = 'sites/default/files/config_UqAxpBxlX-FIJQQOTAxsI9Q1uv1O69qCVJ7XRR1A9yeXn-EO5D6aIvnezW0lbmD9GHvPSTDwtA/sync';
```

### [Create the Drupal Users on RDS Data base](https://mariadb.com/kb/en/create-user/)

Note : only calls from both private subnets must be approved.
* [By Password](https://mariadb.com/kb/en/create-user/#identified-by-password)
* [Account Name](https://mariadb.com/kb/en/create-user/#account-names)
* [Network Mask](https://cric.grenoble.cnrs.fr/Administrateurs/Outils/CalculMasque/)

```sql
MariaDB [(none)]> CREATE USER bn_drupal@'10.0.14.0/[Subnet Mask - A]]' IDENTIFIED BY '5f336f17ee8ba18d3125f6d984a8fbfa23816fdf50ef8f07a94f6f30ce076f7c';
Query OK, 0 rows affected (0.004 sec)

MariaDB [(none)]> GRANT ALL PRIVILEGES ON bitnami_drupal.* TO 'bn_drupal'@'10.0.14.0/[Subnet Mask - A]]';
Query OK, 0 rows affected (0.002 sec)

MariaDB [(none)]> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.001 sec)
```

```sql
--validation
MariaDB [(none)]> SHOW GRANTS for 'bn_drupal'@'10.0.14.0/[Subnet Mask - A]]';
+-------------------------------------------------------------------------------------------------------------------------------------+
| Grants for bn_drupal@10.0.14.0/[subnet mask - a]]
                  |
+-------------------------------------------------------------------------------------------------------------------------------------+
| GRANT USAGE ON *.* TO `bn_drupal`@`10.0.14.0/[subnet mask - a]]` IDENTIFIED BY PASSWORD '*72E2FF7F1F81BE6859E01AFCB2EC5C4E6344091F' |
| GRANT ALL PRIVILEGES ON `bitnami_drupal`.* TO `bn_drupal`@`10.0.14.0/[subnet mask - a]]`
                  |
+-------------------------------------------------------------------------------------------------------------------------------------+
2 rows in set (0.000 sec)
```

### Validate access (on the drupal instance)

```sql
bitnami@ip-10-0-14-10:~$ mariadb -h dbi-devopsteam14.cshki92s4w5p.eu-west-3.rds.amazonaws.com -u bn_drupal -p
Enter password:
ERROR 1698 (28000): Access denied for user 'bn_drupal'@'10.0.14.10'

[INPUT]
show databases;

[OUTPUT]
+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
+--------------------+
2 rows in set (0.001 sec)
```

* Repeat the procedure to enable the instance on subnet 2 to also talk to your RDS instance.