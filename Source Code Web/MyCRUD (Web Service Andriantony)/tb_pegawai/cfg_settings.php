<?php
	include $_SERVER['DOCUMENT_ROOT'] . '/connection.php';

	//Set header
	function SetJsonHeader()
	{
		header('Content-Type: application/json');
	}

	//MySQL Queries
	const QUERY_ADD			= 'INSERT INTO tb_pegawai VALUES (DEFAULT, ?, ?, ?)';
	const QUERY_DELETE		= 'DELETE FROM tb_pegawai WHERE id = ?';
	const QUERY_GET			= 'SELECT tb_pegawai.id, tb_pegawai.nama, tb_posisi.posisi, tb_pegawai.gaji from tb_pegawai inner join tb_posisi where tb_pegawai.posisi = tb_posisi.id_posisi and id = ?';
	const QUERY_GETALL		= 'SELECT * FROM tb_pegawai';
	const QUERY_UPDATE		= 'UPDATE tb_pegawai SET nama = ?, posisi = ?, gaji = ? WHERE id =?';

	//General Keys
	const ID				= 'id';
	const NAME				= 'name';
	const POSITION			= 'position';
	const SALARY			= 'salary';

	//Database Keys
	const TBL_ID			= 'id';
	const TBL_NAME			= 'nama';
	const TBL_POSITION		= 'posisi';
	const TBL_SALARY		= 'gaji';
