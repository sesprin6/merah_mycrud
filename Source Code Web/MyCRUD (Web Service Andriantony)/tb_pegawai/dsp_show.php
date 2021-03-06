<?php
	require_once 'cfg_settings.php';

	$p_statement = $connection->prepare(QUERY_GET);
	$p_statement->bind_param('i', $_GET[ID]);
	$p_statement->execute();

	$data = $p_statement->get_result()->fetch_all(MYSQLI_ASSOC)[0];

	$result = array();
	array_push($result, array
	(
		ID => $data[TBL_ID],
		NAME => $data[TBL_NAME],
		POSITION => $data[TBL_POSITION],
		SALARY => $data[TBL_SALARY]
	));

	SetJsonHeader();
	echo json_encode(array('result' => $result));
	$connection->close();
