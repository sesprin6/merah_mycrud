<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_DELETE);
	$p_statement->bind_param('i', $_GET[ID]);

	if ($p_statement->execute())
		echo 'Berhasil menghapus posisi jabatan';
	else
		echo 'Gagal menghapus posisi jabatan';

	$connection->close();
