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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_acao`
--

LOCK TABLES `t_acao` WRITE;
/*!40000 ALTER TABLE `t_acao` DISABLE KEYS */;
INSERT INTO `t_acao` VALUES (1,1,'Iniciada','2026-06-03 21:00:00'),(2,2,'Iniciada','2026-06-03 21:01:00'),(3,3,'Iniciada','2026-06-03 21:02:00'),(4,4,'Iniciada','2026-06-03 21:03:00'),(5,5,'Iniciada','2026-06-03 21:04:00'),(6,6,'Iniciada','2026-06-03 21:05:00'),(7,7,'Iniciada','2026-06-03 21:06:00'),(8,8,'Iniciada','2026-06-03 21:07:00'),(9,9,'Iniciada','2026-06-03 21:08:00'),(10,1,'Finalizada','2026-06-03 21:30:00'),(11,2,'Finalizada','2026-06-03 21:31:00'),(12,3,'Finalizada','2026-06-03 21:32:00'),(13,4,'Finalizada','2026-06-03 21:33:00'),(14,5,'Finalizada','2026-06-03 21:34:00'),(15,6,'Finalizada','2026-06-03 21:35:00'),(16,7,'Finalizada','2026-06-03 21:36:00'),(17,8,'Finalizada','2026-06-03 21:37:00'),(18,9,'Finalizada','2026-06-03 21:38:00');
/*!40000 ALTER TABLE `t_acao` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tarefa`
--

LOCK TABLES `t_tarefa` WRITE;
/*!40000 ALTER TABLE `t_tarefa` DISABLE KEYS */;
INSERT INTO `t_tarefa` VALUES (1,1,1,'Revisar Prova de Linguagem',NULL,NULL,'2026-06-02','2026-06-03'),(2,1,2,'Cortar Grama',NULL,NULL,'2026-06-02','2026-06-03'),(3,1,3,'Programar Tela Esqueceu a Senha','Projeto Integrador',NULL,'2026-06-02','2026-06-03'),(4,2,4,'Dormir',NULL,NULL,'2026-06-02','2026-06-03'),(5,2,5,'Vetorizar camisa dos Corujas','Rudolph',100.00,'2026-06-02','2026-06-03'),(6,2,6,'Comprar Escova','Item da Viagem',NULL,'2026-06-02','2026-06-03'),(7,3,7,'Ler Capitulo 2','Tecnologias de Redes de Comunicação e Computadores',NULL,'2026-06-02','2026-06-03'),(8,3,8,'O que é tabela intermediária','Modelagem de Dados',NULL,'2026-06-02','2026-06-03'),(9,3,9,'Comprar Casaco',NULL,NULL,'2026-06-02','2026-06-03');
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
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UN_USUARIO_EMAIL` (`ds_email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_usuario`
--

LOCK TABLES `t_usuario` WRITE;
/*!40000 ALTER TABLE `t_usuario` DISABLE KEYS */;
INSERT INTO `t_usuario` VALUES (1,'Kalyson','2000-08-17','4555854224@estudante.sed.sc.gov.br','1234'),(2,'Gabriel','1999-02-28','4544039745@estudante.sed.sc.gov.br','1234'),(3,'Jose','1967-10-18','4555854518@estudante.sed.sc.gov.br','1234');
/*!40000 ALTER TABLE `t_usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 17:49:19
