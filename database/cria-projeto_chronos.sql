-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_chronos
-- ------------------------------------------------------
-- Server version	11.1.4-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_acao`
--

DROP TABLE IF EXISTS `t_acao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_acao` (
  `id_acao` int(11) NOT NULL AUTO_INCREMENT,
  `id_tarefa` int(11) NOT NULL,
  `tp_acao` enum('Iniciada','Finalizada') NOT NULL COMMENT 'Permitir o cadastro dos usuarios dentro do sistema.',
  `dt_registro_acao` datetime NOT NULL,
  PRIMARY KEY (`id_acao`),
  KEY `fk_T_ACAO_T_TAREFA1_idx` (`id_tarefa`),
  CONSTRAINT `fk_T_ACAO_T_TAREFA1` FOREIGN KEY (`id_tarefa`) REFERENCES `t_tarefa` (`id_tarefa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_acao`
--

LOCK TABLES `t_acao` WRITE;
/*!40000 ALTER TABLE `t_acao` DISABLE KEYS */;
INSERT INTO `t_acao` VALUES (1,1,'Iniciada','2026-06-03 21:00:00'),(2,2,'Iniciada','2026-06-03 21:01:00'),(3,3,'Iniciada','2026-06-03 21:02:00'),(4,4,'Iniciada','2026-06-03 21:03:00'),(5,5,'Iniciada','2026-06-03 21:04:00'),(6,6,'Iniciada','2026-06-03 21:05:00'),(7,7,'Iniciada','2026-06-03 21:06:00'),(8,8,'Iniciada','2026-06-03 21:07:00'),(9,9,'Iniciada','2026-06-03 21:08:00'),(10,1,'Finalizada','2026-06-03 21:30:00'),(11,2,'Finalizada','2026-06-03 21:31:00'),(12,3,'Finalizada','2026-06-03 21:32:00'),(13,4,'Finalizada','2026-06-03 21:33:00'),(14,5,'Finalizada','2026-06-03 21:34:00'),(15,6,'Finalizada','2026-06-03 21:35:00'),(16,7,'Finalizada','2026-06-03 21:36:00'),(17,8,'Finalizada','2026-06-03 21:37:00'),(18,9,'Finalizada','2026-06-03 21:38:00'),(19,10,'Iniciada','2026-07-02 09:55:05'),(20,12,'Iniciada','2026-07-02 09:57:47'),(21,10,'Finalizada','2026-07-02 09:57:56'),(22,14,'Iniciada','2026-07-02 14:38:10'),(23,14,'Iniciada','2026-07-02 14:39:01'),(24,14,'Finalizada','2026-07-02 15:00:56'),(25,12,'Finalizada','2026-07-03 06:05:00'),(26,15,'Iniciada','2026-07-03 06:05:40'),(33,15,'Finalizada','2026-07-03 07:28:22'),(34,15,'Iniciada','2026-07-03 07:28:31'),(35,15,'Finalizada','2026-07-03 09:07:35'),(36,15,'Iniciada','2026-07-03 09:07:43'),(37,11,'Iniciada','2026-07-03 09:08:01'),(38,19,'Iniciada','2026-07-03 09:29:27'),(39,20,'Iniciada','2026-07-03 09:41:00'),(40,21,'Iniciada','2026-07-03 09:41:49'),(41,21,'Finalizada','2026-07-03 10:01:35'),(42,19,'Finalizada','2026-07-03 10:01:47'),(43,20,'Finalizada','2026-07-03 10:29:30'),(44,20,'Iniciada','2026-07-03 11:13:21'),(45,20,'Finalizada','2026-07-03 11:13:30'),(48,19,'Iniciada','2026-07-03 13:04:45'),(49,20,'Iniciada','2026-07-03 13:04:48'),(50,21,'Iniciada','2026-07-03 13:04:49'),(51,21,'Finalizada','2026-07-03 13:04:59'),(52,20,'Finalizada','2026-07-03 13:05:01'),(53,19,'Finalizada','2026-07-03 13:05:02'),(54,19,'Iniciada','2026-07-03 13:23:22'),(55,20,'Iniciada','2026-07-03 13:23:22'),(56,21,'Iniciada','2026-07-03 13:23:23'),(57,19,'Finalizada','2026-07-03 13:23:32'),(58,20,'Finalizada','2026-07-03 13:23:32'),(59,21,'Finalizada','2026-07-03 13:23:33'),(60,15,'Finalizada','2026-07-03 13:40:53'),(61,12,'Iniciada','2026-07-03 13:40:58'),(62,12,'Finalizada','2026-07-03 14:23:04'),(63,23,'Iniciada','2026-07-03 14:23:57'),(64,11,'Finalizada','2026-07-03 14:24:10'),(65,11,'Iniciada','2026-07-03 14:24:13'),(66,24,'Iniciada','2026-07-03 14:25:10'),(67,25,'Iniciada','2026-07-03 14:26:58'),(68,23,'Finalizada','2026-07-03 15:06:29'),(69,24,'Finalizada','2026-07-03 15:06:32'),(70,25,'Finalizada','2026-07-03 15:06:36'),(78,11,'Finalizada','2026-07-04 08:33:20'),(79,10,'Iniciada','2026-07-04 08:33:23'),(80,29,'Iniciada','2026-07-04 08:34:06'),(81,29,'Finalizada','2026-07-04 16:37:33'),(82,10,'Finalizada','2026-07-04 16:37:45'),(83,26,'Iniciada','2026-07-04 16:37:51'),(84,31,'Iniciada','2026-07-04 16:38:23'),(85,32,'Iniciada','2026-07-04 16:38:43'),(86,33,'Iniciada','2026-07-04 16:39:07'),(87,34,'Iniciada','2026-07-04 16:39:29'),(88,35,'Iniciada','2026-07-04 16:40:01'),(89,36,'Iniciada','2026-07-04 16:41:11'),(91,32,'Finalizada','2026-07-04 17:46:56'),(92,33,'Finalizada','2026-07-04 17:46:59'),(93,34,'Finalizada','2026-07-04 17:47:01'),(94,35,'Finalizada','2026-07-04 17:47:04'),(96,31,'Finalizada','2026-07-04 17:47:47'),(97,36,'Finalizada','2026-07-04 17:47:48'),(98,38,'Iniciada','2026-07-04 17:48:22'),(99,39,'Iniciada','2026-07-05 14:34:06'),(100,39,'Finalizada','2026-07-05 14:35:03'),(101,38,'Finalizada','2026-07-05 14:37:22'),(102,26,'Finalizada','2026-07-05 14:38:43'),(103,10,'Iniciada','2026-07-05 14:38:46'),(104,40,'Iniciada','2026-07-05 19:48:37'),(105,41,'Iniciada','2026-07-05 19:50:11'),(106,42,'Iniciada','2026-07-05 19:50:45'),(107,43,'Iniciada','2026-07-05 19:51:46'),(108,34,'Iniciada','2026-07-05 19:52:56'),(109,20,'Iniciada','2026-07-05 19:53:08'),(110,45,'Iniciada','2026-07-05 19:54:02'),(111,20,'Finalizada','2026-07-05 19:54:14'),(112,20,'Iniciada','2026-07-05 19:54:15'),(113,46,'Iniciada','2026-07-05 19:54:40'),(114,47,'Iniciada','2026-07-05 19:55:36'),(115,48,'Iniciada','2026-07-05 19:56:10'),(116,49,'Iniciada','2026-07-05 19:57:43'),(117,50,'Iniciada','2026-07-05 19:58:45'),(118,51,'Iniciada','2026-07-05 19:59:07'),(119,20,'Finalizada','2026-07-05 21:53:15'),(120,45,'Finalizada','2026-07-05 21:53:21'),(121,34,'Finalizada','2026-07-05 21:53:32'),(122,46,'Finalizada','2026-07-05 21:53:35'),(123,10,'Finalizada','2026-07-05 21:53:44'),(124,40,'Finalizada','2026-07-05 21:53:54'),(125,41,'Finalizada','2026-07-05 21:53:55'),(126,42,'Finalizada','2026-07-05 21:53:56'),(127,43,'Finalizada','2026-07-05 21:53:58'),(128,51,'Finalizada','2026-07-05 21:54:04'),(129,50,'Finalizada','2026-07-05 21:54:05'),(130,49,'Finalizada','2026-07-05 21:54:06'),(131,48,'Finalizada','2026-07-05 21:54:08'),(132,47,'Finalizada','2026-07-05 21:54:08'),(133,10,'Iniciada','2026-07-05 21:54:21'),(135,10,'Finalizada','2026-07-06 16:24:22'),(136,26,'Iniciada','2026-07-06 16:24:27'),(137,11,'Iniciada','2026-07-06 16:24:30'),(138,52,'Iniciada','2026-07-06 17:08:11'),(139,52,'Finalizada','2026-07-06 17:09:23');
/*!40000 ALTER TABLE `t_acao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_permissao`
--

DROP TABLE IF EXISTS `t_permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_permissao` (
  `id_permissao` int(11) NOT NULL AUTO_INCREMENT,
  `ds_permissao` varchar(50) NOT NULL,
  PRIMARY KEY (`id_permissao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permissao`
--

LOCK TABLES `t_permissao` WRITE;
/*!40000 ALTER TABLE `t_permissao` DISABLE KEYS */;
INSERT INTO `t_permissao` VALUES (1,'Admin'),(2,'Usuário');
/*!40000 ALTER TABLE `t_permissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_tarefa`
--

DROP TABLE IF EXISTS `t_tarefa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_tarefa` (
  `id_tarefa` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `id_topico` int(11) NOT NULL,
  `nm_tarefa` varchar(80) NOT NULL,
  `ds_tarefa` varchar(200) DEFAULT NULL,
  `vl_tarefa` decimal(9,2) DEFAULT NULL,
  `dt_criacao` date NOT NULL,
  `dt_conclusao` date DEFAULT NULL,
  PRIMARY KEY (`id_tarefa`),
  KEY `fk_T_TAREFA_T_TOPICO1_idx` (`id_topico`),
  KEY `fk_T_TAREFA_T_USUARIO1_idx` (`id_usuario`),
  CONSTRAINT `fk_T_TAREFA_T_TOPICO1` FOREIGN KEY (`id_topico`) REFERENCES `t_topico` (`id_topico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_TAREFA_T_USUARIO1` FOREIGN KEY (`id_usuario`) REFERENCES `t_usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tarefa`
--

LOCK TABLES `t_tarefa` WRITE;
/*!40000 ALTER TABLE `t_tarefa` DISABLE KEYS */;
INSERT INTO `t_tarefa` VALUES (1,1,1,'Revisar Prova de Linguagem',NULL,NULL,'2026-06-02','2026-06-03'),(2,1,2,'Cortar Grama',NULL,NULL,'2026-06-02','2026-06-03'),(3,1,3,'Programar Tela Esqueceu a Senha','Projeto Integrador',NULL,'2026-06-02','2026-06-03'),(4,2,4,'Dormir',NULL,NULL,'2026-06-02','2026-06-03'),(5,2,5,'Vetorizar camisa dos Corujas','Rudolph',100.00,'2026-06-02','2026-06-03'),(6,2,6,'Comprar Escova','Item da Viagem',NULL,'2026-06-02','2026-06-03'),(7,3,7,'Ler Capitulo 2','Tecnologias de Redes de Comunicação e Computadores',NULL,'2026-06-02','2026-06-03'),(8,3,8,'O que é tabela intermediária','Modelagem de Dados',NULL,'2026-06-02','2026-06-03'),(9,3,9,'Comprar Casaco',NULL,NULL,'2026-06-02','2026-06-03'),(10,5,4,'Rudolph Usinados','Dom á Sex',0.00,'2026-07-02',NULL),(11,5,4,'Dormir','',0.00,'2026-07-02',NULL),(12,5,3,'Programar Tela Tarefas','Projeto Integrador',0.00,'2026-07-02',NULL),(14,2,4,'Cedup','Curso',0.00,'2026-07-02',NULL),(15,5,3,'Programar tela Indicadores','Projeto Integrador',0.00,'2026-07-03',NULL),(19,5,1,'Enviar Relatório para os clientes','Google Ads',0.00,'2026-07-03',NULL),(20,5,1,'Como criar um objeto na tela Netbeans','Programação Java',0.00,'2026-07-03',NULL),(21,5,1,'Como criar  views no MySQL','Banco de dados',0.00,'2026-07-03',NULL),(23,5,3,'Programar Tela Adicionar Tarefa','Projeto Integrador',0.00,'2026-07-03',NULL),(24,5,3,'Programar Tutorial nas telas','Projeto Integrador',0.00,'2026-07-03',NULL),(25,5,3,'Criar Views para Tela Indicadores no MySQL','Projeto Integrador',0.00,'2026-07-03',NULL),(26,5,4,'Cedup','Estudando',0.00,'2026-07-03',NULL),(29,5,3,'Modelo de Documentação Acadêmica','Projeto Integrador',0.00,'2026-07-04',NULL),(31,5,9,'Ajudar na mudança da mãe','',0.00,'2026-07-04',NULL),(32,5,2,'Limpar casa','',0.00,'2026-07-04',NULL),(33,5,2,'Lavar louça','',0.00,'2026-07-04',NULL),(34,5,2,'Fazer compras','Comida',0.00,'2026-07-04',NULL),(35,5,2,'Lavar Roupa','',0.00,'2026-07-04',NULL),(36,5,9,'Almoço em família e jogos','',0.00,'2026-07-04',NULL),(38,5,3,'Programar Tela Usuário','',0.00,'2026-07-04',NULL),(39,11,6,'Paraguai','Comprar coisas',0.00,'2026-07-05',NULL),(40,5,5,'Reunião de resultados N6','Negócios',100.00,'2026-07-05',NULL),(41,5,5,'Criar anúncio CA Instalações','Google Ads',100.00,'2026-07-05',NULL),(42,5,5,'Criar Anúncio Café com flor','Google Ads',100.00,'2026-07-05',NULL),(43,5,5,'Criar Anúncio AP Instalações','Google Ads',100.00,'2026-07-05',NULL),(44,5,5,'Criar camisa Corujas','Rudolph',100.00,'2026-07-05',NULL),(45,5,1,'Como tirar print da programação','VS Code',0.00,'2026-07-05',NULL),(46,5,2,'Estender Roupa','',0.00,'2026-07-05',NULL),(47,5,6,'Toledo PR','Ver a família',0.00,'2026-07-05',NULL),(48,5,6,'Caxias do Sul RS','Casamento de amigos',0.00,'2026-07-05',NULL),(49,5,6,'Rio Grande do Sul','Noivado',0.00,'2026-07-05',NULL),(50,5,6,'Curitiba PR','Reunião de Jovens',0.00,'2026-07-05',NULL),(51,5,6,'Itaquera SP','Reunião de Jovens',0.00,'2026-07-05',NULL),(52,12,3,'estudar +','',10000.00,'2026-07-06',NULL),(53,12,3,'estudar menos','',-5000.00,'2026-07-06',NULL);
/*!40000 ALTER TABLE `t_tarefa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_topico`
--

DROP TABLE IF EXISTS `t_topico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_topico` (
  `id_topico` int(11) NOT NULL AUTO_INCREMENT,
  `nm_topico` enum('Estudo','Casa','Projeto','Rotina','Trabalho','Viagem','Livro','Pesquisa','Outro') NOT NULL,
  PRIMARY KEY (`id_topico`),
  UNIQUE KEY `UN_TOPICO_NOME` (`nm_topico`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_topico`
--

LOCK TABLES `t_topico` WRITE;
/*!40000 ALTER TABLE `t_topico` DISABLE KEYS */;
INSERT INTO `t_topico` VALUES (1,'Estudo'),(2,'Casa'),(3,'Projeto'),(4,'Rotina'),(5,'Trabalho'),(6,'Viagem'),(7,'Livro'),(8,'Pesquisa'),(9,'Outro');
/*!40000 ALTER TABLE `t_topico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_usuario`
--

DROP TABLE IF EXISTS `t_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nm_usuario` varchar(80) NOT NULL,
  `dt_nascimento` date NOT NULL,
  `ds_email` varchar(80) NOT NULL,
  `ds_senha` varchar(50) NOT NULL,
  `id_permissao` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UN_USUARIO_EMAIL` (`ds_email`),
  KEY `id_permissaofK` (`id_permissao`),
  CONSTRAINT `id_permissaofK` FOREIGN KEY (`id_permissao`) REFERENCES `t_permissao` (`id_permissao`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_usuario`
--

LOCK TABLES `t_usuario` WRITE;
/*!40000 ALTER TABLE `t_usuario` DISABLE KEYS */;
INSERT INTO `t_usuario` VALUES (1,'Kalyson','2000-08-17','4555854224@estudante.sed.sc.gov.br','1234',2),(2,'Gabriel','1999-02-28','4544039745@estudante.sed.sc.gov.br','1234',2),(3,'Jose','1967-10-18','4555854518@estudante.sed.sc.gov.br','1234',2),(4,'Admin','2026-06-21','Admin','adm123',1),(5,'Gabriel Gomes','1999-02-28','tarefa@gmail.com','1234',2),(8,'Pedro','2000-02-27','pedro@gmail.com','123',2),(9,'lucas','1999-01-01','lucas@gmail.com','123',2),(11,'Leonardo','2000-01-01','leonardo@gmail.com','123',2),(12,'Silva Gomes','1999-02-28','silva1@gmail.com','1234',2);
/*!40000 ALTER TABLE `t_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_tempo_medio`
--

DROP TABLE IF EXISTS `v_tempo_medio`;
/*!50001 DROP VIEW IF EXISTS `v_tempo_medio`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_tempo_medio` AS SELECT 
 1 AS `id_usuario`,
 1 AS `Tempo_Medio`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_tempo_tarefa`
--

DROP TABLE IF EXISTS `v_tempo_tarefa`;
/*!50001 DROP VIEW IF EXISTS `v_tempo_tarefa`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_tempo_tarefa` AS SELECT 
 1 AS `id_usuario`,
 1 AS `Tarefa`,
 1 AS `Tempo_Total`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_tempo_total`
--

DROP TABLE IF EXISTS `v_tempo_total`;
/*!50001 DROP VIEW IF EXISTS `v_tempo_total`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_tempo_total` AS SELECT 
 1 AS `id_usuario`,
 1 AS `Tempo_Total`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_visao_historico`
--

DROP TABLE IF EXISTS `v_visao_historico`;
/*!50001 DROP VIEW IF EXISTS `v_visao_historico`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_visao_historico` AS SELECT 
 1 AS `id_usuario`,
 1 AS `F_Topico`,
 1 AS `Tarefa`,
 1 AS `Repetida`,
 1 AS `Tempo_Total_Min`,
 1 AS `Media_Min`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_tempo_medio`
--

/*!50001 DROP VIEW IF EXISTS `v_tempo_medio`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_tempo_medio` AS select `v_tempo_tarefa`.`id_usuario` AS `id_usuario`,avg(`v_tempo_tarefa`.`Tempo_Total`) AS `Tempo_Medio` from `v_tempo_tarefa` group by `v_tempo_tarefa`.`id_usuario` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_tempo_tarefa`
--

/*!50001 DROP VIEW IF EXISTS `v_tempo_tarefa`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_tempo_tarefa` AS select `t`.`id_usuario` AS `id_usuario`,`t`.`nm_tarefa` AS `Tarefa`,coalesce(sum(timestampdiff(MINUTE,(select `a2`.`dt_registro_acao` from `t_acao` `a2` where `a2`.`id_tarefa` = `a`.`id_tarefa` and `a2`.`tp_acao` = 'Iniciada' and `a2`.`dt_registro_acao` < `a`.`dt_registro_acao` order by `a2`.`dt_registro_acao` desc limit 1),`a`.`dt_registro_acao`)),0) AS `Tempo_Total` from (`t_acao` `a` join `t_tarefa` `t` on(`a`.`id_tarefa` = `t`.`id_tarefa`)) where `a`.`tp_acao` = 'Finalizada' group by `t`.`id_usuario`,`t`.`id_tarefa`,`t`.`nm_tarefa` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_tempo_total`
--

/*!50001 DROP VIEW IF EXISTS `v_tempo_total`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_tempo_total` AS select `t`.`id_usuario` AS `id_usuario`,coalesce(sum(timestampdiff(MINUTE,(select `a2`.`dt_registro_acao` from `t_acao` `a2` where `a2`.`id_tarefa` = `a`.`id_tarefa` and `a2`.`tp_acao` = 'Iniciada' and `a2`.`dt_registro_acao` < `a`.`dt_registro_acao` order by `a2`.`dt_registro_acao` desc limit 1),`a`.`dt_registro_acao`)),0) AS `Tempo_Total` from (`t_acao` `a` join `t_tarefa` `t` on(`a`.`id_tarefa` = `t`.`id_tarefa`)) where `a`.`tp_acao` = 'Finalizada' group by `t`.`id_usuario` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_visao_historico`
--

/*!50001 DROP VIEW IF EXISTS `v_visao_historico`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb3 */;
/*!50001 SET character_set_results     = utf8mb3 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_visao_historico` AS select `t`.`id_usuario` AS `id_usuario`,`tp`.`nm_topico` AS `F_Topico`,`t`.`nm_tarefa` AS `Tarefa`,count(`a`.`id_acao`) AS `Repetida`,coalesce(sum(timestampdiff(MINUTE,(select `a2`.`dt_registro_acao` from `t_acao` `a2` where `a2`.`id_tarefa` = `a`.`id_tarefa` and `a2`.`tp_acao` = 'Iniciada' and `a2`.`dt_registro_acao` < `a`.`dt_registro_acao` order by `a2`.`dt_registro_acao` desc limit 1),`a`.`dt_registro_acao`)),0) AS `Tempo_Total_Min`,coalesce(avg(timestampdiff(MINUTE,(select `a2`.`dt_registro_acao` from `t_acao` `a2` where `a2`.`id_tarefa` = `a`.`id_tarefa` and `a2`.`tp_acao` = 'Iniciada' and `a2`.`dt_registro_acao` < `a`.`dt_registro_acao` order by `a2`.`dt_registro_acao` desc limit 1),`a`.`dt_registro_acao`)),0) AS `Media_Min` from ((`t_acao` `a` join `t_tarefa` `t` on(`a`.`id_tarefa` = `t`.`id_tarefa`)) join `t_topico` `tp` on(`t`.`id_topico` = `tp`.`id_topico`)) where `a`.`tp_acao` = 'Finalizada' group by `t`.`id_usuario`,`t`.`id_tarefa`,`tp`.`nm_topico`,`t`.`nm_tarefa` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-06 17:45:07
