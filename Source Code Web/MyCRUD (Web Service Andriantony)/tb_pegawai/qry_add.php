<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_ADD);
	$p_statement->bind_param('ssd', $_POST[NAME], $_POST[POSITION], $_POST[SALARY]);
	if ($p_statement->execute())
		echo 'Berhasil menambahkan pegawai';
	else
		echo 'Gagal menambahkan pegawai';

	$connection->close();
