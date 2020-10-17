<?php
	const HOST		= 'localhost';
	const USER		= 'webmaster';
	const PASSWORD	= 'g/177013';
	const DATABASE	= 'db_android';

	$connection = new mysqli(HOST, USER, PASSWORD, DATABASE);
	if ($connection->connect_errno)
		die('Connection error: ' . $connection->connect_error);
