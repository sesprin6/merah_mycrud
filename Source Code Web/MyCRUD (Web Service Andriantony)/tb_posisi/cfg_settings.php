<?php
	include $_SERVER['DOCUMENT_ROOT'] . '/connection.php';

	//Set header
	function SetJsonHeader()
	{
		header('Content-Type: application/json');
	}

	//MySQL Queries
	const QUERY_ADD			= 'INSERT INTO tb_posisi VALUES (DEFAULT, ?, ?)';
	const QUERY_DELETE		= 'DELETE FROM tb_posisi WHERE id_posisi = ?';
	const QUERY_GET			= 'SELECT * FROM tb_posisi WHERE id_posisi = ?';
	const QUERY_GETALL		= 'SELECT * FROM tb_posisi';
	const QUERY_UPDATE		= 'UPDATE tb_posisi SET posisi = ?, gajih = ? WHERE id_posisi =?';

	//General Keys
	const ID				= 'id';
	const POSITION			= 'position';
	const SALARY			= 'salary';

	//Database Keys
	const TBL_ID			= 'id_posisi';
	const TBL_POSITION		= 'posisi';
	const TBL_SALARY		= 'gaji';
