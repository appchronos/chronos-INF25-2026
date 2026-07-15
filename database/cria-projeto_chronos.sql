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
  `tp_acao` enum('Iniciada','Finalizada') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'Permitir o cadastro dos usuarios dentro do sistema.',
  `dt_registro_acao` datetime NOT NULL,
  PRIMARY KEY (`id_acao`),
  KEY `fk_T_ACAO_T_TAREFA1_idx` (`id_tarefa`),
  CONSTRAINT `fk_T_ACAO_T_TAREFA1` FOREIGN KEY (`id_tarefa`) REFERENCES `t_tarefa` (`id_tarefa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_acao`
--

LOCK TABLES `t_acao` WRITE;
/*!40000 ALTER TABLE `t_acao` DISABLE KEYS */;
INSERT INTO `t_acao` VALUES (1,1,'Iniciada','2026-07-06 20:37:38'),(2,4,'Iniciada','2026-07-06 20:38:44'),(3,3,'Iniciada','2026-07-06 20:38:54'),(4,5,'Iniciada','2026-07-06 20:39:46'),(5,2,'Iniciada','2026-07-06 20:39:51'),(6,6,'Iniciada','2026-07-06 20:40:10'),(7,1,'Finalizada','2026-07-06 20:41:48'),(8,2,'Finalizada','2026-07-06 20:41:53'),(9,3,'Finalizada','2026-07-06 20:41:55'),(10,6,'Finalizada','2026-07-06 20:42:33'),(11,5,'Finalizada','2026-07-06 20:42:48'),(12,9,'Iniciada','2026-07-06 20:45:44'),(13,7,'Iniciada','2026-07-06 20:45:44'),(14,8,'Iniciada','2026-07-06 20:45:46'),(15,7,'Finalizada','2026-07-06 20:54:54'),(16,8,'Finalizada','2026-07-06 20:54:55'),(17,9,'Finalizada','2026-07-06 20:54:55'),(18,4,'Finalizada','2026-07-06 20:55:45'),(19,10,'Iniciada','2026-07-06 20:56:42'),(20,10,'Finalizada','2026-07-07 20:27:25');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `dt_previsao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_tarefa`),
  KEY `fk_T_TAREFA_T_TOPICO1_idx` (`id_topico`),
  KEY `fk_T_TAREFA_T_USUARIO1_idx` (`id_usuario`),
  CONSTRAINT `fk_T_TAREFA_T_TOPICO1` FOREIGN KEY (`id_topico`) REFERENCES `t_topico` (`id_topico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_TAREFA_T_USUARIO1` FOREIGN KEY (`id_usuario`) REFERENCES `t_usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ck_tarefa_valor` CHECK (`vl_tarefa` >= 0)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tarefa`
--

LOCK TABLES `t_tarefa` WRITE;
/*!40000 ALTER TABLE `t_tarefa` DISABLE KEYS */;
INSERT INTO `t_tarefa` VALUES (1,1,4,'tarefa 1','',0.00,'2026-07-06',NULL),(2,1,4,'tarefa 2','',0.00,'2026-07-06',NULL),(3,1,4,'tarefa 3','',0.00,'2026-07-06',NULL),(4,1,8,'pesquisa 1','',0.00,'2026-07-06',NULL),(5,1,8,'pesquisa 2','',0.00,'2026-07-06',NULL),(6,1,8,'pesquisa 3','',0.00,'2026-07-06',NULL),(7,1,5,'trabalho 1','',0.00,'2026-07-06',NULL),(8,1,5,'trabalho 2','',0.00,'2026-07-06',NULL),(9,1,5,'trabalho 3','',0.00,'2026-07-06',NULL),(10,1,3,'Projeto Indicador','08/07',0.00,'2026-07-06',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `id_permissao` int(11) NOT NULL,
  `nm_usuario` varchar(80) NOT NULL,
  `dt_nascimento` date NOT NULL,
  `ds_email` varchar(80) NOT NULL,
  `ds_senha` varchar(50) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UN_USUARIO_EMAIL` (`ds_email`),
  KEY `fk_T_USUARIO_T_PERMISSAO1_idx` (`id_permissao`),
  CONSTRAINT `fk_T_USUARIO_T_PERMISSAO1` FOREIGN KEY (`id_permissao`) REFERENCES `t_permissao` (`id_permissao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_usuario`
--

LOCK TABLES `t_usuario` WRITE;
/*!40000 ALTER TABLE `t_usuario` DISABLE KEYS */;
INSERT INTO `t_usuario` VALUES (1,1,'Admin','1999-02-28','admin@gmail.com','123');
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

-- Dump completed on 2026-07-15 20:35:01
