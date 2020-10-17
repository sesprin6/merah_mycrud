<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_ADD);
	$p_statement->bind_param('sd', $_POST[POSITION], $_POST[SALARY]);
	if ($p_statement->execute())
		echo 'Berhasil menambahkan posisi jabatan';
	else
		echo 'Gagal menambahkan posisi jabatan';

	$connection->close();
