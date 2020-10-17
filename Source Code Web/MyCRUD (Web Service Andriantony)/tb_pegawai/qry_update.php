<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_UPDATE);
	$p_statement->bind_param('sidi', $_POST[NAME], $_POST[POSITION], $_POST[SALARY], $_POST[ID]);
	if ($p_statement->execute())
		echo 'Berhasil update data pegawai';
	else
		echo 'Gagal update data pegawai';

	$connection->close();
